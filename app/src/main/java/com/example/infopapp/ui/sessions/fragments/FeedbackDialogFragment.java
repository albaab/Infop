package com.example.infopapp.ui.sessions.fragments;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.infopapp.R;
import com.example.infopapp.entities.Session;
import com.example.infopapp.entities.StudentFeedbackItem;
import com.example.infopapp.ui.sessions.SessionViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

import static androidx.constraintlayout.widget.Constraints.TAG;
import static com.example.infopapp.utils.Constants.FEEDBACK_REQUEST;
import static com.example.infopapp.utils.Constants.SESSION;

public class FeedbackDialogFragment extends DialogFragment {

    //===========================================ATTRIBUTES========================================
    private SessionViewModel sessionViewModel;
    //FEEDBACK ITEM
    //RADIO GROUPS
    private RadioGroup rbAnswer1, rbAnswer2, rbAnswer3, rbAnswer4, rbAnswer5, rbAnswer6, rbAnswer7,
            rbAnswer8, rbAnswer9, rbAnswer10, rbAnswer11, rbAnswer12,
            rbAnswer13, rbAnswer14, rbAnswer15, rbAnswer16, rbAnswer17;

    private RadioButton ans2;
    //EDIT TEXTS
    private EditText etAnswer18, etAnswer19;

    private List<String> feedbacksAnswers;
    private StudentFeedbackItem studentFeedbackItem;

//===========================================ON CREATE DIALOG=======================================

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        //----------------------------initiate builder----------------------------------------------
        AlertDialog.Builder feedbackDialogBuilder = new AlertDialog.Builder(getContext());

        //-----------------------------instantiation of the required objects------------------------
        //Session viewModel
        sessionViewModel = ViewModelProviders.of(this).get(SessionViewModel.class);

        //ArrayList of feedback answers
        feedbacksAnswers = new ArrayList<>();

        //student feedback item object
        studentFeedbackItem = new StudentFeedbackItem();


        //-------------------------------------FRAGMENT VIEW----------------------------------------
        LayoutInflater inflater = Objects.requireNonNull(getActivity()).getLayoutInflater();
        @SuppressLint("InflateParams") final View view = inflater.inflate(R.layout.fragment_feedback_dialog, null);

        assert getArguments() != null;
        final Session selectedSession = getArguments().getParcelable(SESSION);

        //retrieve the view items from the dialog fragment
        retrieveViewItems(view);


