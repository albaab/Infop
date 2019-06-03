package com.example.infopapp.ui.sessions.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.infopapp.R;
import com.example.infopapp.entities.Session;

import static com.example.infopapp.utils.Constants.SESSION;


public class SelectedSessionFragment extends Fragment {

    private TextView description;
    private TextView homework;
    private TextView resources;
    private TextView feedBacks;
    private LinearLayout layout_description, layout_homework, layout_resources,layout_feedBacks;

    private FeedbackDialogFragment feedbackDialogFragment;



    public SelectedSessionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_selected_session, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView title = view.findViewById(R.id.session_view_title);
        description = view.findViewById(R.id.description_text_view);
        homework = view.findViewById(R.id.homework_text_view);
        resources = view.findViewById(R.id.resources_text_view);
        feedBacks = view.findViewById(R.id.feedback_text_view);

        layout_description = view.findViewById(R.id.description_layout);
        layout_homework = view.findViewById(R.id.homework_layout);
        layout_resources = view.findViewById(R.id.resources_layout);
        layout_feedBacks = view.findViewById(R.id.feedback_layout);

        feedbackDialogFragment = new FeedbackDialogFragment();

        Bundle selectedSessionBundle = getArguments();
        assert selectedSessionBundle != null;
        Session thisSession = selectedSessionBundle.getParcelable(SESSION);


        assert thisSession != null;
        title.setText(thisSession.getSessionTitle());
        description.setText(thisSession.getSessionDescription());
        homework.setText(thisSession.getSessionHomework());
        resources.setText(thisSession.getSessionResources());

        showHideTextViews(thisSession);

    }

    private void showHideTextViews(final Session session){

        layout_description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(description.getVisibility() == View.GONE){
                    description.setVisibility(View.VISIBLE);
                }else if(description.getVisibility() == View.VISIBLE){
                    description.setVisibility(View.GONE);
                }
            }
        });

        layout_homework.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(homework.getVisibility() == View.GONE){
                    homework.setVisibility(View.VISIBLE);
                }else if(homework.getVisibility() == View.VISIBLE){
                    homework.setVisibility(View.GONE);
                }
            }
        });

        layout_resources.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(resources.getVisibility() == View.GONE){
                    resources.setVisibility(View.VISIBLE);
                }else if(resources.getVisibility() == View.VISIBLE){
                    resources.setVisibility(View.GONE);
                }
            }
        });
        layout_feedBacks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(feedBacks.getVisibility() == View.GONE){
                    feedBacks.setVisibility(View.VISIBLE);
                    feedBacks.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            assert getFragmentManager() != null;
                            Bundle bundle = new Bundle();

                            bundle.putParcelable(SESSION,session);
                            feedbackDialogFragment.setArguments(bundle);
                            feedbackDialogFragment.show(getFragmentManager(),"Feedback Dialog");
                        }
                    });
                }else if(feedBacks.getVisibility() == View.VISIBLE){
                    feedBacks.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        mListener = null;
    }



}
