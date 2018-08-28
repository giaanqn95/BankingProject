package com.example.e7440.bankingproject.connect_api.config;

import com.example.e7440.bankingproject.connect_api.responses.BaseResponseData;
import com.example.e7440.bankingproject.connect_api.responses.BaseResponseList;
import com.example.e7440.bankingproject.connect_api.responses.BaseResponseLogin;
import com.example.e7440.bankingproject.connect_api.responses.ResponseApi;
import com.example.e7440.bankingproject.module.model.DetailUser;
import com.example.e7440.bankingproject.module.model.Schedules;
import com.example.e7440.bankingproject.module.model.User;
import com.google.gson.JsonObject;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiInterfacesSchedules {

    @GET("getSurvey")
    Call<BaseResponseList<Schedules>> getSurvey(@Query("extension") String extension, @Query("secret") String secret);

    @POST("login")
    @FormUrlEncoded
    Call<BaseResponseLogin<User>> postLogin(@Field("secret") String secret, @Field("user") String username, @Field("pass") String password);

    @Headers("Content-Type: application/json")
    @POST("saveInfoBasic")
    Call<BaseResponseData<User>> postBasicInfo(@Query("id_customer") String id_customer, @Query("secret") String secret, @Body JsonObject data);

    @Headers("Content-Type: application/json")
    @POST("saveInfoDetails")
    Call<BaseResponseData<User>> postDetailInfo(@Query("id_customer") String id_customer, @Query("secret") String secret, @Body JsonObject data);

    @GET("getDetail")
    Call<BaseResponseData<DetailUser>> getDetailCustomer(@Query("id_customer") String id_customer, @Query("secret") String secret);

    @POST("saveDataImage")
    @Multipart
    Call<BaseResponseData<ResponseApi>> saveImage(@Query("id_customer") String id_customer, @Query("secret") String secret, @Part MultipartBody.Part image);
}
