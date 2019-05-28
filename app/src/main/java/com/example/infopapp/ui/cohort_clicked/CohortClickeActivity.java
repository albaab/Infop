package com.example.infopapp.ui.cohort_clicked;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.infopapp.R;
import com.example.infopapp.entities.Cohort;
import com.example.infopapp.entities.Student;
import com.example.infopapp.ui.cohort_clicked.fragments.list_of_students.ListOfStudentsInCohortFragment;
import com.example.infopapp.ui.cohort_clicked.fragments.StudentProfileFragment;

import static com.example.infopapp.utils.StringConstants.COHORT;

public class CohortClickeActivity extends AppCompatActivity
        implements ListOfStudentsInCohortFragment.OnListOfStudentsFragmentListener {

    private ListOfStudentsInCohortFragment lisfOfStudents = new ListOfStudentsInCohortFragment();
    private StudentProfileFragment studentProfileFragment = new StudentProfileFragment();

    private FragmentManager manager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cohort_clicked);

        //get the cohort number clicked from the intent of the portfolios fragment
        Intent intent = getIntent();
        int cohortclicked = intent.getIntExtra(COHORT,1);

        setTitle(COHORT + " " + String.valueOf(cohortclicked));
        Bundle cohortClickedBundle = new Bundle();
        cohortClickedBundle.putInt(COHORT,cohortclicked);
        lisfOfStudents.setArguments(cohortClickedBundle);
        goToSelectedFragment(lisfOfStudents);

    }

    private void goToSelectedFragment(Fragment fragment){
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.cohort_clicked_container,fragment);
        transaction.commit();
    }

    @Override
    public void onListOfStudentCallBack(Student student) {
        studentProfileFragment.setStudent(student);
        goToSelectedFragment(studentProfileFragment);
    }

    @Override
    public void finish() {
        super.finish();
        studentProfileFragment = null;
        lisfOfStudents = null;
    }
}
