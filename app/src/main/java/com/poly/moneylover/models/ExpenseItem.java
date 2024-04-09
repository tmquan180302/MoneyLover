package com.poly.moneylover.models;

import java.io.Serializable;

public class ExpenseItem implements Serializable {
    private String id;
    private int imageResourceId;
    private String title;
    private String date;
    private String price;
    private long total;

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public ExpenseItem(String id, int imageResourceId, String title, String date, String price, String note) {
        this.id = id;
        this.imageResourceId = imageResourceId;
        this.title = title;
        this.date = date;
        this.price = price;
        this.note = note;
    }

    private String note;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public void setImageResourceId(int imageResourceId) {
        this.imageResourceId = imageResourceId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public ExpenseItem(String id, int imageResourceId, String title, String date, String price) {
        this.id = id;
        this.imageResourceId = imageResourceId;
        this.title = title;
        this.date = date;
        this.price = price;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public ExpenseItem(long total) {
        this.total = total;
    }
}

