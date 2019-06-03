package com.example.infopapp.ui.dashboard.fragments.portfolios;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.infopapp.R;
import com.example.infopapp.entities.Cohort;
import com.example.infopapp.ui.cohort_clicked.CohortClickedActivity;
import com.example.infopapp.ui.home_screens.HomeViewModel;

import java.util.ArrayList;
import java.util.List;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.example.infopapp.utils.Constants.COHORT;


public class PortFoliosFragment extends Fragment {

    private PortoFolioAdapter portoFolioAdapter;
    private List<Cohort> cohorts = new ArrayList<>();

    private Context mContext;

    public PortFoliosFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_portfolios, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final RecyclerView porfolioRv = view.findViewById(R.id.list_of_cohorts_rv);
        porfolioRv.setHasFixedSize(true);
        ProgressBar progressBar = view.findViewById(R.id.firebase_progress_bar);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        portoFolioAdapter = new PortoFolioAdapter();

        porfolioRv.setLayoutManager(mLayoutManager);

        HomeViewModel homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);

        homeViewModel.getAllCohortsDataVm(progressBar,new HomeViewModel.HomeViewModelCallbackListener() {
            @Override
            public void OnHomeViewModelCallback(List<Cohort> cohorts) {
                portoFolioAdapter.setListOfCohorts(cohorts);
                porfolioRv.setAdapter(portoFolioAdapter);
                portoFolioAdapter.setOnItemClickListener(new PortoFolioAdapter.OnCohortClickListener() {
                    @Override
                    public void onCohortClicked(Cohort cohort) {
                        //todo go to respective cohort
                        Intent intent = new Intent(mContext, CohortClickedActivity.class);
                        intent.putExtra(COHORT,cohort.getCohortNumber());
                        startActivity(intent);
                    }
                });

            }
        });

//        portoFolioAdapter.setListOfCohorts(cohorts);
//        porfolioRv.setAdapter(portoFolioAdapter);
//        portoFolioAdapter.setOnItemClickListener(new PortoFolioAdapter.OnCohortClickListener() {
//            @Override
//            public void onCohortClicked(Cohort cohort) {
//                Toast.makeText(mContext, cohort + " Clicked", Toast.LENGTH_SHORT).show();
//            }
//        });



    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;

    }


}
