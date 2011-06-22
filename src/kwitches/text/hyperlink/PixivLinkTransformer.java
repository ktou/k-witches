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
public class PixivLinkTransformer
    extends HyperlinkTransformAbstract {

    private static final String ARTICLE_TYPE = "pixiv";
    private static final String REGEXP_URL_STRING = "^http://www.pixiv.net/member_illust.php.*illust_id=(\\d+).*";

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
        final String illustId = m.group(1);

        HashMap<String, String> pixivProperties = new HashMap<String, String>() {{
            put("class", "new_pixiv_thumb");
            put("data-pixivillustid", illustId);
        }};

        return String.format("<a href='%1$s' target='_blank'>%1$s</a><br>%2$s<br>%3$s",
            url,
            getDivHtml(pixivProperties),
            getSBMLinks(url)
        );
    }

}
