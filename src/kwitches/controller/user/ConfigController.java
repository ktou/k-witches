package kwitches.controller.user;

import kwitches.meta.UserModelMeta;
import kwitches.model.UserModel;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class ConfigController extends Controller {

    private static final UserModelMeta meta =  UserModelMeta.get();
    
    @Override
    public Navigation run() throws Exception {
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        UserModel userModel = Datastore.query(meta).filter(meta.user.equal(user)).asSingle();
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
        
        return forward("config.jsp");
    }
}
