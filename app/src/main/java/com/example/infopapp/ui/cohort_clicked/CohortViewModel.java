package com.example.infopapp.ui.cohort_clicked;

import android.app.Application;
import android.util.Log;

import com.example.infopapp.entities.Cohort;
import com.example.infopapp.entities.Student;
import com.example.infopapp.repositories.UserRepository;
import com.example.infopapp.ui.profile.ProfileView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class CohortViewModel extends AndroidViewModel implements ProfileView {

    private UserRepository userRepo;


    CohortViewModel(@NonNull Application application) {
        super(application);
        userRepo = new UserRepository(this);
    }

    public void deleteStudentDbEntry(Student studentAt) {
        userRepo.deteleStudentInFirebase(studentAt);
    }


//======================================Cohort ViewModel method ====================================

    public void getTheCohort(final int cohortNumber, final HomeViewModelCallbackListener listener) {
        userRepo.getAllCohorts(new UserRepository.UserRepoCallBackInterface() {
            @Override
            public void onCallBackRepo(List<Cohort> cohorts) {

                //go through the cohorts
                for (Cohort currentCohort : cohorts) {
                    if (cohortNumber == currentCohort.getCohortNumber()) {
                        Log.d(TAG, "onCallBackRepo: START " + currentCohort);
                        listener.OnCohortViewModelCallback(currentCohort);
                        break;
                    }
                }

            }
        });
    }

    //========================================unused profile view methods===============================
    @Override
    public void setUploadProfilePic(boolean myBoolean, int visibility) {

    }

    @Override
    public void setUpdateUserInFirebaseDbStatus(boolean myBoolean) {

    }

    @Override
    public void setUploadUserInFirebaseDbStatus(boolean myBoolean) {

    }

    //========================================CallBack interface listener ==============================
    public interface HomeViewModelCallbackListener {
        void OnCohortViewModelCallback(Cohort cohort);
    }

}
