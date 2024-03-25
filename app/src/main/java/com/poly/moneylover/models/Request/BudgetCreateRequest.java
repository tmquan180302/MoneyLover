package com.poly.moneylover.models.Request;

import com.poly.moneylover.models.Category;

public class BudgetCreateRequest {
    private Category category;
    private long dayStart ;
    private long dayEnd;
    private String note;
    private int price;
    private String frequency;

    public BudgetCreateRequest(Category category,  long dayStart, long dayEnd, String note, int price, String frequency) {
        this.category = category;
        this.dayStart = dayStart;
        this.dayEnd = dayEnd;
        this.note = note;
        this.price = price;
        this.frequency = frequency;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public long getDayStart() {
        return dayStart;
    }

    public void setDayStart(Integer dayStart) {
        this.dayStart = dayStart;
    }

    public long getDayEnd() {
        return dayEnd;
    }

    public void setDayEnd(Integer dayEnd) {
        this.dayEnd = dayEnd;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }
}