package kwitches.controller.api;

import javax.servlet.http.HttpServletResponse;

import kwitches.message.MessageFactory;
import kwitches.message.MessageInterface;
import kwitches.model.UserModel;
import kwitches.service.MessageService;
import kwitches.service.dao.UserModelDao;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

public class InjectionController extends Controller {

    private static MessageService ms = new MessageService();

    @Override
    public Navigation run() throws Exception {
        UserModel userModel =  UserModelDao.getCurrentUser();
        if (userModel == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        } else if(request.getAttribute("data") == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } else {
            MessageInterface message =
                MessageFactory.create(MessageFactory.Type.INJECTION);
            message.setInformation(request.getAttribute("data"));
            ms.sendMessageAll(message);
        }

        return null;
    }
}
