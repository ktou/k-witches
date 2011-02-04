package kwitches.service;

import java.util.Date;
import java.util.Map;

import org.slim3.util.BeanUtil;

import kwitches.message.MessageFactory;
import kwitches.message.MessageInterface;
import kwitches.model.BBSDataModel;
import kwitches.model.UserModel;
import kwitches.service.dao.BBSDataModelDao;
import kwitches.text.tokenizer.AnalyzerInterface;
import kwitches.text.tokenizer.SimpleAnalyzer;

public class SignService {

    private static BBSDataModelDao bbsDao = BBSDataModelDao.GetInstance();
    private static MessageService ms = new MessageService();
    private static AnalyzerInterface analizer = new SimpleAnalyzer();
    
    public BBSDataModel sign(Map<String, Object> input, String ipAddress, Date createdDate, UserModel userModel) {
        String comment = (String) input.get("comment");
        BBSDataModel bbsDataModel = new BBSDataModel();
        bbsDataModel.getUserModelRef().setModel(userModel);
        input.put("id", BBSDataModelDao.getMaxId() + 1);
        input.put("createdDate", createdDate);
        input.put("ipAddress", ipAddress);
        input.put("invertedIndex", analizer.parse(comment));
        BeanUtil.copy(input, bbsDataModel);
        bbsDao.putBBSData(bbsDataModel);
        MessageInterface message = 
            MessageFactory.create(MessageFactory.Type.SIGN);
        message.setInformation(bbsDataModel);
        ms.sendMessageAll(message);
        return bbsDataModel;
     }

}
