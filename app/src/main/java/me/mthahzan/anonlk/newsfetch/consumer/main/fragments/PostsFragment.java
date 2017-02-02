package me.mthahzan.anonlk.newsfetch.consumer.main.fragments;


import android.os.Bundle;
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

import java.util.HashMap;
import java.util.List;

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


    public PostsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.networkManager = new NetworkManager(this.getActivity().getApplicationContext());

        this.networkManager.makeGETRequest(URLBuilder.modelURL("post-type"),
                new HashMap<String, String>(), new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray resultsArray = response.getJSONArray("results");
                            List<PostType> postTypes = PostType
                                    .deserializeCollection(resultsArray.toString());
                            bindData(postTypes);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e(LOG_TAG, "Error when parsing data");
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e(LOG_TAG, "Error when retrieving data");
                    }
                });

        return inflater.inflate(R.layout.fragment_posts, container, false);
    }

    /**
     * Binds the given {@link List<PostType> to the view}
     * @param postTypes The {@link List<PostType>} to bind to the view
     */
    private void bindData(List<PostType> postTypes) {
        Log.e(LOG_TAG, postTypes.toString());
    }

}
