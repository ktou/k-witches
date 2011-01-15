/**
 * 
 */
package kwitches.text.hyperlink;

/**
 * @author voidy21
 * 
 */
public class NicovideoLinkTransformer extends HyperlinkTransformAbstract {

    /* (非 Javadoc)
     * @see kwitches.text.hyperlink.HyperlinkTransformInterface#getArticleType()
     */
    public String getArticleType() {
        return "nicovideo";
    }

    /* (非 Javadoc)
     * @see kwitches.text.LineMessageTransformInterface#getRegexp()
     */
    public String getRegexp() {
        return "";
    }

    /* (非 Javadoc)
     * @see kwitches.text.LineMessageTransformInterface#transform(java.lang.String)
     */
    public String transform(String rawString) {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

}
