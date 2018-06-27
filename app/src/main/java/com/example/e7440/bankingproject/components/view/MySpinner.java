package com.example.e7440.bankingproject.components.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.e7440.bankingproject.R;

import java.util.Arrays;

public class MySpinner extends LinearLayout {
    ArrayAdapter<String> arrayAdapter;
    Spinner spinner;
    MyTextView myTextView;
    public MySpinner(Context ctx, String label, String value)
    {
        super(ctx);
        spinner = new Spinner(getContext());
        LinearLayout.LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.setMargins(0,10,0,0);
        spinner.setLayoutParams(layoutParams);
        spinner.setTextAlignment(TEXT_ALIGNMENT_CENTER);
        String[] arr = value.split(",");
        Arrays.sort(arr);
        arrayAdapter = new ArrayAdapter<>(ctx,
                android.R.layout.simple_spinner_dropdown_item, arr);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinner.setAdapter(arrayAdapter);


        myTextView = new MyTextView(ctx, label, false);
        this.addView(myTextView);
        this.addView(spinner);
    }

    public String getValue()
    {
        return spinner.getSelectedItem().toString();
    }
    public String getLabel(){
        return myTextView.getString();
    }
}
