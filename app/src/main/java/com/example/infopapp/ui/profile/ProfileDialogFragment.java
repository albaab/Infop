package com.example.infopapp.ui.profile;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Spinner;

import com.example.infopapp.R;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import static com.example.infopapp.ui.account.ConnectToAccountActivity.USERTYPE;
import static com.example.infopapp.utils.Constants.COHORT;
import static com.example.infopapp.utils.Constants.DATA_SCIENCE;
import static com.example.infopapp.utils.Constants.DISPLAY_PROFILE_NAME;
import static com.example.infopapp.utils.Constants.GUEST;
import static com.example.infopapp.utils.Constants.HCS;
import static com.example.infopapp.utils.Constants.INSTRUCTOR;
import static com.example.infopapp.utils.Constants.JOB_TITLE;
import static com.example.infopapp.utils.Constants.LEAD_FACILITATOR;
import static com.example.infopapp.utils.Constants.MOBILE_DEV;
import static com.example.infopapp.utils.Constants.OFFICE_MANAGER;
import static com.example.infopapp.utils.Constants.SPECILIZATION;
import static com.example.infopapp.utils.Constants.SSC;
import static com.example.infopapp.utils.Constants.STAFF;
import static com.example.infopapp.utils.Constants.STUDENT;
import static com.example.infopapp.utils.Constants.USER;
import static com.example.infopapp.utils.Constants.USER_LAST_NAME;
import static com.example.infopapp.utils.Constants.USER_PHONE_KEY;
import static com.example.infopapp.utils.Constants.WEB_DEV;

public class ProfileDialogFragment extends DialogFragment {


    //=========================================ATTRIBUTES===============================================
    private Bundle profileDialogBundle;
    private Bundle profileBundleFromActivity;
    private ProfileDialogListener mListener;


    private EditText firstNameEdit, lastNameEdit, displayNameEdit, phoneEdit;
    private Spinner specializationSpinner;
    private Spinner jobTitleSpinner;
    private NumberPicker cohortNumberPicker;

    private LinearLayout cohortLayout;

    interface ProfileDialogListener {
        void saveProfileInfo(Bundle bundle);
    }


    //=========================================ON CREATE DIALOG=========================================
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        profileBundleFromActivity = getArguments();
        profileDialogBundle = new Bundle();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.profile_edit_dialog, null);

        //----------------------------------VIEW ITEMS----------------------------------------------
        firstNameEdit = view.findViewById(R.id.profile_first_name_edit);
        lastNameEdit = view.findViewById(R.id.profile_last_name_edit);
        displayNameEdit = view.findViewById(R.id.profile_display_edit);
        cohortLayout = view.findViewById(R.id.cohort_layout);
        cohortNumberPicker = view.findViewById(R.id.profile_cohort_number);
        specializationSpinner = view.findViewById(R.id.profile_specialization_spinner);
        jobTitleSpinner = view.findViewById(R.id.job_title_spinner);
        phoneEdit = view.findViewById(R.id.profile_phone_edit);

        cohortNumberPicker.setMinValue(1);
        cohortNumberPicker.setMaxValue(100);

            displayUserTypeEdit();


        //----------------------------------DIALOG BUIDLER------------------------------------------
        builder.setView(view)
                .setNegativeButton(R.string.cancel,
                        new DialogInterface
                                .OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                .setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        profileDialogBundle.putString(USER
                                , firstNameEdit.getText().toString().trim());
                        profileDialogBundle.putString(USER_LAST_NAME
                                , lastNameEdit.getText().toString().trim());
                        profileDialogBundle.putString(DISPLAY_PROFILE_NAME
                                , displayNameEdit.getText().toString().trim());
                        profileDialogBundle.putInt(COHORT
                                , cohortNumberPicker.getValue());
                        profileDialogBundle.putString(SPECILIZATION
                                , specializationSpinner.getSelectedItem().toString());
                        profileDialogBundle.putString(JOB_TITLE
                                , jobTitleSpinner.getSelectedItem().toString());
                        profileDialogBundle.putString(USER_PHONE_KEY
                                , phoneEdit.getText().toString().trim());

                        mListener.saveProfileInfo(profileDialogBundle);
                        setArguments(profileDialogBundle);
                    }
                });

        return builder.create();
    }

    //=========================================ON ATTACH METHOD=========================================
    @Override
    public void onAttach(@NonNull Context context) throws ClassCastException {
        super.onAttach(context);
        mListener = (ProfileDialogListener) context;
    }

    //=========================================UTILITY METHODS==========================================
    private void displayUserTypeEdit() {

        if (profileBundleFromActivity != null) {
            firstNameEdit.setText(profileBundleFromActivity.getString(USER));
            lastNameEdit.setText(profileBundleFromActivity.getString(USER_LAST_NAME));
            displayNameEdit.setText(profileBundleFromActivity.getString(DISPLAY_PROFILE_NAME));
            phoneEdit.setText(profileBundleFromActivity.getString(USER_PHONE_KEY));
            adaptUserProfileDialog(profileBundleFromActivity);
            if (USERTYPE.equals(STUDENT) || USERTYPE.equals(INSTRUCTOR)) {
                selectSpecialization(profileBundleFromActivity);
            } else if (USERTYPE.equals(STAFF)) {
                selectJobTitle(profileBundleFromActivity);
            }

        }

    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void selectSpecialization(Bundle bundle) {
        if (bundle.getString(SPECILIZATION) != null)
            switch (bundle.getString(SPECILIZATION)) {

                case DATA_SCIENCE:
                    specializationSpinner.setSelection(0);
                    break;
                case MOBILE_DEV:
                    specializationSpinner.setSelection(1);
                    break;
                case WEB_DEV:
                    specializationSpinner.setSelection(2);
                    break;
                case INSTRUCTOR:
                    specializationSpinner.setSelection(3);
                    break;

            }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void selectJobTitle(Bundle bundle) {
        switch (Objects.requireNonNull(bundle.getString(JOB_TITLE))) {

            case LEAD_FACILITATOR:
                jobTitleSpinner.setSelection(0);
                break;
            case SSC:
                jobTitleSpinner.setSelection(1);
                break;
            case HCS:
                jobTitleSpinner.setSelection(2);
                break;
            case OFFICE_MANAGER:
                jobTitleSpinner.setSelection(3);
                break;

        }
    }

    private void adaptUserProfileDialog(Bundle bundle) {
        switch (USERTYPE) {
            case STUDENT:
                cohortNumberPicker.setValue(bundle.getInt(COHORT));
                cohortLayout.setVisibility(View.VISIBLE);
                jobTitleSpinner.setVisibility(View.GONE);
                specializationSpinner.setVisibility(View.VISIBLE);
                break;
            case STAFF:
                jobTitleSpinner.setVisibility(View.VISIBLE);
                specializationSpinner.setVisibility(View.GONE);
                break;
            case INSTRUCTOR:
                specializationSpinner.setVisibility(View.VISIBLE);
                break;
            case GUEST:
                jobTitleSpinner.setVisibility(View.GONE);
                specializationSpinner.setVisibility(View.GONE);
                break;
            default:
                break;

        }
    }
}
