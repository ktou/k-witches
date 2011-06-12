package kwitches.message;

public class LiveMessageImpl implements MessageInterface {
    
    private String liveUserJson;
    
    public String getType() {
        return "live";
    }

    public String getMessage() {
        return "{\"type\":\"" + this.getType() + "\",\"content\":" + this.getInformation() + "}";
    }

    public Object getInformation() {
        return liveUserJson;
    }

    public void setInformation(Object obj) {
        this.liveUserJson = (String) obj;
    }
}
