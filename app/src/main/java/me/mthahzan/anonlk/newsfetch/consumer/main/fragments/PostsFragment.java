package me.mthahzan.anonlk.newsfetch.consumer.main.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import me.mthahzan.anonlk.newsfetch.R;
import me.mthahzan.anonlk.newsfetch.consumer.main.adapter.ConsumerMainGridBaseAdapter;
import me.mthahzan.anonlk.newsfetch.consumer.posts.PostsListActivity;
import me.mthahzan.anonlk.newsfetch.lib.models.ITypeModel;
import me.mthahzan.anonlk.newsfetch.lib.models.PostType;
import me.mthahzan.anonlk.newsfetch.lib.network.NetworkManager;
import me.mthahzan.anonlk.newsfetch.lib.utils.URLBuilder;

/**
 * A simple {@link Fragment} subclass.
 */
public class PostsFragment extends Fragment {

    private static final String LOG_TAG = PostsFragment.class.getSimpleName();

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
     * {@link GridView} instance
     */
    private GridView gridView;

    /**
     * {@link ConsumerMainGridBaseAdapter} instance
     */
    private ConsumerMainGridBaseAdapter consumerMainGridBaseAdapter;


    public PostsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the view
        View view = inflater.inflate(R.layout.fragment_posts, container, false);

        // Instantiate view elements
        instantiateViewElements(view);

        // Instantiate the NetworkManager instance
        this.networkManager = new NetworkManager(this.getActivity().getApplicationContext());

        RealmResults<PostType> postTypes = queryPostTypes()
                .sort("createdAt");

        if (postTypes.size() > 0) {
            // There's data available
            // Set the to the UI
            bindData();

            // Get the last update time
            Date latestUpdated = postTypes
                    .sort("updatedAt")
                    .last()
                    .getUpdatedAt();

            HashMap<String, String> queryParams = new HashMap<>();
            queryParams.put("lastUpdate", String.valueOf(latestUpdated.getTime()));

            // Fetch with query params
            fetchPostTypes(queryParams);
        } else {
            // There's no PostTypes. Get them all
            fetchPostTypes(null);
        }

        return view;
    }

    /**
     * Instantiates the view elements
     * @param view The parent {@link View} element
     */
    private void instantiateViewElements(@NonNull View view) {
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);
        gridView = (GridView) swipeRefreshLayout.findViewById(R.id.gridview);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchPostTypes(null);
            }
        });
    }

    /**
     * Query the PostTypes
     * @return {@link RealmResults<PostType>}
     */
    private RealmResults<PostType> queryPostTypes() {
        return realm
                .where(PostType.class)
                .findAll();
    }

    /**
     * Fetches the data from the API
     */
    private void fetchPostTypes(@Nullable HashMap<String, String> queryParams) {
        // Display a progress
        updateProgress(true);

        this.networkManager.makeGETRequest(URLBuilder.modelURL("post-type"), queryParams,
                new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray resultsArray = response.getJSONArray("results");
                            List<PostType> postTypes = PostType
                                    .deserializeCollection(resultsArray.toString());

                            // Save the objects
                            persistPostTypes(postTypes);

                            // Bind data
                            bindData();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e(LOG_TAG, "Error when parsing data");
                        } finally {
                            // Hide the progress
                            updateProgress(false);
                        }
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
     * @param postTypes {@link PostType} object collection
     */
    private void persistPostTypes(@NonNull List<PostType> postTypes) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(postTypes);
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
        RealmResults<PostType> postTypes =  queryPostTypes();

        List<ITypeModel> typeModels = new ArrayList<>();
        for (PostType postType : postTypes) {
            typeModels.add(postType);
        }

        if (consumerMainGridBaseAdapter == null) {
            consumerMainGridBaseAdapter = new ConsumerMainGridBaseAdapter(getActivity(), typeModels);
            gridView.setAdapter(consumerMainGridBaseAdapter);

            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    PostType clickedPostType = (PostType) consumerMainGridBaseAdapter
                            .getItem(position);

                    navigateToSinglePostTypeView(clickedPostType);
                }
            });
        } else {
            consumerMainGridBaseAdapter.setTypeModels(typeModels);
        }
    }

    /**
     * Navigate to single post type activity
     * @param postType The clicked {@link PostType}
     */
    private void navigateToSinglePostTypeView(PostType postType) {
        Intent intent = new Intent(getActivity(), PostsListActivity.class);
        intent.putExtra(PostType.INTENT_TAG, postType.getId());
        startActivity(intent);
    }

}
