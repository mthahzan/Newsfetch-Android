package me.mthahzan.anonlk.newsfetch.consumer.post;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import io.realm.Realm;
import me.mthahzan.anonlk.newsfetch.BaseActivity;
import me.mthahzan.anonlk.newsfetch.R;
import me.mthahzan.anonlk.newsfetch.lib.models.Post;

public class PostViewActivity extends BaseActivity {

    /**
     * Post ID
     */
    private int postId;

    /**
     * {@link Realm} instance
     */
    private Realm realm;

    // UI elements
    private TextView title;
    private TextView content;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_view);

        postId = getIntent().getIntExtra(Post.INTENT_TAG, -1);

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
                    Toast.makeText(PostViewActivity.this, "Back pressed", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }
            });
        }

        title = (TextView) findViewById(R.id.title);
        content = (TextView) findViewById(R.id.content);
    }

    /**
     * Queries the {@link Post} object from local DB
     * @return The {@link Post} of ID
     */
    private Post queryPost() {
        return realm
                .where(Post.class)
                .equalTo("id", postId)
                .findFirst();
    }

    /**
     * Binds data to UI elements
     */
    private void bindData() {
        Post post = queryPost();

        if (post != null) {
            title.setText(post.getTitle());
            content.setText(post.getContent());
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
