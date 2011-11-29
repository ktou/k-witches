package kwitches.message;

public class MaxIdMessageImpl implements MessageInterface {

    private String maxIdJson;

    public String getType() {
        return "max_id";
    }

    public String getMessage() {
        return "{\"type\":\"" + this.getType() + "\",\"content\":" + this.getInformation() + "}";
    }

    public Object getInformation() {
        return maxIdJson;
    }

    public void setInformation(Object obj) {
        this.maxIdJson = (String) obj;
    }
}
