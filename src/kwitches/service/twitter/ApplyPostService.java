package kwitches.service.twitter;

import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import kwitches.model.UserModel;
import kwitches.service.SignService;
import kwitches.service.dao.UserModelDao;
import kwitches.util.TimeUtils;

import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.TwitterException;

public class ApplyPostService {

    private static final String IMPORT_USER_NAME = "ktoubot";

    public void checkTwitterMentions() throws TwitterException,
            UnsupportedEncodingException {
        ResponseList<Status> mentions =
            new TwitterAccessService().getMentionsAtCounter();
        Collections.reverse(mentions);

        for (Status s : mentions) {
            Map<String, Object> input = new HashMap<String, Object>();
            String ipAddress = "192.168.0.1";

            String name = s.getUser().getScreenName();
            String text = s.getText();
            if (text.startsWith("@ktoubot "))
                text = text.substring(9);
            text = text.replace("&gt;", ">").replace("&lt;", "<");

            UserModel userModel = getUserModelById(name);
            if (userModel == null) {
                userModel = getUserModelByName(IMPORT_USER_NAME);
                if (userModel == null) {
                    Logger.getLogger(this.getClass().getName()).info(
                        "Please append user : " + IMPORT_USER_NAME);
                    return;
                }
            }

            input.put("name", userModel.getName());
            input.put("comment", text);
            input.put("location", "twitter");

            new SignService().sign(
                input,
                ipAddress,
                TimeUtils.getJstDate(),
                userModel,
                null);
        }
    }

    private UserModel getUserModelById(String id) {
        return UserModelDao.GetInstance().getUserByTwitterId(id);
    }

    private UserModel getUserModelByName(String name) {
        return UserModelDao.GetInstance().getUserByName(name);
    }

}
