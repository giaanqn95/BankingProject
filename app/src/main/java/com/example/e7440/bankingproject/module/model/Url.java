package com.example.e7440.bankingproject.module.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by E7440 on 6/11/2018.
 */

public class Url {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("link")
    @Expose
    private String link;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
