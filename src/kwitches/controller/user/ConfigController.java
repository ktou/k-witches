package kwitches.controller.user;

import kwitches.model.UserModel;
import kwitches.service.dao.UserModelDao;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Key;

public class ConfigController extends Controller {


    @Override
    public Navigation run() throws Exception {
        UserModel userModel =  UserModelDao.getCurrentUser();
        if (userModel == null) {
            userModel = new UserModel();
            userModel.setName("No_Name");
        }
        Key key = userModel.getIconRef().getKey();
        if (key != null) {
            String imageKey = Datastore.keyToString(key);
            request.setAttribute("imageKey",imageKey);
        }
        request.setAttribute("username",userModel.getName());
        request.setAttribute("amazonAccount",userModel.getAmazonAccount());
        request.setAttribute("twitterId",userModel.getTwitterId());

        return forward("config.jsp");
    }
}
