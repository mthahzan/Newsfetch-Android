package me.mthahzan.anonlk.newsfetch.lib.utils;

/**
 * Created by mthahzan on 1/31/17.
 * Holds the set of constants
 */
public class ConstantHolder {

    /**
     * The Base URL for the Web API
     */
    private final String apiBaseUrl;

    ConstantHolder(String apiBaseUrl) {
        this.apiBaseUrl = apiBaseUrl;
    }

    /**
     * Gets the API Base URL
     * @return API Base URL string
     */
    String getApiBaseUrl() {
        return apiBaseUrl;
    }

    /**
     * Gets the API response date format
     * @return API response date format
     */
    public String getApiDateFormat() {
        return "yyyy-MM-dd HH:mm:ss.SSSZ";
    }
}
