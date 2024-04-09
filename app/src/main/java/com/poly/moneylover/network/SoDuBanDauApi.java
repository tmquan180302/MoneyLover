package com.poly.moneylover.network;

import com.poly.moneylover.models.Balance;
import com.poly.moneylover.models.Category;
import com.poly.moneylover.models.Response.BalanceRequestBody;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface SoDuBanDauApi {

    SoDuBanDauApi api = RetrofitClient.getInstance().create(SoDuBanDauApi.class);

    @POST("/balance")
    Call<List<Balance>> getSoDuBanDau();
    @POST("/balance/create")
    Call<String> createSoDuBanDau(@Body BalanceRequestBody body);
}
