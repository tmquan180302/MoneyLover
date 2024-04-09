package com.poly.moneylover.network;

import com.poly.moneylover.models.Bill;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface BillApi {

    BillApi api = RetrofitClient.getInstance().create(BillApi.class);


    @POST("bill/create")
    Call<Void> create(@Body Bill bill);

    @GET("bill/checkUser")
    Call<Void> checkUser();



}
