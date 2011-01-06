package kwitches.service;

import java.util.Date;
import java.util.Map;

import org.slim3.datastore.Datastore;
import org.slim3.util.BeanUtil;

import com.google.appengine.api.datastore.Transaction;

import kwitches.model.BBSDataModel;
import kwitches.model.UserModel;

public class SignService {

    public BBSDataModel sign(Map<String, Object> input, String ipAddress, Date createdDate, UserModel userModel) {
        BBSDataModel bbsDataModel = new BBSDataModel();
        bbsDataModel.getUserModelRef().setModel(userModel);
        input.put("id", JsonService.getMaxId() + 1);
        input.put("createdDate", createdDate);
        input.put("ipAddress", ipAddress);
        BeanUtil.copy(input, bbsDataModel);
        Transaction tx = Datastore.beginTransaction();
        Datastore.put(bbsDataModel);
        tx.commit();
        return bbsDataModel;
     }

}
