package com.ucergy.ilocator.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public final class DateFormat {

    private static Date getDateDayMonthYearFormat(Date date) {
        // Display a date in day, month, year format
        java.text.DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String today = formatter.format(date);
        System.out.println("Today : " + today);
        return date;
    }

    public static Date getDateWithAllDetailt(Date date) {
        // Display a date in day, month, year format
        java.text.DateFormat formatter = new SimpleDateFormat("EEEE, dd MMMM yyyy, hh:mm:ss.SSS a");
        String today = formatter.format(date);
        System.out.println("Today : " + today);
        return date;
    }

}
