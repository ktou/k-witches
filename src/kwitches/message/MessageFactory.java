package kwitches.message;

/**
 * @author voidy21
 */
public class MessageFactory {
    public enum Type {SIGN, BOOTH_IN};
    
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
        }
        return mi;
    }
}
