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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Profile;
import com.nhaarman.supertooltips.ToolTip;
import com.nhaarman.supertooltips.ToolTipRelativeLayout;
import com.nhaarman.supertooltips.ToolTipView;
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


public class FirstAppFragment extends Fragment {
    private Button go_to_next_fragment;
    private TextView app_name_textview;
    private String appName;
    private Context context;
    private ImageView app_icon;
    private DiscreteSeekBar bar_1;
    private DiscreteSeekBar bar_2;
    private DiscreteSeekBar bar_3;
    private TextView q1;
    private TextView q2;
    private TextView q3;
    private int q1_value;
    private int q2_value;
    private int q3_value;
    private LinearLayout root_layout;
    private ToolTipRelativeLayout slider_tooltip;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.first_app_fragment_layout, container, false);

        final List<ParseObject> votes = new ArrayList<>();
        Profile profile = Profile.getCurrentProfile();
        final String userObjectId = ((MainActivity) getActivity()).getUserObjectId(view);
        final String first_name = profile.getFirstName();
        go_to_next_fragment = (Button) view.findViewById(R.id.go_to_next_fragment);
        TextView app_name_textview = (TextView) view.findViewById(R.id.app_name);
        appName = ((MainActivity) getActivity()).getAppNameFromList(0);
        app_name_textview.setText(appName);
        app_icon = (ImageView) view.findViewById(R.id.app_icon);
        app_icon.setImageDrawable(((MainActivity) getActivity()).getAppIconFromList(0));
        bar_1 = (DiscreteSeekBar) view.findViewById(R.id.bar_1);
        bar_2 = (DiscreteSeekBar) view.findViewById(R.id.bar_2);
        bar_3 = (DiscreteSeekBar) view.findViewById(R.id.bar_3);
        root_layout = (LinearLayout) view.findViewById(R.id.root_layout);
        q1 = (TextView) view.findViewById(R.id.q1);
        q2 = (TextView) view.findViewById(R.id.q2);
        q3 = (TextView) view.findViewById(R.id.q3);

        slider_tooltip = (ToolTipRelativeLayout) view.findViewById(R.id.slider_tooltip);

        ToolTip toolTip = new ToolTip()
                .withText("Use the sliders to set the value")
                .withColor(Color.parseColor("#FF1D4D"))
                .withShadow().withTextColor(Color.WHITE)
                .withAnimationType(ToolTip.AnimationType.FROM_TOP);
        ToolTipView toolTipView = slider_tooltip.showToolTipForView(toolTip, view.findViewById(R.id.q1));
        getValuesFromBars();
        setBarsColor();
        this.context = context;

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
        bar_1.setTrackColor(Color.parseColor("#FF1D4D"));
        bar_1.setScrubberColor(Color.parseColor("#FF1D4D"));
        bar_1.setThumbColor(Color.parseColor("#FFFFFF"), Color.parseColor("#FF1D4D"));


        bar_2.setTrackColor(Color.parseColor("#FF1D4D"));
        bar_2.setScrubberColor(Color.parseColor("#FF1D4D"));
        bar_2.setThumbColor(Color.parseColor("#FFFFFF"), Color.parseColor("#FF1D4D"));

        bar_3.setTrackColor(Color.parseColor("#FF1D4D"));
        bar_3.setScrubberColor(Color.parseColor("#FF1D4D"));
        bar_3.setThumbColor(Color.parseColor("#FFFFFF"), Color.parseColor("#FF1D4D"));
    }

    public void getValuesFromBars() {
        bar_1.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
                q1_value = value;
                slider_tooltip.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onStartTrackingTouch(DiscreteSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(DiscreteSeekBar seekBar) {

            }
        });
        bar_2.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
                q2_value = value;
            }

            @Override
            public void onStartTrackingTouch(DiscreteSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(DiscreteSeekBar seekBar) {

            }
        });

        bar_3.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
                q3_value = value;
            }

            @Override
            public void onStartTrackingTouch(DiscreteSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(DiscreteSeekBar seekBar) {

            }
        });
    }
}
