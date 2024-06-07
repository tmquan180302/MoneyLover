package com.poly.moneylover.network;

import com.poly.moneylover.models.Transaction;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface TransactionApi {
    TransactionApi api = RetrofitClient.getInstance().create(TransactionApi.class);


    @GET("transaction/getListDeleted")
    Call<List<Transaction>> getListDeleted();
    @POST("transaction/create")
    Call<Void> create(@Body Transaction transaction);

    @POST("transaction/{id}/update")
    Call<Void> update(@Body Transaction transaction, @Path("id") String id);

    @GET("transaction/")
    Call<List<Transaction>> getListTransaction();

    @DELETE("transaction/{id}")
    Call<Void> delete(@Path("id") String id);

    @GET("transaction/{id}/restore")
    Call<Void> restore(@Path("id") String id);

    @DELETE("transaction/{id}/force")
    Call<Void> forceDelete(@Path("id") String id);
}
