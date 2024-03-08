package com.poly.moneylover.network;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        String authToken = RetrofitClient.getAuthToken();
        if (authToken != null) {
            Request.Builder requestBuilder = original.newBuilder()
                    .header("Authorization", "Bearer " + authToken);
            Request request = requestBuilder.build();
            return chain.proceed(request);
        }

        return chain.proceed(original);
    }
}
