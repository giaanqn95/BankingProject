package com.example.e7440.bankingproject.module.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.example.e7440.bankingproject.R;
import com.example.e7440.bankingproject.components.InternetConnection;
import com.example.e7440.bankingproject.components.message_dialog.DialogResultItem;
import com.example.e7440.bankingproject.components.message_dialog.MessageDialogHelper;
import com.example.e7440.bankingproject.components.message_dialog.MessageDialogManger;
import com.example.e7440.bankingproject.components.message_dialog.OnClickDialogListener;
import com.example.e7440.bankingproject.components.message_dialog.TypeMessageDialog;
import com.example.e7440.bankingproject.module.model.Item;

import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by E7440 on 6/11/2018.
 */

public class BaseActivity extends AppCompatActivity implements OnClickDialogListener, BaseView {
    protected final int DIALOG_LOADING = 540;
    protected final int DIALOG_ERROR = 541;
    protected final int DIALOG_SUCCESS = 542;
    protected final int DIALOG_LOGIN_AGAIN = 543;
    protected final int DIALOG_YESNO = 544;
    protected final int DIALOG_IMAGE = 545;
    protected MessageDialogManger mMessageDialogManger;
    protected ProgressDialog progressDialog;

    protected BaseApplication mMyApp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMyApp = (BaseApplication) this.getApplicationContext();
        mMessageDialogManger = new MessageDialogManger();
    }

    private void clearReferences(){
        Activity currActivity = mMyApp.getCurrentActivity();
        Log.d("clearReferences",currActivity + "" );
        if (this.equals(currActivity))
            mMyApp.setCurrentActivity(null);
    }

    protected void onResume() {
        super.onResume();
        mMyApp.setCurrentActivity(this);
    }
    protected void onPause() {
        clearReferences();
        super.onPause();
    }
    protected void onDestroy() {
        clearReferences();
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    protected void showDialogLoading(Integer dialogId, String content) {
        mMessageDialogManger.onCreate(new MessageDialogHelper.MessageDialogBuilder()
                .setTypeMessageDialog(TypeMessageDialog.MESSAGEDIALOG_TYPE_LOADING)
                .setDialogId(dialogId)
                .setTitleDialog(getResources().getString(R.string.dialog_title))
                .setContentDialog(content)
                .setOnClickDialogListener(this)
                .build(this));
        mMessageDialogManger.onShow();
    }

    protected void showDialogError(Integer dialogId, String content) {
        mMessageDialogManger.onCreate(new MessageDialogHelper.MessageDialogBuilder()
                .setTypeMessageDialog(TypeMessageDialog.MESSAGEDIALOG_TYPE_ERROR)
                .setDialogId(dialogId)
                .setContentDialog(content)
                .setOnClickDialogListener(this)
                .build(this));
        mMessageDialogManger.onShow();
    }

    protected void showDialogSuccess(Integer dialogId, String content) {
        mMessageDialogManger.onCreate(new MessageDialogHelper.MessageDialogBuilder()
                .setTypeMessageDialog(TypeMessageDialog.MESSAGEDIALOG_TYPE_SUCCESS)
                .setDialogId(dialogId)
                .setTitleDialog(getResources().getString(R.string.dialog_title))
                .setContentDialog(content)
                .setOnClickDialogListener(this)
                .build(this));
        mMessageDialogManger.onShow();
    }

    protected void showDialogYesNo(Integer dialogId, String content) {
        mMessageDialogManger.onCreate(new MessageDialogHelper.MessageDialogBuilder()
                .setTypeMessageDialog(TypeMessageDialog.MESSAGEDIALOG_TYPE_YESNO)
                .setDialogId(dialogId)
                .setTitleDialog(content)
                .setOnClickDialogListener(this)
                .build(this));
        mMessageDialogManger.onShow();
    }

    protected void showDialogChooseItem(int dialogId, String header, List<Item> mItemList) {
        mMessageDialogManger.onCreate(new MessageDialogHelper.MessageDialogBuilder()
                .setTypeMessageDialog(TypeMessageDialog.MESSAGE_DIALOG_TYPE_CHOOSE_ITEM)
                .setDialogId(dialogId)
                .setTitleDialog(header)
                .setItemList(mItemList)
                .setOnClickDialogListener(this)
                .build(this));
        mMessageDialogManger.onShow();
    }

    protected void showDialogLogin(Integer dialogId, String content) {
        mMessageDialogManger.onCreate(new MessageDialogHelper.MessageDialogBuilder()
                .setTypeMessageDialog(TypeMessageDialog.MESSAGEDIALOG_TYPE_ERROR)
                .setDialogId(dialogId)
                .setContentDialog(content)
                .setOnClickDialogListener(this)
                .build(this));
        mMessageDialogManger.onShow();
    }

    protected void showDialogImage(Integer dialogId, String content){
        mMessageDialogManger.onCreate(new MessageDialogHelper.MessageDialogBuilder()
                .setTypeMessageDialog(TypeMessageDialog.MESSAGE_DIALOG_TYPE_SHOW_IMAGE)
                .setDialogId(dialogId)
                .setContentDialog(content)
                .setOnClickDialogListener(this)
                .build(this));
        mMessageDialogManger.onShow();
    }

    @Override
    public void showDialogError(int resId) {
        showDialogError(DIALOG_ERROR, getResources().getString(resId));
    }

    @Override
    public void showDialogLogin(int resId) {
        showDialogLogin(DIALOG_LOGIN_AGAIN, getString(resId));
    }

    @Override
    public void showDialogLoading() {
        showDialogLoading(DIALOG_LOADING, "Loading");
    }

    @Override
    public void hideDialogLoading() {
        mMessageDialogManger.onDimiss();
    }

    @Override
    public void showProgressDialog() {
        showProgress();
    }

    @Override
    public void hideProgressDialog() {
        hideProgress();
    }

    @Override
    public boolean isNetworkConnect() {
        if (InternetConnection.getInstance().isOnline(this)) {
            return true;
        }
        return false;
    }

    @Override
    public void networkConnectError() {
        showDialogError(DIALOG_ERROR, getResources().getString(R.string.error_internet));
    }

    protected void showProgress() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    protected void hideProgress() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void onClickDialog(DialogResultItem dialogResultItem) {
        switch (dialogResultItem.getDialogId()) {
            case DIALOG_ERROR:
                mMessageDialogManger.onDimiss();
                break;
            case DIALOG_SUCCESS:
                mMessageDialogManger.onDimiss();
                break;
            case DIALOG_LOGIN_AGAIN: {
                mMessageDialogManger.onDimiss();
//                SessionManagerUser.getInstance().logoutUser();
//                Intent intent = new Intent(this, LoginActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                startActivity(intent);
                break;
            }
            case DIALOG_IMAGE:{
                mMessageDialogManger.onDimiss();
                break;
            }

        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


}