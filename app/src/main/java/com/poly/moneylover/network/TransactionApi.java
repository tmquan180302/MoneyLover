package com.poly.moneylover.network;

import com.poly.moneylover.models.Transaction;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface TransactionApi {
    TransactionApi api = RetrofitClient.getInstance().create(TransactionApi.class);

    @POST("transaction/create")
    Call<Void> create(@Body Transaction transaction);

    @GET("transaction/")
    Call<List<Transaction>> getListTransaction();
}
