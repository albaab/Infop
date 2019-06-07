package com.example.infopapp.ui.sessions;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.infopapp.R;
import com.example.infopapp.entities.Session;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class SessionAdapter extends ListAdapter<Session, SessionAdapter.SessionViewHolder> {


//=======================================PRIVATE ATTRIBUTES=========================================
    private List<Session> sessions = new ArrayList<>();
    private OnSessionClickListener mListener;


//=======================================ADAPTER CONSTRUCTOR========================================
    public SessionAdapter(){
        super(DIFF_CALLBACK);
    }


//=======================================SESSION VIEWHOLDER=========================================
    class SessionViewHolder extends RecyclerView.ViewHolder{

        //VIEW ITEMS
        private TextView sessionTitle, sessionMonth, sessionDay;

        SessionViewHolder(@NonNull View itemView) {
            super(itemView);

            //GET THE VIEW ITEMS FROM THE LAYOUT
            sessionTitle = itemView.findViewById(R.id.session_title);
            sessionMonth = itemView.findViewById(R.id.session_month);
            sessionDay = itemView.findViewById(R.id.session_day);

            //ON SESSION CLICKED LISTENER
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION && mListener != null){
                        mListener.onItemClicked(sessions.get(position));
                    }

                }
            });

        }
    }


//=======================================ON CREATE VIEW HOLDER======================================
    @NonNull
    @Override
    public SessionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.session_item,
                parent,false);
        return new SessionViewHolder(v);
    }


//=======================================ON BIND VIEW HOLDER======================================
    @Override
    public void onBindViewHolder(@NonNull SessionViewHolder holder, int position) {
        Session currentSession = sessions.get(position);
        holder.sessionTitle.setText(currentSession.getSessionTitle());
        holder.sessionMonth.setText(currentSession.getSessionMonth());
        holder.sessionDay.setText(String.valueOf(currentSession.getSessionDay()));
    }


//=======================================GET ITEM COUNT=============================================
    @Override
    public int getItemCount() {
        return sessions.size();
    }

    public void setListOfSessions(List<Session> sessions){
        this.sessions = sessions;
        notifyDataSetChanged();
    }


//===============================DIFF UTIL OBJECT TO COMPARE ITEM===================================

    private static final DiffUtil.ItemCallback<Session> DIFF_CALLBACK = new DiffUtil.ItemCallback<Session>() {
        @Override
        public boolean areItemsTheSame(@NonNull Session oldItem, @NonNull Session newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Session oldItem, @NonNull Session newItem) {
            return oldItem.getSessionTitle().equals(newItem.getSessionTitle()) &&
                    oldItem.getSessionDay() == newItem.getSessionDay()&&
                    oldItem.getSessionMonth().equals(newItem.getSessionMonth());
        }
    };


//=======================================INTERFACE CALLBACK LISTENER================================

    public interface OnSessionClickListener {
        void onItemClicked(Session session);
    }

//=======================================SET LISTENER METHOD========================================

    public void setOnItemClickListener(OnSessionClickListener mListener){
        this.mListener = mListener;
    }


}