package com.example.e7440.bankingproject.components;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by E7440 on 6/11/2018.
 */

public class ViewPagerInFragmentAdapter extends FragmentStatePagerAdapter {
    private final List<Fragment> fragmentList = new ArrayList<Fragment>();
    private final List<String> titles = new ArrayList<String>();

    public ViewPagerInFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

    public void addFragment(Fragment fragment, String title) {
        fragmentList.add(fragment);
        titles.add(title);
    }
}
