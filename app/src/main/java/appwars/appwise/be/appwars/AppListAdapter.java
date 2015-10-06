package appwars.appwise.be.appwars;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class AppListAdapter extends RecyclerView.Adapter<AppListAdapter.ViewHolder> implements Parcelable {
    private static List<App> mDataset;
    private List<String> chosenApps;
    public static int counter;

    public AppListAdapter(List<App> dataset) {
        this.mDataset = dataset;

    }

    protected AppListAdapter(Parcel in) {
        chosenApps = in.createStringArrayList();
    }

    public static final Creator<AppListAdapter> CREATOR = new Creator<AppListAdapter>() {
        @Override
        public AppListAdapter createFromParcel(Parcel in) {
            return new AppListAdapter(in);
        }

        @Override
        public AppListAdapter[] newArray(int size) {
            return new AppListAdapter[size];
        }
    };

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.app_list_item_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        chosenApps = new ArrayList<>();
        holder.appNameTextView.setText(mDataset.get(position).getName());
        holder.appIconImageView.setImageDrawable(mDataset.get(position).getIcon());
        holder.appCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                {
                    App app = mDataset.get(position);

                    if (isChecked) {
                        counter++;
                        chosenApps.add(app.getName());
                    } else if (!isChecked) {
                        counter--;
                        chosenApps.remove(app.getName());
                    }
                    if (counter >= 4) {
                        Toast.makeText(buttonView.getContext(), "You can only select 3 applications.", Toast.LENGTH_LONG).show();
                        buttonView.setChecked(false);
                        counter--;
                    } else {
                        app.setIsChecked(isChecked);
                    }
                    for (String name : chosenApps) {
                        Log.e("appname: ", name);
                    }
                }
            }
        });
        if (holder.appCheckBox.isChecked()) {
            mDataset.get(position).setIsChecked(true);
        }
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(mDataset);

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final ArrayList<Object> chosenApps;
        public TextView appNameTextView;
        public ImageView appIconImageView;
        public CheckBox appCheckBox;

        public ViewHolder(final View v) {
            super(v);
            appNameTextView = (TextView) v.findViewById(R.id.app_name);
            appIconImageView = (ImageView) v.findViewById(R.id.app_icon);
            appCheckBox = (CheckBox) v.findViewById(R.id.checkbox);
            chosenApps = new ArrayList<>();
            int max_options = 3;

            appCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (appCheckBox.isChecked()) {
                        chosenApps.add(mDataset.get(getPosition()).getName());
                    } else {
                        chosenApps.remove(mDataset.get(getPosition()).getName());
                    }
                }
            });

        }
    }
}



