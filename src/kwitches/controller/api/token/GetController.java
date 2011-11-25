package kwitches.controller.api.token;

import javax.servlet.http.HttpServletResponse;

import kwitches.model.UserModel;
import kwitches.service.MessageService;
import kwitches.service.dao.UserModelDao;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

public class GetController extends Controller {

    private static MessageService ms = new MessageService();

    @Override
    public Navigation run() throws Exception {
        UserModel userModel =  UserModelDao.getCurrentUser();
        if (userModel != null) {
            ms.putClients(userModel);
            response.setContentType("text/html; charset=utf-8");
            response.getWriter().write(MessageService.getToken(userModel));
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }

        return null;
    }
}
