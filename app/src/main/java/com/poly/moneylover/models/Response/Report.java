package com.poly.moneylover.models.Response;

public class Report {
    private double revenue;
    private double expense;
    private double total;
    private double balance;
    private double cumulation;

    public Report(double revenue, double expense, double total, int balance, double cumulation) {
        this.revenue = revenue;
        this.expense = expense;
        this.total = total;
        this.balance = balance;
        this.cumulation = cumulation;
    }

    public Report() {
    }

    public double getRevenue() {
        return revenue;
    }

    public double getExpense() {
        return expense;
    }

    public double getTotal() {
        return total;
    }

    public double getBalance() {
        return balance;
    }

    public double getCumulation() {
        return cumulation;
    }
}
