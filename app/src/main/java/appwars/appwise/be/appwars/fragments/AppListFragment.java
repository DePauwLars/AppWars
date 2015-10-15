package appwars.appwise.be.appwars.fragments;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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

import appwars.appwise.be.appwars.App;
import appwars.appwise.be.appwars.activities.MainActivity;
import appwars.appwise.be.appwars.utils.PackageInformation;
import appwars.appwise.be.appwars.R;

public class AppListFragment extends Fragment {
    private String title;
    private int page;
    private RecyclerView appListRecyclerView;
    private AppListAdapter appListAdapter;
    private RecyclerView.LayoutManager appListLayoutLManager;
    private List<App> apps;
    private Button go_to_next_fragment;


    public static AppListFragment newInstance() {
        AppListFragment fragmentFirst = new AppListFragment();
        Bundle args = new Bundle();
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MainActivity) getActivity()).initializeAppsFromListToZero();
        ((MainActivity) getActivity()).putTextFieldVisible();
        ((MainActivity) getActivity()).initializeAppIconsFromListToZero();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.app_list_fragment_layout, container, false);
        appListRecyclerView = (RecyclerView) view.findViewById(R.id.app_list_recycler_view);
        go_to_next_fragment = (Button) view.findViewById(R.id.go_to_next_fragment);
        ((MainActivity) getActivity()).initializeAppIconsFromListToZero();
        ((MainActivity) getActivity()).putTextFieldVisible();
        go_to_next_fragment.setVisibility(View.GONE);
        go_to_next_fragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).putTextFieldInvisible();
                ((MainActivity) getActivity()).selectFirstAppFragment(v);
            }
        });

        apps = new ArrayList<>();

        apps = getAllUserInstalledApps();

        appListLayoutLManager = new LinearLayoutManager(getContext());
        appListAdapter = new AppListAdapter(apps, getContext());
        appListRecyclerView.setLayoutManager(appListLayoutLManager);
        appListRecyclerView.setAdapter(appListAdapter);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        apps = new ArrayList<>();
    }

    public List<App> getAllUserInstalledApps() {
        PackageInformation pi = new PackageInformation(getContext());
        pi.getInstalledApps(true);
        List<App> retrievedApps = new ArrayList<>();

        PackageInformation androidPackagesInfo = new PackageInformation(getContext());
        ArrayList<PackageInformation.InfoObject> appsData = androidPackagesInfo.getInstalledApps(true);


        int flags = PackageManager.GET_META_DATA |
                PackageManager.GET_SHARED_LIBRARY_FILES |
                PackageManager.GET_UNINSTALLED_PACKAGES;

        PackageManager pm = getActivity().getPackageManager();
        List<ApplicationInfo> applications = pm.getInstalledApplications(flags);

        for (ApplicationInfo appInfo : applications) {
            if ((appInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 1) {
            } else {

                String appName = (String) pm.getApplicationLabel(appInfo);
                Drawable appIcon = appInfo.loadIcon(pm);
                if (!appName.equals("") && (!TextUtils.isEmpty(appName))) {
                    App app = new App(appName, appIcon, false);
                    retrievedApps.add(app);
                }
            }
        }
        return retrievedApps;
    }

    public void setButtonVisible(View view) {
        go_to_next_fragment.setVisibility(View.VISIBLE);
    }


    public class AppListAdapter extends RecyclerView.Adapter<AppListAdapter.ViewHolder> {
        private List<App> mDataset;
        private List<String> chosenApps;
        private AppListFragment fragment;
        private Context context;

        public AppListAdapter(List<App> dataset, Context context) {
            this.mDataset = dataset;
            this.context = context;
            chosenApps = new ArrayList<>();
            ((MainActivity) getActivity()).initializeAppsFromListToZero();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.app_list_item_layout, parent, false);
            ViewHolder viewHolder = new ViewHolder(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            final App app = mDataset.get(position);
            holder.appNameTextView.setText(mDataset.get(position).getName());
            holder.appIconImageView.setImageDrawable(mDataset.get(position).getIcon());
            holder.appCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (chosenApps.size() == 3 && !chosenApps.contains(mDataset.get(position).getName())) {
                        Toast.makeText(context, "You can only select 3 applications.", Toast.LENGTH_SHORT).show();
                        holder.appCheckBox.setChecked(false);
                        return;
                    }

                    if (!chosenApps.contains(app.getName())) {
                        ((MainActivity) getActivity()).addAppNameToList(app.getName());
                        ((MainActivity) getActivity()).addAppIconToList(app.getIcon());
                        chosenApps.add(app.getName());


                    } else {
                        ((MainActivity) getActivity()).removeAppNameFromList(app.getName());
                        ((MainActivity) getActivity()).removeIconFromList(app.getIcon());
                        chosenApps.remove(app.getName());

                    }
                    if (chosenApps.size() < 3) {
                        go_to_next_fragment.setVisibility(View.GONE);
                    } else {
                        go_to_next_fragment.setVisibility(View.VISIBLE);
                    }

                    for (String name : chosenApps) {
                        Log.i("name", name);
                    }
                }
            });
            if (chosenApps.contains(mDataset.get(position).getName())) {
                holder.appCheckBox.setChecked(true);
            } else {
                holder.appCheckBox.setChecked(false);
            }
        }

        public void setOnItemListener(AppListFragment fragment) {
            this.fragment = fragment;
        }

        @Override
        public int getItemCount() {
            return mDataset.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            public TextView appNameTextView;
            public ImageView appIconImageView;
            public CheckBox appCheckBox;

            public ViewHolder(final View v) {
                super(v);
                appNameTextView = (TextView) v.findViewById(R.id.app_name);
                appIconImageView = (ImageView) v.findViewById(R.id.app_icon);
                appCheckBox = (CheckBox) v.findViewById(R.id.checkbox);

            }
        }
    }
}