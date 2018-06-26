package com.example.e7440.bankingproject.module.tab.personal;

import com.example.e7440.bankingproject.module.base.BaseView;
import com.example.e7440.bankingproject.module.model.DetailTab;

import java.util.List;

public interface PersonalGeneral {
    interface PersonalView extends BaseView{
        void fetchTab(List<DetailTab> mDetailTabs);
    }
    interface PersonalPresenter{
        void getTab(String url);
    }
}
