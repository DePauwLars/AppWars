
package appwars.appwise.be.appwars.fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Profile;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import java.util.ArrayList;
import java.util.List;

import appwars.appwise.be.appwars.R;
import appwars.appwise.be.appwars.activities.MainActivity;
import butterknife.Bind;


public class SecondAppFragment extends Fragment {
    private String appName;

    private int q1_value;
    private int q2_value;
    private int q3_value;

    @Bind(R.id.go_to_next_fragment) Button go_to_next_fragment;
    @Bind(R.id.app_name) TextView app_name_textview;
    @Bind(R.id.bar_1) DiscreteSeekBar bar_1;
    @Bind(R.id.bar_2) DiscreteSeekBar bar_2;
    @Bind(R.id.bar_3) DiscreteSeekBar bar_3;
    @Bind(R.id.app_icon) ImageView app_icon;

    final static String APPWARS_COLOUR = "#FF1D4D";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.second_app_fragment_layout, container, false);

        final List<ParseObject> votes = new ArrayList<>();
        go_to_next_fragment = (Button) view.findViewById(R.id.go_to_next_fragment);
        appName = ((MainActivity) getActivity()).getAppNameFromList(1);
        app_name_textview.setText(appName);
        app_icon.setImageDrawable(((MainActivity) getActivity()).getAppIconFromList(1));

        getValuesFromBars();
        setBarsColor();


        go_to_next_fragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(q1_value == 0) && !(q2_value == 0) && !(q3_value == 0)) {

                    ((MainActivity) getActivity()).setDataForSecondApp(appName, q1_value, q2_value, q3_value);
                    ((MainActivity) getActivity()).putTextFieldInvisible();
                    ((MainActivity) getActivity()).selectThirdAppFragment(v);
                } else {
                    Toast.makeText(getContext(), "Please fill in  your answers before continuing.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    public static SecondAppFragment newInstance() {
        SecondAppFragment fragmentSecond = new SecondAppFragment();
        Bundle args = new Bundle();
        fragmentSecond.setArguments(args);
        return fragmentSecond;
    }

    public void setBarsColor() {
        bar_1.setTrackColor(Color.parseColor(APPWARS_COLOUR));
        bar_1.setScrubberColor(Color.parseColor(APPWARS_COLOUR));
        bar_1.setThumbColor(Color.parseColor("#FFFFFF"), Color.parseColor(APPWARS_COLOUR));

        bar_2.setTrackColor(Color.parseColor(APPWARS_COLOUR));
        bar_2.setScrubberColor(Color.parseColor(APPWARS_COLOUR));
        bar_2.setThumbColor(Color.parseColor("#FFFFFF"), Color.parseColor(APPWARS_COLOUR));

        bar_3.setTrackColor(Color.parseColor(APPWARS_COLOUR));
        bar_3.setScrubberColor(Color.parseColor(APPWARS_COLOUR));
        bar_3.setThumbColor(Color.parseColor("#FFFFFF"), Color.parseColor(APPWARS_COLOUR));
    }

    public void getValuesFromBars() {
        bar_1.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
                q1_value = value;
            }

            @Override
            public void onStartTrackingTouch(DiscreteSeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(DiscreteSeekBar seekBar) {}
        });

        bar_2.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
                q2_value = value;
            }

            @Override
            public void onStartTrackingTouch(DiscreteSeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(DiscreteSeekBar seekBar) {}
        });

        bar_3.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
                q3_value = value;
            }

            @Override
            public void onStartTrackingTouch(DiscreteSeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(DiscreteSeekBar seekBar) {}
        });
    }
}
