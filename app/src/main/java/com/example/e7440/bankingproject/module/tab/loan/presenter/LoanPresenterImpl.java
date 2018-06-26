package com.example.e7440.bankingproject.module.tab.loan.presenter;

import com.example.e7440.bankingproject.R;
import com.example.e7440.bankingproject.connect_api.api.ApiFunction;
import com.example.e7440.bankingproject.connect_api.api.ApiStatus;
import com.example.e7440.bankingproject.connect_api.responses.BaseResponseList;
import com.example.e7440.bankingproject.connect_api.responses.ResponseTool;
import com.example.e7440.bankingproject.module.base.BasePresenter;
import com.example.e7440.bankingproject.module.model.DetailTab;
import com.example.e7440.bankingproject.module.model.Tab;
import com.example.e7440.bankingproject.module.tab.loan.LoanGeneral;

/**
 * Created by E7440 on 6/11/2018.
 */

public class LoanPresenterImpl extends BasePresenter<LoanGeneral.TabView> implements LoanGeneral.TabPresenter {

    public LoanPresenterImpl(LoanGeneral.TabView mView) {
        super(mView);
    }

    @Override
    public void onResponseListener(ApiFunction apiFunction, ApiStatus statusId, Object object) {
        switch (apiFunction) {
            case GET_TAB_LOAN: {
                switch (statusId) {
                    case CALL_API_RESULT_SUCCESS: {
                        ResponseTool responseTool = (ResponseTool) object;
                        getmView().fetchTab(responseTool.getTab());
                        break;
                    }
                    case CALL_API_RESULT_FAILURE: {
//                        int errorCode = (int) object;
                        getmView().showDialogError(R.string.error_002);
                        break;
                    }
//                    case CALL_API_RESULT_TIMEOUT:{
//                        String error = (String) object;
//                        break;
//                    }
                }
                break;
            }
        }
    }

    @Override
    public void getTab(String url) {
        if (getmView().isNetworkConnect()) {
            mApiMethod.getTabLoan(url);
        } else {
            getmView().networkConnectError();
        }
    }
}
