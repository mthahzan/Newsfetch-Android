package me.mthahzan.anonlk.newsfetch.lib.models;

import com.google.gson.Gson;

import io.realm.RealmObject;

/**
 * Created by mthahzan on 1/28/17.
 * Model class for AppPreferences
 */
public class AppPreference extends RealmObject implements IBaseModel {

    /**
     * The refresh time
     */
    private long refreshInterval;

    /**
     * Gets the refresh time
     * @return The refresh interval
     */
    public long getRefreshInterval() {
        return refreshInterval;
    }

    /**
     * Create an {@link AppPreference} instance by deserializing the given JSON String
     * @param jsonString The JSON String to deserialize
     * @return Deserialized {@link AppPreference} instance
     */
    public static AppPreference deserialize(String jsonString) {
        return new Gson().fromJson(jsonString, AppPreference.class);
    }

    @Override
    public String serialize() {
        return new Gson().toJson(this);
    }
}
