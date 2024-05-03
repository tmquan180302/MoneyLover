package com.poly.moneylover.models.Response;

import com.poly.moneylover.models.CalendarResponse;
import com.poly.moneylover.models.Transaction;

import java.util.List;

public class ResSearchTransaction {

    private long expense;
    private long revenue;
    private long total;
    private List<Transaction> transactions;

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

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}
