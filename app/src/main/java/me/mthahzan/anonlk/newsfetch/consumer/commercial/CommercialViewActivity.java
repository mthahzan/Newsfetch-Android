package me.mthahzan.anonlk.newsfetch.consumer.commercial;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import io.realm.Realm;
import me.mthahzan.anonlk.newsfetch.R;
import me.mthahzan.anonlk.newsfetch.lib.models.Commercial;

public class CommercialViewActivity extends AppCompatActivity {

    /**
     * Commercial ID
     */
    private int commercialId;

    /**
     * {@link Realm} instance
     */
    private Realm realm;

    // UI elements
    private TextView title;
    private TextView content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commercial_view);

        commercialId = getIntent().getIntExtra(Commercial.INTENT_TAG, -1);

        initializeViewElements();

        realm = Realm.getDefaultInstance();

        bindData();
    }

    /**
     * Initialize UI elements
     */
    private void initializeViewElements() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }

        title = (TextView) findViewById(R.id.title);
        content = (TextView) findViewById(R.id.content);
    }

    /**
     * Queries the {@link Commercial} object from local DB
     * @return The {@link Commercial} of ID
     */
    private Commercial queryCommercial() {
        return realm
                .where(Commercial.class)
                .equalTo("id", commercialId)
                .findFirst();
    }

    /**
     * Binds data to UI elements
     */
    private void bindData() {
        Commercial commercial = queryCommercial();

        if (commercial != null) {
            title.setText(commercial.getTitle());
            content.setText(commercial.getContent());
        }
    }

    /**
     * Overriding the custom back press action
     */
    @Override
    public void onBackPressed() {
        finish();
    }
}
