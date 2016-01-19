package appwars.appwise.be.appwars.fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Profile;
import com.nhaarman.supertooltips.ToolTip;
import com.nhaarman.supertooltips.ToolTipRelativeLayout;
import com.nhaarman.supertooltips.ToolTipView;
import com.parse.ParseObject;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import java.util.ArrayList;
import java.util.List;

import appwars.appwise.be.appwars.R;
import appwars.appwise.be.appwars.activities.MainActivity;
import butterknife.Bind;
import butterknife.ButterKnife;


public class FirstAppFragment extends Fragment {
    final static String APPWARS_COLOUR = "#FF1D4D";
    private String appName;

    private int q1_value;
    private int q2_value;
    private int q3_value;

    private ToolTipRelativeLayout slider_tooltip;

    @Bind(R.id.go_to_next_fragment) Button go_to_next_fragment;
    @Bind(R.id.app_name) TextView app_name_textview;
    @Bind(R.id.app_icon) ImageView app_icon;

    @Bind(R.id.bar_1) DiscreteSeekBar bar_1;
    @Bind(R.id.bar_2) DiscreteSeekBar bar_2;
    @Bind(R.id.bar_3) DiscreteSeekBar bar_3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.first_app_fragment_layout, container, false);
        ButterKnife.bind(this, view);

        appName = ((MainActivity) getActivity()).getAppNameFromList(0);
        app_name_textview.setText(appName);
        app_icon.setImageDrawable(((MainActivity) getActivity()).getAppIconFromList(0));
        slider_tooltip = (ToolTipRelativeLayout) view.findViewById(R.id.slider_tooltip);

        ToolTip toolTip = new ToolTip()
                .withText("Use the sliders to set the value")
                .withColor(Color.parseColor(APPWARS_COLOUR))
                .withShadow().withTextColor(Color.WHITE)
                .withAnimationType(ToolTip.AnimationType.FROM_TOP);
        getValuesFromBars();
        setBarsColor();

        go_to_next_fragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(q1_value == 0) && !(q2_value == 0) && !(q3_value == 0)) {

                    ((MainActivity) getActivity()).setDataForFirstApp(appName, q1_value, q2_value, q3_value);
                    ((MainActivity) getActivity()).putTextFieldInvisible();
                    ((MainActivity) getActivity()).selectSecondAppFragment(v);
                } else {
                    Toast.makeText(getContext(), "Please fill in  your answers before continuing.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    public static FirstAppFragment newInstance() {
        FirstAppFragment fragmentFirst = new FirstAppFragment();
        Bundle args = new Bundle();
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    public void setBarsColor() {
        /* Ik wilde material design colours gebruiken maar m'n stagebegeleider
        vroeg of ik overal de main colour van het bedrijf kon gebruiken */

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
            public void onStopTrackingTouch(DiscreteSeekBar seekBar) {
                slider_tooltip.setVisibility(View.INVISIBLE);
            }
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
