package com.example.e7440.bankingproject.module.main;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.e7440.bankingproject.R;
import com.example.e7440.bankingproject.components.ViewPagerAdapter;
import com.example.e7440.bankingproject.module.base.BaseActivity;
import com.example.e7440.bankingproject.module.config.Config;
import com.example.e7440.bankingproject.module.model.Item;
import com.example.e7440.bankingproject.module.model.Toolbar;
import com.example.e7440.bankingproject.module.tab.contact.view.FragmentContact;
import com.example.e7440.bankingproject.module.tab.employment.view.FragmentEmployment;
import com.example.e7440.bankingproject.module.tab.personal.view.FragmentPersonal;
import com.example.e7440.bankingproject.module.tab.loan.view.FragmentLoan;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;



public class MainActivity extends BaseActivity implements View.OnClickListener {

    public static String dataJSON = "";

    @BindView(R.id.tb_main)
    TabLayout mTabLayout;
    @BindView(R.id.vp_main)
    ViewPager mViewPager;

    private List<Item> mItemToolbar = new ArrayList<>();
    private ViewPagerAdapter mViewPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        setupViewPager(mViewPager);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void setupViewPager(ViewPager mViewPager) {
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        for (Toolbar toolbar : Config.getInstance().getmToolbarsList()) {
            mItemToolbar.add(new Item(String.valueOf(toolbar.getId()), toolbar.getName()));
            if (toolbar.getName().equals("LOAN")) {
                mViewPagerAdapter.addFragment(new FragmentLoan(), "LOAN");
            }
            if (toolbar.getName().equals("PERSONAL")) {
                mViewPagerAdapter.addFragment(new FragmentPersonal(), "PERSONAL");
            }
            if (toolbar.getName().equals("CONTACT")) {
                mViewPagerAdapter.addFragment(new FragmentContact(), "CONTACT");
            }
            if (toolbar.getName().equals("EMPLOYMENT")) {
                mViewPagerAdapter.addFragment(new FragmentEmployment(), "EMPLOYMENT");
            }
        }
        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.setOffscreenPageLimit(4);
    }

    @Override
    public void onClick(View v) {

    }

    public void setCurrentItem(int item, boolean smoothScroll) {
        mViewPager.setCurrentItem(item, smoothScroll);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        int count = getFragmentManager().getBackStackEntryCount();
        if (count == 0) {
            super.onBackPressed();
        } else {
            getFragmentManager().popBackStack();
        }
    }
}
