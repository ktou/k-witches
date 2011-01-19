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
public class GistLinkTransformer
    implements HyperlinkTransformInterface {

    private static final String ARTICLE_TYPE = "gist";
    private static final String REGEXP_URL_STRING = "^https?://gist.github.com/(\\d*)/?";
    
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
        String gistId = m.group(1);
        
        return String.format("%s<br>%s",
            url,
            String.format("<script src='https://gist.github.com/%s.js'></script>",
                gistId
            )
        );
    }

}
