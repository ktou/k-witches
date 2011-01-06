package kwitches.controller;

import java.util.Date;

import kwitches.meta.UserModelMeta;
import kwitches.model.UserModel;
import kwitches.service.SignService;
import kwitches.util.TimeUtils;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.datastore.Datastore;
import org.slim3.util.RequestMap;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class SignController extends Controller {

    private SignService service = new SignService();
    
    private static final UserModelMeta meta =  UserModelMeta.get();
    
    @Override
    public Navigation run() throws Exception {
        String comment = (String) request.getAttribute("comment");
        if (comment == null || comment.equals("")) {
            return null;
        }
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        UserModel userModel = Datastore.query(meta).filter(meta.user.equal(user)).asSingle();
        String ipAddress = request.getRemoteAddr();
        Date date = TimeUtils.getJstDate();
        service.sign(new RequestMap(request), ipAddress , date, userModel);
        return null;
    }
}
