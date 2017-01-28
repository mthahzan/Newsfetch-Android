package me.mthahzan.anonlk.newsfetch.lib.models;

/**
 * Created by mthahzan on 1/28/17.
 * Base model class to extends from
 */
abstract class BaseModel {

    /**
     * Abstract method to override on subclasses to serialize
     * @return Serialized JSON Stirng
     */
    public abstract String serialize();
}
