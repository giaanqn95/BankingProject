package com.example.e7440.bankingproject.components.message_dialog;

import java.util.Stack;

/**
 * Created by Kanet tran on 5/15/2017.
 */

public class MessageDialogManger {

    private Stack<MessageDialogHelper> mMessageDialogHelperQueue;

    public MessageDialogManger() {
        mMessageDialogHelperQueue = new Stack<>();
    }

    public void onCreate(MessageDialogHelper messageDialogHelper) {
        mMessageDialogHelperQueue.push(messageDialogHelper);
    }

    public void onToast(MessageDialogHelper messageDialogHelper) {

    }


    public void onShow() {
        mMessageDialogHelperQueue.peek().onShow();
    }

    public void onDimiss() {
        if (mMessageDialogHelperQueue.size() > 0)
            mMessageDialogHelperQueue.pop().onDimiss();
    }

}
