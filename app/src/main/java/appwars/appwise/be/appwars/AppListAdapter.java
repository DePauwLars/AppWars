package appwars.appwise.be.appwars;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import appwars.appwise.be.appwars.activities.MainActivity;
import appwars.appwise.be.appwars.fragments.AppListFragment;
import appwars.appwise.be.appwars.fragments.FirstAppFragment;
import appwars.appwise.be.appwars.fragments.TestFragment;


public class AppListAdapter extends RecyclerView.Adapter<AppListAdapter.ViewHolder>  {
    private static ArrayList<App> mDataset;
    private ArrayList<String> chosenApps;
    public static int counter;

    public AppListAdapter(ArrayList<App> dataset) {
        this.mDataset = dataset;

    }

    protected AppListAdapter(Parcel in) {
        chosenApps = in.createStringArrayList();
    }


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
        holder.appCheckBox.setTag(String.valueOf(position));

        holder.appCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                          @Override
                                                          public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                              {
                                                                  App app = mDataset.get(position);
                                                                  final Intent intent = new Intent(buttonView.getContext(), FirstAppFragment.class);

                                                                  if (isChecked) {
                                                                      counter++;
                                                                      Counter.count ++;
                                                                      switch (counter) {
                                                                          case 1:
                                                                              intent.putExtra("first_app", app.getName());
                                                                              break;
                                                                          case 2:
                                                                              intent.putExtra("second_app", app.getName());
                                                                              break;
                                                                          case 3:
                                                                              intent.putExtra("third_app", app.getName());
                                                                              break;
                                                                      }

                                                                      chosenApps.add(app.getName());
                                                                  } else if (!isChecked) {
                                                                      counter--;
                                                                      Counter.count --;

                                                                      chosenApps.remove(app.getName());
                                                                  }
                                                                  if (counter >= 4) {
                                                                      Toast.makeText(buttonView.getContext(), "You can only select 3 applications.", Toast.LENGTH_LONG).show();
                                                                      buttonView.setChecked(false);
                                                                      counter--;
                                                                      Counter.count --;

                                                                  } else {
                                                                      app.setIsChecked(isChecked);
                                                                  }
                                                                  for (String name : chosenApps) {
                                                                      Log.i("appname: ", name);
                                                                  }
                                                              }
                                                          }
                                                      });

        Bundle bundle = new Bundle();
        bundle.putStringArrayList("chosen_apps", chosenApps);


        if (holder.appCheckBox.isChecked()) {
            mDataset.get(position).setIsChecked(true);
        }
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
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

    public static final Parcelable.Creator<AppListAdapter> CREATOR = new Parcelable.Creator<AppListAdapter>() {
        public AppListAdapter createFromParcel(Parcel in) {
            return new AppListAdapter(in);
        }

        public AppListAdapter[] newArray(int size) {
            return new AppListAdapter[size];
        }
    };
}



