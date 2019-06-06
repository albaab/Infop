package com.example.infopapp.utils;

import android.os.AsyncTask;

import com.example.infopapp.entities.Session;

import java.util.ArrayList;
import java.util.List;

import static com.example.infopapp.utils.Constants.HARD_SKILLS;
import static com.example.infopapp.utils.Constants.MOBILE_DEV;
import static com.example.infopapp.utils.Constants.WEB_DEV;

public class MobileSessionsExample {
    private final List<Session> sessionList = new ArrayList<>();

    public MobileSessionsExample() {
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
            //Android Part 1
            session1.setSessionTitle("Android Part 1");
            session1.setSessionModule(HARD_SKILLS);
            session1.setSessionType(MOBILE_DEV);
            session1.setSessionDescription("● Introduction to Android\n" +
                    "● Android Development Environment Setup\n" +
                    "● Making an Android Application\n" +
                    "● Android Widgets\n" +
                    "● Web View");
            session1.setSessionDay(1);
            session1.setSessionMonth("May");
            session1.setSessionResources("www.edacy.con");
            session1.setIsFeedbackComplete(false);


            //
            //Android Part 2
            session2.setSessionTitle("Android Part 2");
            session2.setSessionModule(HARD_SKILLS);
            session2.setSessionType(MOBILE_DEV);
            session2.setSessionDescription("● Activity and Intents\n"+
                    "● Android Fragments\n" +
                    "● Android Menu\n" +
                    "● Android Services\n" +
                    "● Android User Interface");
            session2.setSessionDay(8);
            session2.setSessionMonth("May");
            session2.setSessionResources("www.edacy.con");
            session2.setIsFeedbackComplete(false);

            //Android Part 3
            session3.setSessionTitle("Android Part 3");
            session3.setSessionModule(HARD_SKILLS);
            session3.setSessionType(MOBILE_DEV);
            session3.setSessionDescription("● UI Layouts\n" +
                    "● Styles & Themes\n" +
                    "● Drag & Drop\n" +
                    "● JSON Parser");
            session3.setSessionDay(15);
            session3.setSessionMonth("May");
            session3.setSessionResources("www.edacy.con");
            session3.setIsFeedbackComplete(false);

            //Android Part 4
            session4.setSessionTitle("Android Part 4");
            session4.setSessionModule(HARD_SKILLS);
            session4.setSessionType(MOBILE_DEV);
            session4.setSessionDescription("● Shared Preferences\n" +
                    "● Android Alarm Manager\n" +
                    "● Android SQLite\n" +
                    "● SQLite Spinner\n" +
                    "● XML and JSON");
            session4.setSessionDay(22);
            session4.setSessionMonth("May");
            session4.setSessionResources("www.edacy.con");
            session4.setIsFeedbackComplete(false);

            //Android Part 5
            session5.setSessionTitle("Android Part 5");
            session5.setSessionModule(HARD_SKILLS);
            session5.setSessionType(MOBILE_DEV);
            session5.setSessionDescription("● Android Multimedia\n" +
                    "● Text to Speech\n" +
                    "● Broadcasting\n" +
                    "● Telephony Manager\n" +
                    "● Get Call State\n" +
                    "● Simple Caller Talker\n" +
                    "● Phone Call\n" +
                    "● Send SMS\n" +
                    "● Send Email");
            session5.setSessionDay(29);
            session5.setSessionMonth("May");
            session5.setSessionResources("www.edacy.con");
            session5.setIsFeedbackComplete(false);

            //Android Part 6
            session6.setSessionTitle("Android Part 6");
            session6.setSessionModule(HARD_SKILLS);
            session6.setSessionType(MOBILE_DEV);
            session6.setSessionDescription("● Permission\n" +
                    "● Bluetooth Tutorial\n" +
                    "● Camera Tutorial\n" +
                    "● Sensor Tutorial\n" +
                    "● Android Animation\n" +
                    "● Android Web Service\n" +
                    "● Google Map\n" +
                    "● Final project");
            session6.setSessionDay(5);
            session6.setSessionMonth("June");
            session6.setSessionResources("www.edacy.con");
            session6.setIsFeedbackComplete(false);

            //Angular 4: Part 1
            session7.setSessionTitle("Angular 4: Part 1");
            session7.setSessionModule(HARD_SKILLS);
            session7.setSessionType(MOBILE_DEV);
            session7.setSessionDescription("● Architecture\n" +
                    "● Template and DataBinding\n" +
                    "● Forms\n" +
                    "● ngModules\n" +
                    "● Dependency Injection\n" +
                    "● Give exercise to the talents\n" +
                    "● Add the project on Github\n" +
                    "● The instructor will correct the\n" +
                    "project before the next makerday");
            session7.setSessionDay(12);
            session7.setSessionMonth("June");
            session7.setSessionResources("www.edacy.con");
            session7.setIsFeedbackComplete(false);

            //Angular 4: Part 2
            session8.setSessionTitle("Angular 4: Part 2");
            session8.setSessionModule(HARD_SKILLS);
            session8.setSessionType(MOBILE_DEV);
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

            //Android with Firebase
            session9.setSessionTitle("Android with Firebase");
            session9.setSessionModule(HARD_SKILLS);
            session9.setSessionType(MOBILE_DEV);
            session9.setSessionDescription("● Integrate Firebase in the APP\n" +
                    "● Build an app with Authentication and a CRUD and connect in Firebase"
            );
            session9.setSessionDay(26);
            session9.setSessionMonth("June");
            session9.setSessionResources("www.edacy.con");
            session9.setIsFeedbackComplete(false);

            //Android in Offline
            session10.setSessionTitle("Android in Offline");
            session10.setSessionModule(HARD_SKILLS);
            session10.setSessionType(MOBILE_DEV);
            session10.setSessionDescription(" Strategies and algorithms to " +
                    "ensure an app that works offline and synchronize data when online"
            );
            session10.setSessionDay(2);
            session10.setSessionMonth("July");
            session10.setSessionResources("www.edacy.con");
            session10.setIsFeedbackComplete(false);

            //Background tasks
            session11.setSessionTitle("Microservices architectures");
            session11.setSessionModule(HARD_SKILLS);
            session11.setSessionType(MOBILE_DEV);
            session11.setSessionDescription("● What is it?\n" +
                    "● Why background processing?\n" +
                    "● Thread & Handler\n" +
                    "● AsyncTask\n" +
                    "● Loader & Cursor Loader\n" +
                    "● RxAndroid"
            );
            session11.setSessionDay(9);
            session11.setSessionMonth("July");
            session11.setSessionResources("www.edacy.con");
            session11.setIsFeedbackComplete(false);

            //Introduction to IONIC 3
            session12.setSessionTitle("Introduction to IONIC 3");
            session12.setSessionModule(HARD_SKILLS);
            session12.setSessionType(MOBILE_DEV);
            session12.setSessionDescription("● Introduction & Set-up\n" +
                    "● Getting Started\n" +
                    "● Ionic Components\n" +
                    "● Navigation\n" +
                    "● Theming\n" +
                    "● Loaders, Modals and Popovers\n" +
                    "● Gestures Events\n" +
                    "● Storage\n" +
                    "● Ionic Native\n" +
                    "● Deployment"
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
