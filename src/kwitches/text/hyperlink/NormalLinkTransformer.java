/**
 * 
 */
package kwitches.text.hyperlink;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * リンクの種類がニコニコ動画URLの場合の変換クラス
 * @author voidy21
 */ 
public class NormalLinkTransformer
    implements HyperlinkTransformInterface {

    private static final String ARTICLE_TYPE = "link";
    private static final String REGEXP_URL_STRING = "(https?):([^\\x00-\\x20()\"<>\\x7F-\\xFF])*";
    
    /* (非 Javadoc)
     * @see kwitches.text.hyperlink.HyperlinkTransformInterface#getArticleType()
     */
    public String getArticleType() {
        return ARTICLE_TYPE;
    }

    /* (非 Javadoc)
     * @see kwitches.text.LineMessageTransformInterface#getRegexp()
     */
    public String getRegexp() {
        return REGEXP_URL_STRING;
    }

    /* (非 Javadoc)
     * @see kwitches.text.LineMessageTransformInterface#transform(java.lang.String)
     */
    public String transform(String rawString) {
        Pattern p = Pattern.compile(this.getRegexp(), Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(rawString);   
        return m.replaceAll("<a class='link' href='$0'>$0</a>");
    }

}
