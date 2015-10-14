package appwars.appwise.be.appwars.fragments;

import android.content.Context;
import android.content.Intent;
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
import appwars.appwise.be.appwars.activities.EndActivity;
import appwars.appwise.be.appwars.activities.MainActivity;


public class ThirdAppFragment extends Fragment {
    private Button go_to_next_fragment;
    private TextView app_name_textview;
    private EditText app_1_a_1;
    private EditText app_1_a_2;
    private EditText app_1_a_3;
    private String appName;
    private Context context;
    private ImageView app_icon;
    private DiscreteSeekBar bar_1;
    private DiscreteSeekBar bar_2;
    private DiscreteSeekBar bar_3;
    private TextView instruction_text;
    private TextView q1;
    private TextView q2;
    private TextView q3;
    private int q1_value;
    private int q2_value;
    private int q3_value;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.second_app_fragment_layout, container, false);

        final List<ParseObject> votes = new ArrayList<>();
        Profile profile = Profile.getCurrentProfile();
        final String userObjectId = ((MainActivity) getActivity()).getUserObjectId(view);
        final String first_name = profile.getFirstName();
        go_to_next_fragment = (Button) view.findViewById(R.id.go_to_next_fragment);
        TextView app_name_textview = (TextView) view.findViewById(R.id.app_name);
        appName = ((MainActivity) getActivity()).getAppNameFromList(2);
        app_name_textview.setText(appName);
        app_icon = (ImageView) view.findViewById(R.id.app_icon);
        app_icon.setImageDrawable(((MainActivity) getActivity()).getAppIconFromList(2));
        bar_1 = (DiscreteSeekBar) view.findViewById(R.id.bar_1);
        bar_2 = (DiscreteSeekBar) view.findViewById(R.id.bar_2);
        bar_3 = (DiscreteSeekBar) view.findViewById(R.id.bar_3);
//        instruction_text = (TextView) view.findViewById(R.id.instruction_text);
        q1 = (TextView) view.findViewById(R.id.q1);
        q2 = (TextView) view.findViewById(R.id.q2);
        q3 = (TextView) view.findViewById(R.id.q3);
        getValuesFromBars();
        setBarsColor();
        this.context = context;


        go_to_next_fragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(q1_value == 0) && !(q2_value == 0) && !(q3_value == 0)) {

                    ParseQuery<ParseObject> query = ParseQuery.getQuery("Vote");
                    query.include("User");
                    query.whereEqualTo("User", ParseUser.getCurrentUser());
                    query.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(final List<ParseObject> list, ParseException e) {
                            if (e == null) {
                                if (list.size() < 3) {
                                    ParseObject voteForUser = ParseObject.create("Vote");
                                    voteForUser.put("User", ParseUser.getCurrentUser());
                                    voteForUser.put("appname", appName);
                                    voteForUser.put("design", q1_value);
                                    voteForUser.put("performance", q2_value);
                                    voteForUser.put("intuitivity", q3_value);
                                    voteForUser.saveInBackground(new SaveCallback() {
                                        @Override
                                        public void done(ParseException e) {
                                            Log.i(appName, "data has been saved for " + appName);
                                            int objectsInParse = list.size() + 1;
                                            Log.i("amount of apps saved to parse", objectsInParse + "");
                                        }
                                    });
                                }
                            } else {
                                e.printStackTrace();
                            }
                        }
                    });
                    ((MainActivity) getActivity()).putTextFieldInvisible();
                    Intent intent = new Intent(getContext(), EndActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getContext(), "Please fill in  your answers before continuing.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    public static ThirdAppFragment newInstance() {
        ThirdAppFragment fragmentThird = new ThirdAppFragment();
        Bundle args = new Bundle();
        fragmentThird.setArguments(args);
        return fragmentThird;
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

