package com.poly.moneylover.network;

import com.poly.moneylover.models.Bill;
import com.poly.moneylover.models.Service;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ServiceApi {

    ServiceApi api = RetrofitClient.getInstance().create(ServiceApi.class);

    @GET("service/getList")
    Call<List<Service>> getListService();
}
