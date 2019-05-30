package com.example.infopapp.ui.home_screens;

import com.example.infopapp.ui.ContactsActivity;
import com.example.infopapp.ui.dashboard.DashActivity;
import com.example.infopapp.ui.profile.ProfileActivity;
import com.example.infopapp.ui.account.ConnectToAccountActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.infopapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.example.infopapp.ui.account.ConnectToAccountActivity.USERTYPE;
import static com.example.infopapp.ui.account.ConnectToAccountActivity.thisInstructor;
import static com.example.infopapp.ui.account.ConnectToAccountActivity.thisStaff;
import static com.example.infopapp.ui.account.ConnectToAccountActivity.thisStudent;
import static com.example.infopapp.ui.account.ConnectToAccountActivity.thisUser;
import static com.example.infopapp.utils.StringConstants.STAFF;


public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

//======================================private attributes==========================================
    private DrawerLayout drawerLayout;
    public static FirebaseUser firebaseUser;

//    public static Student currentStudent = new Student();

//=======================================ON CREATE =================================================
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //---------------------------------navigation DRAWER----------------------------------------
        drawerLayout = findViewById(R.id.main_content);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.open_drawer,
                R.string.close_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //---------------------------------navigation VIEW----------------------------------------
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //---------------------------------View pager and adapter-----------------------------------
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        HomePagerAdapter homePagerAdapter = new HomePagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        ViewPager mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(homePagerAdapter);

        //---------------------------------Tab layout-----------------------------------
        TabLayout tabLayout = findViewById(R.id.tabs);
        //add new tabs to the tab layout
        final TabLayout.Tab homeTab = tabLayout.newTab();
        final TabLayout.Tab middleTab = tabLayout.newTab();
        final TabLayout.Tab messageTab = tabLayout.newTab();

        //add the icons of the tab items
        homeTab.setIcon(R.drawable.ic_home_selected);
        middleTab.setIcon(R.drawable.ic_sessions);
        messageTab.setIcon(R.drawable.ic_message);

        //set the position of the tab items
        tabLayout.addTab(homeTab, 0);
        tabLayout.addTab(middleTab, 1);
        tabLayout.addTab(messageTab, 2);


        //----------------------------View Pager and TabLyout Listener------------------------------
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        //----------------------------tab icon change item selection---------------------------
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
                        middleTab.setIcon(R.drawable.ic_sessions);
                        messageTab.setIcon(R.drawable.ic_message);
                        break;
                    }
                    case 1: {

                        homeTab.setIcon(R.drawable.ic_home);
                        middleTab.setIcon(R.drawable.ic_session_selected);
                        messageTab.setIcon(R.drawable.ic_message);
                        if(USERTYPE.equals(STAFF)){
                            setTitle(R.string.title_portfolios);
                        }else{
                            setTitle(R.string.title_fragment_sessions);
                        }
                        break;
                    }
                    case 2: {
                        setTitle(R.string.title_fragment_messages);
                        homeTab.setIcon(R.drawable.ic_home);
                        middleTab.setIcon(R.drawable.ic_sessions);
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

    //===============================back button events=============================================
    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onStart() {
        super.onStart();
        if (firebaseUser == null) {
            startActivity(new Intent(this, ConnectToAccountActivity.class));
            thisUser = null;
            thisStaff = null;
            thisStudent = null;
            thisInstructor = null;
            finish();
        }

    }

    //=================================Create Option Menu===========================================
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

//==================================== menu Item actions ===========================================
    //right Menu actions
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(this, "Signed Out", Toast.LENGTH_SHORT).show();
                thisUser = null;
                thisStudent = null;
                thisStaff = null;
                thisInstructor = null;
                finish();
                startActivity(new Intent(this, ConnectToAccountActivity.class));
                break;
            case R.id.action_search:
                //TODO action research
                break;
            case R.id.action_settings:
                //TODO action settings
                break;

        }

//
        return super.onOptionsItemSelected(item);
    }

    //Left menu actions
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.nav_profile:
                startActivity(new Intent(this, ProfileActivity.class));
                break;
            case R.id.nav_dashboard:
                startActivity(new Intent(this, DashActivity.class));
                break;
            case R.id.nav_contacts:
                startActivity(new Intent(this, ContactsActivity.class));
        }
        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }


}
