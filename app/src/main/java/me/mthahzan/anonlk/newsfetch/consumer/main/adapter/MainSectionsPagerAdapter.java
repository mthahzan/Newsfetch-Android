package me.mthahzan.anonlk.newsfetch.consumer.main.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import me.mthahzan.anonlk.newsfetch.lib.models.FragmentHolder;

/**
 * Created by mthahzan on 1/28/17.
 * Adapter to use for {@link me.mthahzan.anonlk.newsfetch.consumer.main.MainActivity} {@link android.support.v4.view.ViewPager}
 */

public class MainSectionsPagerAdapter extends FragmentPagerAdapter {

    /**
     * The {@link List<FragmentHolder>}
     */
    private final List<FragmentHolder> fragmentHolders;

    public MainSectionsPagerAdapter(FragmentManager fm, List<FragmentHolder> fragmentHolders) {
        super(fm);

        this.fragmentHolders = fragmentHolders;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentHolders.get(position).getFragment();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentHolders.get(position).getTitle();
    }

    @Override
    public int getCount() {
        int count = 0;

        if (this.fragmentHolders != null) {
            count = fragmentHolders.size();
        }

        return count;
    }
}
