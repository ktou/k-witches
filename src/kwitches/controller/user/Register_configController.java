package kwitches.controller.user;

import kwitches.meta.UserModelMeta;
import kwitches.model.UserModel;
import kwitches.service.UploadImageService;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.datastore.Datastore;
import org.slim3.util.RequestMap;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class Register_configController extends Controller {

    private UploadImageService service = new UploadImageService();
    private static final UserModelMeta meta =  UserModelMeta.get();
    
    @Override
    public Navigation run() throws Exception {
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        UserModel userModel = Datastore.query(meta).filter(meta.user.equal(user)).asSingle();
        if (userModel == null) {
            userModel = new UserModel();
            userModel.setUser(user);
        }
        service.create(new RequestMap(request), userModel);
        return redirect("./config");
    }
}
