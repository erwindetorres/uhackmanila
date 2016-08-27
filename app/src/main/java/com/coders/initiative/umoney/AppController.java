package com.coders.initiative.umoney;

import android.app.Application;

import okhttp3.OkHttpClient;

/**
 * Created by Kira on 8/27/2016.
 */
public class AppController extends Application {

    public static final String TAG = AppController.class.getSimpleName();
    private static AppController appControllerInstance;
    private static OkHttpClient myHttpClient;

    public static synchronized AppController getInstance() {
        return appControllerInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appControllerInstance = this;
        myHttpClient = new OkHttpClient();
    }

    public OkHttpClient getMyHttpClient(){
        return  myHttpClient;
    }


}
