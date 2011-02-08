package kwitches.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slim3.controller.upload.FileItem;
import org.slim3.datastore.Datastore;
import org.slim3.util.ByteUtil;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Transaction;

import kwitches.meta.UploadedDataFragmentMeta;
import kwitches.meta.UploadedDataMeta;
import kwitches.model.UploadedData;
import kwitches.model.UploadedDataFragment;


public class UploadFileService {
    //ファイル分割サイズ(1Mくらい)
    private static final int FRAGMENT_SIZE = 900000;

    private UploadedDataMeta d = UploadedDataMeta.get();
    private UploadedDataFragmentMeta f = UploadedDataFragmentMeta.get();

    public List<UploadedData> getDataList() {
        return Datastore.query(d).asList();
    }

    public UploadedData upload(FileItem formFile) {
        //ファイルが空だったら、nullを返す
        if (formFile == null) {
            return null;
        }
        List<Object> models = new ArrayList<Object>();
        UploadedData data = new UploadedData();
        //datastoreに保存するデータの
        models.add(data);
        //各種Dataのパラメータ指定
        data.setKey(Datastore.allocateId(d));
        data.setFileName(formFile.getShortFileName());
        data.setLength(formFile.getData().length);
        //formFileをバイト化
        byte[] bytes = formFile.getData();
        //bytesをFragmentのサイズに分割
        byte[][] bytesArray = ByteUtil.split(bytes, FRAGMENT_SIZE);
        //byteArrayの長さ分のKeyを生成
        Iterator<Key> keys =
            Datastore
                .allocateIds(data.getKey(), f, bytesArray.length)
                .iterator();
        for (int i = 0; i < bytesArray.length; i++) {
            //FragmentData用のbyte[]
            byte[] fragmentData = bytesArray[i];
            //インスタンス生成しmodelsに格納
            UploadedDataFragment fragment = new UploadedDataFragment();
            models.add(fragment);
            //Fragmentに各種プロパティを設定
            fragment.setKey(keys.next());
            fragment.setBytes(fragmentData);
            fragment.setIndex(i);
            fragment.getUploadedDataRef().setModel(data);
        }
        //トランザクション開始
        Transaction tx = Datastore.beginTransaction();
        //Data,Fragmentを含むモデルオブジェクトの一覧をdatastoreに追加していく
        for (Object model : models) {
            Datastore.put(tx, model);
        }
        //トランザクション終了
        tx.commit();
        //dataのオブジェクトを返す
        return data;
    }

    
    //Dataの情報を取得
    public UploadedData getData(Key key, Long version) {
        return Datastore.get(d, key, version);
    }
    
    //取得したDataの情報からFragmentをかき集めて修復
    public byte[] getBytes(UploadedData uploadedData) {
        //Dataが空だった場合にNullPointExceptionを投げる
        if (uploadedData == null) {
            throw new NullPointerException(
                "The uploadedData parameter must not be null.");
        }
        //DataからFragmentの一覧を取得
        List<UploadedDataFragment> fragmentList =
            uploadedData.getFragmentListRef().getModelList();
        //各々のFragmentからbyteを取得してbyte[][]に格納
        byte[][] bytesArray = new byte[fragmentList.size()][0];
        for (int i = 0; i < fragmentList.size(); i++) {
            bytesArray[i] = fragmentList.get(i).getBytes();
        }
        //bite[][]をjoinしてリターン
        return ByteUtil.join(bytesArray);
    }
    
    //削除するDataのKeyを指定してDataとFragmentの両方を削除
    public void delete(Key key) {
        Transaction tx = Datastore.beginTransaction();
        //削除するエンティティのKey一覧
        List<Key> keys = new ArrayList<Key>();
        //削除するエンティティ一覧にDataとFragmentのKeyを追加
        keys.add(key);
        keys.addAll(Datastore.query(f, key).asKeyList());
        //トランザクション内で削除とトランザクション完了
        Datastore.delete(tx, keys);
    }

}
