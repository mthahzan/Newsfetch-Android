package me.mthahzan.anonlk.newsfetch.consumer.posts;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONObject;

import java.util.HashMap;

import io.realm.Realm;
import me.mthahzan.anonlk.newsfetch.BaseActivity;
import me.mthahzan.anonlk.newsfetch.R;
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
     * {@link SwipeRefreshLayout} instance
     */
    private SwipeRefreshLayout swipeRefreshLayout;

    /**
     * The current PostType ID
     */
    private int postTypeId = -1;

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
     * Instantiates the UI element references
     */
    private void initializeViewElements() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchPostType(null);
            }
        });

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

//        List<ITypeModel> typeModels = new ArrayList<>();
//        for (PostType postType : postTypes) {
//            typeModels.add(postType);
//        }
//
//        if (consumerMainGridBaseAdapter == null) {
//            consumerMainGridBaseAdapter = new ConsumerMainGridBaseAdapter(getActivity(), typeModels);
//            gridView.setAdapter(consumerMainGridBaseAdapter);
//
//            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    PostType clickedPostType = (PostType) consumerMainGridBaseAdapter
//                            .getItem(position);
//
//                    navigateToSinglePostTypeView(clickedPostType);
//                }
//            });
//        } else {
//            consumerMainGridBaseAdapter.setTypeModels(typeModels);
//        }
    }

    /**
     * Navigate to single post type activity
     * @param postType The clicked {@link PostType}
     */
    private void navigateToSinglePostTypeView(PostType postType) {
//        Intent intent = new Intent(PostsListActivity.this, PostsListActivity.class);
//        intent.putExtra(PostType.INTENT_TAG, postType.getId());
//        startActivity(intent);
    }

}
