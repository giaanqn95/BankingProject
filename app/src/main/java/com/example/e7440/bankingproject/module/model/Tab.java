package com.example.e7440.bankingproject.module.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by E7440 on 6/11/2018.
 */

public class Tab {
    @SerializedName("toolbar")
    @Expose
    private List<Toolbar> toolbar = new ArrayList<>();
    @SerializedName("url")
    @Expose
    private List<Url> url = new ArrayList();

    public List<Toolbar> getToolbar() {
        return toolbar;
    }

    public void setToolbar(List<Toolbar> toolbar) {
        this.toolbar = toolbar;
    }

    public List<Url> getUrl() {
        return url;
    }

    public void setUrl(List<Url> url) {
        this.url = url;
    }

}
