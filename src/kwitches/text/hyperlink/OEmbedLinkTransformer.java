/**
 *
 */
package kwitches.text.hyperlink;

import static kwitches.text.hyperlink.HyperlinkTransformUtil.*;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author thrakt
 *
 */
public class OEmbedLinkTransformer
    extends  HyperlinkTransformAbstract {

    private static final String ARTICLE_TYPE = "oembed";
    private static final String REGEXP_URL_STRING =
        "(^https?://((www\\.)|(mobile\\.)|)twitter.com/.*/status(es)?/.+)|" + // twitter
        "(^http://instagr((\\.am)|(am\\.com))/p/.+)|" + // instagram
        "(^http://((soundcloud\\.com)|(snd\\.sc))/.+)|" + // soundcloud
        "(^http://((tumblr\\.com/)|(.*\\.tumblr\\.com/post/)).+)|" + // tumblr
        "(^https?://(www\\.)?twitpic\\.com/.+)|" + // twitpic
        "(^http://.*yfrog\\.com/.+)|" + // yfrog
        "(^https?://(\\w+)\\.youtube\\.com/((watch)|(v/)|(view_play_list)|(playlist)).+)|" + // youtube1
        "(^http://youtu\\.be/.+)|" + // youtube2
        "(^https?://maps\\.google\\.((com)|(co\\.jp))/((maps)|(maps/ms)|)\\?.+)|" + // google maps
        "(^https?://.*\\.wikipedia\\.org/wiki/.+)|" + // slideshare
        "(^http://www\\.ustream\\.tv/.+)"; // ustream

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

        HashMap<String, String> properties = new HashMap<String, String>();
        properties.put("class", "new_oembed_thumb");
        properties.put("data-uri", url);

        return String.format("<a href='%1$s' target='_blank'>%1$s</a>%2$s<br /><br />%3$s",
            url,
            getSBMLinks(url),
            getDivHtml(properties)
        );
    }

}
