package com.poly.moneylover.adapter;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class YourResponseClass {
    @SerializedName("data")
    private List<Ban> data;

    // Các getters và setters

    public static class Ban {


        @SerializedName("giovao")
        private String giovao;



        // Các getters và setters
    }
}
