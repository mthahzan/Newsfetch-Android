package me.mthahzan.anonlk.newsfetch.consumer.main.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import me.mthahzan.anonlk.newsfetch.lib.models.CommercialType;
import me.mthahzan.anonlk.newsfetch.lib.models.ITypeModel;
import me.mthahzan.anonlk.newsfetch.lib.network.NetworkManager;
import me.mthahzan.anonlk.newsfetch.lib.utils.URLBuilder;

/**
 * A simple {@link Fragment} subclass.
 */
public class CommercialsFragment extends Fragment {

    private static final String LOG_TAG = CommercialsFragment.class.getSimpleName();

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


    public CommercialsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the view
        View view = inflater.inflate(R.layout.fragment_commercials, container, false);

        // Instantiate view elements
        instantiateViewElements(view);

        // Instantiate the NetworkManager instance
        this.networkManager = new NetworkManager(this.getActivity().getApplicationContext());

        RealmResults<CommercialType> commercialTypes = queryCommercialTypes()
                .sort("createdAt");

        if (commercialTypes.size() > 0) {
            // There's data available
            // Set the to the UI
            bindData();

            // Get the last update time
            Date latestUpdated = commercialTypes
                    .sort("updatedAt")
                    .last()
                    .getUpdatedAt();

            HashMap<String, String> queryParams = new HashMap<>();
            queryParams.put("lastUpdate", String.valueOf(latestUpdated.getTime()));

            // Fetch with query params
            fetchCommercialTypes(queryParams);
        } else {
            // There's no PostTypes. Get them all
            fetchCommercialTypes(null);
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
                fetchCommercialTypes(null);
            }
        });
    }

    /**
     * Query the CommercialTypes
     * @return {@link RealmResults<CommercialType>}
     */
    private RealmResults<CommercialType> queryCommercialTypes() {
        return realm
                .where(CommercialType.class)
                .findAll();
    }

    /**
     * Fetches the data from the API
     */
    private void fetchCommercialTypes(@Nullable HashMap<String, String> queryParams) {
        // Display a progress
        updateProgress(true);

        this.networkManager.makeGETRequest(URLBuilder.modelURL("commercial-type"), queryParams,
                new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray resultsArray = response.getJSONArray("results");
                            List<CommercialType> commercialTypes = CommercialType
                                    .deserializeCollection(resultsArray.toString());

                            // Save the objects
                            persistCommercialTypes(commercialTypes);

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
     * Persist the {@link CommercialType} objects to the database
     * @param commercialTypes {@link CommercialType} object collection
     */
    private void persistCommercialTypes(@NonNull List<CommercialType> commercialTypes) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(commercialTypes);
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
        RealmResults<CommercialType> commercialTypes =  queryCommercialTypes()
                .sort("updatedAt");

        List<ITypeModel> typeModels = new ArrayList<>();
        for (CommercialType commercialType: commercialTypes) {
            typeModels.add(commercialType);
        }

        if (consumerMainGridBaseAdapter == null) {
            consumerMainGridBaseAdapter = new ConsumerMainGridBaseAdapter(getActivity(), typeModels);
            gridView.setAdapter(consumerMainGridBaseAdapter);
        } else {
            consumerMainGridBaseAdapter.setTypeModels(typeModels);
        }
    }

}
