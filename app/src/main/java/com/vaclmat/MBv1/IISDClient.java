package com.vaclmat.MBv1;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class IISDClient {
    private static final OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
            .connectTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.SECONDS)
            .addInterceptor(chain -> {
        Request originalRequest = chain.request();
        Request.Builder builder = originalRequest.newBuilder();
        Request newRequest = builder.build();
        return chain.proceed(newRequest);
    }).build();
    private static Retrofit retrofit;
    //Define the base URL//
    private static final String BASE_URL = "https://www.melonberry-weby.cz/wp-json/";
    //Create the Retrofit instance//
    static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                     //Add the converter//
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    //Build the Retrofit instance//
                    .build();
        }
        return retrofit;
    }
}
