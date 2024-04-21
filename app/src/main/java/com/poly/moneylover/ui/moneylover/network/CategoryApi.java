package com.poly.moneylover.ui.moneylover.network;

import com.poly.moneylover.ui.moneylover.models.Category;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CategoryApi {

    CategoryApi api = RetrofitClient.getInstance().create(CategoryApi.class);

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
