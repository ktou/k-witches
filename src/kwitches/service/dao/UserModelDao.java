/**
 *
 */
package kwitches.service.dao;

import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Transaction;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import kwitches.meta.UserModelMeta;
import kwitches.model.UserModel;

/**
 * UserModelのDAO(Singleton)
 * @author voidy21
 */
public class UserModelDao {
    private static final UserModelMeta meta =  UserModelMeta.get();
    private static UserModelDao instance = new UserModelDao();

    private static MemcacheService memcached = MemcacheServiceFactory.getMemcacheService();
    private static final String USER = "user_";

    private UserModelDao(){}

    public static UserModelDao GetInstance(){
             return instance;
    }

    public void appendUser(UserModel userModel) {
        Transaction tx = Datastore.beginTransaction();
        Datastore.put(userModel);
        memcached.delete(USER.concat(userModel.getUser().toString()));
        tx.commit();
    }

    public UserModel getUser(String keyString) {
        return Datastore.get(meta, Datastore.stringToKey(keyString));
    }

    public static UserModel getCurrentUser() {
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        if(user == null) return null;

        UserModel model = (UserModel) memcached.get(USER.concat(user.toString()));
        if(model == null){
            model = Datastore.query(meta).filter(meta.user.equal(user)).asSingle();
            memcached.put(USER.concat(user.toString()), model);
        }
        return model;
    }

    public UserModel getUserByName(String name){
        return Datastore.query(meta).filter(meta.name.equal(name)).limit(1).asSingle();
    }

    public UserModel getUserByTwitterId(String id){
        return Datastore.query(meta).filter(meta.twitterId.equal(id)).limit(1).asSingle();
    }
}
