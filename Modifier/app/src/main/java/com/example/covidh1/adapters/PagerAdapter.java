package com.example.covidh1.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.covidh1.fragments.FindAndModifyFragment;
import com.example.covidh1.fragments.InfectedUsers;
import com.example.covidh1.fragments.RecoveredUsers;

public class PagerAdapter extends FragmentPagerAdapter {
    private int tabsNumber;

    public PagerAdapter(@NonNull FragmentManager fm, int behavior, int tabs) {
        super(fm, behavior);
        tabsNumber = tabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FindAndModifyFragment();
            case 1:
                return new InfectedUsers();
            case 2:
                return new RecoveredUsers();
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return tabsNumber;
    }
}
