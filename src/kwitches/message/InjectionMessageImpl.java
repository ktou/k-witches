package kwitches.message;

public class InjectionMessageImpl implements MessageInterface {

    private String script;

    public String getType() {
        return "injection";
    }

    public String getMessage() {
        return "{\"type\":\"" + this.getType() + "\",\"content\":" + this.getInformation() + "}";
    }

    public Object getInformation() {
        return script;
    }

    public void setInformation(Object obj) {
        this.script = new StringBuilder("\"").append(obj).append("\"").toString();
    }
}
