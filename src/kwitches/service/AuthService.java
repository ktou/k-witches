package kwitches.service;

import java.util.Map;

import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Transaction;

import kwitches.meta.UserModelMeta;
import kwitches.model.UserModel;


public class AuthService {
    
    private static final UserModelMeta meta =  UserModelMeta.get();
    
    public void set(Map<String, Object> input) {
        String keyString = (String) input.get("user");
        boolean isAuthUser = input.get("is_auth_user") != null;
        UserModel userModel = Datastore.get(meta, Datastore.stringToKey(keyString));
        userModel.setAuthUser(isAuthUser);
        Transaction tx = Datastore.beginTransaction();
        Datastore.put(userModel);
        tx.commit();
    }
}
