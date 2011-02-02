package kwitches.controller;

import kwitches.model.UserModel;
import kwitches.service.MessageService;
import kwitches.service.dao.BBSDataModelDao;
import kwitches.service.dao.UserModelDao;

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
        }
        requestScope("maxId", BBSDataModelDao.getMaxId());
        return forward("index.jsp");
    }
}
