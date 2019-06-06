package com.example.infopapp.utils;

import android.os.AsyncTask;

import com.example.infopapp.entities.Session;

import java.util.ArrayList;
import java.util.List;

import static com.example.infopapp.utils.Constants.DATA_SCIENCE;
import static com.example.infopapp.utils.Constants.HARD_SKILLS;
import static com.example.infopapp.utils.Constants.WEB_DEV;

public class DataScienceSessionsExample {
    public final List<Session> sessionList = new ArrayList<>();

    public DataScienceSessionsExample() {
        new PopulateAsyncTask(sessionList).execute();
    }

    public List<Session> getSessionList() {
        return sessionList;
    }

    private static class PopulateAsyncTask extends AsyncTask<Void, Void, Void> {

        List<Session> list = new ArrayList<>();

        PopulateAsyncTask(List<Session> list) {
            this.list = list;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Session session1 = new Session();
            Session session2 = new Session();
            Session session3 = new Session();
            Session session4 = new Session();
            Session session5 = new Session();
            Session session6 = new Session();
            Session session7 = new Session();
            Session session8 = new Session();
            Session session9 = new Session();
            Session session10 = new Session();
            Session session11 = new Session();
            Session session12 = new Session();

            //Data Analysis with Statistics
            session1.setSessionTitle("Data Analysis with Statistics");
            session1.setSessionModule(HARD_SKILLS);
            session1.setSessionType(DATA_SCIENCE);
            session1.setSessionDescription("● Descriptive statistics\n" +
                    "● Basic probability\n" +
                    "● Random variables\n" +
                    "● Sampling and confidence intervals\n" +
                    "● Hypothesis testing");
            session1.setSessionDay(1);
            session1.setSessionMonth("May");
            session1.setSessionResources("www.edacy.con");
            session1.setIsFeedbackComplete(false);


            //
            //Data Communication with Information
            //Visualization:
            session2.setSessionTitle("Data Communication with Information Visualization");
            session2.setSessionModule(HARD_SKILLS);
            session2.setSessionType(DATA_SCIENCE);
            session2.setSessionDescription("● Why use data to tell a story?\n" +
                    "● Identify your objective(s) &\n" +
                    "audience");
            session2.setSessionDay(8);
            session2.setSessionMonth("May");
            session2.setSessionResources("www.edacy.con");
            session2.setIsFeedbackComplete(false);

            //Data Communication with Information
            //Visualization
            session3.setSessionTitle("Data Communication with Information Visualization");
            session3.setSessionModule(HARD_SKILLS);
            session3.setSessionType(DATA_SCIENCE);
            session3.setSessionDescription("● Critically evaluate data visualizations\n" +
                    "● Common data types (discrete v. continuous, numeric vs. " +
                    "categorical, spatial vs. non-spatial)");
           session3.setSessionDay(15);
           session3.setSessionMonth("May");
           session3.setSessionResources("www.edacy.con");
           session3.setIsFeedbackComplete(false);

            //Introduction to R programming
            session4.setSessionTitle("Introduction to R programming");
            session4.setSessionModule(HARD_SKILLS);
            session4.setSessionType(DATA_SCIENCE);
            session4.setSessionDescription("● Introduction\n" +
                    "● Getting your data into R\n" +
                    "● Easy ways to do basic data analysis\n" +
                    "● Painless data visualization\n" +
                    "● Syntax quirks you'll want to know\n" +
                    "● Useful resources");
            session4.setSessionDay(22);
            session4.setSessionMonth("May");
            session4.setSessionResources("www.edacy.con");
            session4.setIsFeedbackComplete(false);

            //Apache Hadoop
            session5.setSessionTitle("Apache Hadoop");
            session5.setSessionModule(HARD_SKILLS);
            session5.setSessionType(DATA_SCIENCE);
            session5.setSessionDescription("● What is Hadoop?\n" +
                    "● Why Hadoop?\n" +
                    "● Hadoop Architecture\n" +
                    "● Key Components of Hadoop");
            session5.setSessionDay(29);
            session5.setSessionMonth("May");
            session5.setSessionResources("www.edacy.con");
            session5.setIsFeedbackComplete(false);

            //Apache Hadoop
            session6.setSessionTitle("Apache Hadoop");
            session6.setSessionModule(HARD_SKILLS);
            session6.setSessionType(DATA_SCIENCE);
            session6.setSessionDescription("● Hadoop Daemons\n" +
                    "● How do Hadoop works?\n" +
                    "● Hadoop Flavors\n" +
                    "● Hadoop Ecosystem");
            session6.setSessionDay(5);
            session6.setSessionMonth("June");
            session6.setSessionResources("www.edacy.con");
            session6.setIsFeedbackComplete(false);

            //Data Analysis with Statistics
            session7.setSessionTitle("Data Analysis with Statistics");
            session7.setSessionModule(HARD_SKILLS);
            session7.setSessionType(DATA_SCIENCE);
            session7.setSessionDescription("● Descriptive statistics\n" +
                    "● Basic probability\n" +
                    "● Random variables\n" +
                    "● Sampling and confidence intervals\n" +
                    "● Hypothesis testing");
            session7.setSessionDay(12);
            session7.setSessionMonth("June");
            session7.setSessionResources("www.edacy.con");
            session7.setIsFeedbackComplete(false);

            //In-depth Python
            session8.setSessionTitle("In-depth Python");
            session8.setSessionModule(HARD_SKILLS);
            session8.setSessionType(DATA_SCIENCE);
            session8.setSessionDescription("● Create and manipulate regular Python lists\n" +
                    "● Use functions and import packages\n" +
                    "● Build Numpy arrays and perform interesting calculations\n" +
                    "● Create and customize plots on real data"
            );
            session8.setSessionDay(19);
            session8.setSessionMonth("June");
            session8.setSessionResources("www.edacy.con");
            session8.setIsFeedbackComplete(false);

            //In-depth Python
            session9.setSessionTitle("In-depth Python");
            session9.setSessionModule(HARD_SKILLS);
            session9.setSessionType(DATA_SCIENCE);
            session9.setSessionDescription("● Supercharge your scripts with control " +
                    "flow and get to know the Pandas DataFrame\n" +
                    "● Introduction to Flask\n" +
                    "● Build a to-do app with Flask"
            );
            session9.setSessionDay(26);
            session9.setSessionMonth("June");
            session9.setSessionResources("www.edacy.con");
            session9.setIsFeedbackComplete(false);

            //Apache Spark Part 1
            session10.setSessionTitle("Apache Spark Part 1");
            session10.setSessionModule(HARD_SKILLS);
            session10.setSessionType(DATA_SCIENCE);

            session10.setSessionDay(2);
            session10.setSessionMonth("July");
            session10.setSessionResources("www.edacy.con");
            session10.setIsFeedbackComplete(false);

            //Apache Spark Part 2
            session11.setSessionTitle("Apache Spark Part 2");
            session11.setSessionModule(HARD_SKILLS);
            session11.setSessionType(DATA_SCIENCE);

            session11.setSessionDay(9);
            session11.setSessionMonth("July");
            session11.setSessionResources("www.edacy.con");
            session11.setIsFeedbackComplete(false);

            //Introduction to Deep Learning
            session12.setSessionTitle("Introduction to Deep Learning");
            session12.setSessionModule(HARD_SKILLS);
            session12.setSessionType(DATA_SCIENCE);

            session12.setSessionDay(16);
            session12.setSessionMonth("July");
            session12.setSessionResources("www.edacy.con");
            session12.setIsFeedbackComplete(false);

            list.add(session1);
            list.add(session2);
            list.add(session3);
            list.add(session4);
            list.add(session5);
            list.add(session6);
            list.add(session7);
            list.add(session8);
            list.add(session9);
            list.add(session10);
            list.add(session11);
            list.add(session12);

            return null;
        }
    }


}
