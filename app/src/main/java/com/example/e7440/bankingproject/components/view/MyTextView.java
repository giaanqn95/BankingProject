package com.example.e7440.bankingproject.components.view;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class MyTextView extends LinearLayout {
    android.widget.TextView tvLabel;

    public MyTextView(Context context){
        super(context);
    }

    public MyTextView(Context context, String label, Boolean isBold) {
        super(context);
        tvLabel = new android.widget.TextView(context);
        tvLabel.setText(label);
        tvLabel.setTextSize(18);
        tvLabel.setGravity(Gravity.RIGHT);
        if(isBold)
            tvLabel.setTypeface(null, Typeface.BOLD);
        tvLabel.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        this.addView(tvLabel);
    }

    public String getString() {
        return tvLabel.getText().toString();
    }
}
