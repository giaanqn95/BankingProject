package com.example.e7440.bankingproject.components.view;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.e7440.bankingproject.R;
import com.example.e7440.bankingproject.components.NumberTextWatcher;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyEditText extends LinearLayout {
    EditText editText = new EditText(getContext());
    MyTextView myTextView;

    public MyEditText(Context ctx) {
        super(ctx);
    }

    public MyEditText(Context ctx, String label, int type) {
        super(ctx);
        editText.setInputType(type);
        editText.setTextSize(18);
        editText.setGravity(Gravity.CENTER);
        editText.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
        editText.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
        editText.setPadding(0, 5, 0, 0);
        editText.setBackground(getResources().getDrawable(R.drawable.background_border_editext));
        editText.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        myTextView = new MyTextView(ctx, label, false);
        this.addView(myTextView);

        this.addView(editText);
    }

    public MyEditText(Context ctx, String label, int type, Boolean money) {
        super(ctx);
        LinearLayout.LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 5, 0, 0);
        editText.setLayoutParams(layoutParams);
        editText.setInputType(type);
        editText.setTextSize(18);
//        editText.setHeight(50);
        editText.setGravity(Gravity.CENTER);
        editText.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
        editText.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
        editText.setPadding(0, 5, 0, 0);
        editText.setBackground(getResources().getDrawable(R.drawable.background_border_editext));
//        editText.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        editText.addTextChangedListener(new NumberTextWatcher(editText));

        myTextView = new MyTextView(ctx, label, false);
        this.addView(myTextView);

        this.addView(editText);
    }

    public MyEditText(Context ctx, String label, int type, NumberTextWatcher watcher) {
        super(ctx);
        editText.setInputType(type);
        editText.setTextSize(18);
        editText.setGravity(Gravity.CENTER);
        editText.addTextChangedListener(watcher);
        editText.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
        editText.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
        editText.setPadding(0, 5, 0, 0);
        editText.setBackground(getResources().getDrawable(R.drawable.background_border_editext));
        editText.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        myTextView = new MyTextView(ctx, label, false);
        this.addView(myTextView);

        this.addView(editText);
    }

    public MyEditText(Context context, String label, int type, String mail) {
        super(context);
        editText.setInputType(type);
        editText.setTextSize(18);
        editText.setGravity(Gravity.CENTER);
        editText.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
        editText.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
        editText.setPadding(0, 5, 0, 0);
        editText.setBackground(getResources().getDrawable(R.drawable.background_border_editext));
        editText.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        myTextView = new MyTextView(context, label, false);
        this.addView(myTextView);

        this.addView(editText);
    }

    public void setValue(String a) {
        editText.setText(a);
    }

    public void setEnabled(boolean isEnabled) {
        editText.setEnabled(isEnabled);
        editText.setFocusable(false);
    }

    public String getValue() {
        return editText.getText().toString();
    }

    public String getType() {
        String value = "";
        if (editText.getInputType() == InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS) {
            value = editText.getText().toString();
            return value;
        }
        return value;
    }

    public String getLabel() {
        return myTextView.getString();
    }


    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean isValidPhone(String phone) {
        if (phone.length() < 0) {
            return false;
        } else if (phone.length() > 11) {
            return false;
        } else if (phone.length() < 10) {
            return false;
        }
        return true;
    }
}
