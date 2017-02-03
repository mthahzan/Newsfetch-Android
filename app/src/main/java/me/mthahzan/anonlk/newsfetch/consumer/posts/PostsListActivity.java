package me.mthahzan.anonlk.newsfetch.consumer.posts;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import me.mthahzan.anonlk.newsfetch.BaseActivity;
import me.mthahzan.anonlk.newsfetch.R;
import me.mthahzan.anonlk.newsfetch.consumer.post.PostViewActivity;
import me.mthahzan.anonlk.newsfetch.consumer.shared.adapter.ConsumerItemAdapter;
import me.mthahzan.anonlk.newsfetch.consumer.shared.adapter.OnConsumerItemClickListener;
import me.mthahzan.anonlk.newsfetch.lib.models.IItemModel;
import me.mthahzan.anonlk.newsfetch.lib.models.Post;
import me.mthahzan.anonlk.newsfetch.lib.models.PostType;
import me.mthahzan.anonlk.newsfetch.lib.network.NetworkManager;
import me.mthahzan.anonlk.newsfetch.lib.utils.URLBuilder;

public class PostsListActivity extends BaseActivity {

    private static final String LOG_TAG = PostsListActivity.class.getSimpleName();

    /**
     * {@link NetworkManager} instance
     */
    private NetworkManager networkManager;

    /**
     * {@link Realm} instance
     */
    private Realm realm = Realm.getDefaultInstance();

    /**
     * {@link CollapsingToolbarLayout} instance
     */
    private CollapsingToolbarLayout collapsingToolbarLayout;

    /**
     * {@link SwipeRefreshLayout} instance
     */
    private SwipeRefreshLayout swipeRefreshLayout;

    /**
     * The current PostType ID
     */
    private int postTypeId = -1;

    /**
     * The {@link RecyclerView} instance
     */
    private RecyclerView recyclerView;

    /**
     * {@link ConsumerItemAdapter} instance
     */
    private ConsumerItemAdapter consumerItemAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts_list);

        // Get the PostTypeID from the intent
        postTypeId = getIntent().getIntExtra(PostType.INTENT_TAG, -1);

        networkManager = new NetworkManager(this.getApplicationContext());

        initializeViewElements();

        processPostTypeBinding();
    }

    /**
     * Instantiates the UI element references
     */
    private void initializeViewElements() {
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingContainer);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchPostType(null);
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recycler);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
    }

    /**
     * Process the binding logic
     */
    private void processPostTypeBinding() {
        PostType postType = queryPostType(postTypeId);

        if (postType != null) {
            // We have data, so bind it
            bindData();
        }

        if (this.isNetworkAvailable()) {
            HashMap<String, String> queryParams = new HashMap<>();

            // TODO : Find the latest updated value for query

            fetchPostType(queryParams);
        }

        if (postType == null && !this.isNetworkAvailable()) {
            Snackbar.make(swipeRefreshLayout, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

    /**
     * Query the PostTypes
     * @param postTypeId The {@link PostType} ID
     * @return {@link PostType} of ID
     */
    private PostType queryPostType(int postTypeId) {
        return realm
                .where(PostType.class)
                .equalTo("id", postTypeId)
                .findFirst();
    }

    /**
     * Fetches the data from the API
     */
    private void fetchPostType(@Nullable HashMap<String, String> queryParams) {
        // Display a progress
        updateProgress(true);

        this.networkManager.makeGETRequest(URLBuilder.modelURL("post-type", postTypeId), queryParams,
                new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        PostType postType = PostType.deserialize(response.toString());

                        // Save the objects
                        persistPostType(postType);

                        // Bind data
                        bindData();

                        // Hide the progress
                        updateProgress(false);
                    }

                    @Override
                    public void onError(ANError anError) {
                        anError.printStackTrace();
                        Log.e(LOG_TAG, "Error when retrieving data");

                        // Hide the progress
                        updateProgress(false);
                    }
                });
    }

    /**
     * Persist the {@link PostType} objects to the database
     * @param postType {@link PostType} object
     */
    private void persistPostType(@NonNull PostType postType) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(postType);
        realm.commitTransaction();
    }

    /**
     * Shows or hides the progress indicator
     * @param show Boolean indicating whether to show or hide the progress
     */
    private void updateProgress(boolean show) {
        swipeRefreshLayout.setRefreshing(show);
    }

    /**
     * Binds the data to the UI components
     */
    private void bindData() {
        PostType postType = queryPostType(postTypeId);

        // Set the title to the toolbar
        if (postType != null) {
            collapsingToolbarLayout.setTitle(postType.getName());
        }

        List<IItemModel> itemModels = new ArrayList<>();

        if (postType != null && postType.getPosts() != null) {
            RealmList<Post> posts = postType.getPosts();

            itemModels.addAll(posts);
        }

        if (consumerItemAdapter == null) {
            consumerItemAdapter = new ConsumerItemAdapter(itemModels,
                    new OnConsumerItemClickListener() {
                @Override
                public void onItemClick(IItemModel item) {
                    Post post = (Post) item;

                    // Navigate to single post page
                    navigateToSinglePostTypeView(post);
                }
            });

            recyclerView.setAdapter(consumerItemAdapter);

            RecyclerView.LayoutManager mLayoutManager =
                    new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
        } else {
            consumerItemAdapter.setItems(itemModels);
        }
    }

    /**
     * Navigate to single post type activity
     * @param post The clicked {@link Post}
     */
    private void navigateToSinglePostTypeView(Post post) {
        Intent intent = new Intent(PostsListActivity.this, PostViewActivity.class);
        intent.putExtra(Post.INTENT_TAG, post.getId());
        startActivity(intent);
    }

    /**
     * Overriding the default back press action
     */
    @Override
    public void onBackPressed() {
        finish();
    }
}
