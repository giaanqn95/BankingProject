package com.example.e7440.bankingproject.connect_api.api;

import android.util.Log;

import com.example.e7440.bankingproject.R;
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
//       apiInterfaces.getToolBar().enqueue(new Callback<ResponseTool>() {
//           @Override
//           public void onResponse(Call<ResponseTool> call, Response<ResponseTool> response) {
//                if (response.isSuccessful()) {
//                    if (response.body().isSuccess()) {
//                        mApiListener.onResponseListener(apiFunction, ApiStatus.CALL_API_RESULT_SUCCESS, response.body());
//                    } else {
//                        notifyErrorFromServer(apiFunction, ApiStatus.CALL_API_RESULT_FAILURE, response.body().getErrorCode());
//                    }
//                } else {
//                    notifyErrorFromServer(apiFunction, ApiStatus.CALL_API_RESULT_FAILURE, response.code());
//                }
//               mApiListener.onResponseListener(ApiFunction.GET_TOOLBAR, ApiStatus.CALL_API_RESULT_SUCCESS, response.body());
//           }
//
//           @Override
//           public void onFailure(Call<ResponseTool> call, Throwable t) {
//               mApiListener.onResponseListener(ApiFunction.GET_TOOLBAR, ApiStatus.CALL_API_RESULT_TIMEOUT, t.getMessage());
//           }
//       });
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
//                    if (response.body().isSuccess()) {
//                        mApiListener.onResponseListener(apiFunction, ApiStatus.CALL_API_RESULT_SUCCESS, response.body());
//                    } else {
//                        notifyErrorFromServer(apiFunction, ApiStatus.CALL_API_RESULT_FAILURE, response.body().getErrorCode());
//                    }
//                } else {
//                    notifyErrorFromServer(apiFunction, ApiStatus.CALL_API_RESULT_FAILURE, response.code());
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
//            case 401:
//                mApiListener.onResponseListener(apiFunction, apiStatus, 401);
//                break;
//            case 100: {
//                mApiListener.onResponseListener(apiFunction, apiStatus, R.string.error_100);
//                break;
//            }
//            case 101: {
//                mApiListener.onResponseListener(apiFunction, apiStatus, R.string.error_101);
//                break;
//            }
//            case 102: {
//                mApiListener.onResponseListener(apiFunction, apiStatus, R.string.error_102);
//                break;
//            }
//            case 103: {
//                mApiListener.onResponseListener(apiFunction, apiStatus, R.string.error_103);
//                break;
//            }
//            case 104: {
//                mApiListener.onResponseListener(apiFunction, apiStatus, R.string.error_104);
//                break;
//            }
//            case 105: {
//                mApiListener.onResponseListener(apiFunction, apiStatus, R.string.error_105);
//                break;
//            }
//            case 106: {
//                mApiListener.onResponseListener(apiFunction, apiStatus, R.string.error_106);
//                break;
//            }
//            case 107: {
//                mApiListener.onResponseListener(apiFunction, apiStatus, R.string.error_107);
//                break;
//            }
//            case 108: {
//                mApiListener.onResponseListener(apiFunction, apiStatus, R.string.error_108);
//                break;
//            }
//            case 109: {
//                mApiListener.onResponseListener(apiFunction, apiStatus, R.string.error_109);
//                break;
//            }
//            case 110: {
//                mApiListener.onResponseListener(apiFunction, apiStatus, R.string.error_110);
//                break;
//            }
//            case 116: {
//                mApiListener.onResponseListener(apiFunction, apiStatus, R.string.error_116);
//                break;
//            }
//            case 126: {
//                mApiListener.onResponseListener(apiFunction, apiStatus, R.string.error_126);
//                break;
//            }
//            case 150: {
//                mApiListener.onResponseListener(apiFunction, apiStatus, R.string.error_150);
//                break;
//            }
//            case 160: {
//                mApiListener.onResponseListener(apiFunction, apiStatus, R.string.error_160);
//                break;
//            }
//            case 170: {
//                mApiListener.onResponseListener(apiFunction, apiStatus, R.string.error_170);
//                break;
//            }
//            case 171: {
//                mApiListener.onResponseListener(apiFunction, apiStatus, R.string.error_171);
//                break;
//            }
//            case 180: {
//                mApiListener.onResponseListener(apiFunction, apiStatus, R.string.error_180);
//                break;
//            }
//            case 190: {
//                mApiListener.onResponseListener(apiFunction, apiStatus, R.string.error_190);
//                break;
//            }
//            case 210: {
//                mApiListener.onResponseListener(apiFunction, apiStatus, R.string.error_210);
//                break;
//            }
//            case 211: {
//                mApiListener.onResponseListener(apiFunction, apiStatus, R.string.error_211);
//                break;
//            }
//            case 212: {
//                mApiListener.onResponseListener(apiFunction, apiStatus, R.string.error_212);
//                break;
//            }
//            case 213: {
//                mApiListener.onResponseListener(apiFunction, apiStatus, R.string.error_213);
//                break;
//            }
//            case 214: {
//                mApiListener.onResponseListener(apiFunction, apiStatus, R.string.error_214);
//                break;
//            }
//            case 215: {
//                mApiListener.onResponseListener(apiFunction, apiStatus, R.string.error_215);
//                break;
//            }
//            case 407: {
//                mApiListener.onResponseListener(apiFunction, apiStatus, R.string.error_408);
//                break;
//            }
//            case 600: {
//                mApiListener.onResponseListener(apiFunction, apiStatus, R.string.error_600);
//                break;
//            }
//            case 700: {
//                mApiListener.onResponseListener(apiFunction, apiStatus, R.string.error_700);
//                break;
//            }
//            case 800: {
//                mApiListener.onResponseListener(apiFunction, apiStatus, R.string.error_800);
//                break;
//            }

            default:
        }
    }
}
