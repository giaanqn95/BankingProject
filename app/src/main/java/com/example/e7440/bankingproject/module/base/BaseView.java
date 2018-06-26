package com.example.e7440.bankingproject.module.base;

/**
 * Created by E7440 on 6/11/2018.
 */

public interface BaseView {
    boolean isNetworkConnect();
    void networkConnectError();
    void showDialogLoading();
    void hideDialogLoading();
    void showProgressDialog();
    void hideProgressDialog();
    void showDialogError(int resId);
    void showDialogLogin(int resId);
}
