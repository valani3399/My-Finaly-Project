package com.example.softices.pankajtrainee.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> fragmentList=new ArrayList<>();
    private final List<String> fragmenttiltal=new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmenttiltal.get(position);
    }
    @Override
    public int getCount() {
        return fragmentList.size();
    }

    public void addFradment(Fragment fragment, String tital){
        fragmentList.add(fragment);
        fragmenttiltal.add(tital);
    }
}
