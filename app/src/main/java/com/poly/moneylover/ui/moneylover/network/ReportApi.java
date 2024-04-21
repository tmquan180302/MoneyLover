package com.poly.moneylover.ui.moneylover.network;

import com.poly.moneylover.ui.moneylover.models.Response.Report;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface ReportApi {
    ReportApi api = RetrofitClient.getInstance().create(ReportApi.class);
    @Headers("Content-Type: application/json")
    @GET("/budget/allTimeReport")
    Call<Report> getAllTimeReport();

}
