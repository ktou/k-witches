/**
 * 
 */
package kwitches.message;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kwitches.util.StringUtils;

/**
 * @author voidy21
 */
public abstract class MapMessageAbstract implements MapMessageInterface {
    
    private Map<String, Object> information;
    
    public Map<String, Object> getInformation() {
        return this.information;
    }
    
    public abstract String getType();

    /* (非 Javadoc)
     * @see kwitches.message.MessageInterface#getMessage()
     */
    public String getMessage() {
        List<String> kvList = new ArrayList<String>();
        Map<String, Object> information = this.getInformation();
        for (String key : information.keySet()) {
            String value = (String) information.get(key);
            String kv = MessageFormat.format("'{0}':'{1}'", 
                new Object[] {key, value});
            try {
                kvList.add(URLEncoder.encode(kv, "UTF-8"));
            } catch (UnsupportedEncodingException e) {}
        }
        return MessageFormat.format(JSON_DATA_FORMAT,
            this.getType(), StringUtils.join(kvList, ","))
            .replace("<", "{").replace(">", "}");
    }

    public void setInformation(Map<String, Object> information) {
        this.information = information;
    }

}
