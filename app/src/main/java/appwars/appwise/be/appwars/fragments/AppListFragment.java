package appwars.appwise.be.appwars.fragments;

import android.content.Intent;

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
import android.widget.TextView;

import java.text.Collator;
import java.util.ArrayList;

import appwars.appwise.*;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import appwars.appwise.be.appwars.App;
import appwars.appwise.be.appwars.AppListAdapter;
import appwars.appwise.be.appwars.PackageInformation;
import appwars.appwise.be.appwars.R;

/**
 * Created by Lakkedelakke on 5/10/2015.
 */
public class AppListFragment extends Fragment {
    private String title;
    private int page;
    private RecyclerView appListRecyclerView;
    private RecyclerView.Adapter appListAdapter;
    private RecyclerView.LayoutManager appListLayoutLManager;
    private List<App> apps;

    public static AppListFragment newInstance(int page, String title) {
        AppListFragment fragmentFirst = new AppListFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.app_list_fragment_layout, container, false);
        TextView tvLabel = (TextView) view.findViewById(R.id.tv_label);
        tvLabel.setText("Select 3 of your apps");
        appListRecyclerView = (RecyclerView) view.findViewById(R.id.app_list_recycler_view);

        apps = new ArrayList<>();
        apps = getAllUserInstalledApps();

        appListLayoutLManager = new LinearLayoutManager(getContext());
        appListAdapter = new AppListAdapter(apps);
        appListRecyclerView.setLayoutManager(appListLayoutLManager);
        appListRecyclerView.setAdapter(appListAdapter);

        return view;
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
                App app = new App(appName, appIcon);
                retrievedApps.add(app);
            }
        }
        return retrievedApps;
    }
}