package com.poly.moneylover.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Budget implements Serializable {

    @SerializedName("_id")
    private String _id;
    private Category category;
    private Long dayStart;
    private String dayEnd;
    private String note;
    private int price;
    private String frequency;



    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
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

    public void setDayStart(Long dayStart) {
        this.dayStart = dayStart;
    }

    public String getDayEnd() {
        return dayEnd;
    }

    public void setDayEnd(String dayEnd) {
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
