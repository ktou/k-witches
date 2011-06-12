package kwitches.message;

import java.text.MessageFormat;

public class BoothInMessageImpl implements MessageInterface {

    String userName;
    public Object getInformation() {
        return this.userName;
    }

    public String getMessage() {
        return MessageFormat.format(JSON_DATA_FORMAT, new Object[] {
            this.getType(), "\"" + this.getInformation() + "\""}).replace("<", "{").replace(">", "}");
    }

    public String getType() {
        return "booth_in";
    }

    public void setInformation(Object obj) {
        this.userName = (String) obj;
    }

}

