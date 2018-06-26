package com.example.e7440.bankingproject.connect_api.api;

/**
 * Created by E7440 on 6/11/2018.
 */

public interface ApiListener {
    void onResponseListener(ApiFunction apiFunction, ApiStatus statusId, Object object);
}
