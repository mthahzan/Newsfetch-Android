package me.mthahzan.anonlk.newsfetch.lib.utils;

import java.util.HashMap;

import me.mthahzan.anonlk.newsfetch.BuildConfig;

/**
 * Created by mthahzan on 1/28/17.
 * Contains app constants
 */
public class Constants {

    // TODO : Find a better way to set/get the constants based on app flavor

    /**
     * The flavor map
     */
    private HashMap<String, ConstantHolder> flavorMap;

    public Constants() {
        this.flavorMap = new HashMap<>();

        this.flavorMap.put("dev", new ConstantHolder("http://192.168.1.9:3000"));
        this.flavorMap.put("staging", new ConstantHolder("http://10.0.2.2:3000"));
        this.flavorMap.put("production", new ConstantHolder("http://10.0.2.2:3000"));
    }

    /**
     * Gets the current constants
     * @return {@link ConstantHolder} instance with all the constants
     */
    public ConstantHolder getConstants() {
        ConstantHolder constantHolder = this.flavorMap.get(BuildConfig.FLAVOR);

        if (constantHolder == null) {
            constantHolder = this.flavorMap.get("dev");
        }

        return constantHolder;
    }
}
