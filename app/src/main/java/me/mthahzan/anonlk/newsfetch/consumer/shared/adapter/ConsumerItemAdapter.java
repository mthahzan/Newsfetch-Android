package me.mthahzan.anonlk.newsfetch.consumer.shared.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import me.mthahzan.anonlk.newsfetch.R;
import me.mthahzan.anonlk.newsfetch.lib.models.IItemModel;

/**
 * Created by mthahzan on 2/3/17.
 * Comsumer Item (Post, Commercial) adapter
 */
public class ConsumerItemAdapter extends RecyclerView.Adapter<ConsumerItemAdapter.ViewHolder> {

    /**
     * The model items to display
     */
    @Nullable
    private List<IItemModel> items;

    /**
     * Click listener
     */
    private OnConsumerItemClickListener consumerItemClickListener;

    /**
     * Create a new {@link ConsumerItemAdapter} instance
     * @param items The items to bind
     * @param consumerItemClickListener Item click listener
     */
    public ConsumerItemAdapter(@Nullable List<IItemModel> items,
                               @NonNull OnConsumerItemClickListener consumerItemClickListener) {
        this.items = items;
        this.consumerItemClickListener = consumerItemClickListener;
    }

    /**
     * Sets the item list
     * @param items The collection of items to bind to the adapter
     */
    public void setItems(@Nullable List<IItemModel> items) {
        this.items = items;

        notifyDataSetChanged();
    }

    /**
     * Creates the {@link ViewHolder} element
     * @param parent The parent view
     * @param viewType The view type
     * @return {@link ViewHolder} element
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_consumer_data, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final IItemModel itemModel = getItem(position);

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                consumerItemClickListener.onItemClick(itemModel);
            }
        });
        holder.title.setText(itemModel.getName());
        holder.content.setText(itemModel.getContent());
    }

    @NonNull
    private IItemModel getItem(int position) {
        assert items != null; // getItem should only be called for positions available
        return items.get(position);
    }

    /**
     * Gets the item count
     * @return The item count
     */
    @Override
    public int getItemCount() {
        int count = 0;

        if (items != null) {
            count = items.size();
        }

        return count;
    }

    /**
     * The static class to bind to the {@link RecyclerView} view element
     */
    static class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout layout;
        TextView title;
        TextView content;

        ViewHolder(View itemView) {
            super(itemView);

            layout = (RelativeLayout) itemView.findViewById(R.id.layout);
            title = (TextView) itemView.findViewById(R.id.title);
            content = (TextView) itemView.findViewById(R.id.contentSummary);
        }
    }
}
