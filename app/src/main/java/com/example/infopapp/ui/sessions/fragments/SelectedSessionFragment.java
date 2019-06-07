package com.example.infopapp.ui.sessions.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.infopapp.R;
import com.example.infopapp.entities.Session;
import com.example.infopapp.ui.sessions.SessionViewModel;

import java.util.Calendar;

import static android.view.View.GONE;
import static androidx.constraintlayout.widget.Constraints.TAG;
import static com.example.infopapp.ui.account.ConnectToAccountActivity.USERTYPE;
import static com.example.infopapp.utils.Constants.DATA_SCIENCE;
import static com.example.infopapp.utils.Constants.HOMEWORK;
import static com.example.infopapp.utils.Constants.INSTRUCTOR;
import static com.example.infopapp.utils.Constants.MOBILE_DEV;
import static com.example.infopapp.utils.Constants.RESOURCES;
import static com.example.infopapp.utils.Constants.SESSION;
import static com.example.infopapp.utils.Constants.STAFF;
import static com.example.infopapp.utils.Constants.STUDENT;
import static com.example.infopapp.utils.Constants.UPDATE_SESSION_REQUEST;
import static com.example.infopapp.utils.Constants.WEB_DEV;


public class SelectedSessionFragment extends Fragment {


//========================================ATTRIBUTES================================================

    private ImageView sessionImage;
    private TextView description;
    private TextView timeAndDate;
    private TextView instructor;
    private TextView dialog_instructor;
    private TextView dialog_description;
    private  TextView dialog_homework;
    private TextView dialog_resources;
    private TextView homework;
    private TextView resources;
    private TextView feedBacks;


    private LinearLayout layout_course_info, layout_homework, layout_resources, layout_feedBacks;

    private DialogFragment pickTimeFragment;
    private FeedbackDialogFragment feedbackDialogFragment;
    private AlertDialog.Builder textInputdialogBuilder;

    private SessionViewModel sessionViewModel;
    private Session thisSession;


    //========================================CONSTRUCTOR===============================================
    public SelectedSessionFragment() {
        // Required empty public constructor
    }


    //========================================ON CREATE VIEW METHOD=====================================
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_selected_session, container, false);
    }


    //========================================ON VIEW CREATED METHOD====================================
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pickTimeFragment = new DatePickerDialogFragment();
        feedbackDialogFragment = new FeedbackDialogFragment();
        textInputdialogBuilder = new AlertDialog.Builder(getActivity());
        sessionViewModel = ViewModelProviders.of(this).get(SessionViewModel.class);


        sessionImage = view.findViewById(R.id.session_view_image);
        TextView title = view.findViewById(R.id.session_view_title);
        description = view.findViewById(R.id.description_text_view);
        timeAndDate = view.findViewById(R.id.time_and_date_text_view);
        instructor = view.findViewById(R.id.session_instructor_text_view);
        homework = view.findViewById(R.id.homework_text_view);
        resources = view.findViewById(R.id.resources_text_view);
        feedBacks = view.findViewById(R.id.feedback_text_view);
        layout_course_info = view.findViewById(R.id.course_info_layout);
        layout_homework = view.findViewById(R.id.homework_layout);
        layout_resources = view.findViewById(R.id.resources_layout);
        layout_feedBacks = view.findViewById(R.id.feedback_layout);

        Bundle selectedSessionBundle = getArguments();
        assert selectedSessionBundle != null;
        thisSession = selectedSessionBundle.getParcelable(SESSION);
        assert thisSession != null;

        Calendar c = Calendar.getInstance();
        //adapt the ui from the info of the selected session
        setDrawableFromSessionType(thisSession.getSessionType());
        title.setText(thisSession.getSessionTitle());

        if (thisSession.getSessionDay() != 0 && thisSession.getSessionMonth() != null &
                thisSession.getSessionHour() != null) {
            String timeAndDateAndHour = thisSession.getSessionMonth() +
                    ", " +
                    thisSession.getSessionDay() +
                    " " +
                    c.get(Calendar.YEAR) +
                    " @ " +
                    thisSession.getSessionHour();
            timeAndDate.setText(timeAndDateAndHour);
        }


        if (thisSession.getSessionInstructor() != null) {
            instructor.setText(thisSession.getSessionInstructor());
        }
        if (thisSession.getSessionDescription() != null) {
            description.setText(thisSession.getSessionDescription());
        }
        if (thisSession.getSessionHomework() != null) {
            homework.setText(thisSession.getSessionHomework());
        }
        if (thisSession.getSessionResources() != null) {
            resources.setText(thisSession.getSessionResources());
        }

        showHideTextViews(thisSession);

    }


    //========================================ON ATTACH METHOD==========================================
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

    //========================================ON DETACH METHOD==========================================
    @Override
    public void onDetach() {
        super.onDetach();
//        mListener = null;
    }


    //========================================UTILITY METHODS=======================================

