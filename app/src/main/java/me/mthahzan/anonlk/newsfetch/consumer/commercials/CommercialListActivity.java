package me.mthahzan.anonlk.newsfetch.consumer.commercials;

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
import me.mthahzan.anonlk.newsfetch.consumer.commercial.CommercialViewActivity;
import me.mthahzan.anonlk.newsfetch.consumer.posts.PostsListActivity;
import me.mthahzan.anonlk.newsfetch.consumer.shared.adapter.ConsumerItemAdapter;
import me.mthahzan.anonlk.newsfetch.consumer.shared.adapter.OnConsumerItemClickListener;
import me.mthahzan.anonlk.newsfetch.lib.models.Commercial;
import me.mthahzan.anonlk.newsfetch.lib.models.CommercialType;
import me.mthahzan.anonlk.newsfetch.lib.models.IItemModel;
import me.mthahzan.anonlk.newsfetch.lib.network.NetworkManager;
import me.mthahzan.anonlk.newsfetch.lib.utils.URLBuilder;

public class CommercialListActivity extends BaseActivity {

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
     * The current CommercialType ID
     */
    private int commercialTypeId = -1;

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
        setContentView(R.layout.activity_commercial_list);

        // Get the PostTypeID from the intent
        commercialTypeId = getIntent().getIntExtra(CommercialType.INTENT_TAG, -1);

        networkManager = new NetworkManager(this.getApplicationContext());

        initializeViewElements();

        processCommercialTypeBinding();
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
                fetchCommercialType(null);
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
    private void processCommercialTypeBinding() {
        CommercialType commercialType = queryCommercialType(commercialTypeId);

        if (commercialType != null) {
            // We have data, so bind it
            bindData();
        }

        if (this.isNetworkAvailable()) {
            HashMap<String, String> queryParams = new HashMap<>();

            // TODO : Find the latest updated value for query

            fetchCommercialType(queryParams);
        }

        if (commercialType == null && !this.isNetworkAvailable()) {
            Snackbar.make(swipeRefreshLayout, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

    /**
     * Query the CommercialTypes
     * @param commercialTypeId The {@link CommercialType} ID
     * @return {@link CommercialType} of ID
     */
    private CommercialType queryCommercialType(int commercialTypeId) {
        return realm
                .where(CommercialType.class)
                .equalTo("id", commercialTypeId)
                .findFirst();
    }

    /**
     * Fetches the data from the API
     */
    private void fetchCommercialType(@Nullable HashMap<String, String> queryParams) {
        // Display a progress
        updateProgress(true);

        this.networkManager.makeGETRequest(
                URLBuilder.modelURL("commercial-type", commercialTypeId), queryParams,
                new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        CommercialType commercialType =
                                CommercialType.deserialize(response.toString());

                        // Save the objects
                        persistCommercialType(commercialType);

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
     * Persist the {@link CommercialType} objects to the database
     * @param commercialType {@link CommercialType} object
     */
    private void persistCommercialType(@NonNull CommercialType commercialType) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(commercialType);
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
        CommercialType commercialType = queryCommercialType(commercialTypeId);

        // Set the title to the toolbar
        if (commercialType != null) {
            collapsingToolbarLayout.setTitle(commercialType.getName());
        }

        List<IItemModel> itemModels = new ArrayList<>();

        if (commercialType != null && commercialType.getCommercials() != null) {
            RealmList<Commercial> posts = commercialType.getCommercials();

            itemModels.addAll(posts);
        }

        if (consumerItemAdapter == null) {
            consumerItemAdapter = new ConsumerItemAdapter(itemModels,
                    new OnConsumerItemClickListener() {
                        @Override
                        public void onItemClick(IItemModel item) {
                            Commercial commercial = (Commercial) item;

                            // Navigate to single commercial page
                            navigateToSinglePostTypeView(commercial);
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
     * Navigate to single commercial activity
     * @param commercial The clicked {@link Commercial}
     */
    private void navigateToSinglePostTypeView(Commercial commercial) {
        Intent intent = new Intent(CommercialListActivity.this, CommercialViewActivity.class);
        intent.putExtra(Commercial.INTENT_TAG, commercial.getId());
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
