package util;

import model.User;

import java.util.*;

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

    public static Date minusMonths(int months) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("GMT"));
        cal.add(Calendar.MONTH, -months);
        return cal.getTime();
    }

    public static Date addADay(Date referenceDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("GMT"));
        cal.add(Calendar.DAY_OF_MONTH, +1);
        return cal.getTime();
    }

    public static void sortingCustomers(List<User> users) {
        Comparator<User> comparator = User::compare;
        users.sort(comparator);
    }
}