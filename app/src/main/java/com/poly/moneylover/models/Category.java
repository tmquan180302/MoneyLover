package com.poly.moneylover.models;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Category implements Serializable {

    @SerializedName("_id")
    private String id;

    private String userId;

    private int type;

    private String name;

    @DrawableRes
    private int icon;

    @DrawableRes
    private int color;


    public Category(int type,int icon,int color) {
        this.type = type;
        this.icon = icon;
        this.color = color;
    }

    public Category(String name) {
        this.name = name;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", type=" + type +
                ", name='" + name + '\'' +
                ", icon=" + icon +
                ", color=" + color +
                '}';
    }
}
