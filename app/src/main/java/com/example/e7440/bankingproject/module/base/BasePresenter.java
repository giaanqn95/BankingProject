package com.example.e7440.bankingproject.module.base;

import com.example.e7440.bankingproject.connect_api.api.ApiListener;
import com.example.e7440.bankingproject.connect_api.api.ApiMethod;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by E7440 on 6/11/2018.
 */

public abstract class BasePresenter<T extends BaseView> implements ApiListener {
    protected ApiMethod mApiMethod;
    private T mView;
    protected DatabaseReference mDatabase;
    protected FirebaseAuth mAuth;

    public BasePresenter(T mView) {
        this.mView = mView;
        mApiMethod = new ApiMethod();
        mApiMethod.setmApiListener(this);
//        mAuth = FirebaseAuth.getInstance();
//        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public T getmView() {
        return mView;
    }

    protected int loginAgain(int errorCode) {
        if (errorCode == 400 || errorCode == 401 || errorCode == 403 || errorCode == 405 ||
                errorCode == 406 || errorCode == 440) {
            return 401;
        }
        return -1;
    }
}
