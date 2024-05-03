package com.poly.moneylover.models;

import java.time.LocalDate;

public class CalendarApp {
    private LocalDate date;
    private long expense;
    private long revenue;

    public CalendarApp(LocalDate date, long expense, long revenue) {
        this.date = date;
        this.expense = expense;
        this.revenue = revenue;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
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
}
