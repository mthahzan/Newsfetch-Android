package me.mthahzan.anonlk.newsfetch.lib.models;

import com.google.gson.Gson;

/**
 * Created by mthahzan on 1/28/17.
 * Model class for AppSession
 */
public class AppSession extends BaseModel {

    /**
     * Auth token
     */
    private String authToken;

    /**
     * Session user object
     */
    private User user;

    /**
     * Gets the auth token
     * @return The auth token string
     */
    public String getAuthToken() {
        return authToken;
    }

    /**
     * Gets the {@link User}
     * @return Current {@link User} object
     */
    public User getUser() {
        return user;
    }

    /**
     * Create an {@link AppSession} instance by deserializing the given JSON String
     * @param jsonString The JSON String to deserialize
     * @return Deserialized {@link AppSession} instance
     */
    public static AppSession deserialize(String jsonString) {
        return new Gson().fromJson(jsonString, AppSession.class);
    }

    @Override
    public String serialize() {
        return new Gson().toJson(this);
    }
}
