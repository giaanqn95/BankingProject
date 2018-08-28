package com.example.e7440.bankingproject.module.schedules;

import com.example.e7440.bankingproject.R;
import com.example.e7440.bankingproject.connect_api.api.ApiFunction;
import com.example.e7440.bankingproject.connect_api.api.ApiStatus;
import com.example.e7440.bankingproject.connect_api.responses.BaseResponseList;
import com.example.e7440.bankingproject.module.base.BasePresenter;
import com.example.e7440.bankingproject.module.model.Schedules;
import com.example.e7440.bankingproject.module.model.Summary;

public class SchedulesPresenterImpl extends BasePresenter<SchedulesGeneral.SchedulesView> implements SchedulesGeneral.SchedulesPresenterImpl {

    public SchedulesPresenterImpl(SchedulesGeneral.SchedulesView mView) {
        super(mView);
    }

    @Override
    public void onResponseListener(ApiFunction apiFunction, ApiStatus statusId, Object object) {
        switch (apiFunction) {
            case GET_SURVEY: {
                switch (statusId) {
                    case CALL_API_RESULT_SUCCESS: {
                        BaseResponseList<Schedules> schedulesBaseResponseList = (BaseResponseList<Schedules>) object;
                        Summary summary = ((BaseResponseList<Schedules>) object).getSummary();
                        getmView().getSumary(summary);
                        getmView().getData(schedulesBaseResponseList.getListData());
                        break;
                    }
                    case CALL_API_RESULT_FAILURE: {
                        getmView().showDialogError(R.string.error_002);
                        break;
                    }
                }
                break;
            }
        }
    }

    @Override
    public void fetchData() {
        if (getmView().isNetworkConnect()) {
            mApiMethod.getSurvey();
        } else {
            getmView().networkConnectError();
        }
    }
}
