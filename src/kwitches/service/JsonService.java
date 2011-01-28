package kwitches.service;

import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.List;

import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Key;

import kwitches.meta.BBSDataModelMeta;
import kwitches.model.BBSDataModel;
import kwitches.model.UserModel;
import kwitches.text.TextTransformer;
import kwitches.util.TimeUtils;

public class JsonService {

    public static final int DEFAULT_LIMIT = 30;
    private static final String JSON_DATA_FORMAT =
        "<'name':'{0}','ip':'{1}','date':'{2}'," +
        "'comment':'{3}','id':'{4}','classtype':'{5}','icon':'{6}'>";

    private static final BBSDataModelMeta meta =  BBSDataModelMeta.get();

    public String getJson() throws Exception {
        StringBuilder sb = new StringBuilder();
        List<BBSDataModel> bbsDataList = this.getBBSDataList();

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

    public List<BBSDataModel> getBBSDataList() {
        return Datastore.query(meta)
                        .sort(meta.id.desc)
                        .limit(DEFAULT_LIMIT)
                        .asList();
    }

    public List<BBSDataModel> getBBSDataList(int offset,int limit) {
        return Datastore.query(meta)
                        .sort(meta.id.desc)
                        .offset(offset)
                        .limit(limit).asList();
    }

    static int getMaxId() {
        return Datastore.query(meta)
                        .max(meta.id);
     }
}
