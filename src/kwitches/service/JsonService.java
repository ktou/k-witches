package kwitches.service;

import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.List;

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
    
    private String getJson(List<BBSDataModel> bbsDataList) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"articles\":[");
        for (int i = 0, len = bbsDataList.size(); i < len; i++) {
            BBSDataModel bbsData = bbsDataList.get(i);
            String comment = bbsData.getComment();
            comment = TextTransformer.transform(comment);
            comment = comment != null ? URLEncoder.encode(comment, "UTF-8") : "";
            UserModel userModel = bbsData.getUserModelRef().getModel();
            String name = (userModel != null) ? userModel.getName() : "null";
            Key iconKey = (userModel != null) ? userModel.getIconRef().getKey() : null;
            String iconKeyString = (iconKey != null) ? Datastore.keyToString(iconKey) : "";
            sb.append(
                MessageFormat.format(
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
                )
            );
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
