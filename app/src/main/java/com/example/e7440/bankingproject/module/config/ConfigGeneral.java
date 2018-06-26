package com.example.e7440.bankingproject.module.config;

import com.example.e7440.bankingproject.module.base.BaseView;
import com.example.e7440.bankingproject.module.model.Tab;

/**
 * Created by E7440 on 6/11/2018.
 */

public interface ConfigGeneral {
    interface ConfigPresenter {
        void getConfig();
    }

    interface ConfigView extends BaseView {
        void getDataConfig(Tab mExample);
    }
}
