package com.example.infopapp.ui.home_screens;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.infopapp.ui.home_screens.fragments.MessagesFragment;
import com.example.infopapp.ui.home_screens.fragments.ModulesFragment;
import com.example.infopapp.ui.home_screens.fragments.HomeFragment;

import static com.example.infopapp.ui.account.ConnectToAccountActivity.USERTYPE;

import static com.example.infopapp.utils.Constants.INSTRUCTOR;
import static com.example.infopapp.utils.Constants.GUEST;
import static com.example.infopapp.utils.Constants.STAFF;
import static com.example.infopapp.utils.Constants.STUDENT;

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
                return new ModulesFragment();
            case INSTRUCTOR:
                //TODO return the list of cohorts or list of sessions
                return new ModulesFragment();
            case GUEST:
                //todo return a text "You do not have access to the courses"
                return new HomeFragment();
            default:
                return new HomeFragment();
        }
    }

}
