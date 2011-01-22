/**
 * 
 */
package kwitches.text.hyperlink;

import static kwitches.text.hyperlink.HyperlinkTransformUtil.*;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author voidy21
 *
 */
public class TwitterLinkTransformer
    extends  HyperlinkTransformAbstract {

    private static final String ARTICLE_TYPE = "twitter";
    private static final String REGEXP_URL_STRING = "^http://twitter.com/(\\w+)/status/(\\d+)/?";
    
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
        String userId = m.group(1);
        String statusNum = m.group(2);
        
        HashMap<String, String> properties = new HashMap<String, String>();
        properties.put("class", "twitter_thumbnail");
        properties.put("data-twitter_id", userId);
        properties.put("data-status_num", statusNum);
        
        
        return String.format("<a href='%1$s'>%1$s</a><br>%2$s",
            url,
            getDivHtml(properties)
        );
    }

}
