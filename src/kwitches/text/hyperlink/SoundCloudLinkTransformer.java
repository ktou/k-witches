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
public class SoundCloudLinkTransformer
    extends HyperlinkTransformAbstract {

    private static final String ARTICLE_TYPE = "soundcloud";
    private static final String REGEXP_URL_STRING = "http://soundcloud.com/.*";

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

        HashMap<String, String> scdivProperties = new HashMap<String, String>() {{
            put("class", "new_sc_thumb");
        }};
        scdivProperties.put("url", url);

        return String.format("<a href='%1$s' target='_blank'>%1$s</a>%2$s<br>%3$s",
            url,
            getSBMLinks(url),
            getDivHtml(scdivProperties)
        );
    }

}
