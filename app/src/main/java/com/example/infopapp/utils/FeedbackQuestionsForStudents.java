package com.example.infopapp.utils;

import android.content.Context;

import com.example.infopapp.R;

import java.util.ArrayList;
import java.util.List;

public class FeedbackQuestionsForStudents {

    public final List<String> QUESTIONS_LIST = new ArrayList<>();

    public FeedbackQuestionsForStudents(Context context){
        String QUESTION_1 = (String) context.getResources().getText(R.string.student_q_1);
        QUESTIONS_LIST.add(QUESTION_1);
//        String QUESTION_2 = (String) context.getResources().getText(R.string.student_q_2);
//        QUESTIONS_LIST.add(QUESTION_2);
        String QUESTION_3 = (String) context.getResources().getText(R.string.student_q_3);
        QUESTIONS_LIST.add(QUESTION_3);
        String QUESTION_4 = (String) context.getResources().getText(R.string.student_q_4);
        QUESTIONS_LIST.add(QUESTION_4);
        String QUESTION_5 = (String) context.getResources().getText(R.string.student_q_5);
        QUESTIONS_LIST.add(QUESTION_5);
        String QUESTION_6 = (String) context.getResources().getText(R.string.student_q_6);
        QUESTIONS_LIST.add(QUESTION_6);
        String QUESTION_7 = (String) context.getResources().getText(R.string.student_q_7);
        QUESTIONS_LIST.add(QUESTION_7);
        String QUESTION_8 = (String) context.getResources().getText(R.string.student_q_8);
        QUESTIONS_LIST.add(QUESTION_8);
        String QUESTION_9 = (String) context.getResources().getText(R.string.student_q_9);
        QUESTIONS_LIST.add(QUESTION_9);
        String QUESTION_10 = (String) context.getResources().getText(R.string.student_q_10);
        QUESTIONS_LIST.add(QUESTION_10);
        String QUESTION_11 = (String) context.getResources().getText(R.string.student_q_11);
        QUESTIONS_LIST.add(QUESTION_11);
        String QUESTION_12 = (String) context.getResources().getText(R.string.student_q_12);
        QUESTIONS_LIST.add(QUESTION_12);
        String QUESTION_13 = (String) context.getResources().getText(R.string.student_q_13);
        QUESTIONS_LIST.add(QUESTION_13);
        String QUESTION_14 = (String) context.getResources().getText(R.string.student_q_14);
        QUESTIONS_LIST.add(QUESTION_14);
        String QUESTION_15 = (String) context.getResources().getText(R.string.student_q_15);
        QUESTIONS_LIST.add(QUESTION_15);
        String QUESTION_16 = (String) context.getResources().getText(R.string.student_q_16);
        QUESTIONS_LIST.add(QUESTION_16);
        String QUESTION_17 = (String) context.getResources().getText(R.string.student_q_17);
        QUESTIONS_LIST.add(QUESTION_17);
        String QUESTION_18 = (String) context.getResources().getText(R.string.student_q_18);
        QUESTIONS_LIST.add(QUESTION_18);
        String QUESTION_19 = (String) context.getResources().getText(R.string.student_q_19);
        QUESTIONS_LIST.add(QUESTION_19);
    }

}
