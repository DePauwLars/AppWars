package appwars.appwise.be.appwars.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import appwars.appwise.be.appwars.AppListAdapter;
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
    private List<String> apps;

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
        addAppsToList();
        appListLayoutLManager = new LinearLayoutManager(getContext());
        appListAdapter = new AppListAdapter(apps);
        appListRecyclerView.setLayoutManager(appListLayoutLManager);
        appListRecyclerView.setAdapter(appListAdapter);

        return view;
    }

    public void addAppsToList() {
        apps.add("blabla");
        apps.add("dit en dat");
        apps.add("test een twee drie");
        apps.add("appnaam dit en dat");
        apps.add("Rollercoaster tycoon");
        apps.add("een of andere bankapp");
        apps.add("google maps");
        apps.add(" youtube app");
        apps.add("De lijn");
        apps.add("online brood smeren");
        apps.add("remote control app");
        apps.add("just-eat");
        apps.add("grasgroeier 3000");
        apps.add("what to wear today   ");

    }
}
