package com.example.infopapp.ui.profile;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.infopapp.R;
import com.example.infopapp.entities.Instructor;
import com.example.infopapp.entities.Staff;
import com.example.infopapp.entities.Student;
import com.example.infopapp.utils.RoundPicasso;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import static com.example.infopapp.ui.account.ConnectToAccountActivity.thisUser;
import static com.example.infopapp.utils.StringConstants.COHORT;
import static com.example.infopapp.utils.StringConstants.DISPLAY_PROFILE_NAME;
import static com.example.infopapp.utils.StringConstants.INSTRUCTOR;
import static com.example.infopapp.utils.StringConstants.GUEST;
import static com.example.infopapp.utils.StringConstants.SPECILIZATION;
import static com.example.infopapp.utils.StringConstants.STAFF;
import static com.example.infopapp.utils.StringConstants.STUDENT;
import static com.example.infopapp.utils.StringConstants.USER;
import static com.example.infopapp.utils.StringConstants.USER_LAST_NAME;
import static com.example.infopapp.utils.StringConstants.USER_PHONE_KEY;
import static com.example.infopapp.utils.StringConstants.ZERO;

public class ProfileActivity extends AppCompatActivity implements
        ProfileDialogFragment.ProfileDialogListener, ProfileView {

    //======================================PRIVATE ATTRIBUTES==========================================
    private static final String TAG = "ProfileActivity";
    private static final int CHOOSE_IMAGE = 111;
    private ImageView profileImage;
    private TextView fullName, displayName, email, cohort, cohortTv, specialization, phoneNumber;
    private ProfilePresenter profilePresenter;
    private ProgressBar uploadProgressBar;
    protected FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private Uri profileImageUri;

    private ProfileDialogFragment profileDialogFragment;
    private Bundle bundleForDialogFragment;


//======================================ON CREATE METHOD ===========================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bundleForDialogFragment = new Bundle();
        profileDialogFragment = new ProfileDialogFragment();
        setContentView(R.layout.activity_profile);
        profileImage = findViewById(R.id.profile_image);
        FloatingActionButton uploadImageButton = findViewById(R.id.upload_image_button);
        FloatingActionButton editProfileButton = findViewById(R.id.edit_profile_button);
        uploadProgressBar = findViewById(R.id.upload_progress_bar);

        fullName = findViewById(R.id.profile_name_tv);
        displayName = findViewById(R.id.profile_display_tv);
        email = findViewById(R.id.profile_email_tv);
        cohort = findViewById(R.id.profile_cohort_num_tv);
        cohortTv = findViewById(R.id.profile_cohort_tv);
        specialization = findViewById(R.id.profile_specialization_tv);
        phoneNumber = findViewById(R.id.profile_phone_number_tv);

        profilePresenter = new ProfilePresenter(this);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileDialogFragment.show(getSupportFragmentManager(), "Profile Dialog");
            }
        });

        uploadImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });


        Log.d(TAG, "onCreate: profile view created");

    }


    //======================================ON ACTIVITY RESULT METHOD ==================================
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CHOOSE_IMAGE && resultCode == RESULT_OK
                && data != null & data.getData() != null) {
            profileImageUri = data.getData();
            profilePresenter.uploadImage(profileImageUri);
            Log.d(TAG, "onActivityResult: display image chosen");
        }
    }

    //======================================ON START METHOD=============================================
    @Override
    protected void onStart() {
        super.onStart();
        loadProfileInfo();
        Log.d(TAG, "onStart: Profile view success");
    }

    //=========================================ON DESTROY METHOD========================================
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: Profile View closed");
    }

    ////======================================UTILITY METHODS===========================================
    public void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Profile Picture"), CHOOSE_IMAGE);
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)

    private void loadProfileInfo() {

        if (currentUser != null) {
            email.setText(currentUser.getEmail());
            if (currentUser.getPhotoUrl() != null) {

                uploadProgressBar.setVisibility(View.VISIBLE);
                Picasso.with(this)
                        .load(currentUser.getPhotoUrl().toString())
                        .transform(new RoundPicasso(profileImage.getMaxHeight(), 0))
                        .into(profileImage, new Callback() {
                            @Override
                            public void onSuccess() {
                                uploadProgressBar.setVisibility(View.GONE);
                            }

                            @Override
                            public void onError() {
                                uploadProgressBar.setVisibility(View.GONE);
                                Toast.makeText(ProfileActivity.this
                                        , getText(R.string.error_message), Toast.LENGTH_SHORT).show();
                            }
                        });
                Log.d(TAG, "loadProfileInfo: download Picture with Picasso");
            } else {
                profileImage.setImageResource(R.drawable.talent);
                Log.d(TAG, "loadProfileInfo: Load talent icon");
            }

            String userFullName = thisUser.getFirstName() + " " + thisUser.getLastName();
            if (!TextUtils.isEmpty(userFullName)) {
                Log.d(TAG, "loadProfileInfo: Name of user displayed on screen");
                fullName.setText(userFullName);
            } else {
                fullName.setText(getText(R.string.name));
            }
        }
        if (thisUser.getDisplayName() != null) {
            displayName.setText(thisUser.getDisplayName());
        } else {
            displayName.setText(getText(R.string.display_name));
        }
        if (thisUser.getPhone() != null) {
            phoneNumber.setText(thisUser.getPhone());
        } else {
            phoneNumber.setText(getText(R.string.phone_number));
        }
        loadUserTypeInfo();
    }


    private void loadUserTypeInfo() {
        switch (currentUser.getDisplayName()) {
            case STUDENT:
                cohort.setVisibility(View.VISIBLE);
                cohortTv.setVisibility(View.VISIBLE);
                Student thisStudent = (Student) thisUser;

                if (thisStudent.getSpecialization() != null) {
                    specialization.setText(thisStudent.getSpecialization());
                    bundleForDialogFragment.putString(SPECILIZATION
                            , thisStudent.getSpecialization());
                } else {
                    specialization.setText(getText(R.string.specialization));
                    bundleForDialogFragment.putString(SPECILIZATION
                            , (String) getText(R.string.specialization));
                }
                if (thisStudent.getCohort() != 0) {
                    cohort.setText(String.valueOf(thisStudent.getCohort()));
                    bundleForDialogFragment.putInt(COHORT, thisStudent.getCohort());
                } else {
                    cohort.setText(String.valueOf(ZERO));
                    bundleForDialogFragment.putInt(COHORT, Integer.valueOf(ZERO));
                }

                break;

            case STAFF:
                Staff thisStaff = (Staff)thisUser;

                if (thisStaff.getJobTitle() != null) {
                    specialization.setText(thisStaff.getJobTitle());
                    bundleForDialogFragment.putString(SPECILIZATION, thisStaff.getJobTitle());
                } else {
                    bundleForDialogFragment.putString(SPECILIZATION
                            , (String) getText(R.string.job_title));
                    specialization.setText(getText(R.string.job_title));
                }
                break;
            case INSTRUCTOR:
                Instructor thisInstructor = (Instructor) thisUser;

                if (thisInstructor.getJobTitle() != null) {
                    specialization.setText(thisInstructor.getJobTitle());
                    bundleForDialogFragment.putString(SPECILIZATION, thisInstructor.getJobTitle());
                } else {
                    specialization.setText(getText(R.string.job_title));
                    bundleForDialogFragment.putString(SPECILIZATION
                            , (String)getText(R.string.job_title));

                }
                break;
            case GUEST:
                specialization.setVisibility(View.GONE);
                //TODO DISPLAY GUEST INFO
                break;
            default:
                displayName.setText(getText(R.string.display_name));
                phoneNumber.setText(getText(R.string.phone_number));
                specialization.setText(getText(R.string.specialization));
        }

        bundleForDialogFragment.putString(USER, thisUser.getFirstName());
        bundleForDialogFragment.putString(USER_LAST_NAME, thisUser.getLastName());
        bundleForDialogFragment.putString(DISPLAY_PROFILE_NAME, thisUser.getDisplayName());
        bundleForDialogFragment.putString(USER_PHONE_KEY, thisUser.getPhone());

        profileDialogFragment.setArguments(bundleForDialogFragment);

    }


    //----------------------------Profile dialog fragment interface method--------------------------
    @Override
    public void saveProfileInfo(Bundle bundle) {

        if (bundle != null) {

            thisUser.setEmail(currentUser.getEmail());
            thisUser.setFirstName(bundle.getString(USER));
            thisUser.setLastName((bundle.getString(USER_LAST_NAME)));
            thisUser.setDisplayName(bundle.getString(DISPLAY_PROFILE_NAME));
            thisUser.setPhone(bundle.getString(USER_PHONE_KEY));

            String fullNameString = thisUser.getFirstName() + " " + thisUser.getLastName();
            fullName.setText(fullNameString);
            displayName.setText(thisUser.getDisplayName());
            phoneNumber.setText(thisUser.getPhone());

            switch (thisUser.getUserType()) {
                case STUDENT:
                    Student thisStudent = (Student) thisUser;
                    thisStudent.setCohort(bundle.getInt(COHORT));
                    thisStudent.setSpecialization(bundle.getString(SPECILIZATION));
                    cohort.setText(String.valueOf(thisStudent.getCohort()));
                    specialization.setText(thisStudent.getSpecialization());

                    break;
                case STAFF:
                    Staff thisStaff = (Staff) thisUser;
                    thisStaff.setJobTitle(bundle.getString(SPECILIZATION));
                    specialization.setText(thisStaff.getJobTitle());
                    break;
                case GUEST:
                    //TODO handle guest display info
                    break;
            }

            // UPLOAD OR UPDATE USER IN FIREBASE DATABASE
            if (thisUser.getId() == null) {
                Log.d(TAG, "saveProfileInfo: UPLOADING USER INFO IN DATABASE");
                profilePresenter.uploadDatabaseInfo(thisUser);
            } else {
                profilePresenter.updateDbStudentInfo(thisUser);
                Log.d(TAG, "saveProfileInfo: UPDATING USER INFO IN DATABASE");
            }

        } else {
            Toast.makeText(this, getText(R.string.error_message), Toast.LENGTH_SHORT).show();
        }

    }

    //======================================VIEW INTERFACE METHODS======================================
    @Override
    public void setUploadProfilePic(boolean myBoolean, int visibility) {

        String uploadMessageSuccess = this.getResources().getString(R.string.upload_success);
        String uploadMessageFail = this.getResources().getString(R.string.upload_failed);

        uploadProgressBar.setVisibility(visibility);

        if (myBoolean) {
            Toast.makeText(this, uploadMessageSuccess, Toast.LENGTH_SHORT).show();
            Picasso.with(this)
                    .load(profileImageUri)
                    .transform(new RoundPicasso(profileImage.getHeight(), 0))
                    .into(profileImage);
            Log.d(TAG, "viewMethod: SETUPLOAD Picture uploadead");
        } else if (visibility == View.VISIBLE) {
            // file Uploading
            profileImage.refreshDrawableState();
        } else {
            Toast.makeText(this, uploadMessageFail, Toast.LENGTH_SHORT).show();
            Log.d(TAG, "viewMethod: Image uploading failed");
        }
    }

    @Override
    public void setUploadUserToDbStatus(boolean myBoolean) {
        if (myBoolean) {
            Toast.makeText(this, this.getText(R.string.profile_updated_message), Toast.LENGTH_SHORT).show();

            if (currentUser.getPhotoUrl() != null) {
                thisUser.setProfileImageurl(currentUser.getPhotoUrl().toString());
            }
        } else {
            Toast.makeText(this, this.getText(R.string.complete_profile_message), Toast.LENGTH_SHORT).show();
        }
    }
}
