package me.mthahzan.anonlk.newsfetch.lib.models;

/**
 * Created by mthahzan on 1/28/17.
 * Base model class to extends from
 */
interface IBaseModel {

    /**
     * Abstract method to override on subclasses to serialize
     * @return Serialized JSON Stirng
     */
    String serialize();
}
