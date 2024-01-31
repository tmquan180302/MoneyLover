package com.poly.moneylover.utils;


import java.text.DecimalFormat;
import java.util.Calendar;

public class Convert {
    public static String FormatNumber(long number) {
        String str = new DecimalFormat("###,###").format(number);
        return str.replace(".",",");
    }

    public static String ConvertDayOfWeekString(int year,int month,int day){
        Calendar selectedCalendar = Calendar.getInstance();
        selectedCalendar.set(Calendar.YEAR, year);
        selectedCalendar.set(Calendar.MONTH, month);
        selectedCalendar.set(Calendar.DAY_OF_MONTH, day);

        int dayOfWeek = selectedCalendar.get(Calendar.DAY_OF_WEEK);
       return getDayOfWeekString(dayOfWeek);
    }


    private static String getDayOfWeekString(int dayOfWeek) {
        switch (dayOfWeek) {
            case Calendar.SUNDAY:
                return "(CN)";
            case Calendar.MONDAY:
                return "(Th 2)";
            case Calendar.TUESDAY:
                return "(Th 3)";
            case Calendar.WEDNESDAY:
                return "(Th 4)";
            case Calendar.THURSDAY:
                return "(Th 5)";
            case Calendar.FRIDAY:
                return "(Th 6)";
            case Calendar.SATURDAY:
                return "(Th 7)";
            default:
                return "";
        }
    }
}
