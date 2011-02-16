package kwitches.service.dao;

import java.util.List;

import kwitches.meta.BBSDataModelMeta;
import kwitches.model.BBSDataModel;

import org.slim3.datastore.Datastore;
import org.slim3.datastore.ModelQuery;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Transaction;

/**
 * BBSDataModelのDAO(Singleton)
 * @author voidy21
 */
public class BBSDataModelDao {
    /** 引数を指定しない場合のデフォルト取得件数 */
    public static final int DEFAULT_LIMIT = 30;
    
    private static final BBSDataModelMeta meta =  BBSDataModelMeta.get();
    private static BBSDataModelDao instance = new BBSDataModelDao();

    private BBSDataModelDao(){}

    public static BBSDataModelDao GetInstance(){
             return instance;
    }

    public List<BBSDataModel> getBBSDataList() {
        return Datastore.query(meta)
                        .sort(meta.id.desc)
                        .limit(DEFAULT_LIMIT)
                        .asList();
    }

    public List<BBSDataModel> getBBSDataList(int offset, int limit) {
        return Datastore.query(meta)
                        .sort(meta.id.desc)
                        .offset(offset)
                        .limit(limit).asList();
    }

    public List<BBSDataModel> getBBSData(int resNumber) {
        return Datastore.query(meta)
                        .filter(meta.id.equal(resNumber))
                        .asList();
    }
    
    public void putBBSData(BBSDataModel bbsDataModel) {
        Transaction tx = Datastore.beginTransaction();
        Datastore.put(bbsDataModel);
        tx.commit();
    }
    
    public void deleteBBSData(int resNumber) {
        Key key = Datastore.query(meta)
                        .filter(meta.id.equal(resNumber))
                        .asSingle().getKey();
        Transaction tx = Datastore.beginTransaction();
        Datastore.delete(key);
        tx.commit();
    }
    
    public List<BBSDataModel> searchBBSData(List<String> token) {
        if (token == null) {
            return null;
        }
        ModelQuery<BBSDataModel> query = Datastore.query(meta);
        for (String t : token) {
            query = query.filterInMemory(meta.invertedIndex.contains(t)).limit(1000);
        }
        return query.sort(meta.id.desc).limit(1000).asList();
    }
    
    public static int getMaxId() {
        return Datastore.query(meta)
                        .max(meta.id);
     }
}
