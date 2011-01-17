/**
 * 
 */
package kwitches.text.hyperlink.sbm;

/**
 * @author voidy21
 *
 */
public class LivedoorClipLink extends SBMLinkAbstract {

    public static final String LINK_URL = "http://clip.livedoor.com/page/";
    public static final String IMAGE_LINK_URL = "http://image.clip.livedoor.com/counter/medium/";
    
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
