package com.example.e7440.bankingproject.module.main;

import com.example.e7440.bankingproject.module.base.BaseView;
import com.example.e7440.bankingproject.module.model.Tab;

/**
 * Created by E7440 on 6/12/2018.
 */

public interface MainGeneral {

    interface MainView extends BaseView{
        void getMain(Tab example);
    }

    interface MainPresenter {
        void getDataMain();
    }
}
