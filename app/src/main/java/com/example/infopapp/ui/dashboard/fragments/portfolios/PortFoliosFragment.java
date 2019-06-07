package com.example.infopapp.ui.dashboard.fragments.portfolios;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.infopapp.R;
import com.example.infopapp.entities.Cohort;
import com.example.infopapp.entities.PostItem;
import com.example.infopapp.ui.cohort_clicked.CohortClickedActivity;
import com.example.infopapp.ui.home_screens.HomeViewModel;

import java.util.List;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.example.infopapp.utils.Constants.COHORT;


public class PortFoliosFragment extends Fragment {


//======================================PRIVATE ATTRIVUTES==========================================
    private PortoFolioAdapter portoFolioAdapter;

//======================================CONSTRUCTOR=================================================
    public PortFoliosFragment() {
        // Required empty public constructor
    }

//======================================ON CREATE METHOD============================================
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


//==================================ON CREATE VIEW METHOD===========================================
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_portfolios, container, false);
    }

//======================================ON VIEW CREATED=============================================
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //RECYCLER VIEW
        final RecyclerView porfolioRv = view.findViewById(R.id.list_of_cohorts_rv);
        porfolioRv.setHasFixedSize(true);
        //PROGRESSBAR
        ProgressBar progressBar = view.findViewById(R.id.firebase_progress_bar);

        //LAYOUT MANAGER FOR THE RECYCLER VIEW
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());

        //RECYCLER VIEW ADAPTER
        portoFolioAdapter = new PortoFolioAdapter();

        //SET LAYOUT MANAGER FOR THE RECYCLER VIEW
        porfolioRv.setLayoutManager(mLayoutManager);

        //GET INSTANCE OF A HOME VIEWMODEL
        HomeViewModel homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);

        //CALL THE VIEWMODEL METHOD TO GET ALL THE COHORTS
        homeViewModel.getAllCohortsDataVm(progressBar,new HomeViewModel.HomeViewModelCallbackListener() {
            @Override
            public void getListOfcohorts(List<Cohort> cohorts) {
                portoFolioAdapter.setListOfCohorts(cohorts);
                porfolioRv.setAdapter(portoFolioAdapter);
                portoFolioAdapter.setOnItemClickListener(new PortoFolioAdapter.OnCohortClickListener() {
                    @Override
                    public void onCohortClicked(Cohort cohort) {
                        //todo go to respective cohort
                        Intent intent = new Intent(getContext(), CohortClickedActivity.class);
                        intent.putExtra(COHORT,cohort.getCohortNumber());
                        startActivity(intent);
                    }
                });

            }

            @Override
            public void getListOfPosts(List<PostItem> postItems) {
                //do nothing
            }
        });

    }


}
