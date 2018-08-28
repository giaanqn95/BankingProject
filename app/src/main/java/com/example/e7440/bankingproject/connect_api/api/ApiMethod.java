package com.example.e7440.bankingproject.connect_api.api;

import android.util.Log;

import com.example.e7440.bankingproject.components.SessionManagerUser;
import com.example.e7440.bankingproject.connect_api.config.ApiClient;
import com.example.e7440.bankingproject.connect_api.config.ApiClientSchedules;
import com.example.e7440.bankingproject.connect_api.config.ApiInterfaces;
import com.example.e7440.bankingproject.connect_api.config.ApiInterfacesSchedules;
import com.example.e7440.bankingproject.connect_api.responses.BaseResponseData;
import com.example.e7440.bankingproject.connect_api.responses.BaseResponseList;
import com.example.e7440.bankingproject.connect_api.responses.BaseResponseLogin;
import com.example.e7440.bankingproject.connect_api.responses.BaseResponseObject;
import com.example.e7440.bankingproject.connect_api.responses.ResponseApi;
import com.example.e7440.bankingproject.connect_api.responses.ResponseTool;
import com.example.e7440.bankingproject.module.model.DetailUser;
import com.example.e7440.bankingproject.module.model.Schedules;
import com.example.e7440.bankingproject.module.model.User;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import java.util.HashMap;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.e7440.bankingproject.AppConstants.KEY_SECRET;

/**
 * Created by E7440 on 6/11/2018.
 */

public class ApiMethod {

    private ApiListener mApiListener;

    public void setmApiListener(ApiListener mApiListener) {
        this.mApiListener = mApiListener;
    }

    private HashMap<String, String> dataUser = new HashMap<>();


   /*
    Danh sách các hàm gọi api
     */

    public void getToolbar() {
        ApiInterfaces apiInterfaces = ApiClient.getClient().create(ApiInterfaces.class);
        handleResponseObject(apiInterfaces.getToolBar(), ApiFunction.GET_TOOLBAR);
    }

    public void getTabLoan(String url) {
        ApiInterfaces apiInterfaces = ApiClient.getClient().create(ApiInterfaces.class);
        apiInterfaces.getUrl(url).enqueue(new Callback<ResponseTool>() {
            @Override
            public void onResponse(Call<ResponseTool> call, Response<ResponseTool> response) {
                if (response.isSuccessful()) {
                    mApiListener.onResponseListener(ApiFunction.GET_TAB_LOAN, ApiStatus.CALL_API_RESULT_SUCCESS, response.body());
                }
            }

            @Override
            public void onFailure(Call<ResponseTool> call, Throwable t) {
                mApiListener.onResponseListener(ApiFunction.GET_TAB_LOAN, ApiStatus.CALL_API_RESULT_FAILURE, t.getMessage());
            }
        });
    }

    public void getTabPersonal(String url) {
        ApiInterfaces apiInterfaces = ApiClient.getClient().create(ApiInterfaces.class);
        apiInterfaces.getUrl(url).enqueue(new Callback<ResponseTool>() {
            @Override
            public void onResponse(Call<ResponseTool> call, Response<ResponseTool> response) {
                if (response.isSuccessful()) {
                    mApiListener.onResponseListener(ApiFunction.GET_TAB_PERSONAL, ApiStatus.CALL_API_RESULT_SUCCESS, response.body());
                }
            }

            @Override
            public void onFailure(Call<ResponseTool> call, Throwable t) {
                mApiListener.onResponseListener(ApiFunction.GET_TAB_PERSONAL, ApiStatus.CALL_API_RESULT_FAILURE, t.getMessage());
            }
        });
    }

    public void getTabEmployments(String url) {
        ApiInterfaces apiInterfaces = ApiClient.getClient().create(ApiInterfaces.class);
        apiInterfaces.getUrl(url).enqueue(new Callback<ResponseTool>() {
            @Override
            public void onResponse(Call<ResponseTool> call, Response<ResponseTool> response) {
                if (response.isSuccessful()) {
                    mApiListener.onResponseListener(ApiFunction.GET_TAB_EMPLOYMENT, ApiStatus.CALL_API_RESULT_SUCCESS, response.body());
                }
            }

            @Override
            public void onFailure(Call<ResponseTool> call, Throwable t) {
                mApiListener.onResponseListener(ApiFunction.GET_TAB_EMPLOYMENT, ApiStatus.CALL_API_RESULT_FAILURE, t.getMessage());
            }
        });
    }

