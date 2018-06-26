package com.example.e7440.bankingproject.components.view;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.example.e7440.bankingproject.R;

public class MyCheckBox extends LinearLayout {
    CheckBox checkBox;
    MyTextView myTextView;
    public MyCheckBox(Context ctx, String label)
    {
        super(ctx);
        checkBox = new CheckBox(ctx);
        checkBox.setText(label.replace(":", ""));
        checkBox.setTextSize(18);
        checkBox.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        checkBox.setTextColor(ContextCompat.getColor(ctx, R.color.colorAccent));
        checkBox.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        this.addView(checkBox);
    }

    public String isChecked() {
        return String.valueOf(checkBox.isChecked());
    }
}

