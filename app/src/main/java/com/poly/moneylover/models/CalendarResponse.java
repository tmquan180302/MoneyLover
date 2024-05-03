package com.poly.moneylover.models;

public class CalendarResponse {

    private int day;
    private int month;
    private int year;
    private long expense;
    private long revenue;

    public CalendarResponse(int day, int month, int year, long expense, long revenue) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.expense = expense;
        this.revenue = revenue;
    }

    public long getExpense() {
        return expense;
    }

    public void setExpense(long expense) {
        this.expense = expense;
    }

    public long getRevenue() {
        return revenue;
    }

    public void setRevenue(long revenue) {
        this.revenue = revenue;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
