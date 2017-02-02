package me.mthahzan.anonlk.newsfetch.lib.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
 * Model class for CommercialType
 */
public class CommercialType extends RealmObject implements BaseModel, ITypeModel {

    /**
     * ID of the CommercialType model
     */
    @PrimaryKey
    private int id;

    /**
     * Name of the commercial type
     */
    private String name;

    /**
     * Flag indicating whether it's active
     */
    private boolean active;

    /**
     * The child {@link Commercial} objects
     */
    private RealmList<Commercial> commercials;

    /**
     * Record creation {@link Date}
     */
    private Date createdAt;

    /**
     * Record last updated {@link Date}
     */
    private Date updatedAt;

    /**
     * Gets the CommercialType ID
     * @return CommercialType ID
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

    @Override
    public String getIconURL() {
        return null;
    }

    /**
     * Gets the flag which indicates whether this post type is active
     * @return Boolean flag which indicates if this is active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Gets the child {@link RealmList<Commercial>} objects
     * @return Child {@link RealmList<Commercial>} objects
     */
    public RealmList<Commercial> getPosts() {
        return commercials;
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
     * @return Deserialized {@link CommercialType} object
     */
    public static CommercialType deserialize(String jsonString) {
        return new Gson().fromJson(jsonString, CommercialType.class);
    }

    /**
     * Deserializes a collection of model instances
     * @param jsonString The model JSON Array
     * @return Deserialized {@link List< CommercialType >}
     */
    public static List<CommercialType> deserializeCollection(String jsonString) {
        String dateFormat = new Constants().getConstants().getApiDateFormat();
        Gson gson = new GsonBuilder().setDateFormat(dateFormat).create();

        Type listType = new TypeToken<ArrayList<CommercialType>>(){}.getType();

        return gson.fromJson(jsonString, listType);
    }

    @Override
    public String serialize() {
        return new Gson().toJson(this);
    }
}