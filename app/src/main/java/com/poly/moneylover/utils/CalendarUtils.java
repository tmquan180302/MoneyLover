package com.poly.moneylover.utils;

import com.poly.moneylover.models.CalendarApp;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class CalendarUtils {
    public static LocalDate selectedDate;

    public static String startDayOfMonthInMileSecond(LocalDate time) {
        LocalDateTime startOfMonth = time.withDayOfMonth(1).atStartOfDay();
        ZoneId zoneId = ZoneId.of("Asia/Ho_Chi_Minh");
        long milliseconds = startOfMonth.atZone(zoneId).toInstant().toEpochMilli();
        return String.valueOf(milliseconds);
    }

    public static String endDayOfMonthInMileSecond(LocalDate time) {
        LocalDateTime endOfMonth = time.withDayOfMonth(time.lengthOfMonth()).atTime(23, 59, 59);

        ZoneId zoneId = ZoneId.of("Asia/Ho_Chi_Minh");

        long milliseconds = endOfMonth.atZone(zoneId).toInstant().toEpochMilli();

        return String.valueOf(milliseconds);
    }

    public static LocalDate millisecondToLocalDate(long millisecond) {
        Instant instant = Instant.ofEpochMilli(millisecond);
        ZoneId zoneId = ZoneId.of("Asia/Ho_Chi_Minh");
        LocalDate localDate = instant.atZone(zoneId).toLocalDate();
        return localDate;
    }


    public static String formattedDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        return date.format(formatter);
    }

    public static String formattedTime(LocalTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
        return time.format(formatter);
    }

    public static String formattedShortTime(LocalTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return time.format(formatter);
    }

    public static String monthYearFromDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");
        String monthYear = date.format(formatter);
        LocalDate startOfMonth = date.withDayOfMonth(1);
        LocalDate endOfMonth = date.withDayOfMonth(date.lengthOfMonth());
        return monthYear + " (" + startOfMonth.getDayOfMonth() + "/" + date.getMonth().getValue() + " - "
                + endOfMonth.getDayOfMonth() + "/" + date.getMonth().getValue() + ")";
    }

    public static String monthDayFromDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d");
        return date.format(formatter);
    }

    public static ArrayList<CalendarApp> daysInMonthArray() {
        ArrayList<CalendarApp> daysInMonthArray = new ArrayList<>();

        YearMonth yearMonth = YearMonth.from(selectedDate);
        int daysInMonth = yearMonth.lengthOfMonth();

        LocalDate prevMonth = selectedDate.minusMonths(1);
        LocalDate nextMonth = selectedDate.plusMonths(1);

        YearMonth prevYearMonth = YearMonth.from(prevMonth);
        int prevDaysInMonth = prevYearMonth.lengthOfMonth();

        LocalDate firstOfMonth = selectedDate.withDayOfMonth(1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();

        for (int i = 1; i <= 35; i++) {
            LocalDate currentDate;
            if (i <= dayOfWeek) {
                currentDate = LocalDate.of(prevMonth.getYear(), prevMonth.getMonth(), prevDaysInMonth + i - dayOfWeek);
            } else if (i > daysInMonth + dayOfWeek) {
                currentDate = LocalDate.of(nextMonth.getYear(), nextMonth.getMonth(), i - dayOfWeek - daysInMonth);
            } else {
                currentDate = LocalDate.of(selectedDate.getYear(), selectedDate.getMonth(), i - dayOfWeek);
            }

            // Thêm vào ArrayList với expense và revenue mặc định là 0
            daysInMonthArray.add(new CalendarApp(currentDate, 0, 0));
        }

        return daysInMonthArray;
    }

    public static ArrayList<LocalDate> daysInWeekArray(LocalDate selectedDate) {
        ArrayList<LocalDate> days = new ArrayList<>();
        LocalDate current = sundayForDate(selectedDate);
        LocalDate endDate = current.plusWeeks(1);

        while (current.isBefore(endDate)) {
            days.add(current);
            current = current.plusDays(1);
        }
        return days;
    }

    private static LocalDate sundayForDate(LocalDate current) {
        LocalDate oneWeekAgo = current.minusWeeks(1);

        while (current.isAfter(oneWeekAgo)) {
            if (current.getDayOfWeek() == DayOfWeek.SUNDAY)
                return current;

            current = current.minusDays(1);
        }

        return null;
    }


}
