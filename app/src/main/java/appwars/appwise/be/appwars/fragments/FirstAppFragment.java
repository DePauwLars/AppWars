package appwars.appwise.be.appwars.fragments;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import appwars.appwise.be.appwars.R;
/**
 * Created by Lakkedelakke on 6/10/2015.
 */
public class FirstAppFragment extends Fragment {
    private String appName;

    public static FirstAppFragment newInstance(int page, String title) {
        FirstAppFragment fragmentFirst = new FirstAppFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.first_app_fragment_layout, container, false);
        TextView app_name = (TextView) view.findViewById(R.id.app_name);


        return view;
    }

}
