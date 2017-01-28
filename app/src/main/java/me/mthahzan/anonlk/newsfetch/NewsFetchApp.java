package me.mthahzan.anonlk.newsfetch;

import android.app.Application;

import com.androidnetworking.AndroidNetworking;

/**
 * Created by mthahzan on 1/28/17.
 * The base application class to initialize required libraries
 */
public class NewsFetchApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize Fast-Android-Networking
        AndroidNetworking.initialize(this.getApplicationContext());
    }
}
