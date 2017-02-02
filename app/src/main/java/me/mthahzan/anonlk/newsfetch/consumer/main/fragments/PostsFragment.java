package me.mthahzan.anonlk.newsfetch.consumer.main.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import me.mthahzan.anonlk.newsfetch.R;
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
    private Realm realm = Realm.getDefaultInstance();;


    public PostsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Instantiate the NetworkManager instance
        this.networkManager = new NetworkManager(this.getActivity().getApplicationContext());

        RealmResults<PostType> postTypes = queryPostTypes()
                .sort("createdAt");

        if (postTypes.size() > 0) {
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

        return inflater.inflate(R.layout.fragment_posts, container, false);
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
    private void fetchPostTypes(HashMap<String, String> queryParams) {
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
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        anError.printStackTrace();
                        Log.e(LOG_TAG, "Error when retrieving data");
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
     * Binds the data to the UI components
     */
    private void bindData() {
        // TODO : Bind the data
    }

}
