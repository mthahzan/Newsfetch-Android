package me.mthahzan.anonlk.newsfetch.consumer.main.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.mthahzan.anonlk.newsfetch.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CommercialsFragment extends Fragment {


    public CommercialsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_commercials, container, false);
    }

}