    public void getTabContact(String url) {
        ApiInterfaces apiInterfaces = ApiClient.getClient().create(ApiInterfaces.class);
        apiInterfaces.getUrl(url).enqueue(new Callback<ResponseTool>() {
            @Override
            public void onResponse(Call<ResponseTool> call, Response<ResponseTool> response) {
                if (response.isSuccessful()) {
                    mApiListener.onResponseListener(ApiFunction.GET_TAB_CONTACT, ApiStatus.CALL_API_RESULT_SUCCESS, response.body());
                }
            }

            @Override
            public void onFailure(Call<ResponseTool> call, Throwable t) {
                mApiListener.onResponseListener(ApiFunction.GET_TAB_CONTACT, ApiStatus.CALL_API_RESULT_FAILURE, t.getMessage());
            }
        });
    }

    public void getSurvey() {
        dataUser = SessionManagerUser.getInstance().getUserDetails();
        ApiInterfacesSchedules apiInterfacesSchedules = ApiClientSchedules.getRetrofit().create(ApiInterfacesSchedules.class);
        apiInterfacesSchedules.getSurvey(dataUser.get(SessionManagerUser.KEY_ENXTENSION), KEY_SECRET).enqueue(new Callback<BaseResponseList<Schedules>>() {
            @Override
            public void onResponse(Call<BaseResponseList<Schedules>> call, Response<BaseResponseList<Schedules>> response) {
                if (response.isSuccessful()) {
                    mApiListener.onResponseListener(ApiFunction.GET_SURVEY, ApiStatus.CALL_API_RESULT_SUCCESS, response.body());
                    Log.d("AAAAA", "success");
                    Log.d("AAAAA", String.valueOf(response.body().getSummary().getWaiting()));
                    Log.d("AAAAA", String.valueOf(response.body().getListData().size()));
                }
            }

            @Override
            public void onFailure(Call<BaseResponseList<Schedules>> call, Throwable t) {
                mApiListener.onResponseListener(ApiFunction.GET_SURVEY, ApiStatus.CALL_API_RESULT_FAILURE, t.getMessage());
                Log.d("AAAAA", t.getMessage());
            }
        });
    }

