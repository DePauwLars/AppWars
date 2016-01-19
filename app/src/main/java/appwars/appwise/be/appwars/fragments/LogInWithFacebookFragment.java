package appwars.appwise.be.appwars.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import appwars.appwise.be.appwars.R;
import appwars.appwise.be.appwars.activities.LogInActivity;
import appwars.appwise.be.appwars.activities.MainActivity;

public class LogInWithFacebookFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.log_in_with_facebook_fragment, container, false);
        ((MainActivity)getActivity()).putTextFieldInvisible();
        ImageView facebook_login_button = (ImageView) view.findViewById(R.id.facebook_login_button);
        facebook_login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((LogInActivity)getActivity()).signInWithFacebook();
            }
        });

    return view;
    }

    public static LogInWithFacebookFragment newInstance() {
        LogInWithFacebookFragment facebookLogInFragment = new LogInWithFacebookFragment();
        Bundle args = new Bundle();
        facebookLogInFragment.setArguments(args);
        return facebookLogInFragment;
    }
}