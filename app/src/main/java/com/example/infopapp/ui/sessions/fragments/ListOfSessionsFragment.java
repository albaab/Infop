package com.example.infopapp.ui.sessions.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.infopapp.R;
import com.example.infopapp.entities.Session;
import com.example.infopapp.ui.sessions.SessionViewModel;
import com.example.infopapp.ui.sessions.SessionAdapter;

import java.util.List;

import static com.example.infopapp.utils.Constants.HARD_SKILLS;
import static com.example.infopapp.utils.Constants.SESSION;


public class ListOfSessionsFragment extends Fragment {

//=======================================ATTRIBUTES=================================================
//    private LiveData<List<Session>> listOfSessionsLive;

    private RecyclerView sessionsRecyclerView;
    private SessionAdapter sessionAdapter;
    private ProgressBar progressBar;

    private Context mContext;

    private ListOfSessionsFragmentListener mListOfSessionsListener;


//=======================================CONSTRUCTOR=================================================
    public ListOfSessionsFragment() {
        // Required empty public constructor
    }

//=======================================ON CREATE VIEW=============================================
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_of_sessions, container, false);
    }

//=======================================ON VIEW CREATED============================================
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressBar = view.findViewById(R.id.progressBar_sessions);
        sessionsRecyclerView = view.findViewById(R.id.list_of_session_rv);
        final Bundle selectedSessionBundle = new Bundle();
        RecyclerView.LayoutManager sessionsLayoutManager = new LinearLayoutManager(mContext);
        sessionAdapter = new SessionAdapter();

        String sessionType = getArguments() != null ? getArguments().getString(HARD_SKILLS) : null;


        sessionsRecyclerView.setHasFixedSize(true);
        sessionsRecyclerView.setLayoutManager(sessionsLayoutManager);

        SessionViewModel sessionViewModel = ViewModelProviders.of(this).get(SessionViewModel.class);

        sessionViewModel.getAllSessionsFromFirebase(sessionType, new SessionViewModel.OnSessionModelCallBack() {

            @Override
            public void getSessionList(List<Session> sessions) {
                progressBar.setVisibility(View.GONE);
                sessionAdapter.setListOfSessions(sessions);
                sessionsRecyclerView.setAdapter(sessionAdapter);
            }
        });

//        sessionViewModel.getAllSessionsFromRoomDatabase().observe(this, new Observer<List<Session>>() {
//            @Override
//            public void onChanged(List<Session> sessions) {
//                sessionAdapter.setListOfSessions(sessions);
//                sessionsRecyclerView.setAdapter(sessionAdapter);
//            }
//        });

        sessionAdapter.setOnItemClickListener(new SessionAdapter.OnSessionClickListener() {
            @Override
            public void onItemClicked(Session session) {
                selectedSessionBundle.putParcelable(SESSION,session);
                mListOfSessionsListener.goToSelectedSession(selectedSessionBundle);
            }
        });

    }


//========================================ON ATTACH=================================================
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
        if (context instanceof ListOfSessionsFragmentListener) {
            mListOfSessionsListener = (ListOfSessionsFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }


//========================================ON DETACH=================================================
    @Override
    public void onDetach() {
        super.onDetach();
        mListOfSessionsListener = null;
    }

//==================================FRAGMENT INTERFACE LISTENER=====================================
    public interface ListOfSessionsFragmentListener {
        void goToSelectedSession(Bundle bundle);
    }
}
