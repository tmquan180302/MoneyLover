package com.poly.moneylover.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.poly.moneylover.constants.Constants;
import com.poly.moneylover.models.DataDetailsReportModelApi;
import com.poly.moneylover.models.DataReportModelApi;
import com.poly.moneylover.models.Request.ReqRegister;
import com.poly.moneylover.models.Request.ServerReqLogin;
import com.poly.moneylover.models.Request.ServerReqLoginGoogle;
import com.poly.moneylover.models.Response.ServerResToken;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {

    ApiService api = RetrofitClient.getInstance().create(ApiService.class);

    //API GET ADD UPDATE DELETE

    @POST("user/register")
    Call<ServerResToken> register(@Body ReqRegister serverReqSignup);

    @POST("user/login")
    Call<ServerResToken> login(@Body ServerReqLogin serverReqLogin);

    @POST("user/loginGoogle")
    Call<ServerResToken> loginByEmail(@Body ServerReqLoginGoogle serverReqLoginGoogle);


}
