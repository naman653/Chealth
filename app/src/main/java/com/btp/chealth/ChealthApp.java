package com.btp.chealth;

import android.app.Application;
import android.content.Context;


public class ChealthApp extends Application {

    private static Context appContext;
    @Override
    public void onCreate() {
        super.onCreate();
        ChealthApp.appContext = getApplicationContext();
    }

    public static Context getAppContext() {
        return appContext;
    }
}
