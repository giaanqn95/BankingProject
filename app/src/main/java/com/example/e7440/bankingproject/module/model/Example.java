package com.example.e7440.bankingproject.module.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Example {


    @SerializedName("tab")
    @Expose
    private Tab tab;

    public Tab getTab() {
        return tab;
    }

    public void setTab(Tab tab) {
        this.tab = tab;
    }
}
