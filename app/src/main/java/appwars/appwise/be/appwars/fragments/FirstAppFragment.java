package appwars.appwise.be.appwars.fragments;

import android.content.Context;
import android.content.pm.PackageInstaller;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.parse.CountCallback;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import appwars.appwise.be.appwars.R;
import appwars.appwise.be.appwars.activities.MainActivity;
import bolts.Continuation;
import bolts.Task;


public class FirstAppFragment extends Fragment {
    private Button go_to_next_fragment;
    private TextView app_name_textview;
    private EditText app_1_a_1;
    private EditText app_1_a_2;
    private EditText app_1_a_3;
    private String appName;
    private Context context;
    private ImageView app_icon;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.first_app_fragment_layout, container, false);
        final TextInputLayout app_1_a_1_wrapper = (TextInputLayout) view.findViewById(R.id.app_1_a_1_wrapper);
        final TextInputLayout app_1_a_2_wrapper = (TextInputLayout) view.findViewById(R.id.app_1_a_2_wrapper);
        final TextInputLayout app_1_a_3_wrapper = (TextInputLayout) view.findViewById(R.id.app_1_a_3_wrapper);
        app_1_a_1_wrapper.setHint("1.1 Why did you choose this app?");
        app_1_a_2_wrapper.setHint("1.2 What is the best feature about it?");
        app_1_a_3_wrapper.setHint("1.3 What would you like to see improved?");
        app_1_a_1 = (EditText) view.findViewById(R.id.app_1_a_1);
        app_1_a_2 = (EditText) view.findViewById(R.id.app_1_a_2);
        app_1_a_3 = (EditText) view.findViewById(R.id.app_1_a_3);
        final List<ParseObject> votes = new ArrayList<>();
        Profile profile = Profile.getCurrentProfile();
        final String userObjectId = ((MainActivity) getActivity()).getUserObjectId(view);
        final String first_name = profile.getFirstName();
        go_to_next_fragment = (Button) view.findViewById(R.id.go_to_next_fragment);
        TextView app_name_textview = (TextView) view.findViewById(R.id.app_name);
        appName = ((MainActivity) getActivity()).getAppNameFromList(0);
        app_name_textview.setText(appName);
        app_icon = (ImageView) view.findViewById(R.id.app_icon);
        app_icon.setImageDrawable(((MainActivity)getActivity()).getAppIconFromList(0));
        this.context = context;

        go_to_next_fragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(app_1_a_1.getText()) && !TextUtils.isEmpty(app_1_a_2.getText()) && !TextUtils.isEmpty(app_1_a_3.getText())) {

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
                                    voteForUser.put("answer_1", app_1_a_1.getText().toString());
                                    voteForUser.put("answer_2", app_1_a_2.getText().toString());
                                    voteForUser.put("answer_3", app_1_a_3.getText().toString());
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
                    ((MainActivity)getActivity()).putTextFieldInvisible();
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
}
