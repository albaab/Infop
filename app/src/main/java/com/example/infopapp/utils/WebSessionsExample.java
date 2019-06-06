package com.example.infopapp.utils;

import android.content.Context;
import android.os.AsyncTask;

import com.example.infopapp.entities.Session;

import java.util.ArrayList;
import java.util.List;

import static com.example.infopapp.utils.Constants.HARD_SKILLS;
import static com.example.infopapp.utils.Constants.WEB_DEV;

public class WebSessionsExample {
    public final List<Session> sessionList = new ArrayList<>();

    public WebSessionsExample() {
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
            //DATABASE
            session1.setSessionTitle("Database");
            session1.setSessionModule(HARD_SKILLS);
            session1.setSessionType(WEB_DEV);
            session1.setSessionDescription("● Introduction to Database concepts\n" +
                    "● Installing MySQL Workbench\n" +
                    "● Introduction to Relational Database\n" +
                    "● Creating schemas, models, tables\n" +
                    "● Querying using SQL Inserting, Updating & Deleting using SQL\n" +
                    "● Add the project on Github\n" +
                    "● The instructor will correct the project before the next makerday");
            session1.setSessionDay(1);
            session1.setSessionMonth("May");
            session1.setSessionResources("www.edacy.con");
            session1.setIsFeedbackComplete(false);


            //
            //BootStrap/HTML/CSS:
            session2.setSessionTitle("BootStrap/HTML/CSS");
            session2.setSessionModule(HARD_SKILLS);
            session2.setSessionType(WEB_DEV);
            session2.setSessionDescription("Build a CRUD\n" +
                    "Page of Movie Trailer Website\n" +
                    "● Talents work in groups of 2\n" +
                    "● Talents will receive PDF file of a"
                    + "wireframe\n" +
                    "● The talents have to implement the webpage with HTML and CSS\n" +
                    "● Add the project on Github\n" +
                    "● The instructor will correct the project before the next makerday");
            session2.setSessionDay(8);
            session2.setSessionMonth("May");
            session2.setSessionResources("www.edacy.con");
            session2.setIsFeedbackComplete(false);

            //Python/Django:
            session3.setSessionTitle("Python/Django");
            session3.setSessionModule(HARD_SKILLS);
            session3.setSessionType(WEB_DEV);
            session3.setSessionDescription("Movie Trailer Website\n" +
                    "● Talents work in groups of 2\n" +
                    "● Develop server side to store entities\n" +
                    "● Use Django to implement the CRUD page\n" +
                    "● Make the method as APIs\n" +
                    "● Add the project on Github\n" +
                    "● The instructor will correct the project before the next makerday");
           session3.setSessionDay(15);
           session3.setSessionMonth("May");
           session3.setSessionResources("www.edacy.con");
           session3.setIsFeedbackComplete(false);

            //NodeJS
            session4.setSessionTitle("NodeJS");
            session4.setSessionModule(HARD_SKILLS);
            session4.setSessionType(WEB_DEV);
            session4.setSessionDescription("● What is Nodejs\n" +
                    "● Create first CRUD APIs\n" +
                    "● How Node's evented I/O works\n" +
                    "● Event Loops");
            session4.setSessionDay(22);
            session4.setSessionMonth("May");
            session4.setSessionResources("www.edacy.con");
            session4.setIsFeedbackComplete(false);

            //NodeJS Part 2
            session5.setSessionTitle("NodeJS Part 2");
            session5.setSessionModule(HARD_SKILLS);
            session5.setSessionType(WEB_DEV);
            session5.setSessionDescription("● Essential Node's design patterns\n" +
                    "● NPM, what is it & how to use it\n" +
                    "● Give exercise to the talents\n" +
                    "● Add the project on Github\n" +
                    "● The instructor will correct the project before the next makerday");
            session5.setSessionDay(29);
            session5.setSessionMonth("May");
            session5.setSessionResources("www.edacy.con");
            session5.setIsFeedbackComplete(false);

            //ExpressJS
            session6.setSessionTitle("ExpressJS");
            session6.setSessionModule(HARD_SKILLS);
            session6.setSessionType(WEB_DEV);
            session6.setSessionDescription("● What is Expressjs?\n" +
                    "● Hands on Sample app\n" +
                    "● Structuring an Express.js application\n" +
                    "● Different serverside templating languages\n" +
                    "● Understanding middlewares\n" +
                    "● Give exercise to the talents\n" +
                    "● Add the project on Github\n" +
                    "● The instructor will correct the project before the next makerday");
            session6.setSessionDay(5);
            session6.setSessionMonth("June");
            session6.setSessionResources("www.edacy.con");
            session6.setIsFeedbackComplete(false);

            //Angular 4: Part 1
            session7.setSessionTitle("Angular 4: Part 1");
            session7.setSessionModule(HARD_SKILLS);
            session7.setSessionType(WEB_DEV);
            session7.setSessionDescription("● Architecture\n" +
                    "● Template and DataBinding\n" +
                    "● Forms\n" +
                    "● ngModules\n" +
                    "● Dependency Injection\n" +
                    "● Give exercise to the talents\n" +
                    "● Add the project on Github\n" +
                    "● The instructor will correct the project before the next makerday");
            session7.setSessionDay(12);
            session7.setSessionMonth("June");
            session7.setSessionResources("www.edacy.con");
            session7.setIsFeedbackComplete(false);

            //Angular 4: Part 2
            session8.setSessionTitle("Angular 4: Part 2");
            session8.setSessionModule(HARD_SKILLS);
            session8.setSessionType(WEB_DEV);
            session8.setSessionDescription("● HttpClient\n" +
                    "● Routing and navigation\n" +
                    "● Testing\n" +
                    "● Internationalization\n" +
                    "● Security\n" +
                    "● Give exercise to the talents\n" +
                    "● Add the project on Github\n" +
                    "● The instructor will correct the project before the next makerday"
            );
            session8.setSessionDay(19);
            session8.setSessionMonth("June");
            session8.setSessionResources("www.edacy.con");
            session8.setIsFeedbackComplete(false);

            //MEAN Application
            session9.setSessionTitle("MEAN Application");
            session9.setSessionModule(HARD_SKILLS);
            session9.setSessionType(WEB_DEV);
            session9.setSessionDescription("Movie Trailer Website\n" +
                    "● Talents work in groups of 2\n" +
                    "● Develop server side to store entities in MongoDB\n" +
                    "● Use Angular to implement the CRUD page (re-use the HTML built before)\n" +
                    "● Add the project on Github\n" +
                    "● The instructor will correct the project before the next makerday"
            );
            session9.setSessionDay(26);
            session9.setSessionMonth("June");
            session9.setSessionResources("www.edacy.con");
            session9.setIsFeedbackComplete(false);

            //Nginx / Passenger
            session10.setSessionTitle("Nginx / Passenger");
            session10.setSessionModule(HARD_SKILLS);
            session10.setSessionType(WEB_DEV);
            session10.setSessionDescription("● What is web server\n" +
                    "● How does Nginx work\n" +
                    "● Introduction to Passenger\n" +
                    "● Deploying a sample App in Nginx/Passenger"
            );
            session10.setSessionDay(2);
            session10.setSessionMonth("July");
            session10.setSessionResources("www.edacy.con");
            session10.setIsFeedbackComplete(false);

            //Microservices architectures
            session11.setSessionTitle("Microservices architectures");
            session11.setSessionModule(HARD_SKILLS);
            session11.setSessionType(WEB_DEV);
            session11.setSessionDescription("● What is Microservice?\n" +
                    "● Benefits of Microservices architecture\n" +
                    "● Drawbacks of Microservices\n" +
                    "● Microservices vs. monolithic architecture when application grows\n" +
                    "● Why Microservices with Node.JS?"
            );
            session11.setSessionDay(9);
            session11.setSessionMonth("July");
            session11.setSessionResources("www.edacy.con");
            session11.setIsFeedbackComplete(false);

            //Continuous integration
            session12.setSessionTitle("Continuous integration");
            session12.setSessionModule(HARD_SKILLS);
            session12.setSessionType(WEB_DEV);
            session12.setSessionDescription("● What is continuous integration?\n" +
                    "● Sample of use jenkins\n" +
                    "● Build and deploy with jenkins\n" +
                    "● Execute unit tests with jenkins\n" +
                    "● Quality report"
            );
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
