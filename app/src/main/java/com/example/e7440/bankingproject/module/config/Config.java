package com.example.e7440.bankingproject.module.config;

import com.example.e7440.bankingproject.module.model.DetailTab;
import com.example.e7440.bankingproject.module.model.Tab;
import com.example.e7440.bankingproject.module.model.Toolbar;
import com.example.e7440.bankingproject.module.model.Url;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by E7440 on 6/11/2018.
 */

public class Config {
    private static Config instance = null;
    private List<Toolbar> mToolbarsList;
    private List<Url> mUrlList;

    private Config() {
        mToolbarsList = new ArrayList<>();
        mUrlList = new ArrayList<>();
    }

    public static Config getInstance() {
        if (instance == null) {
            instance = new Config();
        }
        return instance;
    }

    public void saveData(Tab example) {
        if (example != null) {
            mUrlList.clear();
            mToolbarsList.clear();
            mToolbarsList.addAll(example.getToolbar());
            mUrlList.addAll(example.getUrl());
        }
    }

    public List<Toolbar> getmToolbarsList() {
        return mToolbarsList;
    }

    public List<Url> getmUrlList() {
        return mUrlList;
    }
}
