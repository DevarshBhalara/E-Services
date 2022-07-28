package com.example.e_services;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


public class PagerAdapter2 extends FragmentPagerAdapter {
    int tabcount2;
    public PagerAdapter2(@NonNull FragmentManager fm, int behavior) {

        super(fm, behavior);
        tabcount2=behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new WorkerNewBookFragment();
            case 1:
                return new WorkerHistoryFragment();
            case 2:
                return new WorkerFeedbackFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabcount2;
    }
}
