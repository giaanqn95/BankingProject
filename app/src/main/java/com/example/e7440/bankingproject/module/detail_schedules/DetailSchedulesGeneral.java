package com.example.e7440.bankingproject.module.detail_schedules;

import com.example.e7440.bankingproject.module.base.BaseView;
import com.example.e7440.bankingproject.module.model.DetailUser;

import org.json.JSONObject;

import okhttp3.MultipartBody;

public interface DetailSchedulesGeneral {

    interface DetailSchedulesView extends BaseView {
        void postDataBasicSuccess();

        void postDataDetailSuccess();

        void getDetailSuccess(DetailUser detailUser);

        void postSaveImageSuccess();
    }

    interface DetailSchedulesPresenter {
        void postBasicInfo(String secret, String id_customer, JSONObject jsonObject);

        void postDetailInfo(String secret, String id_customer, JSONObject jsonObject);

        void getDetail(String id_customer, String secret);

        void postSaveImage(String id_customer, String secret, MultipartBody.Part avatar);
    }
}
