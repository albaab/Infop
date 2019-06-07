package com.example.infopapp.ui.cohort_clicked;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import com.example.infopapp.R;
import com.example.infopapp.entities.Student;
import com.example.infopapp.ui.cohort_clicked.fragments.StudentResumeFragment;
import com.example.infopapp.ui.cohort_clicked.fragments.list_of_students.ListOfStudentsInCohortFragment;
import com.example.infopapp.ui.cohort_clicked.fragments.StudentProfileFragment;

import static com.example.infopapp.utils.Constants.COHORT;
import static com.example.infopapp.utils.Constants.STUDENT_RESUME;

public class CohortClickedActivity extends AppCompatActivity
        implements ListOfStudentsInCohortFragment.OnListOfStudentsFragmentListener,
                    StudentProfileFragment.OnStudentProfileListener{



//===========================================ATTRIBUTES==============================================
    private ListOfStudentsInCohortFragment lisfOfStudents;
    private StudentProfileFragment studentProfileFragment;
    private StudentResumeFragment studentResumeFragment;

    private FragmentManager manager = getSupportFragmentManager();

//===========================================ON CREATE==============================================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cohort_clicked);

        lisfOfStudents = new ListOfStudentsInCohortFragment();
        studentProfileFragment = new StudentProfileFragment();
        studentResumeFragment = new StudentResumeFragment();
        //get the cohort number clicked from the intent of the portfolios fragment
        Intent intent = getIntent();
        int cohortclicked = intent.getIntExtra(COHORT,1);

        setTitle(COHORT + " " + String.valueOf(cohortclicked));
        Bundle cohortClickedBundle = new Bundle();
        cohortClickedBundle.putInt(COHORT,cohortclicked);
        lisfOfStudents.setArguments(cohortClickedBundle);
        goToSelectedFragment(lisfOfStudents);

    }



//=================================INTERFACE CALLBACK METHODS=======================================
    @Override
    public void onListOfStudentCallBack(Student student) {
        studentProfileFragment.setStudent(student);
        goToSelectedFragment(studentProfileFragment);
    }


    @Override
    public void sendResumeUrlToFragment(String url) {
        Bundle bundle = new Bundle();
        bundle.putString(STUDENT_RESUME,url);
        studentResumeFragment.setArguments(bundle);
        goToSelectedFragment(studentResumeFragment);

    }


//========================================FINISH METHOD=============================================

    @Override
    public void finish() {
        super.finish();
        studentProfileFragment = null;
        lisfOfStudents = null;
    }
//========================================UTILITY METHODS===========================================
    private void goToSelectedFragment(Fragment fragment){
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.cohort_clicked_container,fragment);
        if(fragment instanceof ListOfStudentsInCohortFragment){
            transaction.commit();
        }else{
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }
}
