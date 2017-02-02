package me.mthahzan.anonlk.newsfetch.consumer.main.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.androidnetworking.widget.ANImageView;

import java.util.List;

import me.mthahzan.anonlk.newsfetch.R;
import me.mthahzan.anonlk.newsfetch.lib.models.ITypeModel;

/**
 * Created by mthahzan on 2/2/17.
 * Adapter for the Gridview of consumer main fragments
 */
public class ConsumerMainGridBaseAdapter extends BaseAdapter {

    /**
     * The Activity{@link Context}
     */
    private Context context;

    /**
     * The model collection
     */
    private List<ITypeModel> typeModels;

    public ConsumerMainGridBaseAdapter(@NonNull Context context,
                                       @Nullable List<ITypeModel> typeModels) {
        this.context = context;
        this.typeModels = typeModels;
    }

    /**
     * Sets the {@link List<ITypeModel>}
     * @param typeModels The new data
     */
    public void setTypeModels(List<ITypeModel> typeModels) {
        this.typeModels = typeModels;

        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        int size = 0;

        if (typeModels != null) {
            size = typeModels.size();
        }

        return size;
    }

    @Override
    public ITypeModel getItem(int position) {
        ITypeModel typeModel = null;

        if (typeModels != null) {
            typeModel = typeModels.get(position);
        }

        return typeModel;
    }

    @Override
    public long getItemId(int position) {
        long id = -1;

        if (typeModels != null) {
            id = typeModels.get(position).getId();
        }

        return id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        // Apply view holder pattern
        if (convertView == null) {
            // View is null, inflate one
            convertView = LayoutInflater
                    .from(context)
                    .inflate(R.layout.item_consumer_main_grid, parent, false);

            // Create the static reference class
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.textView);
            viewHolder.bitmap = (ANImageView) convertView.findViewById(R.id.imageview);

            viewHolder.bitmap.setErrorImageResId(R.drawable.no_image_available);
            viewHolder.bitmap.setDefaultImageResId(R.drawable.no_image_available);

            // Attach it to the view element
            convertView.setTag(viewHolder);
        } else {
            // The view is already instantiated. Get the tagged ViewHolder element
            viewHolder = (ViewHolder) convertView.getTag();
        }

        ITypeModel typeModel = getItem(position);

        viewHolder.title.setText(typeModel.getName());
        viewHolder.bitmap.setImageUrl(typeModel.getIconURL());

        return convertView;
    }

    /**
     * Static class to implement the ViewHolder pattern
     */
    private static class ViewHolder {
        TextView title;
        ANImageView bitmap;
    }
}
