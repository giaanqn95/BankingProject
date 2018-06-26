package com.example.e7440.bankingproject.connect_api.responses;

import com.example.e7440.bankingproject.module.model.Tab;
import com.google.gson.annotations.SerializedName;

/**
 * Created by E7440 on 6/11/2018.
 */

public class BaseResponseObject<T> {
    @SerializedName("status")
    protected boolean success;
    @SerializedName("message")
    protected String message;
    @SerializedName("code")
    protected int errorCode;
    @SerializedName("tab")
    protected Tab tab;

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public Tab getTab() {
        return tab;
    }

    public void setTab(Tab tab) {
        this.tab = tab;
    }
}
