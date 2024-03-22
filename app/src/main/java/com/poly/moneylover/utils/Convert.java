package com.poly.moneylover.utils;


import android.annotation.SuppressLint;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Convert {
    public static String FormatNumber(long number) {
        String str = new DecimalFormat("###,###").format(number);
        return str.replace(".", ",");
    }

    public static String ConvertDayOfWeekString(int year, int month, int day) {
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

    public static String getDateConvert(Long time) {
        DateFormat formatter = new SimpleDateFormat("dd/MM");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        return formatter.format(calendar.getTime());
    }

    @SuppressLint("NewApi")
    public static Long getTimeStamp(String dateString) {
        LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        LocalDateTime dateTime = date.atStartOfDay();
        long timestamp = dateTime.toInstant(ZoneOffset.UTC).toEpochMilli();
        return timestamp;
    }

    public static String convertNumber(long number) {
        String formattedNumber = NumberFormat.getNumberInstance(Locale.US).format(number);
        return formattedNumber;
    }
}
