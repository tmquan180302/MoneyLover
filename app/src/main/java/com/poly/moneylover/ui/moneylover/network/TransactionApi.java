package com.poly.moneylover.ui.moneylover.network;

import com.google.gson.JsonObject;
import com.poly.moneylover.ui.moneylover.models.Transaction;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface TransactionApi {
    TransactionApi api = RetrofitClient.getInstance().create(TransactionApi.class);

    @POST("transaction/create")
    Call<Void> create(@Body Transaction transaction);

    @GET("transaction/")
    Call<List<Transaction>> getListTransaction();
    @DELETE("transaction/{transactionId}")
    Call<Boolean> delete(@Path("transactionId") String transactionId);


    @POST("transaction/{transactionId}/update")
    Call<String> update(@Path("transactionId") String transactionId, @Body Transaction transaction);

    @GET("transaction/sumbydate")
    Call<JsonObject> getSumbyday();
}