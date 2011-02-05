package kwitches.service;

import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Key;

import kwitches.model.BBSDataModel;
import kwitches.model.UserModel;
import kwitches.service.dao.BBSDataModelDao;
import kwitches.text.TextTransformer;
import kwitches.util.TimeUtils;

public class JsonService {

    private static BBSDataModelDao bbsDao = BBSDataModelDao.GetInstance();
    
    private static final String JSON_DATA_FORMAT =
        "<'name':'{0}','ip':'{1}','date':'{2}'," +
        "'comment':'{3}','id':'{4}','classtype':'{5}','icon':'{6}'>";

    /**
     * RequestMapによる条件指定でBBSDataModelのJSONデータを取得する
     * 指定できる値は数字のみで、以下のパラメータを指定できる。
     * <ul>
     * <li>res_num</li>
     * <li>offset</li>
     * <li>limit</li>
     * <li>page</li>
     * </ul>
     * <br>
     * パラメータ{@code res_num}が設定されている場合、他のパラメータに関わらずレス番号が
     * {@code res_num}のBBSDataModelのみのJSONデータを返却する。
     * <br>
     * パラメータ{@code offset}は取得するオフセット位置を指定できる。デフォルト値は0。
     * <br>
     * パラメータ{@code limit}は取得する件数制限を指定できる。デフォルト値は{@link BBSDataModelDao#DEFAULT_LIMIT}。
     * <br>
     * パラメータ{@code page}は取得する位置を{@code limit}の値飛ばしで指定できる。デフォルト値は1。
     * @param input 条件指定するRequestMap
     * @return BBSDataModelのJSON形式データを返却する。数値以外が存在した場合は空文字列を返す。
     * @throws Exception
     */
    public String getJson(Map<String, Object> input) throws Exception {
        boolean hasResNumber = input.containsKey("res_num");
        boolean hasOffset = input.containsKey("offset");
        boolean hasLimit = input.containsKey("limit");
        boolean hasPage = input.containsKey("page");
        try {
            if (hasResNumber) {
                int resNumber = Integer.parseInt((String) input.get("res_num"));
                return this.getJson(resNumber);
            }
            int offset = hasOffset ? Integer.parseInt((String) input.get("offset")) : 0;
            int limit = hasLimit ? Integer.parseInt((String) input.get("limit")) 
                                          : BBSDataModelDao.DEFAULT_LIMIT;
            int page = hasPage ? Integer.parseInt((String) input.get("page")) : 1;
            offset += limit * (page - 1);
            return this.getJson(offset, limit);
        } catch (NumberFormatException e) {
            return "";
        }
    }
    
    public String getJson() throws Exception {
        List<BBSDataModel> bbsDataList = bbsDao.getBBSDataList();
        return this.getJson(bbsDataList);
    }
    
    public String getJson(int offset, int limit) throws Exception {
        List<BBSDataModel> bbsDataList = bbsDao.getBBSDataList(offset, limit);
        return this.getJson(bbsDataList);
    }
    
    public String getJson(int resNumber) throws Exception {
        List<BBSDataModel> bbsDataList = bbsDao.getBBSData(resNumber);
        return this.getJson(bbsDataList);
    }
    
    public static String getSingleData(BBSDataModel bbsData) throws Exception {
        String comment = bbsData.getBBSComment();
        comment = TextTransformer.transform(comment);
        comment = comment != null ? URLEncoder.encode(comment, "UTF-8") : "";
        UserModel userModel = bbsData.getUserModelRef().getModel();
        String name = (userModel != null) ? userModel.getName() : "null";
        Key iconKey = (userModel != null) ? userModel.getIconRef().getKey() : null;
        String iconKeyString = (iconKey != null) ? Datastore.keyToString(iconKey) : "";
       return MessageFormat.format(
            JSON_DATA_FORMAT.replace("'", "\""),
            new Object[] {
                name,
                bbsData.getIpAddress(),
                TimeUtils.getDateString(bbsData.getCreatedDate()),
                comment,
                bbsData.getId(),
                "text",
                iconKeyString
            }
        );
    }
    
    protected String getJson(List<BBSDataModel> bbsDataList) throws Exception {
        if (bbsDataList == null) {
            return "{\"articles\":[]}";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("{\"articles\":[");
        for (int i = 0, len = bbsDataList.size(); i < len; i++) {
            BBSDataModel bbsData = bbsDataList.get(i);
            sb.append(JsonService.getSingleData(bbsData));
            if (i != len - 1) {
                sb.append(",");
            }
        }
        sb.append("]}");
        String jsonString = sb.toString()
                              .replace("<", "{")
                              .replace(">", "}");
        return jsonString;
    }


}
