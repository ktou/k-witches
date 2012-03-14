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
public class AudioLinkTransformer
    extends HyperlinkTransformAbstract {

    private static final String ARTICLE_TYPE = "audio";
    private static final String REGEXP_URL_STRING = ".*\\.(wav|mp3|m4a|ogg)$";

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

        return String.format("</span> <a href='%1$s' target='_blank'>%1$s</a><br/><audio src='%1$s' controls></audio>",
            url
        );
    }

}
