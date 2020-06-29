package util;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Utility {

    public static String getTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return "" + calendar.get(Calendar.HOUR_OF_DAY) + ":" +
                (calendar.get(Calendar.MINUTE)) + ":" + calendar.get(Calendar.SECOND);
    }

    public static String getDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return "" + calendar.get(Calendar.DATE) + "/" +
                (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.YEAR);
    }

    public static Date minusAMonth() {
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("GMT"));
        cal.add(Calendar.MONTH, -1);
        return cal.getTime();
    }

    public static Date addADay(Date referenceDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("GMT"));
        cal.add(Calendar.DAY_OF_MONTH, +1);
        return cal.getTime();
    }
}