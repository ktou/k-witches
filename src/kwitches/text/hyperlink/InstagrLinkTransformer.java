/**
 * 
 */
package kwitches.text.hyperlink;

import static kwitches.text.hyperlink.HyperlinkTransformUtil.getDivHtml;

import java.util.HashMap;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * リンクの種類が通常URLの場合の変換クラス
 * @author voidy21
 */ 
public class InstagrLinkTransformer
    extends  HyperlinkTransformAbstract {

    private static final String ARTICLE_TYPE = "instagr";
    private static final String REGEXP_URL_STRING = "http://instagr\\.am/p/.*";
    
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
    @SuppressWarnings("serial")
    public String transform(String rawString) {
        Pattern p = Pattern.compile(this.getRegexp(), Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(rawString);
        if (!m.matches()) {
            return rawString;
        }
        final String url = m.group(0);
        
        HashMap<String, String> thumbnailProperties = new HashMap<String, String>() {{
            put("class", "new_instagr_thumb");
            put("data-url", url);
        }};
        return String.format("<a href='%1$s' target='_blank'>%1$s</a><br>%2$s", 
            url, getDivHtml(thumbnailProperties));
    }

}
