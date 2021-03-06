/**
 * 
 */
package kwitches.text.hyperlink;

import static kwitches.text.hyperlink.HyperlinkTransformUtil.getSBMLinks;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * リンクの種類が通常URLの場合の変換クラス
 * @author voidy21
 */ 
public class NormalLinkTransformer
    extends  HyperlinkTransformAbstract {

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
        if (!m.matches()) {
            return rawString;
        }
        String url = m.group(0);
        return String.format("<div class='new_link'><a class='link' href='%1$s' target='_blank'>%1$s</a>%2$s</div>", 
            url, getSBMLinks(url));
    }

}
