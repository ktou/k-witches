/**
 * 
 */
package kwitches.text.hyperlink.sbm;

/**
 * @author voidy21
 *
 */
public class HatenaBookmarkLink extends SBMLinkAbstract {

    public static final String LINK_URL = "http://b.hatena.ne.jp/entry/";
    public static final String IMAGE_LINK_URL = "http://b.hatena.ne.jp/entry/image/";    
    
    /* (非 Javadoc)
     * @see kwitches.text.hyperlink.sbm.SBMLinkAbstract#getImageLinkUrl()
     */
    @Override
    public String getImageLinkUrl() {
        return LINK_URL;
    }

    /* (非 Javadoc)
     * @see kwitches.text.hyperlink.sbm.SBMLinkAbstract#getLinkUrl()
     */
    @Override
    public String getLinkUrl() {
        return IMAGE_LINK_URL;
    }

}
