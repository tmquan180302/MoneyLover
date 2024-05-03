package com.poly.moneylover.network;

import com.poly.moneylover.models.ReportModels.DetailReportDTO;
import com.poly.moneylover.models.ReportModels.ReportCategoryDTO;
import com.poly.moneylover.models.ReportModels.ReportCategoryInYearDTO;
import com.poly.moneylover.models.ReportModels.ReportInYearDTO;
import com.poly.moneylover.models.Response.Report;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ReportApi {
    ReportApi api = RetrofitClient.getInstance().create(ReportApi.class);
    @GET("budget/allTimeReport")
    Call<Report> getAllTimeReport();
    @GET("budget/{type}")
    Call<List<ReportCategoryDTO>> getAllReport(@Path("type") int type);
    @GET("budget/{startDay}/{endDay}/{id}/report")
    Call<DetailReportDTO> getReportDetailCategory(@Path("startDay") long startDay, @Path("endDay") long endDay,@Path("id") String id);

    @GET("budget/{startDay}/{endDay}/{type}/year")
    Call<ReportInYearDTO> getYearReport(@Path("startDay") long startDay, @Path("endDay") long endDay, @Path("type") int type);

    @GET("budget/{startDay}/{endDay}/{type}")
    Call<ReportCategoryInYearDTO> getReport(@Path("startDay") long startDay, @Path("endDay") long endDay, @Path("type") int type);
}
