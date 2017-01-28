package me.mthahzan.anonlk.newsfetch.lib;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import me.mthahzan.anonlk.newsfetch.lib.models.AppSession;

/**
 * Created by mthahzan on 1/28/17.
 * handles shared preferences of the app.
 */
public class PreferenceManager {

    /**
     * The shared preference file name
     */
    private static final String preferenceName = "newsfetcher_pref";

    /**
     * Preference name for app session
     */
    private static final String sessionPreferenceName = "app_session";

    /**
     * Singleton instance
     */
    private static PreferenceManager ourInstance = null;

    /**
     * The shared preferences instance
     */
    private static SharedPreferences sharedPreferences = null;

    /**
     * Get an instance of {@link PreferenceManager}
     * @param context The Application{@link Context}
     * @return Created {@link PreferenceManager} instance
     */
    public static PreferenceManager getInstance(@NonNull Context context) {
        if (ourInstance == null) {
            ourInstance = new PreferenceManager(context);
        }

        return ourInstance;
    }

    /**
     * Private method to create a new class object
     * @param context Application{@link Context}
     */
    private PreferenceManager(Context context) {
        sharedPreferences = context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
    }

    /**
     * Persists the given {@link AppSession} object to {@link SharedPreferences}
     * @param appSession The new {@link AppSession} to be persisted
     */
    public void setSession(AppSession appSession) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(sessionPreferenceName, appSession.serialize());
        editor.apply();
    }

    /**
     * Gets the current {@link AppSession} from {@link SharedPreferences}
     * @return {@link AppSession} instance
     */
    public AppSession getSession() {
        AppSession appSession = null;

        if (sharedPreferences.contains(sessionPreferenceName)) {
            appSession = AppSession
                    .deserialize(sharedPreferences.getString(sessionPreferenceName, null));
        }

        return appSession;
    }
}
