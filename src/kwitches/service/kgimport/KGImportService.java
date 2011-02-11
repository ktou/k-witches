package kwitches.service.kgimport;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions.Builder;
import com.google.appengine.api.urlfetch.HTTPHeader;
import com.google.appengine.api.urlfetch.HTTPMethod;
import com.google.appengine.api.urlfetch.HTTPRequest;
import com.google.appengine.api.urlfetch.URLFetchService;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;

import kwitches.model.UserModel;
import kwitches.service.SignService;
import kwitches.service.dao.UserModelDao;
import kwitches.service.kgimport.dao.KGFetchedIdDao;
import kwitches.util.TimeUtils;

public class KGImportService {

    private static final String BASIC_AUTH_VALUE =
        "Basic a3RvdW5rbzprdG91bmtvMjAxMA==";

    private static final String FETCHED_ID_KEY = "kgimport";

    private static final String IMPORT_USER_NAME = "KtouGold";

    private URLFetchService ufs = URLFetchServiceFactory.getURLFetchService();

    public void checkKG() throws IOException, ParseException {
            int maxid = fetchMaxId();
            int fetchedid =
                KGFetchedIdDao.getInstance().getFetchedId(FETCHED_ID_KEY);

            if (maxid > fetchedid) {
                String articleJson = fetchArticleJson(maxid - fetchedid);
                Map<String, String> article = parseArticleJson(articleJson);

                Map<String, Object> input = new HashMap<String, Object>();
                String ipAddress = "192.168.0.1";

                input.put("comment", article.get("message")
                    + "\nhttps://ktou.appspot.com/article/"
                    + article.get("id"));
                input.put("name",article.get("date") != null ? article.get("name") : "あぼーん");
                Date createdDate =
                    article.get("date") != null
                        ? new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(article.get("date"))
                            : TimeUtils.getJstDate();

                UserModel userModel = getUserModel();
                if (userModel == null) {
                    Logger.getLogger(this.getClass().getName()).info(
                        "Please append user : " + IMPORT_USER_NAME);
                    return;
                }

                new SignService().sign(
                    input,
                    ipAddress,
                    createdDate,
                    userModel,
                    null);

                KGFetchedIdDao.getInstance().setFetchedId(FETCHED_ID_KEY,++fetchedid);
                QueueFactory.getDefaultQueue().add(Builder.withUrl("/kgimport"));
            }

    }

    private int fetchMaxId() throws IOException {
        HTTPRequest request =
            new HTTPRequest(
                new URL("https://ktou.appspot.com/effect?callback=a"),
                HTTPMethod.GET);
        request.setHeader(new HTTPHeader("Authorization", BASIC_AUTH_VALUE));

        String json = new String(ufs.fetch(request).getContent(), "utf-8");
        json = json.substring(12);
        json = json.substring(0, json.indexOf('"'));

        return Integer.parseInt(json);
    }

    private String fetchArticleJson(int num) throws IOException {
        HTTPRequest request =
            new HTTPRequest(
                new URL("https://ktou.appspot.com/json?page=" + num + "&num=1&callback="),
                HTTPMethod.GET);
        request.setHeader(new HTTPHeader("Authorization", BASIC_AUTH_VALUE));

        String json = new String(ufs.fetch(request).getContent(), "utf-8");
        json = json.substring(14);
        json = json.substring(0, json.indexOf('}'));

        return json;
    }

    private Map<String, String> parseArticleJson(String articleJson)
            throws UnsupportedEncodingException {
        Map<String, String> result = new HashMap<String, String>();
        Matcher m = Pattern.compile("\"[^\"]*\"[^:]*:[^\"]*\"[^\"]*\"", Pattern.CASE_INSENSITIVE).matcher(articleJson);
        while(m.find()){
            String s = m.group();
            String key = s.substring(0, s.indexOf(":"));
            String value = s.substring(s.indexOf(":") + 1);
            result.put(washString(key), washString(value));
        }
        return result;
    }

    private String washString(String s) throws UnsupportedEncodingException {
        s = s.trim();
        return URLDecoder.decode(s
            .substring(1, s.length() - 1)
            .replaceAll("(%0D)|(%0A)", "")
            .replaceAll(" *<br> *", "%0A")
            .replaceAll("<[^>]*>", " ")
            .replaceAll("&gt;", ">"), "utf-8");
    }

    private UserModel getUserModel() {
        return UserModelDao.GetInstance().getUserByName(IMPORT_USER_NAME);
    }

}
