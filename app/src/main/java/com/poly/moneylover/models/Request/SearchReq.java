package com.poly.moneylover.models.Request;

public class SearchReq {
    private String key;

    public SearchReq(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
