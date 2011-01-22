/**
 * 
 */
package kwitches.text.hyperlink;

import static kwitches.text.hyperlink.HyperlinkTransformUtil.getDivHtml;
import static kwitches.text.hyperlink.HyperlinkTransformUtil.getSBMLinks;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author voidy21
 *
 */
public class TumblrLinkTransformer 
    extends HyperlinkTransformAbstract {

    private static final String ARTICLE_TYPE = "tumblr";
    private static final String REGEXP_URL_STRING = "^http://(.+)\\.tumblr.com/post/(\\d+).*";
    
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
        String url = m.group(0);
        final String tumblrId = m.group(1);
        final String postId = m.group(2);
        
        HashMap<String, String> tumblrProperties = new HashMap<String, String>() {{
            put("class", "new_tumblr_thumb");
            put("data-tumblrid", tumblrId);
            put("data-postid", postId);
        }};

        return String.format("<a href='%1$s'>%1$s</a><br>%2$s<br>%3$s",
            url,
            getDivHtml(tumblrProperties),
            getSBMLinks(url)
        );
    }

}
