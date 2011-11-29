package kwitches.controller.api;

import kwitches.message.MessageFactory;
import kwitches.message.MessageInterface;
import kwitches.service.MessageService;
import kwitches.service.dao.BBSDataModelDao;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

public class MaxidController extends Controller {

    private static MessageService ms = new MessageService();

    @Override
    public Navigation run() throws Exception {

        String maxId = String.valueOf(BBSDataModelDao.getMaxId());

        MessageInterface message =
            MessageFactory.create(MessageFactory.Type.MAX_ID);
        message.setInformation(maxId);
        ms.sendMessageAll(message);

        response.setContentType("text/html; charset=utf-8");
        response.getWriter().write(maxId);

        return null;
    }
}
