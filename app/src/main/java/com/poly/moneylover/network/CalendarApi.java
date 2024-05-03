package com.poly.moneylover.network;

import com.poly.moneylover.models.Response.Export;
import com.poly.moneylover.models.Response.ResCalendar;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CalendarApi {

    CalendarApi api = RetrofitClient.getInstance().create(CalendarApi.class);


    @GET("budget/{startDay}/{endDay}")
    Call<ResCalendar> getScreenCalendar(@Path("startDay") String startDay, @Path("endDay") String endDay);
}
