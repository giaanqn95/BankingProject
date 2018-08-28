package com.example.e7440.bankingproject.connect_api.config;

import com.example.e7440.bankingproject.connect_api.responses.ResponseApi;

import org.json.JSONObject;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiInterfaceDiff {

    @Headers("Content-Type: application/json")
    @POST("post_data")
    Call<ResponseApi> postData(@Body JSONObject body);

    @POST("post_data_multipart")
    @Multipart
    Call<ResponseApi> postImage(@Part MultipartBody.Part image, @Part("api_key") RequestBody key);
}
