package appwars.appwise.be.appwars.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import appwars.appwise.be.appwars.AppNames;
import appwars.appwise.be.appwars.R;

/**
 * Created by Lakkedelakke on 5/10/2015.
 */
public class TestFragment extends Fragment {
    private String title;
    private int page;
    private TextView selected_apps;

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
        View view = inflater.inflate(R.layout.test_fragment_layout, container, false);
        TextView tvLabel = (TextView) view.findViewById(R.id.tv_label2);
        tvLabel.setText(title);
        selected_apps = (TextView) view.findViewById(R.id.selected_apps);
        Log.e("first app", AppNames.firstAppName);
        Log.e("second app", AppNames.secondAppName);
        Log.e("third app", AppNames.thirdAppName);


        return view;
    }
}
