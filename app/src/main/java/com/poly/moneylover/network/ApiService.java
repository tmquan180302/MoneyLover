package com.poly.moneylover.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.poly.moneylover.constants.Constants;
import com.poly.moneylover.models.Request.ServerReqLogin;
import com.poly.moneylover.models.Request.ServerReqLoginGoogle;
import com.poly.moneylover.models.Response.ServerResToken;
import com.poly.moneylover.models.User;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyyy").create();
    ApiService apiService = new Retrofit.Builder()
            .baseUrl(Constants.API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);

    //API GET ADD UPDATE DELETE

    @POST("user/register")
    Call<ServerResToken> register(@Body ServerReqLogin serverReqSignup);

    @POST("user/login")
    Call<ServerResToken> login(@Body ServerReqLogin serverReqLogin);

    @POST("user/loginByEmail")
    Call<ServerResToken> loginByEmail(@Body ServerReqLoginGoogle serverReqLoginGoogle);

}
