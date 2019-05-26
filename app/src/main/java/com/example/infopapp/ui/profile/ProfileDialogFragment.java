package com.example.infopapp.ui.profile;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Spinner;

import com.example.infopapp.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import static com.example.infopapp.ui.account.ConnectToAccountActivity.thisUser;
import static com.example.infopapp.utils.StringConstants.COHORT;
import static com.example.infopapp.utils.StringConstants.DATA_SCIENCE;
import static com.example.infopapp.utils.StringConstants.DISPLAY_PROFILE_NAME;
import static com.example.infopapp.utils.StringConstants.GUEST;
import static com.example.infopapp.utils.StringConstants.HCS;
import static com.example.infopapp.utils.StringConstants.INSTRUCTOR;
import static com.example.infopapp.utils.StringConstants.MOBILE_DEV;
import static com.example.infopapp.utils.StringConstants.SPECILIZATION;
import static com.example.infopapp.utils.StringConstants.SSC;
import static com.example.infopapp.utils.StringConstants.STAFF;
import static com.example.infopapp.utils.StringConstants.STUDENT;
import static com.example.infopapp.utils.StringConstants.USER;
import static com.example.infopapp.utils.StringConstants.USER_LAST_NAME;
import static com.example.infopapp.utils.StringConstants.USER_PHONE_KEY;
import static com.example.infopapp.utils.StringConstants.WEB_DEV;

public class ProfileDialogFragment extends DialogFragment {
    private Bundle profileDialogBundle = new Bundle();
    private ProfileDialogListener mListener;
    private Bundle profileBundle;


    private EditText firstNameEdit, lastNameEdit, displayNameEdit, phoneEdit;
    private Spinner specializationSpinner;
    private NumberPicker cohortNumberPicker;

    private LinearLayout cohortLayout;

    interface ProfileDialogListener {
        void saveProfileInfo(Bundle bundle);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        profileBundle = getArguments();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.profile_edit_dialog, null);


        firstNameEdit = view.findViewById(R.id.profile_first_name_edit);
        lastNameEdit = view.findViewById(R.id.profile_last_name_edit);
        displayNameEdit = view.findViewById(R.id.profile_display_edit);
        cohortLayout = view.findViewById(R.id.cohort_layout);
        cohortNumberPicker = view.findViewById(R.id.profile_cohort_number);
        specializationSpinner = view.findViewById(R.id.profile_specialization_edit);
        phoneEdit = view.findViewById(R.id.profile_phone_edit);

        cohortNumberPicker.setMinValue(1);
        cohortNumberPicker.setMaxValue(100);

        displayUserTypeEdit();

        builder.setView(view)
                .setTitle(R.string.edit_profile)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
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
                profileDialogBundle.putString(USER_PHONE_KEY
                        , phoneEdit.getText().toString().trim());

                mListener.saveProfileInfo(profileDialogBundle);
            }
        });

        return builder.create();
    }

    @Override
    public void onAttach(Context context) throws ClassCastException {
        super.onAttach(context);
        mListener = (ProfileDialogListener) context;
    }

    private void displayUserTypeEdit() {

        if (profileBundle != null) {
            firstNameEdit.setText(profileBundle.getString(USER));
            lastNameEdit.setText(profileBundle.getString(USER_LAST_NAME));
            displayNameEdit.setText(profileBundle.getString(DISPLAY_PROFILE_NAME));
            phoneEdit.setText(profileBundle.getString(USER_PHONE_KEY));

            switch (profileBundle.getString(SPECILIZATION)) {

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
                case SSC:
                    specializationSpinner.setSelection(4);
                    break;
                case HCS:
                    specializationSpinner.setSelection(5);
                    break;

            }
            switch (thisUser.getUserType()) {
                case STUDENT:
                    cohortNumberPicker.setValue(profileBundle.getInt(COHORT));
                    cohortLayout.setVisibility(View.VISIBLE);
                    break;
                case STAFF:
                    // DO SOMETHING
                    break;
                case INSTRUCTOR:
                    break;
                case GUEST:
                    break;
                default:
                    break;

            }
        }

    }
}
