package com.example.e7440.bankingproject.module.main;

import com.example.e7440.bankingproject.R;
import com.example.e7440.bankingproject.connect_api.api.ApiFunction;
import com.example.e7440.bankingproject.connect_api.api.ApiStatus;
import com.example.e7440.bankingproject.connect_api.responses.BaseResponseObject;
import com.example.e7440.bankingproject.module.base.BasePresenter;
import com.example.e7440.bankingproject.module.model.Tab;

/**
 * Created by E7440 on 6/12/2018.
 */

public class MainPresenterImpl extends BasePresenter<MainGeneral.MainView> implements MainGeneral.MainPresenter {

    public MainPresenterImpl(MainGeneral.MainView mView) {
        super(mView);
    }

    @Override
    public void onResponseListener(ApiFunction apiFunction, ApiStatus statusId, Object object) {
        getmView().showDialogLoading();
        switch (apiFunction) {
            case GET_TOOLBAR: {
                switch (statusId) {
                    case CALL_API_RESULT_SUCCESS: {
                        getmView().hideDialogLoading();
                        BaseResponseObject<Tab> responseObject = (BaseResponseObject<Tab>) object;
                        getmView().getMain(responseObject.getTab());
                        break;
                    }
                    case CALL_API_RESULT_FAILURE: {
                        int errorCode = (int) object;
                        getmView().showDialogError(errorCode);
                        break;
                    }
                    case CALL_API_RESULT_TIMEOUT: {
                        getmView().showDialogError(R.string.app_name);
                        break;
                    }
                }
                break;
            }
        }
    }

    @Override
    public void getDataMain() {
        getmView().showDialogLoading();
        if (!getmView().isNetworkConnect()) {
            getmView().networkConnectError();
            return;
        }
        mApiMethod.getToolbar();
    }
}
