/**
 *
 */
package kwitches.text.hyperlink;

import static kwitches.text.hyperlink.HyperlinkTransformUtil.*;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * リンクの種類が画像URLの場合の変換クラス
 * @author voidy21
 */
public class ImageLinkTransformer
    extends  HyperlinkTransformAbstract {

    private static final String ARTICLE_TYPE = "link";
    private static final String REGEXP_URL_STRING = ".*\\.(png|jpg|jpeg|gif|bmp)$";

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
        String url = rawString;
        return String
            .format(
                "<div class='new_link'><a class='link' href='%1$s' target='_blank'>%1$s</a>%2$s</div><a class='link_image' href='%1$s' target='_blank'><img src='%1$s' /></a>",
                url,
                getSBMLinks(url));
    }

}
