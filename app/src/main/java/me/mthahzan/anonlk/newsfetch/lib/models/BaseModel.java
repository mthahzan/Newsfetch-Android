package me.mthahzan.anonlk.newsfetch.lib.models;

/**
 * Created by mthahzan on 1/28/17.
 * Base model class to extends from
 */
interface BaseModel {

    /**
     * Abstract method to override on subclasses to serialize
     * @return Serialized JSON Stirng
     */
    String serialize();
}
