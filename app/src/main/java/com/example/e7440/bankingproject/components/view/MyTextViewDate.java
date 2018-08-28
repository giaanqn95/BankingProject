package com.example.e7440.bankingproject.components.view;

import android.app.DatePickerDialog;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.e7440.bankingproject.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MyTextViewDate extends LinearLayout{
    TextView tvDate;
    private DatePickerDialog mDatePickerDialog;
    MyTextView myTextView;

    public MyTextViewDate(Context ctx)
    {
        super(ctx);
    }
    public MyTextViewDate(Context ctx, String label, String content)
    {
        super(ctx);
        LinearLayout.LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 0, 0, 10);
        tvDate = new TextView(getContext());
        tvDate.setTextSize(18);
        tvDate.setHint(content);
        tvDate.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
        tvDate.setGravity(Gravity.CENTER);
        tvDate.setLayoutParams(layoutParams);
        final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        Calendar calendar = Calendar.getInstance();
        mDatePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                tvDate.setText(dateFormatter.format(newDate.getTime()));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
//        mDatePickerDialog.setTitle("Please Choose The Day Of Your Last Pay");

        tvDate.setOnClickListener(v -> mDatePickerDialog.show());

        myTextView = new MyTextView(ctx, label, false);
        this.addView(myTextView);
        this.addView(tvDate);
    }

    public String getValue()
    {
        return tvDate.getText().toString();
    }
    public String getLabel() {
        return myTextView.getString();
    }
}