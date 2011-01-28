package kwitches.controller.admin;

import java.util.List;

import kwitches.meta.UserModelMeta;
import kwitches.model.UserModel;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.datastore.Datastore;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class AuthlistController extends Controller {

    private static final UserModelMeta meta =  UserModelMeta.get();
    
    @Override
    public Navigation run() throws Exception {
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        UserModel userModel = Datastore.query(meta).filter(meta.user.equal(user)).asSingle();
        if (userService.isUserAdmin() || userModel.isAuthUser()) {
            List<UserModel> userModelList = Datastore.query(meta).asList();
            request.setAttribute("userModelList", userModelList);
            return forward("authlist.jsp");
        }
       return null;
    }
}
