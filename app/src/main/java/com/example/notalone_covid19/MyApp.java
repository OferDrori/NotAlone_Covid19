package com.example.notalone_covid19;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import java.util.List;

public class MyApp extends Application {

    private static String myUid = "";
    private static Context context;
    private static List<RiskGroupPerson> peopleHelp;


    @Override
    public void onCreate(){
        super.onCreate();
        context = this;
    }

    public static List<RiskGroupPerson> getPeopleHelp() {
        return peopleHelp;
    }

    public static void setPeopleHelp(List<RiskGroupPerson> peopleHelp) {
        MyApp.peopleHelp = peopleHelp;
    }

    public static String getMyUid() {
        return myUid;
    }

    public static void setMyUid(String myUid) {
        Log.e("UID","Got UID: " + myUid);
        MyApp.myUid = myUid;
    }

    public static Context getContext() {
        return context;
    }
}
