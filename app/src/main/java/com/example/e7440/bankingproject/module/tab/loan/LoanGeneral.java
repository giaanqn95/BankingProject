package com.example.e7440.bankingproject.module.tab.loan;

import com.example.e7440.bankingproject.module.base.BaseView;
import com.example.e7440.bankingproject.module.model.DetailTab;
import com.example.e7440.bankingproject.module.model.Tab;

import java.util.List;

/**
 * Created by E7440 on 6/11/2018.
 */

public interface LoanGeneral {
    interface TabView extends BaseView {
        void fetchTab(List<DetailTab> mExampleList);

    }
    interface TabPresenter{
        void getTab(String url);
    }
}