    public void login(String secret, String username, String password) {
        ApiInterfacesSchedules apiInterfacesSchedules = ApiClientSchedules.getRetrofit().create(ApiInterfacesSchedules.class);
        apiInterfacesSchedules.postLogin(secret, username, password).enqueue(new Callback<BaseResponseLogin<User>>() {
            @Override
            public void onResponse(Call<BaseResponseLogin<User>> call, Response<BaseResponseLogin<User>> response) {
                if (response.isSuccessful()) {
                    if (response.body().getMessage().equals("OK")) {
                        mApiListener.onResponseListener(ApiFunction.POST_LOGIN, ApiStatus.CALL_API_RESULT_SUCCESS, response.body());
                        Log.d("AAAAA", "success");
                    } else {
                        mApiListener.onResponseListener(ApiFunction.POST_LOGIN, ApiStatus.CALL_API_RESULT_FAILURE, response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponseLogin<User>> call, Throwable t) {
                mApiListener.onResponseListener(ApiFunction.POST_LOGIN, ApiStatus.CALL_API_RESULT_FAILURE, t.getMessage());
                Log.d("AAAAA", t.getMessage());
            }
        });
    }

    public void saveBasicInfo(String id_customer, String secret, JSONObject jsonObject) {
        ApiInterfacesSchedules apiInterfacesSchedules = ApiClientSchedules.getRetrofit().create(ApiInterfacesSchedules.class);
        apiInterfacesSchedules.postBasicInfo(id_customer, secret, new JsonParser().parse(jsonObject.toString()).getAsJsonObject()).enqueue(new Callback<BaseResponseData<User>>() {
            @Override
            public void onResponse(Call<BaseResponseData<User>> call, Response<BaseResponseData<User>> response) {
                if (response.isSuccessful()) {
                    mApiListener.onResponseListener(ApiFunction.POST_SAVE_BASIC_INFO, ApiStatus.CALL_API_RESULT_SUCCESS, response.body());
                }
            }

            @Override
            public void onFailure(Call<BaseResponseData<User>> call, Throwable t) {
                mApiListener.onResponseListener(ApiFunction.POST_SAVE_BASIC_INFO, ApiStatus.CALL_API_RESULT_FAILURE, t.getMessage());
            }
        });
    }

    public void saveDetailInfo(String id_customer, String secret, JSONObject jsonObject) {
        ApiInterfacesSchedules apiInterfacesSchedules = ApiClientSchedules.getRetrofit().create(ApiInterfacesSchedules.class);
        Log.d("ALOLO123", String.valueOf(new JsonParser().parse(jsonObject.toString())));
        apiInterfacesSchedules.postDetailInfo(id_customer, secret, new JsonParser().parse(jsonObject.toString()).getAsJsonObject()).enqueue(new Callback<BaseResponseData<User>>() {
            @Override
            public void onResponse(Call<BaseResponseData<User>> call, Response<BaseResponseData<User>> response) {
                if (response.isSuccessful()) {
                    mApiListener.onResponseListener(ApiFunction.POST_SAVE_DETAIL_INFO, ApiStatus.CALL_API_RESULT_SUCCESS, response.body());
                    Log.d("AAAAA", "success");
                }
            }

            @Override
            public void onFailure(Call<BaseResponseData<User>> call, Throwable t) {
                mApiListener.onResponseListener(ApiFunction.POST_SAVE_DETAIL_INFO, ApiStatus.CALL_API_RESULT_FAILURE, t.getMessage());
                Log.d("AAAAA", t.getMessage());
            }
        });
    }

    public void getDetailInfo(String id_customer, String secret) {
        ApiInterfacesSchedules apiInterfacesSchedules = ApiClientSchedules.getRetrofit().create(ApiInterfacesSchedules.class);
        apiInterfacesSchedules.getDetailCustomer(id_customer, secret).enqueue(new Callback<BaseResponseData<DetailUser>>() {
            @Override
            public void onResponse(Call<BaseResponseData<DetailUser>> call, Response<BaseResponseData<DetailUser>> response) {
                if (response.isSuccessful()) {
                    mApiListener.onResponseListener(ApiFunction.GET_DETAIL_INFO, ApiStatus.CALL_API_RESULT_SUCCESS, response.body());
                }
            }

            @Override
            public void onFailure(Call<BaseResponseData<DetailUser>> call, Throwable t) {
                mApiListener.onResponseListener(ApiFunction.GET_DETAIL_INFO, ApiStatus.CALL_API_RESULT_FAILURE, t.getMessage());
            }
        });
    }

    public void postSaveImage(String id_customer, String secret, MultipartBody.Part avatar) {
        ApiInterfacesSchedules apiInterfacesSchedules = ApiClientSchedules.getRetrofit().create(ApiInterfacesSchedules.class);
        apiInterfacesSchedules.saveImage(id_customer, secret, avatar).enqueue(new Callback<BaseResponseData<ResponseApi>>() {
            @Override
            public void onResponse(Call<BaseResponseData<ResponseApi>> call, Response<BaseResponseData<ResponseApi>> response) {
                if (response.isSuccessful()) {
                    mApiListener.onResponseListener(ApiFunction.POST_SAVE_IMAGE, ApiStatus.CALL_API_RESULT_SUCCESS, response.body());
                }
            }

            @Override
            public void onFailure(Call<BaseResponseData<ResponseApi>> call, Throwable t) {
                mApiListener.onResponseListener(ApiFunction.POST_SAVE_IMAGE, ApiStatus.CALL_API_RESULT_FAILURE, t.getMessage());
                Log.d("AAAAA", t.getMessage());
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
