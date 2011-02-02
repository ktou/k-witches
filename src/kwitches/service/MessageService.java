package kwitches.service;


import java.util.HashSet;
import java.util.Set;

import org.slim3.datastore.Datastore;

import kwitches.message.MessageInterface;
import kwitches.model.UserModel;
import kwitches.service.dao.UserModelDao;

import com.google.appengine.api.channel.ChannelMessage;
import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;
import com.google.appengine.api.memcache.Expiration;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory; 

public class MessageService {
    private static ChannelService channel = ChannelServiceFactory.getChannelService();
    private static MemcacheService memcached = MemcacheServiceFactory.getMemcacheService();
    public static final String PUSH_ALL_CLIENT = "all_clients";
   
    public static String getToken() {
        return getToken(UserModelDao.getCurrentUser());
    }
    
    public static String getToken(UserModel userModel) {
        String channelId = Datastore.keyToString(userModel.getKey());
        return channel.createChannel(channelId);
    }
    
    public void sendMessageAll(String message) {
        Set<String> clients = this.getClients();
        if (clients == null) {
            return;
        }
        for (String channelId: clients) {
            ChannelMessage cm = new ChannelMessage(channelId, message);
            channel.sendMessage(cm);
        }
    }
    
    public void sendMessageAll(MessageInterface message) {
        this.sendMessageAll(message.getMessage());
    }
    
    @SuppressWarnings("unchecked")
    public Set<String> getClients() {
        return (Set<String>) memcached.get(PUSH_ALL_CLIENT);
    }
    
    public void putClients(UserModel userModel) {
        Set<String> clients = this.getClients();
        if (clients == null) {
            clients = new HashSet<String>();
        }
        String clientKey = Datastore.keyToString(userModel.getKey());
        if (!clients.contains(clientKey)) {
            clients.add(clientKey);
        }
        memcached.put(PUSH_ALL_CLIENT, clients, Expiration.byDeltaSeconds(3600));
    }
}
