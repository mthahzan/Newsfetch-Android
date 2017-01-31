package me.mthahzan.anonlk.newsfetch.splash;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import me.mthahzan.anonlk.newsfetch.R;
import me.mthahzan.anonlk.newsfetch.consumer.main.MainActivity;
import me.mthahzan.anonlk.newsfetch.lib.PreferenceManager;
import me.mthahzan.anonlk.newsfetch.lib.models.AppSession;
import me.mthahzan.anonlk.newsfetch.login.LoginActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Call the navigation
        navigateUser(this);
    }

    /**
     * Navigate user to the appropriate activity
     * @param context The Activity{@link Context}
     */
    private void navigateUser(Context context) {
        PreferenceManager preferenceManager = PreferenceManager
                .getInstance(context.getApplicationContext());
        AppSession appSession = preferenceManager.getSession();

        Intent intent =
                new Intent(context, appSession == null ? LoginActivity.class : MainActivity.class);
        startActivity(intent);
        finish();
    }
}
