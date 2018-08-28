package com.example.e7440.bankingproject.module.login;

import com.example.e7440.bankingproject.R;
import com.example.e7440.bankingproject.connect_api.api.ApiFunction;
import com.example.e7440.bankingproject.connect_api.api.ApiStatus;
import com.example.e7440.bankingproject.connect_api.responses.BaseResponseLogin;
import com.example.e7440.bankingproject.module.base.BasePresenter;
import com.example.e7440.bankingproject.module.model.User;

import static com.example.e7440.bankingproject.AppConstants.KEY_SECRET;

public class LoginPresenterImpl extends BasePresenter<LoginGeneral.LoginView> implements LoginGeneral.LoginPresenter {

    public LoginPresenterImpl(LoginGeneral.LoginView mView) {
        super(mView);
    }

    @Override
    public void onResponseListener(ApiFunction apiFunction, ApiStatus statusId, Object object) {
        switch (apiFunction) {
            case POST_LOGIN: {
                getmView().showDialogLoading();
                switch (statusId) {
                    case CALL_API_RESULT_SUCCESS: {
                        BaseResponseLogin<User> userBaseResponseLogin = (BaseResponseLogin<User>) object;
                        getmView().loginSuccess(userBaseResponseLogin.getData());
                        getmView().hideDialogLoading();
                        break;
                    }
                    case CALL_API_RESULT_FAILURE: {
                        getmView().hideDialogLoading();
                        getmView().showDialogError(R.string.error_login_fail);
                        break;
                    }
                }
                break;
            }
        }
    }

    @Override
    public void callLogin(String username, String password) {
        if (getmView().isNetworkConnect()) {
            mApiMethod.login(KEY_SECRET, username, password);
        } else {
            getmView().networkConnectError();
        }
    }
}
