package me.mthahzan.anonlk.newsfetch.main;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import me.mthahzan.anonlk.newsfetch.R;
import me.mthahzan.anonlk.newsfetch.lib.PreferenceManager;
import me.mthahzan.anonlk.newsfetch.lib.models.AppSession;
import me.mthahzan.anonlk.newsfetch.login.LoginActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        if (appSession == null) {
            Intent intent = new Intent(context, LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            // TODO: Add implementation here
            Toast.makeText(context, "Not implemented", Toast.LENGTH_SHORT).show();
        }
    }
}
