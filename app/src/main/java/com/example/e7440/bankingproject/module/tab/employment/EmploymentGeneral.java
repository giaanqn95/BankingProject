package com.example.e7440.bankingproject.module.tab.employment;

import com.example.e7440.bankingproject.module.base.BaseView;
import com.example.e7440.bankingproject.module.model.DetailTab;

import java.util.List;

public interface EmploymentGeneral {
    interface EmploymentView extends BaseView{
        void fetchTab(List<DetailTab> mExampleList);
    }
    interface EmploymentPresenter{
        void getTab(String url);
    }
}
