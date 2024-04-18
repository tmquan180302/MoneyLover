package com.poly.moneylover.network;

import com.poly.moneylover.models.Budget;
import com.poly.moneylover.models.Request.BudgetCreateRequest;
import com.poly.moneylover.models.Response.Export;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface BudgetApi {
    BudgetApi api = RetrofitClient.getInstance().create(BudgetApi.class);


    @GET("budget/exportPdf")
    Call<Export> getLinkPdf();
    @GET("budget/export")
    Call<Export> getLinkCsv();
    @GET("budget/")
    Call<List<Budget>> getList();
    @POST("budget/create")
    Call<Void> create(@Body BudgetCreateRequest request);
    @POST("budget/{id}/update")
    Call<Void> update(@Path("id") String id, @Body BudgetCreateRequest request);
    @DELETE("budget/{id}")
    Call<String> delete(@Path("id") String id);

    @GET("budget/{id}")
    Call<Budget> findBudget(@Path("id") String id);
}
