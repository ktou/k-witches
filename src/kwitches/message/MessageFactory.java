package kwitches.message;

/**
 * @author voidy21
 */
public class MessageFactory {
    public enum Type {SIGN, BOOTH_IN, LIVE, MAX_ID};

    private MessageFactory() {}

    public static MessageInterface create(Type type) {
        MessageInterface mi = null;
        switch (type) {
            case SIGN : {
                mi = new SignMessageImpl();
                break;
            }
            case BOOTH_IN : {
                mi = new BoothInMessageImpl();
                break;
            }
            case LIVE : {
                mi = new LiveMessageImpl();
                break;
            }
            case MAX_ID : {
                mi = new MaxIdMessageImpl();
                break;
            }
        }
        return mi;
    }
}
