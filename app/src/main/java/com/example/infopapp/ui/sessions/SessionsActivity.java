package com.example.infopapp.ui.sessions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TimePicker;

import com.example.infopapp.R;

import com.example.infopapp.ui.sessions.fragments.DatePickerDialogFragment;
import com.example.infopapp.ui.sessions.fragments.ListOfSessionsFragment;
import com.example.infopapp.ui.sessions.fragments.SelectedSessionFragment;


import java.util.Calendar;

import static com.example.infopapp.utils.Constants.DATA_SCIENCE;
import static com.example.infopapp.utils.Constants.DATE_PICKED;
import static com.example.infopapp.utils.Constants.DAY_PICKED;
import static com.example.infopapp.utils.Constants.HARD_SKILLS;
import static com.example.infopapp.utils.Constants.MOBILE_DEV;
import static com.example.infopapp.utils.Constants.MONTH_PICKED;
import static com.example.infopapp.utils.Constants.WEB_DEV;

public class SessionsActivity extends AppCompatActivity
        implements ListOfSessionsFragment.ListOfSessionsFragmentListener,
        DatePickerDialogFragment.MyDatePickerDialogListener {


    private static final String TAG = "Session Activity";
    //=======================================ATTRIBUTES=================================================
    private SelectedSessionFragment selectedSessionFragment;
    private FragmentManager manager = getSupportFragmentManager();

//    private Thread uploadThread;

    //=======================================ON CREATE METHOD===========================================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sessions);
        setTitle(getText(R.string.mobile_development));
        setTitleColor(Color.BLACK);

//        final SessionViewModel sessionViewModel = ViewModelProviders.of(this).get(SessionViewModel.class);
//        WebSessionsExample webSessionsExample = new WebSessionsExample();
//        final MobileSessionsExample mobileSessionsExample = new MobileSessionsExample();

        //adapt the title of the page with respect to the intent of the user
        String intentType = getIntent().getStringExtra(HARD_SKILLS);
        switch (intentType) {
            case MOBILE_DEV:
                setTitle(getText(R.string.mobile_development));
                break;
            case WEB_DEV:
                setTitle(getText(R.string.web_development));
                break;
            case DATA_SCIENCE:
                setTitle(getText(R.string.data_science));
                break;
        }

//        uploadThread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                for (Session session : mobileSessionsExample.getSessionList()) {
//                   sessionViewModel.uploadSessiontoFirbaseDb(session);
//                }
//            }
//        });
//        uploadThread.start();

        Bundle listOfSessionBundle = new Bundle();
        listOfSessionBundle.putString(HARD_SKILLS, intentType);

        ListOfSessionsFragment listOfSessionsFragment = new ListOfSessionsFragment();
        listOfSessionsFragment.setArguments(listOfSessionBundle);
        selectedSessionFragment = new SelectedSessionFragment();

        goToFragment(listOfSessionsFragment);

    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        if(uploadThread.isAlive()){
//            Toast.makeText(this, "Uploading sessions...", Toast.LENGTH_SHORT).show();
//        }else{
////            goToFragment(listOfSessionsFragment);
//        }
//    }

    //=======================================UTILITY METHODS============================================
    private void goToFragment(Fragment fragment) {
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.session_activity_container, fragment);
        if (!(fragment instanceof ListOfSessionsFragment)) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }


    //===============================LISTOFSESSION FRAGMENT LISTENER METHOD=============================
    @Override
    public void goToSelectedSession(Bundle bundle) {
        selectedSessionFragment.setArguments(bundle);
        goToFragment(selectedSessionFragment);
    }

    @Override
    public void getDatePicked(Bundle bundle) {
        final String datePicked = bundle.getString(DATE_PICKED);
        final String monthPicked = bundle.getString(MONTH_PICKED);
        final int dayPicked = bundle.getInt(DAY_PICKED);
        Calendar c = Calendar.getInstance();

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String hourString= String.valueOf(hourOfDay)+":"+String.valueOf(minute);
                selectedSessionFragment.setTimeAndDate
                        (datePicked,dayPicked,monthPicked,hourString);
            }
        }, c.get(Calendar.HOUR),c.get(Calendar.MINUTE),true);
        timePickerDialog.setTitle(null);
        timePickerDialog.show();


        bundle.clear();
    }
}
