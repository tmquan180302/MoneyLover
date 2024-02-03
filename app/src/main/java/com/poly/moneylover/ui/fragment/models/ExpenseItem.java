package com.poly.moneylover.ui.fragment.models;

public class ExpenseItem {
    private int imageResourceId;
    private String title;
    private String date;
    private String price;

    public ExpenseItem(int imageResourceId, String title, String date, String price) {
        this.imageResourceId = imageResourceId;
        this.title = title;
        this.date = date;
        this.price = price;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getPrice() {
        return price;
    }
}

