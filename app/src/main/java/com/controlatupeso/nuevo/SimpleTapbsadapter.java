package com.controlatupeso.nuevo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class SimpleTapbsadapter extends FragmentPagerAdapter {

    private final List<Fragment> FragmentList = new ArrayList();
    private final List<String> FragmentTitles = new ArrayList();

    public SimpleTapbsadapter(FragmentManager fm) {
        super(fm);
    }

    public void addFragment(Fragment fragment, String title) {
        FragmentList.add(fragment);
        FragmentTitles.add(title);
    }

    @Override
    public Fragment getItem(int position) {
        return FragmentList.get(position);
    }

    @Override
    public int getCount() {
        return FragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return FragmentTitles.get(position);
    }

}