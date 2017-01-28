package me.mthahzan.anonlk.newsfetch.lib.network;

import android.content.Context;
import android.support.annotation.NonNull;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import java.util.HashMap;

import me.mthahzan.anonlk.newsfetch.lib.PreferenceManager;
import me.mthahzan.anonlk.newsfetch.lib.models.AppSession;

/**
 * Created by mthahzan on 1/28/17.
 * Network request handler
 */
public class NetworkManager {

    /**
     * Current {@link AppSession} instance
     */
    private AppSession appSession;

    /**
     * Construct {@link NetworkManager} instance with session
     * @param context Appication{@link Context}
     */
    public NetworkManager(@NonNull Context context) {
        this.appSession = PreferenceManager.getInstance(context).getSession();
    }

    /**
     * Construct {@link NetworkManager} instance without session
     */
    public NetworkManager() {}

    /**
     * Update the current {@link NetworkManager} instance with session
     * @param context Application{@link Context}
     */
    public void updateSession(@NonNull Context context) {
        this.appSession = PreferenceManager.getInstance(context).getSession();
    }

    /**
     * Make a HTTP GET request
     * @param url The URL to GET from
     * @param queryParams Data to be sent as request parameters
     * @param listener {@link JSONObjectRequestListener} instance for callbacks
     */
    public void GET(@NonNull String url, @NonNull HashMap<String, String> queryParams,
                    @NonNull  JSONObjectRequestListener listener) {
        // Create request builder instance
        ANRequest.GetRequestBuilder requestBuilder = AndroidNetworking.get(url);

        // Add query params
        requestBuilder.addQueryParameter(queryParams);

        // Add the Authorization header if there's a current session
        if (appSession != null && appSession.getAuthToken() != null) {
            requestBuilder.addHeaders("Authorization", "Bearer: " + appSession.getAuthToken());
        }

        // Build the request
        ANRequest request = requestBuilder.build();

        // Attach the listener
        request.getAsJSONObject(listener);
    }

    /**
     * Make a HTTP POST request
     * @param url The URL to GET from
     * @param bodyParams Data to be sent in the request body
     * @param listener {@link JSONObjectRequestListener} instance for callbacks
     */
    public void POST(@NonNull String url, @NonNull HashMap<String, String> bodyParams,
                     @NonNull JSONObjectRequestListener listener) {
        // Create request builder instance
        ANRequest.PostRequestBuilder requestBuilder = AndroidNetworking.post(url);

        // Add body parameters
        requestBuilder.addBodyParameter(bodyParams);

        // Add the Authorization header if there's a current session
        if (appSession != null && appSession.getAuthToken() != null) {
            requestBuilder.addHeaders("Authorization", "Bearer: " + appSession.getAuthToken());
        }

        // Build the request
        ANRequest request = requestBuilder.build();

        // Attach the listener
        request.getAsJSONObject(listener);
    }
}
