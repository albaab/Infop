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
import com.example.infopapp.entities.FeedbackItem;
import com.example.infopapp.entities.Session;
import com.example.infopapp.utils.FeedbackQuestionsForStudents;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import static androidx.constraintlayout.widget.Constraints.TAG;
import static com.example.infopapp.utils.Constants.SESSION;

public class FeedbackDialogFragment extends DialogFragment {

    //===========================================ATTRIBUTES========================================
    //FEEDBACK ITEM
    private FeedbackItem userFeedback;
    //RADIO GROUPS
    private RadioGroup rbAnswer1, rbAnswer2, rbAnswer3, rbAnswer4, rbAnswer5, rbAnswer6, rbAnswer7,
            rbAnswer8, rbAnswer9, rbAnswer10, rbAnswer11, rbAnswer12,
            rbAnswer13, rbAnswer14, rbAnswer15, rbAnswer16, rbAnswer17;

    //RADIO BUTTONS FOR THE CORRESPONDING RADIO GROUPS
    private RadioButton ans1, ans2, ans3, ans4, ans5, ans6, ans7, ans8, ans9, ans10, ans11, ans12,
            ans13, ans14, ans15, ans16, ans17;
    //EDIT TEXTS
    private EditText etAnswer18, etAnswer19;

    // STRINGS FOR THE CORRESPONDING EDIT TEXTS
    private String ans18, ans19;

    //RETRIEVE THE FEEDBACK QUESTIONS FROM A CLASS OF CONSTANT STRINGS
    private FeedbackQuestionsForStudents fqfs;

//===========================================ON CREATE DIALOG=======================================

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder feedbackDialogBuilder = new AlertDialog.Builder(getContext());
        fqfs = new FeedbackQuestionsForStudents(Objects.requireNonNull(getContext()));
        userFeedback = new FeedbackItem();

        LayoutInflater inflater = Objects.requireNonNull(getActivity()).getLayoutInflater();
        @SuppressLint("InflateParams") final View view = inflater.inflate(R.layout.fragment_feedback_dialog, null);

        assert getArguments() != null;
        final Session selectedSession = getArguments().getParcelable(SESSION);

        //retrieve the view items from the dialog fragment
        retrieveViewItems(view);


        feedbackDialogBuilder.setView(view)
                .setPositiveButton("Send Feedback", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //save the answers provided by the user
                        saveStudentAnswers(view);

                        Map<String, FeedbackItem> sessionFeedback = new HashMap<>();

                        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                            String userEmail = Objects.requireNonNull(FirebaseAuth
                                    .getInstance()
                                    .getCurrentUser()).getEmail();
                            if (userEmail != null && selectedSession != null) {
                                //store the feedback and its identifiers (user email)
                                sessionFeedback.put(userEmail, userFeedback);
                                selectedSession.setSessionFeedbacks(sessionFeedback);

                                //todo update the session in the database

                                Log.d(TAG, "onClick: DEBUG-> FEEDBACK SENT");
                                Toast.makeText(getContext(), getText(R.string.feedback_sent), Toast.LENGTH_SHORT).show();
                            }
                        }else{
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
        ans1 = view.findViewById(rbAnswer1.getCheckedRadioButtonId());
//        ans2 = view.findViewById(rbAnswer2.getCheckedRadioButtonId());
        ans3 = view.findViewById(rbAnswer3.getCheckedRadioButtonId());
        ans4 = view.findViewById(rbAnswer4.getCheckedRadioButtonId());
        ans5 = view.findViewById(rbAnswer5.getCheckedRadioButtonId());
        ans6 = view.findViewById(rbAnswer6.getCheckedRadioButtonId());
        ans7 = view.findViewById(rbAnswer7.getCheckedRadioButtonId());
        ans8 = view.findViewById(rbAnswer8.getCheckedRadioButtonId());
        ans9 = view.findViewById(rbAnswer9.getCheckedRadioButtonId());
        ans10 = view.findViewById(rbAnswer10.getCheckedRadioButtonId());
        ans11 = view.findViewById(rbAnswer11.getCheckedRadioButtonId());
        ans12 = view.findViewById(rbAnswer12.getCheckedRadioButtonId());
        ans13 = view.findViewById(rbAnswer13.getCheckedRadioButtonId());
        ans14 = view.findViewById(rbAnswer14.getCheckedRadioButtonId());
        ans15 = view.findViewById(rbAnswer15.getCheckedRadioButtonId());
        ans16 = view.findViewById(rbAnswer16.getCheckedRadioButtonId());
        ans17 = view.findViewById(rbAnswer17.getCheckedRadioButtonId());

        //get the text provided by the user
        ans18 = etAnswer18.getText().toString();
        ans19 = etAnswer19.getText().toString();

        //store the answer in a map of the corresponding questions
        Map<String, Object> feedbackMap = new HashMap<>();

        feedbackMap.put(fqfs.QUESTIONS_LIST.get(0), ans1);
        feedbackMap.put(fqfs.QUESTIONS_LIST.get(1), ans3);
        feedbackMap.put(fqfs.QUESTIONS_LIST.get(2), ans4);
        feedbackMap.put(fqfs.QUESTIONS_LIST.get(3), ans5);
        feedbackMap.put(fqfs.QUESTIONS_LIST.get(4), ans6);
        feedbackMap.put(fqfs.QUESTIONS_LIST.get(5), ans7);
        feedbackMap.put(fqfs.QUESTIONS_LIST.get(6), ans8);
        feedbackMap.put(fqfs.QUESTIONS_LIST.get(7), ans9);
        feedbackMap.put(fqfs.QUESTIONS_LIST.get(8), ans10);
        feedbackMap.put(fqfs.QUESTIONS_LIST.get(9), ans11);
        feedbackMap.put(fqfs.QUESTIONS_LIST.get(10), ans12);
        feedbackMap.put(fqfs.QUESTIONS_LIST.get(11), ans13);
        feedbackMap.put(fqfs.QUESTIONS_LIST.get(12), ans14);
        feedbackMap.put(fqfs.QUESTIONS_LIST.get(13), ans15);
        feedbackMap.put(fqfs.QUESTIONS_LIST.get(14), ans16);
        feedbackMap.put(fqfs.QUESTIONS_LIST.get(15), ans17);
        feedbackMap.put(fqfs.QUESTIONS_LIST.get(16), ans18);
        feedbackMap.put(fqfs.QUESTIONS_LIST.get(17), ans19);

        //set the map of question and answers in a feedback Item
        userFeedback.setStudentFeedbacks(feedbackMap);
    }

}
