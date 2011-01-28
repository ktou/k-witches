/**
 * 
 */
package kwitches.text.hyperlink.sbm;

/**
 * @author voidy21
 *
 */
public class TwibLink extends SBMLinkAbstract {

    public static final String LINK_URL = "http://twib.jp/url/";
    public static final String IMAGE_LINK_URL = "http://image.twib.jp/counter/";    
    
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
