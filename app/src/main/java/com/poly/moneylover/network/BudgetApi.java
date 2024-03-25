package com.poly.moneylover.network;

import com.poly.moneylover.models.Budget;
import com.poly.moneylover.models.Category;
import com.poly.moneylover.models.Request.BudgetCreateRequest;
import com.poly.moneylover.models.Response.Report;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface BudgetApi {
    BudgetApi api = RetrofitClient.getInstance().create(BudgetApi.class);

    @GET("budget")
    Call<List<Budget>> getList();
    @POST("budget/create")
    Call<String> create(@Body BudgetCreateRequest request);
    @POST("budget/{id}/update")
    Call<String> update(@Path("id") String id, @Body BudgetCreateRequest request);
    @DELETE("budget/{id}")
    Call<String> delete(@Path("id") String id);

}
