package com.meteoriteliu.fa.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by meteo on 2017/3/21.
 */
public class DateUtil {
    public static Date parseDate(String dateStr, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            return sdf.parse(dateStr);
        } catch (Exception e) {
            return null;
        }
    }

    public static Date parseDate(String dateStr) {
        return parseDate(dateStr, "yyyy-MM-dd");
    }

    public static boolean isSameDate(Date date, Date date1) {
        return getDateField(date, Calendar.YEAR) == getDateField(date1, Calendar.YEAR)
                && getDateField(date, Calendar.MONTH) == getDateField(date1, Calendar.MONTH)
                && getDateField(date, Calendar.DATE) == getDateField(date1, Calendar.DATE);
    }

    public static int getDateField(Date date, int field) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        return ca.get(field);
    }
}
