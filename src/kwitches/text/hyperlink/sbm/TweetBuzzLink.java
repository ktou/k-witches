/**
 *
 */
package kwitches.text.hyperlink.sbm;

/**
 * @author thrakt
 *
 */
public class TweetBuzzLink extends SBMLinkAbstract {

    public static final String LINK_URL = "http://tweetbuzz.jp/redirect?url=";
    public static final String IMAGE_LINK_URL = "http://tools.tweetbuzz.jp/imgcount?url=";

    /* (非 Javadoc)
     * @see kwitches.text.hyperlink.sbm.SBMLinkAbstract#getImageLinkUrl()
     */
    @Override
    public String getImageLinkUrl() {
        return IMAGE_LINK_URL;
    }

    /* (非 Javadoc)
     * @see kwitches.text.hyperlink.sbm.SBMLinkAbstract#getLinkUrl()
     */
    @Override
    public String getLinkUrl() {
        return LINK_URL;
    }

}
