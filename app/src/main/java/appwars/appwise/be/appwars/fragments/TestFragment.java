package appwars.appwise.be.appwars.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import appwars.appwise.be.appwars.R;

/**
 * Created by Lakkedelakke on 5/10/2015.
 */
public class TestFragment extends Fragment {
    private String title;
    private int page;

    public static AppListFragment newInstance(int page, String title) {
        AppListFragment fragmentFirst = new AppListFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.test_fragment_layout, container, false);
        TextView tvLabel = (TextView) view.findViewById(R.id.tv_label2);
        tvLabel.setText(title);
        return view;
    }
}
