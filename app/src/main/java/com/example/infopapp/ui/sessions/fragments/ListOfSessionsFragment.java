package com.example.infopapp.ui.sessions.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.infopapp.R;
import com.example.infopapp.entities.Session;
import com.example.infopapp.ui.sessions.SessionViewModel;
import com.example.infopapp.ui.sessions.SessionAdapter;

import java.util.List;


public class ListOfSessionsFragment extends Fragment {

    private SessionViewModel sessionViewModel;

    private LiveData<List<Session>> listOfSessionsLive;

    private RecyclerView sessionsRecyclerView;
    private SessionAdapter sessionAdapter;

    private Context mContext;

    private ListOfSessionsFragmentListener mListOfSessionsListener;

    public ListOfSessionsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_of_sessions, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sessionsRecyclerView = view.findViewById(R.id.list_of_session_rv);
        final Bundle listOfsessionsBundle = new Bundle();
        RecyclerView.LayoutManager sessionsLayoutManager = new LinearLayoutManager(mContext);
        sessionAdapter = new SessionAdapter();


        sessionsRecyclerView.setHasFixedSize(true);
        sessionsRecyclerView.setLayoutManager(sessionsLayoutManager);

        sessionViewModel = ViewModelProviders.of(this).get(SessionViewModel.class);
        sessionViewModel.getAllSessionsFromDatabase().observe(this, new Observer<List<Session>>() {
            @Override
            public void onChanged(List<Session> sessions) {
                sessionAdapter.setListOfSessions(sessions);
                sessionsRecyclerView.setAdapter(sessionAdapter);
            }
        });

        sessionAdapter.setOnItemClickListener(new SessionAdapter.OnSessionClickListener() {
            @Override
            public void onItemClicked(Session session) {

                listOfsessionsBundle.putString("SESSION_TITLE",session.getSessionTitle());
                listOfsessionsBundle.putString("SESSION_MONTH",session.getSessionMonth());
                listOfsessionsBundle.putInt("SESSION_DAY",session.getSessionDay());
                listOfsessionsBundle.putString("SESSION_HOUR",session.getSessionHour());
                listOfsessionsBundle.putString("SESSION_DESCRIPTION",session.getSessionDescription());
                listOfsessionsBundle.putString("SESSION_HOMEWORK",session.getSessionHomework());
                listOfsessionsBundle.putString("SESSION_RESOURCES",session.getSessionResources());

                mListOfSessionsListener.goToSelectedSession(listOfsessionsBundle);
            }
        });

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        if (context instanceof ListOfSessionsFragmentListener) {
            mListOfSessionsListener = (ListOfSessionsFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        mListOfSessionsListener = null;
    }

    public interface ListOfSessionsFragmentListener {
        void goToSelectedSession(Bundle bundle);
    }
}
