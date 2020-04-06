package com.example.notalone_covid19;

import android.app.Application;
import android.content.Context;

public class MyApp extends Application {

    private static String myUid = "";
    private static Context context;

    @Override
    public void onCreate(){
        super.onCreate();
        context = this;
    }

    public static String getMyUid() {
        return myUid;
    }

    public static void setMyUid(String myUid) {
        MyApp.myUid = myUid;
    }

    public static Context getContext() {
        return context;
    }
}
