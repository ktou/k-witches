package kwitches.util;

import org.slim3.util.ByteUtil;
import org.slim3.util.RequestLocator;

public class ViewUtils {
    public static Object get(String attributeKey, ClassLoader clazzLoader) {
        Object o = RequestLocator.get().getAttribute(attributeKey);
        byte[] bytes = ByteUtil.toByteArray(o);
        return ByteUtil.toObject(bytes, clazzLoader);
    }
}
