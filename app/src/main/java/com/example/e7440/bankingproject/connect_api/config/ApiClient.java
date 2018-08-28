package com.example.e7440.bankingproject.connect_api.config;

import com.example.e7440.bankingproject.AppConstants;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by E7440 on 6/11/2018.
 */

public class ApiClient {

    private static Retrofit retrofit;

    public static Retrofit getClient(){
        HttpLoggingInterceptor  interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(chain -> {
                    Request original = chain.request();
                    Request request = original.newBuilder()
                            .method(original.method(), original.body()).build();
                    return chain.proceed(request);
                });
        OkHttpClient client = httpClient.build();
        retrofit = new Retrofit.Builder()
                .baseUrl(AppConstants.URL_CURRENT)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        return retrofit;
    }
}
