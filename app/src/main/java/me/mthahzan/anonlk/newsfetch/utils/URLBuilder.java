package me.mthahzan.anonlk.newsfetch.utils;

import java.util.HashMap;

/**
 * Created by mthahzan on 1/28/17.
 * Utility class to help build URLs
 */
public class URLBuilder {

    /**
     * The URL for authentication
     * @return The URL for authentication
     */
    public static String authURL() {
        return Constants.API_BASE_URL + "/auth";
    }

    /**
     * Get the URL of fetching a single model object
     * @param modelName The model name to fetch (Eg: "user", "post", etc.)
     * @param modelId The ID of the model
     * @return The URL to use for network requests
     */
    public static String modelURL(String modelName, int modelId) {
        // Add an "s" to the end to pluralize the model name for API access
        String pluralizedModelName = modelName + "s";

        // Create the URL segment name
        String segmentName = modelName + "Id";

        // Create the URL segment map
        HashMap<String, String> urlSegmentMap = new HashMap<>();
        urlSegmentMap.put(segmentName, String.valueOf(modelId));

        // Create the raw URL with url segments
        String url = Constants.API_BASE_URL + "/" + pluralizedModelName + "/:" + segmentName;

        return getMappedURL(url, urlSegmentMap);
    }

    /**
     * Get the URL to fetch all model data
     * @param modelName The model name to fetch
     * @return The URL to use for network requests
     */
    public static String modelURL(String modelName) {
        // Add an "s" to the end to pluralize the model name for API access
        String pluralizedModelName = modelName + "s";

        return Constants.API_BASE_URL + "/" + pluralizedModelName;
    }

    /**
     * Maps the URL segments with corresponding values
     * @param rawURL The raw URL with the segment IDs (Eg: /users/:userId)
     * @param segmentMap The map to use when mapping
     * @return The mapped String (Eg: /users/:userId, userId: 12 => /users/12)
     */
    private static String getMappedURL(String rawURL, HashMap<String, String> segmentMap) {
        String url = rawURL;

        for (String key : segmentMap.keySet()) {
            url = url.replace(":" + key, segmentMap.get(key));
        }

        return url;
    }
}
