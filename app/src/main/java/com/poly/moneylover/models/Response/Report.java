package com.poly.moneylover.models.Response;

public class Report {
    private long revenue;
    private long expense;
    private long total;
    private long balance;
    private long cumulation;

    public long getRevenue() {
        return revenue;
    }

    public void setRevenue(long revenue) {
        this.revenue = revenue;
    }

    public long getExpense() {
        return expense;
    }

    public void setExpense(long expense) {
        this.expense = expense;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public long getCumulation() {
        return cumulation;
    }

    public void setCumulation(long cumulation) {
        this.cumulation = cumulation;
    }
}
