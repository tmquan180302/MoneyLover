package com.poly.moneylover.models;

import com.google.gson.annotations.SerializedName;

public class Balance {
    @SerializedName("_id")
    private String id;

    @SerializedName("userId")
    private String userId;

    @SerializedName("price")
    private int price;


    public Balance(String id, String userId, int price) {
        this.id = id;
        this.userId = userId;
        this.price = price;
    }

    public Balance() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Balance{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", price=" + price +
                '}';
    }
}
