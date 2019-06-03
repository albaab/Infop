package com.example.infopapp.entities;

import java.util.HashMap;
import java.util.Map;

public class FeedbackItem {

    //map of questions and answers, whose answers are either "true" or "false"
    private Map<String,Object> studentFeedbacks = new HashMap<>();

    public Map<String, Object> getStudentFeedbacks() {
        return studentFeedbacks;
    }

    public void setStudentFeedbacks(Map<String, Object> studentFeedbacks) {
        this.studentFeedbacks = studentFeedbacks;
    }
}
