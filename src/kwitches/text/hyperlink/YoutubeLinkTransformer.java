/**
 * 
 */
package kwitches.text.hyperlink;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static kwitches.text.hyperlink.HyperlinkTransformUtil.*;

/**
 * @author voidy21
 *
 */
public class YoutubeLinkTransformer
    extends  HyperlinkTransformAbstract {

    private static final String ARTICLE_TYPE = "youtube";
    private static final String REGEXP_URL_STRING = "^http://(\\w+).youtube.com/watch\\?v=(\\w+)";
    
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
        String videoId = m.group(2);
        
        String domStringFormat = "<div><iframe class='youtube-player' " +
            "type='text/html' width='640' height='385' " +
            "src='http://www.youtube.com/embed/%s' " +
            "frameborder='0'></iframe></div>";
        
        return String.format("%s<br>%s",
            String.format(domStringFormat, videoId),
            getSBMLinks(url)
        );
    }

}
