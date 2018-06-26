package com.example.e7440.bankingproject.module.tab.contact;

import com.example.e7440.bankingproject.module.base.BaseView;
import com.example.e7440.bankingproject.module.model.DetailTab;

import java.util.List;

public interface ContactGeneral {
    interface ContactView extends BaseView{
        void fetchTab(List<DetailTab> mExampleList);
    }
    interface ContactPresenter{
        void getTab(String url);
    }
}
