package com.example.e7440.bankingproject.connect_api.responses;

import com.google.gson.annotations.SerializedName;

public class BaseResponseData<T> {


    @SerializedName("message")
    protected String message;
    @SerializedName("data")
    private T data;

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}
