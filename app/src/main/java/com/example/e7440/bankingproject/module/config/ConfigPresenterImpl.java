package com.example.e7440.bankingproject.module.config;

import com.example.e7440.bankingproject.R;
import com.example.e7440.bankingproject.connect_api.api.ApiFunction;
import com.example.e7440.bankingproject.connect_api.api.ApiStatus;
import com.example.e7440.bankingproject.connect_api.responses.BaseResponseObject;
import com.example.e7440.bankingproject.module.base.BasePresenter;
import com.example.e7440.bankingproject.module.model.Tab;

/**
 * Created by E7440 on 6/11/2018.
 */

public class ConfigPresenterImpl extends BasePresenter<ConfigGeneral.ConfigView> implements ConfigGeneral.ConfigPresenter {
    public ConfigPresenterImpl(ConfigGeneral.ConfigView mView) {
        super(mView);
    }

    @Override
    public void onResponseListener(ApiFunction apiFunction, ApiStatus statusId, Object object) {
        switch (apiFunction) {
            case GET_TOOLBAR: {
                switch (statusId) {
                    case CALL_API_RESULT_SUCCESS: {
                        getmView().hideDialogLoading();
                        BaseResponseObject<Tab> responseObject = (BaseResponseObject<Tab>) object;
                        getmView().getDataConfig(responseObject.getTab());
                        break;
                    }
                    case CALL_API_RESULT_FAILURE: {
                        int errorCode = (int) object;
                        getmView().showDialogError(errorCode);
                        break;
                    }
                    case CALL_API_RESULT_TIMEOUT: {
                        getmView().showDialogError(R.string.error_001);
                        getmView().hideDialogLoading();
                        break;
                    }
                }
                break;
            }
        }
    }

    @Override
    public void getConfig() {
        getmView().showDialogLoading();
        if (!getmView().isNetworkConnect()){
            getmView().networkConnectError();
            return;
        }
        mApiMethod.getToolbar();
    }
}
