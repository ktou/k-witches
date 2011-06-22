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
            ms.putClients(userModel);
            String channelToken = MessageService.getToken(userModel);
            requestScope("channelToken", channelToken);

            MessageInterface message =
                MessageFactory.create(MessageFactory.Type.BOOTH_IN);
            message.setInformation(userModel.getName());
            ms.sendMessageAll(message);
            requestScope("userName", userModel.getName());
            requestScope("userId", userModel.getUser().getNickname());

            requestScope("ustId", StaticValueDao.getValue(StaticValueType.USTREAM_CHANNEL_KEY));
        }
        requestScope("maxId", BBSDataModelDao.getMaxId());
        return forward("index.jsp");
    }
}
