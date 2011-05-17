package kwitches.controller.admin;

import java.util.List;

import kwitches.meta.UserModelMeta;
import kwitches.model.UserModel;
import kwitches.service.dao.StaticValueDao;
import kwitches.service.dao.StaticValueDao.StaticValueType;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.datastore.Datastore;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class StaticvalueController extends Controller {
    private static final UserModelMeta meta = UserModelMeta.get();

    @Override
    public Navigation run() throws Exception {
        String message = null;

        UserService userService = UserServiceFactory.getUserService();
        if (userService.isUserAdmin()) {
            List<UserModel> userModelList = Datastore.query(meta).asList();
            request.setAttribute("userModelList", userModelList);
            String type = ((String) request.getAttribute("type"));
            String value = (String) request.getAttribute("value");

            if (type == null == false) {
                type = type.toUpperCase();

                if (isStaticValueType(type)) {
                    StaticValueDao.setValue(StaticValueType.valueOf(type),value);
                    message = "Setting " + type + " to " + value;
                } else {
                    message = type + " is not support type";
                }
            }
        } else {
            message = "Setting static value need admin authority";
        }

        requestScope("message", message);
        return forward("staticvalue.jsp");
    }

    private boolean isStaticValueType(String type) {
        boolean result = false;

        if (type == null == false) {
            for (StaticValueType s : StaticValueType.values()) {
                if (s.toString().equals(type))
                    result = true;
            }
        }

        return result;

    }
}
