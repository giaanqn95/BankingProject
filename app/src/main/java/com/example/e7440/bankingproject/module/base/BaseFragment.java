package com.example.e7440.bankingproject.module.base;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.e7440.bankingproject.R;
import com.example.e7440.bankingproject.components.InternetConnection;
import com.example.e7440.bankingproject.components.message_dialog.DialogResultItem;
import com.example.e7440.bankingproject.components.message_dialog.MessageDialogHelper;
import com.example.e7440.bankingproject.components.message_dialog.MessageDialogManger;
import com.example.e7440.bankingproject.components.message_dialog.OnClickDialogListener;
import com.example.e7440.bankingproject.components.message_dialog.TypeMessageDialog;
import com.example.e7440.bankingproject.module.model.Item;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by E7440 on 6/11/2018.
 */

public abstract class BaseFragment extends Fragment implements BaseView, OnClickDialogListener, View.OnClickListener {
    protected View rootView;
    protected final int DIALOG_LOADING = 540;
    protected final int DIALOG_ERROR = 541;
    protected final int DIALOG_SUCCESS = 542;
    protected final int DIALOG_LOGIN_AGAIN = 543;
    protected final int DIALOG_YESNO = 544;
    protected MessageDialogManger mMessageDialogManger;
    protected ProgressDialog progressDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMessageDialogManger = new MessageDialogManger();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = onCreateView(inflater, container);
        ButterKnife.bind(this, rootView);
        initPresenter();
        setDataToUI();
        addActionClickListener();
        return rootView;
    }

    @Override
    public void showDialogLogin(int resId) {
        showDialogLogin(DIALOG_LOGIN_AGAIN, getString(resId));
    }

    protected abstract View onCreateView(LayoutInflater inflater, ViewGroup container);

    protected abstract void setDataToUI();

    protected abstract void addActionClickListener();


    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.bind(getActivity()).unbind();
    }

    protected abstract void initPresenter();

    protected void showDialogYesNo(Integer dialogId, String content) {
        mMessageDialogManger.onCreate(new MessageDialogHelper.MessageDialogBuilder()
                .setTypeMessageDialog(TypeMessageDialog.MESSAGEDIALOG_TYPE_YESNO)
                .setDialogId(dialogId)
                .setTitleDialog(content)
                .setOnClickDialogListener(this)
                .build(getActivity()));
        mMessageDialogManger.onShow();
    }

    protected void showDialogLoading(Integer dialogId, String content) {
        mMessageDialogManger.onCreate(new MessageDialogHelper.MessageDialogBuilder()
                .setTypeMessageDialog(TypeMessageDialog.MESSAGEDIALOG_TYPE_LOADING)
                .setDialogId(dialogId)
                .setTitleDialog(getResources().getString(R.string.dialog_title))
                .setContentDialog(content)
                .setOnClickDialogListener(this)
                .build(getActivity()));
        mMessageDialogManger.onShow();
    }

    protected void showDialogError(Integer dialogId, String content) {
        mMessageDialogManger.onCreate(new MessageDialogHelper.MessageDialogBuilder()
                .setTypeMessageDialog(TypeMessageDialog.MESSAGEDIALOG_TYPE_ERROR)
                .setDialogId(dialogId)
                .setContentDialog(content)
                .setOnClickDialogListener(this)
                .build(getActivity()));
        mMessageDialogManger.onShow();
    }

    protected void showDialogLogin(Integer dialogId, String content) {
        mMessageDialogManger.onCreate(new MessageDialogHelper.MessageDialogBuilder()
                .setTypeMessageDialog(TypeMessageDialog.MESSAGEDIALOG_TYPE_ERROR)
                .setDialogId(dialogId)
                .setContentDialog(content)
                .setOnClickDialogListener(this)
                .build(getActivity()));
        mMessageDialogManger.onShow();
    }

    protected void showDialogSuccess(Integer dialogId, String content) {
        mMessageDialogManger.onCreate(new MessageDialogHelper.MessageDialogBuilder()
                .setTypeMessageDialog(TypeMessageDialog.MESSAGEDIALOG_TYPE_SUCCESS)
                .setDialogId(dialogId)
                .setTitleDialog(getResources().getString(R.string.dialog_title))
                .setContentDialog(content)
                .setOnClickDialogListener(this)
                .build(getActivity()));
        mMessageDialogManger.onShow();
    }
    protected void showDialogChooseItem(int dialogId, String header, List<Item> mItemList) {
        mMessageDialogManger.onCreate(new MessageDialogHelper.MessageDialogBuilder()
                .setTypeMessageDialog(TypeMessageDialog.MESSAGE_DIALOG_TYPE_CHOOSE_ITEM)
                .setDialogId(dialogId)
                .setTitleDialog(header)
                .setItemList(mItemList)
                .setOnClickDialogListener(this)
                .build(getActivity()));
        mMessageDialogManger.onShow();
    }

    @Override
    public void onClickDialog(DialogResultItem dialogResulltItem) {
        switch (dialogResulltItem.getDialogId()) {
            case DIALOG_ERROR:
                mMessageDialogManger.onDimiss();
                break;
            case DIALOG_SUCCESS:
                mMessageDialogManger.onDimiss();
                break;
            case DIALOG_LOGIN_AGAIN: {
                mMessageDialogManger.onDimiss();
//                SessionManagerUser.getInstance().logoutUser();
//                Intent intent = new Intent(getActivity(), LoginActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                startActivity(intent);
                break;
            }
        }
    }

    @Override
    public void showDialogError(int resId) {
        showDialogError(DIALOG_ERROR, getResources().getString(resId));
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
        if (InternetConnection.getInstance().isOnline(getActivity())) {
            return true;
        }
        return false;
    }

    @Override
    public void networkConnectError() {
        showDialogError(DIALOG_ERROR, getResources().getString(R.string.error_internet));
    }

    protected void showProgress() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    protected void hideProgress() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
//    public void showDialogYesNo(){
//        showDialogYesNo(DIALOG_YESNO, getResources().getString(R.string.profile_logout));
//    }
}
