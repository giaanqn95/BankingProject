package com.example.e7440.bankingproject.connect_api.api;

import android.util.Log;

import com.example.e7440.bankingproject.connect_api.config.ApiClient;
import com.example.e7440.bankingproject.connect_api.config.ApiInterfaces;
import com.example.e7440.bankingproject.connect_api.responses.BaseResponseList;
import com.example.e7440.bankingproject.connect_api.responses.BaseResponseObject;
import com.example.e7440.bankingproject.connect_api.responses.ResponseTool;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by E7440 on 6/11/2018.
 */

public class ApiMethod {

    private ApiListener mApiListener;

    public void setmApiListener(ApiListener mApiListener){
        this.mApiListener = mApiListener;
    }


   /*
    Danh sách các hàm gọi api
     */

   public void getToolbar(){
       ApiInterfaces apiInterfaces = ApiClient.getClient().create(ApiInterfaces.class);
       handleResponseObject(apiInterfaces.getToolBar(), ApiFunction.GET_TOOLBAR);
   }

   public void getTabLoan(String url){
       ApiInterfaces apiInterfaces = ApiClient.getClient().create(ApiInterfaces.class);
       apiInterfaces.getUrl(url).enqueue(new Callback<ResponseTool>() {
           @Override
           public void onResponse(Call<ResponseTool> call, Response<ResponseTool> response) {
               if (response.isSuccessful()){
                   mApiListener.onResponseListener(ApiFunction.GET_TAB_LOAN, ApiStatus.CALL_API_RESULT_SUCCESS, response.body());
               }
           }

           @Override
           public void onFailure(Call<ResponseTool> call, Throwable t) {
                mApiListener.onResponseListener(ApiFunction.GET_TAB_LOAN, ApiStatus.CALL_API_RESULT_FAILURE, t.getMessage());
           }
       });
   }

    public void getTabPersonal(String url){
        ApiInterfaces apiInterfaces = ApiClient.getClient().create(ApiInterfaces.class);
        apiInterfaces.getUrl(url).enqueue(new Callback<ResponseTool>() {
            @Override
            public void onResponse(Call<ResponseTool> call, Response<ResponseTool> response) {
                if (response.isSuccessful()){
                    mApiListener.onResponseListener(ApiFunction.GET_TAB_PERSONAL, ApiStatus.CALL_API_RESULT_SUCCESS, response.body());
                }
            }

            @Override
            public void onFailure(Call<ResponseTool> call, Throwable t) {
                mApiListener.onResponseListener(ApiFunction.GET_TAB_PERSONAL, ApiStatus.CALL_API_RESULT_FAILURE, t.getMessage());
            }
        });
    }

    public void getTabEmployments(String url){
        ApiInterfaces apiInterfaces = ApiClient.getClient().create(ApiInterfaces.class);
        apiInterfaces.getUrl(url).enqueue(new Callback<ResponseTool>() {
            @Override
            public void onResponse(Call<ResponseTool> call, Response<ResponseTool> response) {
                if (response.isSuccessful()){
                    mApiListener.onResponseListener(ApiFunction.GET_TAB_EMPLOYMENT, ApiStatus.CALL_API_RESULT_SUCCESS, response.body());
                }
            }

            @Override
            public void onFailure(Call<ResponseTool> call, Throwable t) {
                mApiListener.onResponseListener(ApiFunction.GET_TAB_EMPLOYMENT, ApiStatus.CALL_API_RESULT_FAILURE, t.getMessage());
            }
        });
    }
    public void getTabContact(String url){
        ApiInterfaces apiInterfaces = ApiClient.getClient().create(ApiInterfaces.class);
        apiInterfaces.getUrl(url).enqueue(new Callback<ResponseTool>() {
            @Override
            public void onResponse(Call<ResponseTool> call, Response<ResponseTool> response) {
                if (response.isSuccessful()){
                    mApiListener.onResponseListener(ApiFunction.GET_TAB_CONTACT, ApiStatus.CALL_API_RESULT_SUCCESS, response.body());
                }
            }

            @Override
            public void onFailure(Call<ResponseTool> call, Throwable t) {
                mApiListener.onResponseListener(ApiFunction.GET_TAB_CONTACT, ApiStatus.CALL_API_RESULT_FAILURE, t.getMessage());
            }
        });
    }


    /*
   response trả về 1 object, thường áp dụng cho response khi login, signup...
    */

    private <T> void handleResponseObject(Call<BaseResponseObject<T>> responseCall, final ApiFunction apiFunction) {
        responseCall.enqueue(new Callback<BaseResponseObject<T>>() {
            @Override
            public void onResponse(Call<BaseResponseObject<T>> call, Response<BaseResponseObject<T>> response) {
                if (response.isSuccessful()) {
                }
                mApiListener.onResponseListener(apiFunction, ApiStatus.CALL_API_RESULT_SUCCESS, response.body());
            }

            @Override
            public void onFailure(Call<BaseResponseObject<T>> call, Throwable t) {
                mApiListener.onResponseListener(apiFunction, ApiStatus.CALL_API_RESULT_TIMEOUT, t.getMessage());
            }
        });
    }
    /*
    response trả về là 1 danh sách, thường áp dụng cho response khi lấy danh sách (không phân trang)
     */

    private <T> void handleResponseList(Call<BaseResponseList<T>> responseListCall, final ApiFunction apiFunction) {
        responseListCall.enqueue(new Callback<BaseResponseList<T>>() {
            @Override
            public void onResponse(Call<BaseResponseList<T>> call, Response<BaseResponseList<T>> response) {
                if (response.isSuccessful()) {
                    if (response.body().isSuccess()) {
                        mApiListener.onResponseListener(apiFunction, ApiStatus.CALL_API_RESULT_SUCCESS, response.body());
                    } else {
                        Log.d("ERROR_CODE", "" + response.body().getErrorCode());
                        notifyErrorFromServer(apiFunction, ApiStatus.CALL_API_RESULT_FAILURE, response.body().getErrorCode());
                    }
                } else {
                    notifyErrorFromServer(apiFunction, ApiStatus.CALL_API_RESULT_FAILURE, response.code());
                }
            }

            @Override
            public void onFailure(Call<BaseResponseList<T>> call, Throwable t) {
                mApiListener.onResponseListener(apiFunction, ApiStatus.CALL_API_RESULT_TIMEOUT, t.getMessage());
            }
        });
    }

    /*
    xử lý lỗi từ server trả về
     */
    private void notifyErrorFromServer(ApiFunction apiFunction, ApiStatus apiStatus, int errorCode) {
        switch (errorCode) {

            default:
        }
    }
}
