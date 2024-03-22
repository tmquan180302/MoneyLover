package com.poly.moneylover.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Transaction  implements Serializable {

    @SerializedName("_id")
    private String transactionId;
    private String userId;
    private Category category;
    private Long day;
    private String note;
    private Long price;


    public Transaction(Category category, Long day, String note, Long price) {
        this.category = category;
        this.day = day;
        this.note = note;
        this.price = price;
    }

    public Transaction() {
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }


    public Long getDay() {
        return day;
    }

    public void setDay(Long day) {
        this.day = day;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId='" + transactionId + '\'' +
                ", userId='" + userId + '\'' +
                ", category=" + category +
                ", day=" + day +
                ", note='" + note + '\'' +
                ", price=" + price +
                '}';
    }

    public String convertDayToDateString() {
        // Định dạng ngày tháng năm
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        // Chuyển đổi millis sang ngày tháng năm
        return sdf.format(new Date(day));
    }
}