//-------------------------------set the time and date picked Method--------------------------------
    public void setTimeAndDate(String datePicked, int dayOfMonth, String month, String hour) {

        if (thisSession != null) {
            thisSession.setSessionDay(dayOfMonth);
            thisSession.setSessionMonth(month);
            thisSession.setSessionHour(hour);
            sessionViewModel.updateSessiontoFirebaseDb(thisSession, UPDATE_SESSION_REQUEST);
            String timeDateHour = datePicked + " @ " + hour;
            timeAndDate.setText(timeDateHour);
            Toast.makeText(getActivity(), getText(R.string.session_updated), Toast.LENGTH_SHORT).show();
        }


    }

    //-----------------------------------show and hide session menu method--------------------------
    private void showHideTextViews(final Session session) {

        //---------------------------SHOW HIDE COURSE INFORMATION-----------------------------------
        layout_course_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Drawable c = getResources().getDrawable(R.drawable.red_circle);
//                c.setBounds( 0, 0, 60, 60 );
//                courseTv.setCompoundDrawables(null,null,c,null);

                if (description.getVisibility() == GONE) {
                    description.setVisibility(View.VISIBLE);
                    if(USERTYPE.equals(STUDENT) || USERTYPE.equals(STAFF)){
                        instructor.setVisibility(View.VISIBLE);
                    }
                    allowUseroEdit();
                    timeAndDate.setVisibility(View.VISIBLE);
                } else if (description.getVisibility() == View.VISIBLE) {
                    description.setVisibility(GONE);
                    timeAndDate.setVisibility(GONE);
                    instructor.setVisibility(GONE);
                }

            }
        });
//---------------------------SHOW HIDE HOMEWORK INFORMATION-----------------------------------
        layout_homework.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (homework.getVisibility() == GONE) {
                    homework.setVisibility(View.VISIBLE);
                    allowUseroEdit();
                } else if (homework.getVisibility() == View.VISIBLE) {
                    homework.setVisibility(GONE);
                }
            }
        });
//---------------------------SHOW HIDE RESOURCE INFORMATION-----------------------------------
        layout_resources.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (resources.getVisibility() == GONE) {
                    resources.setVisibility(View.VISIBLE);
                    allowUseroEdit();
                } else if (resources.getVisibility() == View.VISIBLE) {
                    resources.setVisibility(GONE);
                }
            }
        });
