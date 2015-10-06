package appwars.appwise.be.appwars;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import appwars.appwise.be.appwars.fragments.AppListFragment;

public class AppListAdapter extends RecyclerView.Adapter<AppListAdapter.ViewHolder> {
    private static List<App> mDataset;

    public AppListAdapter(List <App> dataset) {
        this.mDataset = dataset;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.app_list_item_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.appNameTextView.setText(mDataset.get(position).getName());
        holder.appIconImageView.setImageDrawable(mDataset.get(position).getIcon());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView appNameTextView;
        public ImageView appIconImageView;


        public ViewHolder(View v) {
            super(v);
            appNameTextView = (TextView) v.findViewById(R.id.app_name);
            appIconImageView = (ImageView) v.findViewById(R.id.app_icon);
        }

    }

}