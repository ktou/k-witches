/**
 * 
 */
package kwitches.text;

/**
 * 行中の引用文にあたる文字列を変換するクラス
 * @author voidy21
 *
 */
public class QuoteMessageTransformer implements LineMessageTransformInterface {

    /* (非 Javadoc)
     * @see kwitches.text.LineMessageTransformInterface#getRegexp()
     */
    public String getRegexp() {
        return "^>";
    }

    /* (非 Javadoc)
     * @see kwitches.text.LineMessageTransformInterface#transform(java.lang.String)
     */
    public String transform(String rawString) {
        return "<div class='quote'>" + rawString + "</div>";
    }

}
