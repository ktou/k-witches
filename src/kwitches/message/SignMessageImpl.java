/**
 * 
 */
package kwitches.message;

import java.text.MessageFormat;

import kwitches.model.BBSDataModel;
import kwitches.service.JsonService;

/**
 * @author voidy21
 */
public class SignMessageImpl implements SignMessageInterface {

    BBSDataModel bbsDataModel;
    
    public String getType() {
        return "sign";
    }
    
    public String getMessage() {
        String kvData = "";
        try {
            kvData = JsonService.getSingleData(this.bbsDataModel);
        } catch (Exception e) {}
        return MessageFormat.format(JSON_DATA_FORMAT, new Object[] {
            this.getType(), kvData}).replace("<", "{").replace(">", "}");
    }

    public BBSDataModel getInformation() {
        return this.bbsDataModel;
    }

    public void setInformation(Object obj) {
        this.setInformation((BBSDataModel) obj);
    }

    public void setInformation(BBSDataModel bbsDataModel) {
        this.bbsDataModel = bbsDataModel;
    }    

}
