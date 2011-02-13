package kwitches.controller;

import java.util.Date;

import kwitches.model.UserModel;
import kwitches.service.SignService;
import kwitches.service.dao.UserModelDao;
import kwitches.util.TimeUtils;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.util.RequestMap;

public class SignmobileController extends Controller {
    private SignService service = new SignService();
    
    @Override
    public Navigation run() throws Exception {
        String comment = (String) request.getAttribute("comment");
        if (comment == null || comment.equals("")) {
            return null;
        }
        UserModel userModel =  UserModelDao.getCurrentUser();
        String ipAddress = request.getRemoteAddr();
        Date date = TimeUtils.getJstDate();
        service.sign(new RequestMap(request), ipAddress , date, userModel, null);
        return forward("/mobile");
    }
}
