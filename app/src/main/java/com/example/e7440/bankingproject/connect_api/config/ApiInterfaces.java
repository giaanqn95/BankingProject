package com.example.e7440.bankingproject.connect_api.config;

import com.example.e7440.bankingproject.connect_api.responses.BaseResponseObject;
import com.example.e7440.bankingproject.connect_api.responses.ResponseTool;
import com.example.e7440.bankingproject.module.model.Example;
import com.example.e7440.bankingproject.module.model.Tab;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by E7440 on 6/11/2018.
 */

public interface ApiInterfaces {
    @GET("toolbar.json")
    Call<BaseResponseObject<Tab>> getToolBar();

    @GET
    Call<ResponseTool> getUrl(@Url String url);
}
