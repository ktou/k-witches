/**
 * 
 */
package kwitches.text.hyperlink;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import kwitches.text.hyperlink.sbm.*;

/**
 * リンク変換用のクラスの共通機能を提供する
 * @author voidy21
 */
public class HyperlinkTransformUtil {

    @SuppressWarnings("serial")
    public static String getSBMLinks(String url) {
        List<SBMLinkAbstract> sbmLinks = 
            new ArrayList<SBMLinkAbstract>() {
            {
                add(new HatenaBookmarkLink());
                add(new LivedoorClipLink());
                add(new TwibLink());
            }
        };
        StringBuilder sb = new StringBuilder();
        for (SBMLinkAbstract sl : sbmLinks) {
            sb.append(sl.getDomString(url));
        }
        return String.format("<div class='sbm_links'>%s</div>", sb);
    }
    
    public static String getDivHtml(HashMap<String, String> properties) {
        StringBuilder sb = new StringBuilder();
        for (String attr : properties.keySet()) {
            if (sb.length() != 0) {
                sb.append(" ");
            }
            String value = properties.get(attr);
            sb.append(String.format("%s='%s'", attr, value));
        }
        return String.format("<div %s></div>", sb);   
    }

}
