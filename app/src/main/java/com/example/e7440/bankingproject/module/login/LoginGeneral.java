package com.example.e7440.bankingproject.module.login;

import com.example.e7440.bankingproject.module.base.BaseView;
import com.example.e7440.bankingproject.module.model.User;

public interface LoginGeneral {

    interface LoginView extends BaseView {

        void loginSuccess(User user);

        void loginFail();
    }

    interface LoginPresenter {

        void callLogin(String username, String password);
    }
}
