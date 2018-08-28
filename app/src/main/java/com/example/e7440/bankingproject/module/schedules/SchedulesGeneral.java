package com.example.e7440.bankingproject.module.schedules;

import com.example.e7440.bankingproject.module.base.BaseView;
import com.example.e7440.bankingproject.module.model.Schedules;
import com.example.e7440.bankingproject.module.model.Summary;

import java.util.List;

public interface SchedulesGeneral {

    interface SchedulesView extends BaseView{

        void getData(List<Schedules> schedules);

        void noData();

        void getSumary(Summary summary);
    }

    interface SchedulesPresenterImpl{

        void fetchData();
    }
}
