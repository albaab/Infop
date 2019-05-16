package com.example.infopapp.ui.home_screens;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.infopapp.ui.home_screens.fragments.MessagesFragment;
import com.example.infopapp.ui.home_screens.fragments.ModulesFragment;
import com.example.infopapp.ui.home_screens.fragments.HomeFragment;

public class HomePagerAdapter extends FragmentStatePagerAdapter {

    public HomePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = null;
        switch (i) {
            case 0:
                fragment = HomeFragment.newInstance();
                break;

            case 1:
                fragment = new ModulesFragment();
                break;

            case 2:
                fragment = MessagesFragment.newInstance();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
