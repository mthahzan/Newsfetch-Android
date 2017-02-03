package me.mthahzan.anonlk.newsfetch.lib.network;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONObject;

import java.util.HashMap;

import me.mthahzan.anonlk.newsfetch.lib.PreferenceManager;
import me.mthahzan.anonlk.newsfetch.lib.models.AppSession;
import me.mthahzan.anonlk.newsfetch.lib.utils.URLBuilder;

/**
 * Created by mthahzan on 1/28/17.
 * Network request handler
 */
public class NetworkManager {

    /**
     * {@link PreferenceManager} instance
     */
    private PreferenceManager preferenceManager;

    /**
     * Construct {@link NetworkManager} instance with session
     * @param context Appication{@link Context}
     */
    public NetworkManager(@NonNull Context context) {
        this.preferenceManager = PreferenceManager.getInstance(context);
    }

    /**
     * Make an HTTP GET request
     * This function will automatically refresh the auth token if necessary
     * @param url The URL to GET from
     * @param queryParams The query params
     * @param listener The {@link JSONObjectRequestListener} instance
     */
    public void makeGETRequest(final @NonNull String url,
                               final @Nullable HashMap<String, String> queryParams,
                               final @NonNull JSONObjectRequestListener listener) {
        AppSession appSession = this.preferenceManager.getSession();

        if (appSession != null) {
            // There is a current session
            if (System.currentTimeMillis() > appSession.getExpiration()) {
                // Session has expired
                // Request a refreshed token
                GET(URLBuilder.refreshTokenEndpoint(), new HashMap<String, String>(),
                        new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Set the new session
                        AppSession appSession = AppSession.deserialize(response.toString());
                        preferenceManager.setSession(appSession);

                        // Make the actual request
                        GET(url, queryParams, listener);
                    }

                    @Override
                    public void onError(ANError anError) {
                        listener.onError(anError);
                    }
                });
            } else {
                // Session still active
                // Make the request
                GET(url, queryParams, listener);
            }
        } else {
            // There's no session
            // Make the request
            GET(url, queryParams, listener);
        }
    }

    /**
     * Make an HTTP POST request
     * This function will automatically refresh the auth token if necessary
     * @param url The URL to POST to
     * @param bodyParams The body params
     * @param listener The {@link JSONObjectRequestListener} instance
     */
    public void makePOSTRequest(final @NonNull String url,
                               final @Nullable HashMap<String, String> bodyParams,
                               final @NonNull JSONObjectRequestListener listener) {
        AppSession appSession = this.preferenceManager.getSession();

        if (appSession != null) {
            // There is a current session
            if (System.currentTimeMillis() > appSession.getExpiration()) {
                // Session has expired
                // Request a refreshed token
                GET(URLBuilder.refreshTokenEndpoint(), new HashMap<String, String>(),
                        new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                // Set the new session
                                AppSession appSession = AppSession.deserialize(response.toString());
                                preferenceManager.setSession(appSession);

                                // Make the actual request
                                POST(url, bodyParams, listener);
                            }

                            @Override
                            public void onError(ANError anError) {
                                listener.onError(anError);
                            }
                        });
            } else {
                // Session still active
                // Make the request
                POST(url, bodyParams, listener);
            }
        } else {
            // There's no session
            // Make the request
            POST(url, bodyParams, listener);
        }
    }

    /**
     * Make an HTTP GET request
     * @param url The URL to GET from
     * @param queryParams Data to be sent as request parameters
     * @param listener {@link JSONObjectRequestListener} instance for callbacks
     */
    private void GET(@NonNull String url, @Nullable HashMap<String, String> queryParams,
                    @NonNull  JSONObjectRequestListener listener) {
        // Get the current session
        AppSession appSession = this.preferenceManager.getSession();

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
     * Make an HTTP POST request
     * @param url The URL to POST to
     * @param bodyParams Data to be sent in the request body
     * @param listener {@link JSONObjectRequestListener} instance for callbacks
     */
    private void POST(@NonNull String url, @Nullable HashMap<String, String> bodyParams,
                     @NonNull JSONObjectRequestListener listener) {
        // Get the current session
        AppSession appSession = this.preferenceManager.getSession();

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
