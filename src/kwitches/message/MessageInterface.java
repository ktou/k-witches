package kwitches.message;

/**
 * サーバからクライアントへのメッセージを送る際の形式統一インターフェース
 * @author voidy21
 */
public interface MessageInterface {
    String JSON_DATA_FORMAT = 
        "<\"type\":\"{0}\",\"content\":{1}>";
    String getType();
    String getMessage();
    Object getInformation() ;
    void setInformation(Object obj) ;
}
