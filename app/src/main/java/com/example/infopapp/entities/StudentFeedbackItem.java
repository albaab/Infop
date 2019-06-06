package com.example.infopapp.entities;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.room.Ignore;

public class StudentFeedbackItem {

//========================================ATTRIBUTES================================================

    private String answer1,answer2,answer3,answer4,answer5,answer6,answer7,answer8,answer9,answer10,
            answer11,answer12,answer13,answer14,answer15,answer16,answer17,answer18;


    public StudentFeedbackItem(){
    }
//-----------------------------------UTILITY METHOD-------------------------------------------------
    @Ignore
    public void setAnswers(List<String> answers){
        answer1 = answers.get(0);
        answer2 = answers.get(1);
        answer3 = answers.get(2);
        answer4 = answers.get(3);
        answer5 = answers.get(4);
        answer6 = answers.get(5);
        answer7 = answers.get(6);
        answer8 = answers.get(7);
        answer9 = answers.get(8);
        answer10 = answers.get(9);
        answer11 = answers.get(10);
        answer12 = answers.get(11);
        answer13 = answers.get(12);
        answer14 = answers.get(13);
        answer15 = answers.get(14);
        answer16 = answers.get(15);
        answer17 = answers.get(16);
        answer18 = answers.get(17);
    }

}
