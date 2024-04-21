package com.poly.moneylover.ui.moneylover.network;

import com.poly.moneylover.ui.moneylover.models.Balance;
import com.poly.moneylover.ui.moneylover.models.Response.BalanceRequestBody;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface SoDuBanDauApi {

    SoDuBanDauApi api = RetrofitClient.getInstance().create(SoDuBanDauApi.class);

    @GET("/balance")
    Call<List<Balance>> getSoDuBanDau();
    @POST("balance/create")
    Call<String> createSoDuBanDau(@Body BalanceRequestBody body);
}
