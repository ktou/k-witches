package kwitches.service;

import java.util.Map;

import org.slim3.controller.upload.FileItem;
import org.slim3.datastore.Datastore;
import org.slim3.util.BeanUtil;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.Transaction;

import kwitches.model.ImageModel;
import kwitches.model.UserModel;

public class UploadImageService {
    
    public ImageModel create(Map<String, Object> input, UserModel userModel) {
        Transaction tx;
        BeanUtil.copy(input, userModel);
        ImageModel newData = new ImageModel();
        FileItem fileImage = (FileItem)input.get("fileImage");
        String isIconReset = (String) input.get("icon_reset");
  
        if ( (isIconReset != null && isIconReset.equals("yes")) || (fileImage != null)) {
            if (isIconReset != null && isIconReset.equals("yes")) {
                userModel.getIconRef().setModel(null);
            } else if (fileImage != null) {
                newData.setFilename((String)fileImage.getShortFileName());
                newData.setFileContentType((String)fileImage.getContentType());
                byte[] imageData = fileImage.getData();
                newData.setFileImage(new Blob(imageData));

                userModel.getIconRef().setModel(newData);   
                tx = Datastore.beginTransaction();
                Datastore.put(newData);
                tx.commit();
            }
        }

        tx = Datastore.beginTransaction();
        Datastore.put(userModel);
        tx.commit();
        return newData;
    }

}
