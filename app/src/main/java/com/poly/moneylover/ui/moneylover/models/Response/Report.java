package com.poly.moneylover.ui.moneylover.models.Response;

public class Report {

    private double expense;
        private double revenue;
        private double total;
        private double balance;
        private double cumulation;

    public Report(double expense, double revenue, double total, double balance, double cumulation) {
        this.expense = expense;
        this.revenue = revenue;
        this.total = total;
        this.balance = balance;
        this.cumulation = cumulation;
    }

    public void setExpense(double expense) {
        this.expense = expense;
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setCumulation(double cumulation) {
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
