package kwitches.controller;

import kwitches.service.MessageService;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

public class IndexController extends Controller {

 
    
    @Override
    public Navigation run() throws Exception {
        String channelToken = MessageService.getToken();
        requestScope("channelToken", channelToken);
        return forward("index.jsp");
    }
}
