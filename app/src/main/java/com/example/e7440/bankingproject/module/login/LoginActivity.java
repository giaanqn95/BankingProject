package com.example.e7440.bankingproject.module.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.e7440.bankingproject.R;
import com.example.e7440.bankingproject.components.SessionManagerUser;
import com.example.e7440.bankingproject.module.base.BaseActivity;
import com.example.e7440.bankingproject.module.introduce.IntroduceActivity;
import com.example.e7440.bankingproject.module.main.MainActivity;
import com.example.e7440.bankingproject.module.model.User;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by E7440 on 6/12/2018.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.et_username)
    EditText mEditTextUsername;
    @BindView(R.id.et_password)
    EditText mEditTextPassword;
    @BindView(R.id.btn_login)
    Button mButtonLogin;

    private User user;
    private String username, password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mButtonLogin.setOnClickListener(this);
        checkLogin();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                username = mEditTextUsername.getText().toString();
                password = mEditTextPassword.getText().toString();
                if (username.isEmpty()) {
                    showDialogError(R.string.error_login_user);
                    mEditTextUsername.requestFocus();
                    return;
                }
                if (password.isEmpty()) {
                    showDialogError(R.string.error_login_password);
                    mEditTextPassword.requestFocus();
                    return;
                }
                if (password.length() < 8) {
                    showDialogError(R.string.error_login_short_password);
                    mEditTextPassword.requestFocus();
                    return;
                }
                if (!username.equals("andeptrai")) {
                    showDialogError(R.string.error_login_fail);
                    return;
                }
                if (!password.equals("andeptrai")) {
                    showDialogError(R.string.error_login_fail);
                    return;
                }
                user = new User("andeptrai", "andeptrai");
                SessionManagerUser.getInstance().createLoginSession(user);
                Intent intent = new Intent(LoginActivity.this, IntroduceActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
    private void checkLogin(){
        if (SessionManagerUser.getInstance().isLoggedIn()){
            Intent intent = new Intent(LoginActivity.this, IntroduceActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
