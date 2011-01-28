package kwitches.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtils {

    private static SimpleDateFormat sdf = new SimpleDateFormat(
        "yyyy/MM/dd HH:mm:ss");

    /**
     * 現在時刻をUTC+9(JST)で取得
     *
     * @return
     */
    public static Date getJstDate() {
        return getJstDate(new Date());
    }

    /**
     * 指定したUTC+0の時刻をUTC+9(JST)に変換して取得
     *
     * @param date
     *            変換する時刻(UTC+0)
     * @return
     */
    public static Date getJstDate(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, 9);
        return calendar.getTime();
    }

    /**
     * 現在時刻をUTC+9(JST)の文字列で取得
     *
     * @return
     */
    public static String getJstDateString() {
        return getJstDateString(new Date());
    }

    /**
     * 指定したUTC+0の時刻をUTC+9(JST)の文字列に変換して取得
     *
     * @param date
     *            変換する時刻(UTC+0)
     * @return
     */
    public static String getJstDateString(Date date) {
        return getDateString(getJstDate(date));
    }

    /**
     * 指定した時刻を文字列に変換して取得
     *
     * @param date
     *            変換する時刻
     * @return
     */
    public static String getDateString(Date date) {
        if (date == null) {
            return null;
        }
        return sdf.format(date);
    }
}
