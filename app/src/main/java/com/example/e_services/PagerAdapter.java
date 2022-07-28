package com.example.e_services;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PagerAdapter extends FragmentPagerAdapter {

    int tabcount;

    public PagerAdapter(@NonNull FragmentManager fm, int behavior) {

        super(fm, behavior);
        tabcount=behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return new SearchFragment();
            case 1:
                return new Homefragment();
            case 2:
                return new FeedbackFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabcount;
    }
}
