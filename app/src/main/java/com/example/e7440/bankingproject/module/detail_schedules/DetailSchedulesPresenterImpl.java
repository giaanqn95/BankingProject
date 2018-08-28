package com.example.e7440.bankingproject.module.detail_schedules;

import com.example.e7440.bankingproject.R;
import com.example.e7440.bankingproject.connect_api.api.ApiFunction;
import com.example.e7440.bankingproject.connect_api.api.ApiStatus;
import com.example.e7440.bankingproject.connect_api.responses.BaseResponseData;
import com.example.e7440.bankingproject.connect_api.responses.ResponseApi;
import com.example.e7440.bankingproject.module.base.BasePresenter;
import com.example.e7440.bankingproject.module.model.DetailUser;
import com.example.e7440.bankingproject.module.model.User;

import org.json.JSONObject;

import okhttp3.MultipartBody;

public class DetailSchedulesPresenterImpl extends BasePresenter<DetailSchedulesGeneral.DetailSchedulesView>
        implements DetailSchedulesGeneral.DetailSchedulesPresenter {

    public DetailSchedulesPresenterImpl(DetailSchedulesGeneral.DetailSchedulesView mView) {
        super(mView);
    }

    @Override
    public void onResponseListener(ApiFunction apiFunction, ApiStatus statusId, Object object) {
        switch (apiFunction) {
            case POST_SAVE_BASIC_INFO: {
                getmView().showDialogLoading();
                switch (statusId) {
                    case CALL_API_RESULT_SUCCESS: {
                        BaseResponseData<User> userBaseResponseData = (BaseResponseData<User>) object;
                        getmView().postDataBasicSuccess();
                        getmView().hideDialogLoading();
                        break;
                    }
                    case CALL_API_RESULT_FAILURE: {
                        getmView().showDialogError(R.string.error_post_data_basic_fail);
                        getmView().hideDialogLoading();
                        break;
                    }
                }
                break;
            }
            case POST_SAVE_DETAIL_INFO: {
                getmView().showDialogLoading();
                switch (statusId) {
                    case CALL_API_RESULT_SUCCESS: {
                        BaseResponseData<User> userBaseResponseData = (BaseResponseData<User>) object;
                        getmView().postDataDetailSuccess();
                        getmView().hideDialogLoading();
                        break;
                    }
                    case CALL_API_RESULT_FAILURE: {
                        getmView().showDialogError(R.string.error_post_data_basic_fail);
                        getmView().hideDialogLoading();
                        break;
                    }
                }
                break;
            }
            case GET_DETAIL_INFO: {
                getmView().showDialogLoading();
                switch (statusId) {
                    case CALL_API_RESULT_SUCCESS: {
                        BaseResponseData<DetailUser> detailUserBaseResponseData = (BaseResponseData<DetailUser>) object;
                        getmView().getDetailSuccess(detailUserBaseResponseData.getData());
                        getmView().hideDialogLoading();
                        break;
                    }
                }
                break;
            }
            case POST_SAVE_IMAGE: {
                switch (statusId) {
                    case CALL_API_RESULT_SUCCESS: {
                        BaseResponseData<ResponseApi> responseApiBaseResponseData = (BaseResponseData<ResponseApi>) object;
                        getmView().postSaveImageSuccess();
                        break;
                    }
                    case CALL_API_RESULT_FAILURE: {
                        getmView().showDialogError(R.string.error_002);
                    }
                }
            }
        }
    }

    @Override
    public void postBasicInfo(String id_customer, String secret, JSONObject jsonObject) {
        if (getmView().isNetworkConnect()) {
            mApiMethod.saveBasicInfo(id_customer, secret, jsonObject);
        } else {
            getmView().networkConnectError();
        }
    }

    @Override
    public void postDetailInfo(String secret, String id_customer, JSONObject jsonObject) {
        if (getmView().isNetworkConnect()) {
            mApiMethod.saveDetailInfo(secret, id_customer, jsonObject);
        } else {
            getmView().networkConnectError();
        }
    }

    @Override
    public void getDetail(String id_customer, String secret) {
        if (getmView().isNetworkConnect()) {
            mApiMethod.getDetailInfo(id_customer, secret);
        } else {
            getmView().networkConnectError();
        }
    }

    @Override
    public void postSaveImage(String id_customer, String secret, MultipartBody.Part avatar) {
        if (getmView().isNetworkConnect()) {
            mApiMethod.postSaveImage(id_customer, secret, avatar);
        } else {
            getmView().networkConnectError();
        }
    }
}
