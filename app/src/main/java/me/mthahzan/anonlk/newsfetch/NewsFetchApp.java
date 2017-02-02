package me.mthahzan.anonlk.newsfetch;

import android.app.Application;

import com.androidnetworking.AndroidNetworking;

import io.realm.Realm;
import me.mthahzan.anonlk.newsfetch.lib.network.NetworkLogInterceptor;
import okhttp3.OkHttpClient;

/**
 * Created by mthahzan on 1/28/17.
 * The base application class to initialize required libraries
 */
public class NewsFetchApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Create okHttpClient with custom request interceptor
        OkHttpClient okHttpClient = new OkHttpClient()
                .newBuilder()
                .addNetworkInterceptor(new NetworkLogInterceptor())
                .build();

        // Initialize Fast-Android-Networking with custom client
        AndroidNetworking.initialize(this.getApplicationContext(), okHttpClient);
    }
}
