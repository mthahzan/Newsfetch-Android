package me.mthahzan.anonlk.newsfetch.lib.models;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

/**
 * Created by mthahzan on 1/28/17.
 * Base model class for Fragment holders
 */

public class FragmentHolder {

    /**
     * Fragment reference
     */
    private final Fragment fragment;

    /**
     * Fragment title
     */
    private final String title;

    public FragmentHolder(@NonNull Fragment fragment, @NonNull String title) {
        this.fragment = fragment;
        this.title = title;
    }

    /**
     * Gets the {@link Fragment} instance
     * @return {@link Fragment} instance
     */
    public @NonNull Fragment getFragment() {
        return fragment;
    }

    /**
     * Gets the {@link Fragment} title
     * @return Title string
     */
    public @NonNull String getTitle() {
        return title;
    }
}
