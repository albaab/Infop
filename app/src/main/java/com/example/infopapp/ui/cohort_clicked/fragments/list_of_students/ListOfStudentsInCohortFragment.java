package com.example.infopapp.ui.cohort_clicked.fragments.list_of_students;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.infopapp.R;
import com.example.infopapp.entities.Cohort;
import com.example.infopapp.entities.Student;
import com.example.infopapp.ui.cohort_clicked.CohortViewModel;
import com.example.infopapp.ui.profile.ProfileView;

import static com.example.infopapp.utils.Constants.COHORT;


public class ListOfStudentsInCohortFragment extends Fragment {


//===================================PRIVATE ATTRIBUTES=============================================

    private OnListOfStudentsFragmentListener mListener;
    private RecyclerView listRecyclerView;
    private ListOfStudentsAdapter listOfStudentsAdapter;


//==================================CONSTRUCTOR==============================================
    public ListOfStudentsInCohortFragment() {

    }

//======================================ON CREATE VIEW==============================================
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_of_students_in_cohort, container, false);
    }

//======================================ON VIEW CREATED==============================================
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listRecyclerView = view.findViewById(R.id.list_of_students_rv);
        listOfStudentsAdapter = new ListOfStudentsAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        listRecyclerView.setHasFixedSize(true);
        listRecyclerView.setLayoutManager(linearLayoutManager);


        Bundle listOfStudentsFragmentBundle = getArguments();
        assert listOfStudentsFragmentBundle != null;
        int cohortclicked = listOfStudentsFragmentBundle.getInt(COHORT);

        final CohortViewModel cohortViewModel = ViewModelProviders.of(this).get(CohortViewModel.class);
        cohortViewModel.getTheCohort(cohortclicked, new CohortViewModel.HomeViewModelCallbackListener() {
            @Override
            public void OnCohortViewModelCallback(Cohort cohort) {
                listOfStudentsAdapter.setListOfStudents(getContext(),cohort.getStudentList());
                listRecyclerView.setAdapter(listOfStudentsAdapter);
                listOfStudentsAdapter.setOnItemClickListener(new ListOfStudentsAdapter.OnStudentClickListener() {
                    @Override
                    public void onItemClicked(Student student) {
                        mListener.onListOfStudentCallBack(student);
                    }
                });
                new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                        ItemTouchHelper.LEFT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView,
                                          @NonNull RecyclerView.ViewHolder viewHolder,
                                          @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        cohortViewModel.deleteStudentDbEntry(
                                listOfStudentsAdapter.getStudentAt(viewHolder.getAdapterPosition()));
                        Toast.makeText(getActivity(), getText(R.string.student_deleted), Toast.LENGTH_SHORT).show();
                    }
                }).attachToRecyclerView(listRecyclerView);
            }
        });


    }


//===========================================ON ATTACH==============================================
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnListOfStudentsFragmentListener) {
            mListener = (OnListOfStudentsFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }


//===========================================ON DETACH==============================================
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

//===================================INTERFACE CALLBACK LISTNER=====================================
    public interface OnListOfStudentsFragmentListener {
        void onListOfStudentCallBack(Student student);
    }
}
