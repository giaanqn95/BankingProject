package com.example.e7440.bankingproject.connect_api.responses;

import com.example.e7440.bankingproject.module.model.Summary;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by E7440 on 6/11/2018.
 */

public class BaseResponseList<T> {
    @SerializedName("summary")
    private Summary summary;
    @SerializedName("status")
    private boolean success;
    @SerializedName("message")
    private String message;
    @SerializedName("code")
    private int errorCode;
    @SerializedName("data")
    private List<T> listData;

    public BaseResponseList() {
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public List<T> getListData() {
        return listData;
    }

    public Summary getSummary() {
        return summary;
    }

    public BaseResponseList<T> setSummary(Summary summary) {
        this.summary = summary;
        return this;
    }
}
