package com.example.infopapp.ui.home_screens;

import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.infopapp.R;

public class HomeActivity extends AppCompatActivity {

    private HomePagerAdapter homePagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        homePagerAdapter = new HomePagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(homePagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tabs);

        final TabLayout.Tab homeTab = tabLayout.newTab();
        final TabLayout.Tab sessionTab = tabLayout.newTab();
        final TabLayout.Tab messageTab = tabLayout.newTab();

        homeTab.setIcon(R.drawable.ic_home_selected);
        sessionTab.setIcon(R.drawable.ic_sessions);
        messageTab.setIcon(R.drawable.ic_message);

        tabLayout.addTab(homeTab, 0);
        tabLayout.addTab(sessionTab, 1);
        tabLayout.addTab(messageTab, 2);


        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                switch (i) {
                    case 0: {
                        setTitle(R.string.title_fragment_home);
                        homeTab.setIcon(R.drawable.ic_home_selected);
                        sessionTab.setIcon(R.drawable.ic_sessions);
                        messageTab.setIcon(R.drawable.ic_message);
                        break;
                    }
                    case 1:{
                        setTitle(R.string.title_fragment_sessions);
                        homeTab.setIcon(R.drawable.ic_home);
                        sessionTab.setIcon(R.drawable.ic_session_selected);
                        messageTab.setIcon(R.drawable.ic_message);
                        break;
                    }
                    case 2:{
                        setTitle(R.string.title_fragment_messages);
                        homeTab.setIcon(R.drawable.ic_home);
                        sessionTab.setIcon(R.drawable.ic_sessions);
                        messageTab.setIcon(R.drawable.ic_message_selected);
                        break;
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    //=================================Create Option Menu===============================================
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    //================================ menu Item actions ================================================
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
