/**
 * 
 */
package kwitches.message;

import java.util.Map;

/**
 * @author voidy21
 *
 */
public interface MapMessageInterface extends MessageInterface {
    void setMapInformation(Map<String, Object> information);
    Map<String, Object> getMapInformation();
}
