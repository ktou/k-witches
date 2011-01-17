/**
 * 
 */
package kwitches.util;

import java.util.List;

/**
 * @author voidy21
 *
 */
public class StringUtils {
    
    public static String join(String[] array, String with) {
        StringBuffer buf = new StringBuffer();
        for (String s: array) {
            if (buf.length()>0) {
                buf.append(with);
            }
            buf.append(s);
         }
         return buf.toString();
    }
    
    public static String join(List<String> array, String with) {
        String[] strArray = array.toArray(new String[array.size()]);
        return join(strArray, with);
    }
}
