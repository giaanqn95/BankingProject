package com.example.e7440.bankingproject.module.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.e7440.bankingproject.R;
import com.example.e7440.bankingproject.components.SessionManagerUser;
import com.example.e7440.bankingproject.module.base.BaseActivity;
import com.example.e7440.bankingproject.module.introduce.IntroduceActivity;
import com.example.e7440.bankingproject.module.model.User;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by E7440 on 6/12/2018.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener, LoginGeneral.LoginView {

    @BindView(R.id.et_username)
    EditText mEditTextUsername;
    @BindView(R.id.et_password)
    EditText mEditTextPassword;
    @BindView(R.id.btn_login)
    Button mButtonLogin;

    private User user;
    private String username = "", password = "";

    private LoginPresenterImpl loginPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initPresenter();
        initView();
        checkLogin();
        setView();
    }

    private void initPresenter() {
        loginPresenter = new LoginPresenterImpl(this);
    }

    private void initView() {
        mButtonLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                loginPresenter.callLogin(username, password);
                break;
        }
    }

    public void setView() {
        mEditTextUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                username = editable.toString();
                if (editable.length() < 3 || password.length() < 5) {
                    mButtonLogin.setEnabled(false);
                    mButtonLogin.setBackground(getResources().getDrawable(R.drawable.background_border_button_grey));
                } else {
                    mButtonLogin.setEnabled(true);
                    mButtonLogin.setBackground(getResources().getDrawable(R.drawable.background_border_button));
                }
            }
        });
        mEditTextPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                password = editable.toString();
                if (editable.length() < 5 || username.length() < 3) {
                    mButtonLogin.setEnabled(false);
                    mButtonLogin.setBackground(getResources().getDrawable(R.drawable.background_border_button_grey));
                } else {
                    mButtonLogin.setEnabled(true);
                    mButtonLogin.setBackground(getResources().getDrawable(R.drawable.background_border_button));
                }
            }
        });
    }

    private void checkLogin() {
        if (SessionManagerUser.getInstance().isLoggedIn()) {
            Intent intent = new Intent(LoginActivity.this, IntroduceActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void loginSuccess(User user) {
        SessionManagerUser.getInstance().createLoginSession(user);
        Intent intent = new Intent(LoginActivity.this, IntroduceActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void loginFail() {
        showDialogError(R.string.error_login_fail);
    }
}
