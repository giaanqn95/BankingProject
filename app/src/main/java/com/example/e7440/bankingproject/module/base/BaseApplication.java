package com.example.e7440.bankingproject.module.base;

import android.app.Activity;
import android.app.Application;


import java.util.Locale;


/**
 * Created by E7440 on 6/11/2018.
 */

public class BaseApplication extends Application {
    private static Application instance;

    public static Application getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        String locale= Locale.getDefault().getLanguage();
    }

    private Activity mCurrentActivity = null;
    public Activity getCurrentActivity(){
        return mCurrentActivity;
    }
    public void setCurrentActivity(Activity mCurrentActivity){
        this.mCurrentActivity = mCurrentActivity;
    }


}
