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
public class AmazonLinkTransformer 
    extends HyperlinkTransformAbstract {

    private static final String ARTICLE_TYPE = "amazon";
    private static final String REGEXP_URL_STRING = "^http://www\\.amazon\\.[\\w.]+/.*(?:ASIN|product-description|product|dp)/([^/]+)(?:/.*)*$";

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
        String asin = m.group(1);
        
        String amazonAffiliateId = "thrakt-22"; 
        if (this.getAccountTable() != null) {
            String aid = this.getAccountTable().get("amazonAffiliateId");
            if (aid != null) {
                amazonAffiliateId = aid;
            }
        }
        String formatString = "<a href='%1$s'>%1$s</a><br><iframe src='http://rcm-jp.amazon.co.jp/e/cm" +
            "?t=%2$s&o=9&p=8&l=as1&asins=%3$s&IS2=1&fc1=000000" +
            "&lt1=_blank&lc1=0000FF&bc1=FFFFFF&bg1=FFFFFF&f=ifr'" +
            " style='width:120px;height:240px;' scrolling='no'" +
            " marginwidth='0' marginheight='0' frameborder='0'></iframe><br>";
        
        return String.format(formatString, url, amazonAffiliateId, asin);
    }

}
