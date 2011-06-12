package kwitches.controller.api;

import kwitches.message.MessageFactory;
import kwitches.message.MessageInterface;
import kwitches.service.MessageService;
import kwitches.service.api.LiveCheckService;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.util.RequestMap;

public class LiveController extends Controller {

    private LiveCheckService service = new LiveCheckService();
    private MessageService ms = new MessageService();
    
    @Override
    public Navigation run() throws Exception {
        String liveUserJson = this.service.generateLiveUserJson(new RequestMap(request));
        
        MessageInterface message = 
            MessageFactory.create(MessageFactory.Type.LIVE);
        message.setInformation(liveUserJson);
        ms.sendMessageAll(message);
        return null;
    }
}
