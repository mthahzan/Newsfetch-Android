package me.mthahzan.anonlk.newsfetch;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import io.realm.Realm;

/**
 * Created by mthahzan on 1/28/17.
 * Base class to extend from
 */

public class BaseActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Init Realm instance
        Realm.init(this.getApplicationContext());
    }
}
