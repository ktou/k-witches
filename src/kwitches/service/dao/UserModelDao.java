/**
 * 
 */
package kwitches.service.dao;

import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Transaction;

import kwitches.meta.UserModelMeta;
import kwitches.model.UserModel;

/**
 * UserModelのDAO(Singleton)
 * @author voidy21
 */
public class UserModelDao {
    private static final UserModelMeta meta =  UserModelMeta.get();
    private static UserModelDao instance = new UserModelDao();

    private UserModelDao(){}

    public static UserModelDao GetInstance(){
             return instance;
    }
    
    public void appendUser(UserModel userModel) {
        Transaction tx = Datastore.beginTransaction();
        Datastore.put(userModel);
        tx.commit();
    }
    
    public UserModel getUser(String keyString) {
        return Datastore.get(meta, Datastore.stringToKey(keyString));
    }
}
