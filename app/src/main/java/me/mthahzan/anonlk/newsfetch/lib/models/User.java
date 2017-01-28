package me.mthahzan.anonlk.newsfetch.lib.models;

import com.google.gson.Gson;

/**
 * Created by mthahzan on 1/28/17.
 * Model class for user
 */
public class User extends BaseModel {

    /**
     * User ID
     */
    private int id;

    /**
     * User name
     */
    private String name;

    /**
     * User authName
     */
    private String authName;

    /**
     * {@link AppPreference} object
     */
    private AppPreference appPreference;

    /**
     * Gets the user ID
     * @return User ID int
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the user name
     * @return User name string
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the user authentication name string
     * @return Auth name string
     */
    public String getAuthName() {
        return authName;
    }

    /**
     * Gets the user {@link AppPreference} object
     * @return {@link AppPreference} object
     */
    public AppPreference getAppPreference() {
        return appPreference;
    }

    /**
     * Create an {@link User} instance by deserializing the given JSON String
     * @param jsonString The JSON String to deserialize
     * @return Deserialized {@link User} instance
     */
    public static User deserialize(String jsonString) {
        return new Gson().fromJson(jsonString, User.class);
    }

    @Override
    public String serialize() {
        return new Gson().toJson(this);
    }
}
