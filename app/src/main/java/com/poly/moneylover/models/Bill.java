package com.poly.moneylover.models;

import com.google.gson.annotations.SerializedName;

public class Bill {

    @SerializedName("_id")
    private String id;

    private String userId;

    private Service service;

    private long dayStart;

    public Bill(Service service, long dayStart) {
        this.service = service;
        this.dayStart = dayStart;
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

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public long getDayStart() {
        return dayStart;
    }

    public void setDayStart(long dayStart) {
        this.dayStart = dayStart;
    }
}
