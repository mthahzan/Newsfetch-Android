package me.mthahzan.anonlk.newsfetch.lib.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import me.mthahzan.anonlk.newsfetch.lib.utils.Constants;

/**
 * Created by mthahzan on 2/2/17.
 * Model class for Post object
 */
public class Post extends RealmObject implements IBaseModel, IItemModel {

    /**
     * Tha tag to use when putting this as extra when passing through an intent
     */
    public static final String INTENT_TAG = "post_id";

    /**
     * ID of the Post
     */
    @SuppressWarnings("unused")
    @PrimaryKey
    private int id;

    /**
     * Title of the post
     */
    @SuppressWarnings("unused")
    private String title;

    /**
     * The image URL string
     */
    @SuppressWarnings("unused")
    private String imageURL;

    /**
     * The post content
     */
    @SuppressWarnings("unused")
    private String content;

    /**
     * Boolean indicating whether the post is active
     */
    @SuppressWarnings("unused")
    private boolean active;

    /**
     * The relationship link to {@link PostType}
     */
    @SuppressWarnings("unused")
    private PostType postType;

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
     * Gets the post ID
     * @return Post ID int
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the name (title)
     * @return The title to display
     */
    @Override
    public String getName() {
        return title;
    }

    /**
     * Gets the title
     * @return Title string
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets the image URL
     * @return Image URL string
     */
    public String getImageURL() {
        return imageURL;
    }

    /**
     * Gets the content of the post
     * @return Post content string
     */
    @Override
    public String getContent() {
        return content;
    }

    /**
     * Gets the boolean flag specifying whether the post is active
     * @return Boolean flag indicating whether the post is active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Gets the Parent {@link PostType} object
     * @return Parent {@link PostType} object
     */
    public PostType getPostType() {
        return postType;
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
     * Deserializes a collection of model instances
     * @param jsonString The model JSON Array
     * @return Deserialized {@link List <PostType>}
     */
    public static List<Post> deserializeCollection(String jsonString) {
        String dateFormat = new Constants().getConstants().getApiDateFormat();
        Gson gson = new GsonBuilder().setDateFormat(dateFormat).create();

        Type listType = new TypeToken<ArrayList<Post>>(){}.getType();

        return gson.fromJson(jsonString, listType);
    }

    @Override
    public String serialize() {
        String dateFormat = new Constants().getConstants().getApiDateFormat();
        Gson gson = new GsonBuilder().setDateFormat(dateFormat).create();

        return gson.toJson(this);
    }
}
