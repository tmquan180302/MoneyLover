package com.poly.moneylover.network;

import com.poly.moneylover.models.Response.Report;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ReportApi {
    ReportApi api = RetrofitClient.getInstance().create(ReportApi.class);
    @GET("/budget/getAllTimeReport")
    Call<Report> getAllTimeReport();
}
