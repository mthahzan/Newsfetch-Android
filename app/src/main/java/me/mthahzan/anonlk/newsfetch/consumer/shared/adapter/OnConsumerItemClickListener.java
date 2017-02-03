package me.mthahzan.anonlk.newsfetch.consumer.shared.adapter;

import me.mthahzan.anonlk.newsfetch.lib.models.IItemModel;

/**
 * Created by mthahzan on 2/3/17.
 * Consumer item click listener.
 */
public interface OnConsumerItemClickListener {

    /**
     * Dispatched when an item is clicked
     * @param item The clicked item
     */
    void onItemClick(IItemModel item);
}
