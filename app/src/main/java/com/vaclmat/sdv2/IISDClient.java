package com.vaclmat.sdv2;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class IISDClient {
    private static OkHttpClient okHttpClient = new OkHttpClient().newBuilder().addInterceptor(chain -> {
        Request originalRequest = chain.request();
        Request.Builder builder = originalRequest.newBuilder();
        Request newRequest = builder.build();
        return chain.proceed(newRequest);
    }).build();
    private static Retrofit retrofit;
    //Define the base URL//
    private static final String BASE_URL = "https://api.eu-gb.apiconnect.appdomain.cloud/vaclavmatousekczibmcom-dev/sb/web/services/";
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
