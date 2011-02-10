package kwitches.service.kgimport;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions.Builder;

import kwitches.model.UserModel;
import kwitches.service.SignService;
import kwitches.service.kgimport.dao.KGFetchedIdDao;
import kwitches.util.TimeUtils;


public class KGImportService {

    private static final String FETCHED_ID_KEY = "kgimport";

    public void checkKG(){
        int maxid = fetchMaxId();
        int fetchedid = KGFetchedIdDao.getInstance().getFetchedId(FETCHED_ID_KEY);

        if(maxid>fetchedid){
            String articleJson = fetchArticleJson(maxid-fetchedid-1);
            Map<String,String> article = parseArticleJson(articleJson);

            Map<String, Object> input = new HashMap<String, Object>();
            input.put("comment", article.get("message"));
            String ipAddress = "192.168.0.1";
            Date createdDate;
            try {
                createdDate = new SimpleDateFormat().parse(article.get("date"));
            } catch (ParseException e) {
                createdDate = TimeUtils.getJstDate();
            }
            UserModel userModel = getUserModel();
            new SignService().sign(input, ipAddress, createdDate, userModel, null);

            KGFetchedIdDao.getInstance().setFetchedId(FETCHED_ID_KEY,++fetchedid);
            QueueFactory.getDefaultQueue().add(Builder.withUrl("/kgimport"));
        }

    }

    private UserModel getUserModel() {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

    private Map<String, String> parseArticleJson(String articleJson) {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

    private String fetchArticleJson(int i) {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

    private int fetchMaxId() {
        // TODO 自動生成されたメソッド・スタブ
        return 0;
    }

}
