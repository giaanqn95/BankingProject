package com.example.e7440.bankingproject.connect_api.responses;

import com.example.e7440.bankingproject.module.model.DetailTab;
import com.example.e7440.bankingproject.module.model.Tab;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseTool {
    @SerializedName("detail_tab")
    protected List<DetailTab> mDetailTab;

    public List<DetailTab> getTab() {
        return mDetailTab;
    }

    public void setTab(List<DetailTab> mDetailTab) {
        this.mDetailTab = mDetailTab;
    }
}
