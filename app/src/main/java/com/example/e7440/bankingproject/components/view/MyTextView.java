package com.example.e7440.bankingproject.components.view;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class MyTextView extends LinearLayout {
    android.widget.TextView mTextView;

    public MyTextView(Context context){
        super(context);
    }

    public MyTextView(Context context, String label, Boolean isBold) {
        super(context);
        mTextView = new android.widget.TextView(context);
        mTextView.setText(label);
        mTextView.setTextSize(18);
        mTextView.setGravity(Gravity.RIGHT);
        if(isBold)
            mTextView.setTypeface(null, Typeface.BOLD);
        LinearLayout.LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 0, 0, 15);
        mTextView.setLayoutParams(layoutParams);
        this.addView(mTextView);
    }

    public String getString() {
        return mTextView.getText().toString();
    }
}
