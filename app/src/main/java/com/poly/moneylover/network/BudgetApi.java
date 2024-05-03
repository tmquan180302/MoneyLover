package com.poly.moneylover.network;

import com.poly.moneylover.models.Budget;
import com.poly.moneylover.models.DataDetailsReportModelApi;
import com.poly.moneylover.models.DataReportModelApi;
import com.poly.moneylover.models.Request.BudgetCreateRequest;
import com.poly.moneylover.models.Request.SearchReq;
import com.poly.moneylover.models.Response.Export;
import com.poly.moneylover.models.Response.ResCalendar;
import com.poly.moneylover.models.Response.ResSearchTransaction;
import com.poly.moneylover.models.Transaction;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface BudgetApi {
    BudgetApi api = RetrofitClient.getInstance().create(BudgetApi.class);


    @GET("budget/{startDay}/{endDay}/{type}")
    Call<DataReportModelApi> getDataReport(@Path("startDay") String startDay, @Path("endDay") String endDay, @Path("type") int type);
    @GET("budget/{startDay}/{endDay}/{id}/report")
    Call<DataDetailsReportModelApi> getDataReportDetails( @Path("startDay") String startDay, @Path("endDay") String endDay, @Path("id") String id);
    @GET("user/exportPdf")
    Call<Export> getLinkPdf();
    @GET("user/export")
    Call<Export> getLinkCsv();
    @GET("budget/")
    Call<List<Budget>> getList();
    @POST("budget/create")
    Call<Void> create(@Body BudgetCreateRequest request);
    @POST("budget/{id}/update")
    Call<Void> update(@Path("id") String id, @Body BudgetCreateRequest request);
    @DELETE("budget/{id}")
    Call<String> delete(@Path("id") String id);

    @GET("user/{id}/find")
    Call<Budget> findBudget(@Path("id") String id);

    @POST("budget/find")
    Call<ResSearchTransaction> searchTransaction(@Body SearchReq key);


}
