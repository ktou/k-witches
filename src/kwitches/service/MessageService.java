package kwitches.service;

import com.google.appengine.api.channel.ChannelMessage;
import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;

public class MessageService {
    private static ChannelService cs = ChannelServiceFactory.getChannelService();
    
    public static final String channelId = "KWCHANNEL"; 
    
    public static String getToken() {
        return cs.createChannel(channelId);
    }
    
    public static void sendMessage(String message) {
        ChannelMessage cm = new ChannelMessage(channelId, message);
        cs.sendMessage(cm);
    }
}
