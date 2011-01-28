/**
 * 
 */
package kwitches.text.hyperlink;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static kwitches.text.hyperlink.HyperlinkTransformUtil.*;

/**
 * @author voidy21
 *
 */
public class GistLinkTransformer
    extends  HyperlinkTransformAbstract {

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
    @SuppressWarnings("serial")
    public String transform(String rawString) {
        Pattern p = Pattern.compile(this.getRegexp(), Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(rawString);
        if (!m.matches()) {
            return rawString;
        }
        String url = m.group(0);
        final String gistId = m.group(1);
        
        HashMap<String, String> gistProperties = new HashMap<String, String>() {{
            put("class", "new_gist_preview");
            put("data-gistid", gistId);
        }};
        
        return String.format("<a href='%1$s'>%1$s</a><br>%2$s",
            url,
            getDivHtml(gistProperties)
        );
    }

}
