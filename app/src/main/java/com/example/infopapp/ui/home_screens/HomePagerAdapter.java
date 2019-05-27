package com.example.infopapp.ui.home_screens;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.infopapp.ui.home_screens.fragments.MessagesFragment;
import com.example.infopapp.ui.home_screens.fragments.ModulesFragment;
import com.example.infopapp.ui.home_screens.fragments.HomeFragment;
import com.example.infopapp.ui.home_screens.fragments.portfolios.PortFoliosFragment;

import static com.example.infopapp.ui.account.ConnectToAccountActivity.USERTYPE;

import static com.example.infopapp.utils.StringConstants.INSTRUCTOR;
import static com.example.infopapp.utils.StringConstants.GUEST;
import static com.example.infopapp.utils.StringConstants.STAFF;
import static com.example.infopapp.utils.StringConstants.STUDENT;

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
                fragment = getFragmentFromUserType();
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

    private Fragment getFragmentFromUserType() {
        switch (USERTYPE) {
            case STUDENT:
                return new ModulesFragment();
            case STAFF:
                return new PortFoliosFragment();
            case INSTRUCTOR:
                return new HomeFragment();
            case GUEST:
                return new HomeFragment();
            default:
                return new HomeFragment();
        }
    }

}