        //SET THE FEEDBACK DIALOG
        feedbackDialogBuilder.setView(view)
                .setNegativeButton(getText(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //return
                    }
                })
                .setPositiveButton(getText(R.string.send_feedback), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //save the answers provided by the user
                        saveStudentAnswers(view);

                        Map<String, JSONObject> sessionFeedback = new HashMap<>();

                        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                            String userEmail = Objects.requireNonNull(FirebaseAuth
                                    .getInstance()
                                    .getCurrentUser()).getEmail();
                            if (userEmail != null && selectedSession != null) {
                                //store the feedback and its identifiers (user email)
//                                sessionFeedback.put(userEmail, feedbacksAnswers);
                                Gson gson = new Gson();
                                String studentFeedabk = gson.toJson(studentFeedbackItem);

                                selectedSession.setSessionstudentFeedbacks(studentFeedabk);
                                sessionViewModel.updateSessiontoFirebaseDb(selectedSession,FEEDBACK_REQUEST);
                                //todo update the session in the database

                                Log.d(TAG, "onClick: DEBUG-> FEEDBACK SENT");
                                Toast.makeText(getContext(), getText(R.string.feedback_sent), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getContext(), getText(R.string.error_message), Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onClick: DEBUG-> DID NOT FIND A USER");
                        }
                    }
                });

        return feedbackDialogBuilder.create();

    }

    //===========================================ON ATTACH==============================================
    //retrieve the view items from the dialog fragment
    private void retrieveViewItems(View view) {
        rbAnswer1 = view.findViewById(R.id.student_q_1);
//        rbAnswer2  = view.findViewById(R.id.student_q_2);
        rbAnswer3 = view.findViewById(R.id.student_q_3);
        rbAnswer4 = view.findViewById(R.id.student_q_4);
        rbAnswer5 = view.findViewById(R.id.student_q_5);
        rbAnswer6 = view.findViewById(R.id.student_q_6);
        rbAnswer7 = view.findViewById(R.id.student_q_7);
        rbAnswer8 = view.findViewById(R.id.student_q_8);
        rbAnswer9 = view.findViewById(R.id.student_q_9);
        rbAnswer10 = view.findViewById(R.id.student_q_10);
        rbAnswer11 = view.findViewById(R.id.student_q_11);
        rbAnswer12 = view.findViewById(R.id.student_q_12);
        rbAnswer13 = view.findViewById(R.id.student_q_13);
        rbAnswer14 = view.findViewById(R.id.student_q_14);
        rbAnswer15 = view.findViewById(R.id.student_q_15);
        rbAnswer16 = view.findViewById(R.id.student_q_16);
        rbAnswer17 = view.findViewById(R.id.student_q_17);

        etAnswer18 = view.findViewById(R.id.student_q_18);
        etAnswer19 = view.findViewById(R.id.student_q_19);
    }

    //saves the student answers in a feedback item
    private void saveStudentAnswers(View view) {


        // get the answers checked by the user
        //RADIO BUTTONS FOR THE CORRESPONDING RADIO GROUPS
        RadioButton ans1 = view.findViewById(rbAnswer1.getCheckedRadioButtonId());
//        ans2 = view.findViewById(rbAnswer2.getCheckedRadioButtonId());
        RadioButton ans3 = view.findViewById(rbAnswer3.getCheckedRadioButtonId());
        RadioButton ans4 = view.findViewById(rbAnswer4.getCheckedRadioButtonId());
        RadioButton ans5 = view.findViewById(rbAnswer5.getCheckedRadioButtonId());
        RadioButton ans6 = view.findViewById(rbAnswer6.getCheckedRadioButtonId());
        RadioButton ans7 = view.findViewById(rbAnswer7.getCheckedRadioButtonId());
        RadioButton ans8 = view.findViewById(rbAnswer8.getCheckedRadioButtonId());
        RadioButton ans9 = view.findViewById(rbAnswer9.getCheckedRadioButtonId());
        RadioButton ans10 = view.findViewById(rbAnswer10.getCheckedRadioButtonId());
        RadioButton ans11 = view.findViewById(rbAnswer11.getCheckedRadioButtonId());
        RadioButton ans12 = view.findViewById(rbAnswer12.getCheckedRadioButtonId());
        RadioButton ans13 = view.findViewById(rbAnswer13.getCheckedRadioButtonId());
        RadioButton ans14 = view.findViewById(rbAnswer14.getCheckedRadioButtonId());
        RadioButton ans15 = view.findViewById(rbAnswer15.getCheckedRadioButtonId());
        RadioButton ans16 = view.findViewById(rbAnswer16.getCheckedRadioButtonId());
        RadioButton ans17 = view.findViewById(rbAnswer17.getCheckedRadioButtonId());

        //get the text provided by the user
        // STRINGS FOR THE CORRESPONDING EDIT TEXTS
        String ans18 = etAnswer18.getText().toString();
        String ans19 = etAnswer19.getText().toString();

        //store the answer in a map of the corresponding questions

//        try {
//            feedbacksAnswers.put(fqfs.QUESTIONS_LIST.get(0), ans1.getText().toString());
//            feedbacksAnswers.put(fqfs.QUESTIONS_LIST.get(2), ans3.getText().toString());
//            feedbacksAnswers.put(fqfs.QUESTIONS_LIST.get(3), ans4.getText().toString());
//            feedbacksAnswers.put(fqfs.QUESTIONS_LIST.get(4), ans5.getText().toString());
//            feedbacksAnswers.put(fqfs.QUESTIONS_LIST.get(5), ans6.getText().toString());
//            feedbacksAnswers.put(fqfs.QUESTIONS_LIST.get(6), ans6.getText().toString());
//            feedbacksAnswers.put(fqfs.QUESTIONS_LIST.get(7), ans7.getText().toString());
//            feedbacksAnswers.put(fqfs.QUESTIONS_LIST.get(8), ans8.getText().toString());
//            feedbacksAnswers.put(fqfs.QUESTIONS_LIST.get(9), ans9.getText().toString());
//            feedbacksAnswers.put(fqfs.QUESTIONS_LIST.get(10), ans10.getText().toString());
//            feedbacksAnswers.put(fqfs.QUESTIONS_LIST.get(11), ans11.getText().toString());
//            feedbacksAnswers.put(fqfs.QUESTIONS_LIST.get(12), ans12.getText().toString());
//            feedbacksAnswers.put(fqfs.QUESTIONS_LIST.get(13), ans13.getText().toString());
//            feedbacksAnswers.put(fqfs.QUESTIONS_LIST.get(14), ans14.getText().toString());
//            feedbacksAnswers.put(fqfs.QUESTIONS_LIST.get(15), ans15.getText().toString());
//            feedbacksAnswers.put(fqfs.QUESTIONS_LIST.get(16), ans16.getText().toString());
//            feedbacksAnswers.put(fqfs.QUESTIONS_LIST.get(17), ans17.getText().toString());
//            feedbacksAnswers.put(fqfs.QUESTIONS_LIST.get(18), ans18);
//            feedbacksAnswers.put(fqfs.QUESTIONS_LIST.get(19), ans19);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

        feedbacksAnswers.add(ans1.getText().toString());
//        feedbacksAnswers.add(ans2.getText().toString());
        feedbacksAnswers.add(ans3.getText().toString());
        feedbacksAnswers.add(ans4.getText().toString());
        feedbacksAnswers.add(ans5.getText().toString());
        feedbacksAnswers.add(ans6.getText().toString());
        feedbacksAnswers.add(ans7.getText().toString());
        feedbacksAnswers.add(ans8.getText().toString());
        feedbacksAnswers.add(ans9.getText().toString());
        feedbacksAnswers.add(ans10.getText().toString());
        feedbacksAnswers.add(ans11.getText().toString());
        feedbacksAnswers.add(ans12.getText().toString());
        feedbacksAnswers.add(ans13.getText().toString());
        feedbacksAnswers.add(ans14.getText().toString());
        feedbacksAnswers.add(ans15.getText().toString());
        feedbacksAnswers.add(ans16.getText().toString());
        feedbacksAnswers.add(ans17.getText().toString());
        feedbacksAnswers.add(ans18);
        feedbacksAnswers.add(ans19);

        studentFeedbackItem.setAnswers(feedbacksAnswers);


    }

}
