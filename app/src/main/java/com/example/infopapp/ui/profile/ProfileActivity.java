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
import com.example.infopapp.utils.RoundPicasso;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import static com.example.infopapp.ui.account.ConnectToAccountActivity.USERTYPE;
import static com.example.infopapp.ui.account.ConnectToAccountActivity.thisInstructor;
import static com.example.infopapp.ui.account.ConnectToAccountActivity.thisStaff;
import static com.example.infopapp.ui.account.ConnectToAccountActivity.thisStudent;
import static com.example.infopapp.ui.account.ConnectToAccountActivity.thisUser;
import static com.example.infopapp.utils.Constants.COHORT;
import static com.example.infopapp.utils.Constants.DISPLAY_PROFILE_NAME;
import static com.example.infopapp.utils.Constants.INSTRUCTOR;
import static com.example.infopapp.utils.Constants.GUEST;
import static com.example.infopapp.utils.Constants.SPECILIZATION;
import static com.example.infopapp.utils.Constants.STAFF;
import static com.example.infopapp.utils.Constants.STUDENT;
import static com.example.infopapp.utils.Constants.USER;
import static com.example.infopapp.utils.Constants.USER_LAST_NAME;
import static com.example.infopapp.utils.Constants.USER_PHONE_KEY;
import static com.example.infopapp.utils.Constants.ZERO;

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
        setContentView(R.layout.activity_profile);

        bundleForDialogFragment = new Bundle();
        profileDialogFragment = new ProfileDialogFragment();
        setTitle(getText(R.string.profile));
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
        loadProfilePic();
        loadUserInfoFromUserType();
        Log.d(TAG, "onStart: Profile view success");
    }

    //=========================================ON DESTROY METHOD========================================
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: Profile View closed");
    }

    //======================================UTILITY METHODS===========================================
    public void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        profileImage.setImageDrawable(null);
        startActivityForResult(Intent.createChooser(intent, "Select Profile Picture"), CHOOSE_IMAGE);
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)


    //----------------------------Profile dialog fragment interface method--------------------------
    @Override
    public void saveProfileInfo(Bundle bundle) {

        if (bundle != null) {

            switch (currentUser.getDisplayName()) {

                case STUDENT:
                    thisStudent.setFirstName(bundle.getString(USER));
                    thisStudent.setLastName((bundle.getString(USER_LAST_NAME)));
                    thisStudent.setDisplayName(bundle.getString(DISPLAY_PROFILE_NAME));
                    thisStudent.setPhone(bundle.getString(USER_PHONE_KEY));
                    thisStudent.setCohort(bundle.getInt(COHORT));
                    thisStudent.setSpecialization(bundle.getString(SPECILIZATION));
                    String fullNameString = thisStudent.getFirstName() + " " + thisStudent.getLastName();
                    fullName.setText(fullNameString);
                    displayName.setText(thisStudent.getDisplayName());
                    phoneNumber.setText(thisStudent.getPhone());
                    cohort.setText(String.valueOf(thisStudent.getCohort()));
                    specialization.setText(thisStudent.getSpecialization());
                    profilePresenter.updateDbStudentInfo(thisStudent);

                    break;
                case STAFF:
                    thisStaff.setFirstName(bundle.getString(USER));
                    thisStaff.setLastName((bundle.getString(USER_LAST_NAME)));
                    thisStaff.setDisplayName(bundle.getString(DISPLAY_PROFILE_NAME));
                    thisStaff.setPhone(bundle.getString(USER_PHONE_KEY));
                    thisStaff.setJobTitle(bundle.getString(SPECILIZATION));

                    String staffFullNameString = thisStaff.getFirstName() + " " + thisStaff.getLastName();
                    fullName.setText(staffFullNameString);
                    displayName.setText(thisStaff.getDisplayName());
                    phoneNumber.setText(thisStaff.getPhone());
                    specialization.setText(thisStaff.getJobTitle());
                    profilePresenter.updateDbStudentInfo(thisStaff);

                    break;
                case INSTRUCTOR:
                    thisInstructor.setFirstName(bundle.getString(USER));
                    thisInstructor.setLastName((bundle.getString(USER_LAST_NAME)));
                    thisInstructor.setDisplayName(bundle.getString(DISPLAY_PROFILE_NAME));
                    thisInstructor.setPhone(bundle.getString(USER_PHONE_KEY));
                    thisInstructor.setSpecialization(bundle.getString(SPECILIZATION));

                    String insFullNameString = thisStudent.getFirstName() + " " + thisStudent.getLastName();
                    fullName.setText(insFullNameString);
                    displayName.setText(thisInstructor.getDisplayName());
                    phoneNumber.setText(thisInstructor.getPhone());
                    cohort.setText(String.valueOf(thisInstructor.getCohort()));
                    specialization.setText(thisInstructor.getSpecialization());
                    profilePresenter.updateDbStudentInfo(thisInstructor);

                    break;
                case GUEST:
                    thisUser.setFirstName(bundle.getString(USER));
                    thisUser.setLastName((bundle.getString(USER_LAST_NAME)));
                    thisUser.setDisplayName(bundle.getString(DISPLAY_PROFILE_NAME));
                    thisUser.setPhone(bundle.getString(USER_PHONE_KEY));

                    String guestFullNameString = thisUser.getFirstName() + " " + thisUser.getLastName();
                    fullName.setText(guestFullNameString);
                    displayName.setText(thisUser.getDisplayName());
                    phoneNumber.setText(thisUser.getPhone());
                    profilePresenter.updateDbStudentInfo(thisUser);

                    break;
            }

        } else {
            Toast.makeText(this, getText(R.string.error_message), Toast.LENGTH_SHORT).show();
        }
    }

    //======================================VIEW INTERFACE METHODS==================================
    @Override
    public void setUploadProfilePic(boolean myBoolean, int visibility) {

        String uploadMessageSuccess = this.getResources().getString(R.string.upload_success);
        String uploadMessageFail = this.getResources().getString(R.string.upload_failed);

        uploadProgressBar.setVisibility(visibility);
        if (myBoolean) {
            saveImageUrlToFirebaseDatabase(currentUser.getPhotoUrl().toString());
            Picasso.with(this)
                    .load(profileImageUri)
                    .resize(1444,1444)
                    .centerCrop()
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
    public void setUpdateUserInFirebaseDbStatus(boolean myBoolean) {
        if (myBoolean) {
            Toast.makeText(this, this.getText(R.string.profile_updated_message), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, this.getText(R.string.complete_profile_message), Toast.LENGTH_SHORT).show();
        }
    }

//==========================================UTILITY METHODS=========================================

    private void loadProfilePic() {

        if (currentUser != null) {
            email.setText(currentUser.getEmail());
            if (currentUser.getPhotoUrl() != null) {

                uploadProgressBar.setVisibility(View.VISIBLE);
                Picasso.with(this)
                        .load(currentUser.getPhotoUrl().toString())
                        .centerCrop()
                        .resize(1444,1444)
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
                Log.d(TAG, "loadProfilePic: download Picture with Picasso");
            } else {
                float pixelDensity = getResources().getDisplayMetrics().density;
                int pixelValue = (int)pixelDensity*50;

                switch (USERTYPE) {
                    case STUDENT:
                        profileImage.setPadding(pixelValue,pixelValue,pixelValue,pixelValue);
                        profileImage.setImageResource(R.drawable.talent);
                        break;
                    case STAFF:
                        profileImage.setPadding(pixelValue,pixelValue,pixelValue,pixelValue);
                        profileImage.setImageResource(R.drawable.staff_edacy);
                        break;
                    case INSTRUCTOR:
                        profileImage.setPadding(pixelValue,pixelValue,pixelValue,pixelValue);
                        profileImage.setImageResource(R.drawable.ic_instructor);
                        break;
                    case GUEST:
                        profileImage.setPadding(pixelValue,pixelValue,pixelValue,pixelValue);
                        profileImage.setImageResource(R.drawable.ic_person);
                        break;
                    default:
                        profileImage.setPadding(pixelValue,pixelValue,pixelValue,pixelValue);
                        profileImage.setImageResource(R.drawable.ic_person);
                        break;
                }
                Log.d(TAG, "loadProfilePic: Load talent icon");
            }

        }

    }

    private void loadUserInfoFromUserType() {

        switch (USERTYPE) {
            case STUDENT:
                loadStudentInfo();
                break;

            case STAFF:
                loadStaffInfo();
                break;
            case INSTRUCTOR:
                loadInstructorInfo();
                break;
            case GUEST:
                loadGuestInfo();
                break;
            default:
                displayName.setText(getText(R.string.display_name));
                phoneNumber.setText(getText(R.string.phone_number));
                specialization.setText(getText(R.string.specialization));
                break;
        }


    }

    private void loadStudentInfo() {
        if (thisStudent != null) {
            if (thisStudent.getFirstName() != null && thisStudent.getLastName() != null) {
                String userFullName = thisStudent.getFirstName() + " " + thisStudent.getLastName();
                Log.d(TAG, "loadProfilePic: Name of user displayed on screen");
                fullName.setText(userFullName);
            } else {
                fullName.setText(getText(R.string.name));
            }
            if (thisStudent.getDisplayName() != null) {
                displayName.setText(thisStudent.getDisplayName());
            } else {
                displayName.setText(getText(R.string.display_name));
            }
            if (thisStudent.getPhone() != null) {
                phoneNumber.setText(thisStudent.getPhone());
            } else {
                phoneNumber.setText(getText(R.string.phone_number));
            }
            cohort.setVisibility(View.VISIBLE);
            cohortTv.setVisibility(View.VISIBLE);

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
            bundleForDialogFragment.putString(USER, thisStudent.getFirstName());
            bundleForDialogFragment.putString(USER_LAST_NAME, thisStudent.getLastName());
            bundleForDialogFragment.putString(DISPLAY_PROFILE_NAME, thisStudent.getDisplayName());
            bundleForDialogFragment.putString(USER_PHONE_KEY, thisStudent.getPhone());

            profileDialogFragment.setArguments(bundleForDialogFragment);

        }


    }

    private void loadStaffInfo() {
        if (thisStaff != null) {
            String staffFullName = thisStaff.getFirstName() + " " + thisStaff.getLastName();
            if (!TextUtils.isEmpty(staffFullName)) {
                Log.d(TAG, "loadProfilePic: Name of user displayed on screen");
                fullName.setText(staffFullName);
            } else {
                fullName.setText(getText(R.string.name));
            }
            if (thisStaff.getDisplayName() != null) {
                displayName.setText(thisStaff.getDisplayName());
            } else {
                displayName.setText(getText(R.string.display_name));
            }
            if (thisStaff.getPhone() != null) {
                phoneNumber.setText(thisStaff.getPhone());
            } else {
                phoneNumber.setText(getText(R.string.phone_number));
            }

            if (thisStaff.getJobTitle() != null) {
                specialization.setText(thisStaff.getJobTitle());
                bundleForDialogFragment.putString(SPECILIZATION, thisStaff.getJobTitle());
            } else {
                bundleForDialogFragment.putString(SPECILIZATION
                        , (String) getText(R.string.job_title));
                specialization.setText(getText(R.string.job_title));
            }
            bundleForDialogFragment.putString(USER, thisStaff.getFirstName());
            bundleForDialogFragment.putString(USER_LAST_NAME, thisStaff.getLastName());
            bundleForDialogFragment.putString(DISPLAY_PROFILE_NAME, thisStaff.getDisplayName());
            bundleForDialogFragment.putString(USER_PHONE_KEY, thisStaff.getPhone());

            profileDialogFragment.setArguments(bundleForDialogFragment);
        }
    }

    private void loadInstructorInfo() {
        if (thisInstructor != null) {
            String instructorFullName = thisInstructor.getFirstName() + " " + thisInstructor.getLastName();
            if (!TextUtils.isEmpty(instructorFullName)) {
                Log.d(TAG, "loadProfilePic: Name of user displayed on screen");
                fullName.setText(instructorFullName);
            } else {
                fullName.setText(getText(R.string.name));
            }
            if (thisInstructor.getDisplayName() != null) {
                displayName.setText(thisInstructor.getDisplayName());
            } else {
                displayName.setText(getText(R.string.display_name));
            }
            if (thisInstructor.getPhone() != null) {
                phoneNumber.setText(thisInstructor.getPhone());
            } else {
                phoneNumber.setText(getText(R.string.phone_number));
            }
            if (thisInstructor.getSpecialization() != null) {
                specialization.setText(thisInstructor.getSpecialization());
                bundleForDialogFragment.putString(SPECILIZATION, thisInstructor.getSpecialization());
            } else {
                specialization.setText(getText(R.string.job_title));
                bundleForDialogFragment.putString(SPECILIZATION
                        , (String) getText(R.string.job_title));
            }
            bundleForDialogFragment.putString(USER, thisInstructor.getFirstName());
            bundleForDialogFragment.putString(USER_LAST_NAME, thisInstructor.getLastName());
            bundleForDialogFragment.putString(DISPLAY_PROFILE_NAME, thisInstructor.getDisplayName());
            bundleForDialogFragment.putString(USER_PHONE_KEY, thisInstructor.getPhone());

            profileDialogFragment.setArguments(bundleForDialogFragment);

        }
    }

    private void loadGuestInfo() {
        if (thisUser != null) {
            String guestFullName = thisUser.getFirstName() + " " + thisUser.getLastName();
            if (!TextUtils.isEmpty(guestFullName)) {
                Log.d(TAG, "loadProfilePic: Name of user displayed on screen");
                fullName.setText(guestFullName);
            } else {
                fullName.setText(getText(R.string.name));
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
            specialization.setVisibility(View.GONE);
        }
        bundleForDialogFragment.putString(USER, thisUser.getFirstName());
        bundleForDialogFragment.putString(USER_LAST_NAME, thisUser.getLastName());
        bundleForDialogFragment.putString(DISPLAY_PROFILE_NAME, thisUser.getDisplayName());
        bundleForDialogFragment.putString(USER_PHONE_KEY, thisUser.getPhone());

        profileDialogFragment.setArguments(bundleForDialogFragment);
    }

    private void saveImageUrlToFirebaseDatabase(String imageUrl){
        switch (USERTYPE) {
            case STUDENT:
                if(thisStudent != null){
                    thisStudent.setProfileImageurl(imageUrl);
                    profilePresenter.updateDbStudentInfo(thisStudent);
                }
                break;

            case STAFF:
                if(thisStaff != null){
                    thisStaff.setProfileImageurl(imageUrl);
                    profilePresenter.updateDbStudentInfo(thisStaff);
                }
                break;
            case INSTRUCTOR:
                if(thisInstructor != null){
                    thisInstructor.setProfileImageurl(imageUrl);
                    profilePresenter.updateDbStudentInfo(thisInstructor);
                }
                break;
            case GUEST:
                if(thisUser != null){
                    thisUser.setProfileImageurl(imageUrl);
                    profilePresenter.updateDbStudentInfo(thisUser);
                }
                break;
            default:
                if(thisUser != null){
                    thisUser.setProfileImageurl(imageUrl);
                    profilePresenter.updateDbStudentInfo(thisUser);
                }
                break;
        }
    }
}
