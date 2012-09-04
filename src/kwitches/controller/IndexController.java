package kwitches.controller;

import kwitches.message.MessageFactory;
import kwitches.message.MessageInterface;
import kwitches.model.UserModel;
import kwitches.service.MessageService;
import kwitches.service.dao.BBSDataModelDao;
import kwitches.service.dao.StaticValueDao;
import kwitches.service.dao.UserModelDao;
import kwitches.service.dao.StaticValueDao.StaticValueType;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

public class IndexController extends Controller {

    private static MessageService ms = new MessageService();

    @Override
    public Navigation run() throws Exception {
        UserModel userModel =  UserModelDao.getCurrentUser();
        if (userModel != null) {
            MessageInterface message =
                MessageFactory.create(MessageFactory.Type.BOOTH_IN);
            message.setInformation(userModel.getName());
            ms.sendMessageAll(message);
            requestScope("userName", userModel.getName());
            requestScope("userId", userModel.getUser().getNickname());

            requestScope("ustId", StaticValueDao.getValue(StaticValueType.USTREAM_CHANNEL_KEY));
        }
        requestScope("maxId", BBSDataModelDao.getMaxId());

        String page = "1";
        if (request.getAttribute("page") != null) {
            try {
                if (Integer.parseInt((String) request.getAttribute("page")) > 1) {
                    page = (String) request.getAttribute("page");
                }
            } catch (NumberFormatException n) {
                // noting to do when inputted illegal pagenumber
            }
        }
        requestScope("page", page);

        return forward("index.jsp");
    }
}
