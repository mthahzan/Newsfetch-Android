package me.mthahzan.anonlk.newsfetch.lib.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import me.mthahzan.anonlk.newsfetch.lib.utils.Constants;

/**
 * Created by mthahzan on 2/2/17.
 * Model class for Post object
 */
public class Commercial extends RealmObject implements IBaseModel {

    /**
     * ID of the Commercial
     */
    @PrimaryKey
    private int id;

    /**
     * Title of the commercial
     */
    private String title;

    /**
     * The image URL string
     */
    private String imageURL;

    /**
     * The commercial content
     */
    private String content;

    /**
     * Boolean indicating whether the commercial is active
     */
    private boolean active;

    /**
     * The relationship link to {@link CommercialType}
     */
    private CommercialType commercialType;

    /**
     * Record creation {@link Date}
     */
    private Date createdAt;

    /**
     * Record last updated {@link Date}
     */
    private Date updatedAt;

    /**
     * Gets the commercial ID
     * @return Commercial ID int
     */
    public int getId() {
        return id;
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
     * Gets the content of the commercial
     * @return Commercial content string
     */
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
     * Gets the Parent {@link CommercialType} object
     * @return Parent {@link CommercialType} object
     */
    public CommercialType getPostType() {
        return commercialType;
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

    @Override
    public String serialize() {
        String dateFormat = new Constants().getConstants().getApiDateFormat();
        Gson gson = new GsonBuilder().setDateFormat(dateFormat).create();

        return gson.toJson(this);
    }
}
