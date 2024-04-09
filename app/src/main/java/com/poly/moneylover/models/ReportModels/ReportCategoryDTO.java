package com.poly.moneylover.models.ReportModels;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;

import java.io.Serializable;

public class ReportCategoryDTO implements Serializable {
    private String _id;
    private String name;
    private int type;
    @DrawableRes
    private int icon;
    @DrawableRes
    private int color;
    private int total;
    private Float percent;


    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Float getPercent() {
        return percent;
    }

    public void setPercent(Float percent) {
        this.percent = percent;
    }
}
