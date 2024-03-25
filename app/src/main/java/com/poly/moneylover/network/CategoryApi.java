package com.poly.moneylover.network;

import com.poly.moneylover.models.Category;
import com.poly.moneylover.models.Request.ServerReqLogin;
import com.poly.moneylover.models.Response.ServerResToken;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CategoryApi {

    CategoryApi api = RetrofitClient.getInstance().create(CategoryApi.class);
    @GET("category/getAll")
    Call<List<Category>> getListCategory();
    @GET("category/expense")
    Call<List<Category>> getListExpense();

    @GET("category/revenue")
    Call<List<Category>> getListRevenue();

    @DELETE("category/{id}")
    Call<Boolean> delete(@Path("id") String categoryId);

    @POST("category/create")
    Call<String> create(@Body Category category);

    @POST("category/{id}/update")
    Call<String> update(@Path("id") String categoryId, @Body Category category);
}
