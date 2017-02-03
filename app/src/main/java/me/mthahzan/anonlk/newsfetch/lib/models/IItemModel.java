package me.mthahzan.anonlk.newsfetch.lib.models;

/**
 * Created by mthahzan on 2/3/17.
 * The interface to implement for Item models (
 */
public interface IItemModel extends ITypeModel {

    /**
     * Gets the content of the item
     * @return The content of the item
     */
    String getContent();
}
