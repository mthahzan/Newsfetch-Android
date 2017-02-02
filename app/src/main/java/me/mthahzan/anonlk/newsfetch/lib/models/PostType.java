package me.mthahzan.anonlk.newsfetch.lib.models;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mthahzan on 1/31/17.
 * Model class for PostType
 */
public class PostType extends BaseModel {

    /**
     * Name of the post type
     */
    private String name;

    /**
     * Flag indicating whether it's active
     */
    private boolean active;

    /**
     * Flag indicating whether a notification is necessary
     */
    private boolean notification;

    /**
     * Gets the post type name
     * @return Name string
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the flag which indicates whether this post type is active
     * @return Boolean flag which indicates if this is active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Gets the flag which indicates whether this post type requires notifications
     * @return Boolean flag which indicates if this requires notifications
     */
    public boolean isNotification() {
        return notification;
    }

    /**
     * Deserializes a model instance
     * @param jsonString The model JSON string
     * @return Deserialized {@link PostType} object
     */
    public static PostType deserialize(String jsonString) {
        return new Gson().fromJson(jsonString, PostType.class);
    }

    /**
     * Deserializes a collection of model instances
     * @param jsonString The model JSON Array
     * @return Deserialized {@link List<PostType>}
     */
    public static List<PostType> deserializeCollection(String jsonString) {
        Type listType = new TypeToken<ArrayList<PostType>>(){}.getType();

        return new Gson().fromJson(jsonString, listType);
    }

    @Override
    public String serialize() {
        return new Gson().toJson(this);
    }
}
