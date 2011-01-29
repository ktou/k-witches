package kwitches.service;

import java.util.Map;

import kwitches.model.UserModel;
import kwitches.service.dao.UserModelDao;

public class AuthService {
    
    private static UserModelDao userModelDao = UserModelDao.GetInstance();
    
    public void set(Map<String, Object> input) {
        String keyString = (String) input.get("user");
        boolean isAuthUser = input.get("is_auth_user") != null;
        UserModel userModel = userModelDao.getUser(keyString);
        userModel.setAuthUser(isAuthUser);
        userModelDao.appendUser(userModel);
    }
}
