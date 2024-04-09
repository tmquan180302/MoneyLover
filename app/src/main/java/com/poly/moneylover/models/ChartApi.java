package com.poly.moneylover.models;

public class ChartApi {
    public int month;
    public int total;
    public int year;

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public ChartApi() {
    }

    public ChartApi(int month, int total, int year) {
        this.month = month;
        this.total = total;
        this.year = year;
    }
}
