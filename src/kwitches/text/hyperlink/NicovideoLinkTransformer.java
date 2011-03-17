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
 * リンクの種類がニコニコ動画URLの場合の変換クラス
 * @author voidy21
 */ 
public class NicovideoLinkTransformer
    extends  HyperlinkTransformAbstract {

    private static final String ARTICLE_TYPE = "nicovideo";
    private static final String REGEXP_URL_STRING = "^http://www.nicovideo.jp/watch/([1-9A-Za-z_]\\w*)/?";
    
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
        final String videoId = m.group(1);
        
        HashMap<String, String> thumbnailProperties = new HashMap<String, String>() {{
            put("class", "new_nico_thumb");
            put("data-nicovideo", videoId);
        }};

        HashMap<String, String> tagProperties = new HashMap<String, String>() {{
            put("class", "new_nico_tags");
            put("data-nicovideo", videoId);
        }};
        
        return String.format("%s<br>%s<br>%s",
            getDivHtml(thumbnailProperties),
            getSBMLinks(url),
            getDivHtml(tagProperties)
        );
    }

}
