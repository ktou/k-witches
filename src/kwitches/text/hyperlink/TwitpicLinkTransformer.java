/**
 * 
 */
package kwitches.text.hyperlink;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author voidy21
 *
 */
public class TwitpicLinkTransformer 
    extends HyperlinkTransformAbstract {

    private static final String ARTICLE_TYPE = "twitpic";
    private static final String REGEXP_URL_STRING = "^http://twitpic\\.com/([^/]+)/?.*";
    
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
        final String pictureId = m.group(1);

        return String.format("<a href='%1$s' target='_blank'>%1$s<br><img src='http://twitpic.com/show/thumb/%2$s'></a>",
            url,
            pictureId
        );
    }

}
