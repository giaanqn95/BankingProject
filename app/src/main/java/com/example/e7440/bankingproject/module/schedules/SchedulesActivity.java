package com.example.e7440.bankingproject.module.schedules;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e7440.bankingproject.R;
import com.example.e7440.bankingproject.components.SessionManagerUser;
import com.example.e7440.bankingproject.components.message_dialog.DialogResultItem;
import com.example.e7440.bankingproject.module.base.BaseActivity;
import com.example.e7440.bankingproject.module.detail_schedules.DetailSchedulesActivity;
import com.example.e7440.bankingproject.module.login.LoginActivity;
import com.example.e7440.bankingproject.module.model.Schedules;
import com.example.e7440.bankingproject.module.model.Summary;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SchedulesActivity extends BaseActivity implements SchedulesGeneral.SchedulesView {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private NavigationView mNavigationView;
    private Boolean openNavigation = false;

    android.support.v7.widget.SearchView mSearchView;
    ImageView mImageViewMenu, mImageViewAvatar;
    TextView mTextViewWaiting, mTextViewProgress, mTextViewDone, mTextViewName, mTextViewPosition, mTextViewTitle;
    RecyclerView mRecyclerView;
    List<Schedules> schedulesList = new ArrayList<>();
    List<Schedules> schedulesSave = new ArrayList<>();
    List<Schedules> schedulesFilter = new ArrayList<>();
    SchedulesAdapter schedulesAdapter;
    SchedulesPresenterImpl schedulesPresenter;

    private HashMap<String, String> dataUser = new HashMap<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedules);
        initPresenter();
        initView();
        clickAdapter();
        clickView();
    }

    public void initPresenter() {
        schedulesPresenter = new SchedulesPresenterImpl(this);
        schedulesPresenter.fetchData();
    }

    public void initView() {
        dataUser = SessionManagerUser.getInstance().getUserDetails();

        mDrawerLayout = findViewById(R.id.dl_main);
        mNavigationView = findViewById(R.id.nv);
        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.Open, R.string.Close);
        mDrawerLayout.addDrawerListener(mActionBarDrawerToggle);
        mActionBarDrawerToggle.syncState();

        View view = mNavigationView.getHeaderView(0);
        mImageViewAvatar = view.findViewById(R.id.iv_drawer);
        mTextViewName = view.findViewById(R.id.tv_drawer_name);
        mTextViewPosition = view.findViewById(R.id.tv_drawer_position);

        mSearchView = findViewById(R.id.sv_schedules);
        mImageViewMenu = findViewById(R.id.iv_menu);
        mTextViewTitle = findViewById(R.id.tv_title_schedules);
        mTextViewWaiting = findViewById(R.id.tv_waiting);
        mTextViewProgress = findViewById(R.id._tv_progress);
        mTextViewDone = findViewById(R.id.tv_done);
        mRecyclerView = findViewById(R.id.rv_schedules);
        mTextViewWaiting.setEnabled(false);

        schedulesAdapter = new SchedulesAdapter(this, schedulesFilter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(schedulesAdapter);
        schedulesAdapter.notifyDataSetChanged();

        mTextViewName.setText(dataUser.get(SessionManagerUser.KEY_USERNAME));
    }

    public void filter(String status) {
        schedulesFilter.clear();
        for (Schedules mSchedules : schedulesList) {
            if (mSchedules.getStatus().equals(status)) {
                schedulesSave.add(mSchedules);
            }
        }
        schedulesFilter.addAll(schedulesSave);
        schedulesAdapter.notifyDataSetChanged();
    }

    public void clickView() {
        mSearchView.setOnSearchClickListener(view -> {
            mTextViewTitle.setVisibility(View.GONE);
            mImageViewMenu.setVisibility(View.GONE);
        });

        mSearchView.setOnCloseListener(() -> {
            mTextViewTitle.setVisibility(View.VISIBLE);
            mImageViewMenu.setVisibility(View.VISIBLE);
            mSearchView.getQuery();
            return false;
        });

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("getFilterSubmit", query);
                schedulesAdapter.getFilter(query);
                schedulesAdapter.notifyDataSetChanged();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("getFilterChange", newText);
                schedulesAdapter.getFilter(newText);
                schedulesAdapter.notifyDataSetChanged();
                return false;
            }
        });

        mTextViewWaiting.setOnTouchListener((view, motionEvent) -> {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN: {
                    mTextViewWaiting.setBackground(getResources().getDrawable(R.drawable.background_border_button_click));
                    break;
                }
                case MotionEvent.ACTION_UP: {
                    mTextViewWaiting.setBackground(getResources().getDrawable(R.drawable.background_border_button_waiting));
                    break;
                }
            }
            return false;
        });

        mTextViewProgress.setOnTouchListener((view, motionEvent) -> {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN: {
                    mTextViewProgress.setBackground(getResources().getDrawable(R.drawable.background_border_button_click));
                    break;
                }
                case MotionEvent.ACTION_UP: {
                    mTextViewProgress.setBackground(getResources().getDrawable(R.drawable.background_border_button_progress));
                    break;
                }
            }
            return false;
        });

        mTextViewDone.setOnTouchListener((view, motionEvent) -> {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN: {
                    mTextViewDone.setBackground(getResources().getDrawable(R.drawable.background_border_button_click));
                    break;
                }
                case MotionEvent.ACTION_UP: {
                    mTextViewDone.setBackground(getResources().getDrawable(R.drawable.background_border_button_done));
                    break;
                }
            }
            return false;
        });

        mTextViewWaiting.setOnClickListener(view -> {
            filter("waiting");
            schedulesSave.clear();
            mTextViewWaiting.setEnabled(false);
            mTextViewProgress.setEnabled(true);
            mTextViewDone.setEnabled(true);
        });

        mTextViewProgress.setOnClickListener(view -> {
            filter("in_progress");
            schedulesSave.clear();
            mTextViewWaiting.setEnabled(true);
            mTextViewProgress.setEnabled(false);
            mTextViewDone.setEnabled(true);
        });

        mTextViewDone.setOnClickListener(view -> {
            filter("done");
            schedulesSave.clear();
            mTextViewWaiting.setEnabled(true);
            mTextViewProgress.setEnabled(true);
            mTextViewDone.setEnabled(false);
        });

        mNavigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            switch (id) {
                case R.id.account:
                    Toast.makeText(SchedulesActivity.this, getResources().getString(R.string.schedules_user_info), Toast.LENGTH_SHORT).show();
                    break;
                case R.id.settings:
                    Toast.makeText(SchedulesActivity.this, getResources().getString(R.string.schedules_change_password), Toast.LENGTH_SHORT).show();
                    break;
                case R.id.logout:
                    showDialogYesNo(DIALOG_YESNO, getResources().getString(R.string.log_out));
                    break;
                default:
                    return true;
            }
            return true;
        });

        mImageViewMenu.setOnClickListener(view -> {
            mDrawerLayout.openDrawer(Gravity.START);
            openNavigation = true;
        });
    }

    public void clickAdapter() {
        schedulesAdapter.setOnItemClickListener((schedules, position) -> {
            Intent intent = new Intent(SchedulesActivity.this, DetailSchedulesActivity.class);
            EventBus.getDefault().postSticky(schedules);
            startActivity(intent);
            finish();
        });
    }


    @Override
    public void getData(List<Schedules> schedules) {
        if (schedules.size() > 0) {
            for (Schedules mySchedules1 : schedules) {
                if (mySchedules1.getStatus().equals("waiting")) {
                    schedulesFilter.add(mySchedules1);
                }
            }
            schedulesList.addAll(schedules);
            schedulesAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void noData() {
        if (schedulesList.size() == 0) {
            showDialogError(R.string.error_001);
        }
    }

    @SuppressLint("StringFormatMatches")
    @Override
    public void getSumary(Summary summary) {
        mTextViewWaiting.setText(String.format(getResources().getString(R.string.schedules_waiting), summary.getWaiting()));
        mTextViewProgress.setText(String.format(getResources().getString(R.string.schedules_progress), summary.getIn_progress()));
        mTextViewDone.setText(String.format(getResources().getString(R.string.schedules_done), summary.getDone()));
    }

    @Override
    public void onClickDialog(DialogResultItem dialogResultItem) {
        super.onClickDialog(dialogResultItem);
        switch (dialogResultItem.getDialogId()) {
            case DIALOG_YESNO: {
                switch (dialogResultItem.getResultMessageDialog()) {
                    case MESSAGEDIALOG_BUTTON_YES: {
                        Intent intent = new Intent(this, LoginActivity.class);
                        startActivity(intent);
                        SessionManagerUser.getInstance().logoutUser();
                        finish();
                        break;
                    }
                    case MESSAGEDIALOG_BUTTON_NO: {
                        mMessageDialogManger.onDimiss();
                    }
                }
                break;
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mActionBarDrawerToggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (openNavigation) {
            mDrawerLayout.closeDrawer(Gravity.START);
            openNavigation = false;
        } else {
            super.onBackPressed();
        }
    }
}