//---------------------------SHOW HIDE FEEDBACK TEXT VIEW-----------------------------------------
        layout_feedBacks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (feedBacks.getVisibility() == GONE) {
                    feedBacks.setVisibility(View.VISIBLE);
                    if (USERTYPE.equals(STUDENT)) {
                        feedBacks.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                assert getFragmentManager() != null;
                                Bundle bundle = new Bundle();
                                bundle.putParcelable(SESSION, session);
                                feedbackDialogFragment.setArguments(bundle);
                                feedbackDialogFragment.show(getFragmentManager(), "Feedback Dialog");
                            }
                        });
                    } else if (USERTYPE.equals(STAFF)) {

                        if (!session.getIsFeedbackComplete()) {
                            feedBacks.setText(getText(R.string.feedback_imcomplete));
                            feedBacks.setTextColor(Color.RED);
                        } else {
                            feedBacks.setText(getText(R.string.feedback_complete));
                            feedBacks.setTextColor(Color.GREEN);
                        }
                    }

                } else if (feedBacks.getVisibility() == View.VISIBLE) {
                    feedBacks.setVisibility(GONE);
                }
            }
        });
    }

    //-----------------------------------allow Staff to Edit Method-------------------------------------
    private void allowUseroEdit() {


        //--------------------------------ALLOW STAFF TO EDIT---------------------------------------
        if (USERTYPE.equals(STAFF)) {
//            Drawable editDrawable = getResources().getDrawable(R.drawable.ic_edit);
//            editDrawable.setBounds(0,0,60,60);
//            timeAndDate.setCompoundDrawables(editDrawable,null,null,null);
            timeAndDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    assert getFragmentManager() != null;
                    pickTimeFragment.show(getFragmentManager(), "date picker dialog");
                    Log.d(TAG, "onClick: DATE PICKER DIALOG OPENED");
                }
            });

            instructor.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    prepareTextDialoFragment(INSTRUCTOR);
                    textInputdialogBuilder.show();
                }
            });

            description.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    prepareTextDialoFragment(SESSION);
                    textInputdialogBuilder.show();

                }
            });
        }
        //--------------------------------ALLOW INSTRUCTOR TO EDIT----------------------------------
        if (USERTYPE.equals(INSTRUCTOR)) {

            instructor.setVisibility(GONE);

            //----------------------------description Onclick-----------------------------------------
            description.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    prepareTextDialoFragment(SESSION);
                    textInputdialogBuilder.show();

                }
            });

            //----------------------------Homework Onclick-----------------------------------------
            homework.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    prepareTextDialoFragment(HOMEWORK);
                    textInputdialogBuilder.show();

                }
            });

            //----------------------------Resources Onclick-----------------------------------------
            resources.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    prepareTextDialoFragment(RESOURCES);
                    textInputdialogBuilder.show();

                }
            });

        }
    }

    //-----------------------Prepare the instructor input dialog fragmeng Method------------------------
    private void prepareTextDialoFragment(String requestType) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogInstructorView = inflater.inflate(R.layout.text_input_dialog, null);
        textInputdialogBuilder.setView(dialogInstructorView);
        dialog_instructor = dialogInstructorView.findViewById(R.id.dialog_instructor_text_view);
        dialog_description = dialogInstructorView.findViewById(R.id.dialog_course_description_text_view);
        dialog_homework = dialogInstructorView.findViewById(R.id.dialog_homework_text_view);
        dialog_resources = dialogInstructorView.findViewById(R.id.dialo_resources_text_view);

        if (INSTRUCTOR.equals(requestType)) {
            dialog_instructor.setVisibility(View.VISIBLE);
            dialog_description.setVisibility(View.GONE);
            dialog_homework.setVisibility(View.GONE);
            dialog_resources.setVisibility(View.GONE);
        } else if (SESSION.equals(requestType)) {
            dialog_instructor.setVisibility(View.GONE);
            dialog_description.setVisibility(View.VISIBLE);
            dialog_homework.setVisibility(View.GONE);
            dialog_resources.setVisibility(View.GONE);
        } else if (HOMEWORK.equals(requestType)) {
            dialog_instructor.setVisibility(View.GONE);
            dialog_description.setVisibility(View.GONE);
            dialog_homework.setVisibility(View.VISIBLE);
            dialog_resources.setVisibility(View.GONE);
        } else if (RESOURCES.equals(requestType)) {
            dialog_instructor.setVisibility(View.GONE);
            dialog_description.setVisibility(View.GONE);
            dialog_homework.setVisibility(View.GONE);
            dialog_homework.setVisibility(View.VISIBLE);
        }

        //set positive button of the dialog builder -> OK
        textInputdialogBuilder.setPositiveButton(getText(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //get the strings provided by the user
                String instructorString = dialog_instructor.getText().toString();
                String descriptionString = dialog_description.getText().toString();
                String homeworkString = dialog_homework.getText().toString();
                String resourcesString = dialog_resources.getText().toString();

                //store the string in the textviews of the fragment
                //store the strings in the current session object
                if(!descriptionString.trim().isEmpty()){
                    description.setText(descriptionString);
                    thisSession.setSessionDescription(descriptionString);

                }
                if(!instructorString.trim().isEmpty()){
                    instructor.setText(instructorString);
                    thisSession.setSessionInstructor(instructorString);

                }

                if(!homeworkString.trim().isEmpty()){
                    homework.setText(homeworkString);
                    thisSession.setSessionHomework(homeworkString);

                }

                if(!resourcesString.trim().isEmpty()){
                    resources.setText(resourcesString);
                    thisSession.setSessionResources(resourcesString);

                }


                //update the session with the viewModel
                sessionViewModel.updateSessiontoFirebaseDb(thisSession, UPDATE_SESSION_REQUEST);

                //toast message -> session updated
                Toast.makeText(getContext(), getText(R.string.session_updated), Toast.LENGTH_SHORT).show();
            }
        });
        textInputdialogBuilder.setNegativeButton(getText(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //do nothing
            }
        });
    }

    //----------------------------------Set The session drawable----------------------------------------
    private void setDrawableFromSessionType(String sessionType) {
        Drawable mobileDrawable = getResources().getDrawable(R.drawable.android_studio);
        Drawable webDrawable = getResources().getDrawable(R.drawable.web_programming);
        Drawable dataScienceDrawable = getResources().getDrawable(R.drawable.data_science);
        switch (sessionType) {
            case MOBILE_DEV:
                sessionImage.setImageDrawable(mobileDrawable);
                break;
            case WEB_DEV:
                sessionImage.setImageDrawable(webDrawable);
                break;
            case DATA_SCIENCE:
                sessionImage.setImageDrawable(dataScienceDrawable);
                break;
        }
    }

}
