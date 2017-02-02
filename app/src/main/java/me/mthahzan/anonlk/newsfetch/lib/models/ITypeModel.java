package me.mthahzan.anonlk.newsfetch.lib.models;

/**
 * Created by mthahzan on 2/2/17.
 * Type model interface. PostType and CommercialType will implement this.
 */
public interface ITypeModel {

    /**
     * Gets the model ID
     * @return Model ID
     */
    int getId();

    /**
     * Gets the Name of the model
     * @return Name of the model
     */
    String getName();

    /**
     * Gets the bitmap Icon URL
     * @return Bitmap Icon URL string
     */
    String getIconURL();
}
