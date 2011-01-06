package kwitches.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class TimeUtils {

    public static Date getJstDate() {
        return getJstDate(new Date());
    }
    
    public static Date getJstDate(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss z");
        sdf.setTimeZone(TimeZone.getTimeZone("JST"));
        Date jstDate;
        try {
            jstDate = sdf.parse(sdf.format(date));
        } catch (ParseException e) {
            jstDate = null;
        }
        return jstDate;
    }
    
    public static String getJstDateString(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("JST"));
        return sdf.format(date);
    }
}
