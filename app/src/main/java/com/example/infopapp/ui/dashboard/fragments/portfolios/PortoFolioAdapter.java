package com.example.infopapp.ui.dashboard.fragments.portfolios;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.infopapp.R;
import com.example.infopapp.entities.Cohort;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class PortoFolioAdapter extends ListAdapter<Cohort, PortoFolioAdapter.CohortViewHolder> {

    private List<Cohort> cohorts = new ArrayList<>();
    private OnCohortClickListener mListener;



    PortoFolioAdapter(){
        super(DIFF_CALLBACK);
    }

//=========================================VIEWHOLDER===============================================
class CohortViewHolder extends RecyclerView.ViewHolder{

        private TextView CohortTitle;

        //--------------------------------viewholder constructor-----------------------------------
        CohortViewHolder(@NonNull View itemView) {
            super(itemView);
            CohortTitle = itemView.findViewById(R.id.cohort_number_tv);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION && mListener != null){
                        mListener.onCohortClicked(cohorts.get(position));
                    }

                }
            });

        }
    }



//====================================ON CREATE VIEWHOLDER==========================================
    @NonNull
    @Override
    public CohortViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cohort_item,
                parent,false);
        return new CohortViewHolder(v);
    }

//====================================ON BIND VIEWHOLDER==========================================
    @Override
    public void onBindViewHolder(@NonNull CohortViewHolder holder, int position) {
        Cohort currentCohort = cohorts.get(position);
        holder.CohortTitle.setText(String.valueOf(currentCohort.getCohortNumber()));


    }


//=======================================GET ITEM COUNT=============================================
    @Override
    public int getItemCount() {
        return cohorts.size();
    }



//====================================DIFF UTIL CALLBACK============================================

    private static final DiffUtil.ItemCallback<Cohort> DIFF_CALLBACK = new DiffUtil.ItemCallback<Cohort>() {
        @Override
        public boolean areItemsTheSame(@NonNull Cohort oldItem, @NonNull Cohort newItem) {
            return oldItem.getCohortNumber() ==  newItem.getCohortNumber();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Cohort oldItem, @NonNull Cohort newItem) {
            return oldItem.getStudentList().equals(newItem.getStudentList());
        }
    };


//====================================INTERFACE CALLBACKS AND METHODS===============================
    public void setListOfCohorts(List<Cohort> cohorts){
        this.cohorts = cohorts;
        notifyDataSetChanged();
    }

    public interface OnCohortClickListener {
        void onCohortClicked(Cohort cohort);
    }
    public void setOnItemClickListener(OnCohortClickListener mListener){
        this.mListener = mListener;
    }


}