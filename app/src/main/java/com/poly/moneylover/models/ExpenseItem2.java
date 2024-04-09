package com.poly.moneylover.models;

import java.io.Serializable;

public class ExpenseItem2 implements Serializable {
    private String date;
    private ExpenseItem expenseItem;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ExpenseItem getExpenseItem() {
        return expenseItem;
    }

    public void setExpenseItem(ExpenseItem expenseItem) {
        this.expenseItem = expenseItem;
    }

    public ExpenseItem2(String date, ExpenseItem expenseItem) {
        this.date = date;
        this.expenseItem = expenseItem;
    }
}

