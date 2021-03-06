package me.mthahzan.anonlk.newsfetch.lib.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import me.mthahzan.anonlk.newsfetch.lib.utils.Constants;

/**
 * Created by mthahzan on 1/31/17.
 * Model class for PostType
 */
public class PostType extends RealmObject implements IBaseModel, ITypeModel {

    /**
     * Tha tag to use when putting this as extra when passing through an intent
     */
    public static final String INTENT_TAG = "post_type_id";

    /**
     * ID of the PostType model
     */
    @SuppressWarnings("unused")
    @PrimaryKey
    private int id;

    /**
     * Name of the post type
     */
    @SuppressWarnings("unused")
    private String name;

    /**
     * Flag indicating whether it's active
     */
    @SuppressWarnings("unused")
    private boolean active;

    /**
     * Flag indicating whether a notification is necessary
     */
    @SuppressWarnings("unused")
    private boolean notification;

    /**
     * Post type icon URL
     */
    @SuppressWarnings("unused")
    private String imageURL;

    /**
     * The child {@link Post} objects
     */
    @SuppressWarnings("unused")
    @SerializedName("Posts")
    private RealmList<Post> posts;

    /**
     * Record creation {@link Date}
     */
    @SuppressWarnings("unused")
    private Date createdAt;

    /**
     * Record last updated {@link Date}
     */
    @SuppressWarnings("unused")
    private Date updatedAt;

    /**
     * Gets the PostType ID
     * @return PostType ID
     */
    @Override
    public int getId() {
        return id;
    }

    /**
     * Gets the post type name
     * @return Name string
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Gets the Post type Icon URL
     * @return Icon URL string
     */
    @Override
    public String getImageURL() {
        return imageURL;
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
     * Gets the child {@link RealmList<Post>} objects
     * @return Child {@link RealmList<Post>} objects
     */
    public RealmList<Post> getPosts() {
        return posts;
    }

    /**
     * Gets the created {@link Date} of the record
     * @return Created {@link Date}
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * Gets the last updated {@link Date}
     * @return Last updated {@link Date}
     */
    public Date getUpdatedAt() {
        return updatedAt;
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
        Gson gson = new GsonBuilder().setDateFormat(Constants.DATE_FORMAT).create();

        Type listType = new TypeToken<ArrayList<PostType>>(){}.getType();

        return gson.fromJson(jsonString, listType);
    }

    @Override
    public String serialize() {
        return new Gson().toJson(this);
    }
}
