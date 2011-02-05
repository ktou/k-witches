package kwitches.controller;

import java.util.Date;

import kwitches.model.UserModel;
import kwitches.service.SignService;
import kwitches.service.dao.UserModelDao;
import kwitches.util.TimeUtils;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.controller.upload.FileItem;
import org.slim3.util.RequestMap;

public class SignController extends Controller {
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
        FileItem formFile = requestScope("file");
        service.sign(new RequestMap(request), ipAddress , date, userModel, formFile);
        return null;
    }
}
