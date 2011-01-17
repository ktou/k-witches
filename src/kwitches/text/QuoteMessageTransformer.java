/**
 * 
 */
package kwitches.text;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 行中の引用文にあたる文字列を変換するクラス
 * @author voidy21
 *
 */
public class QuoteMessageTransformer implements LineMessageTransformInterface {

    private final static String REGEXP_RES_STRING = "^>.*$";
    
    /* (非 Javadoc)
     * @see kwitches.text.LineMessageTransformInterface#getRegexp()
     */
    public String getRegexp() {
        return REGEXP_RES_STRING;
    }

    /* (非 Javadoc)
     * @see kwitches.text.LineMessageTransformInterface#transform(java.lang.String)
     */
    public String transform(String rawString) {
        Pattern p = Pattern.compile(this.getRegexp());
        Matcher m = p.matcher(rawString);
        return m.replaceAll("<div class='quote'>$0</div>");
    }

}
