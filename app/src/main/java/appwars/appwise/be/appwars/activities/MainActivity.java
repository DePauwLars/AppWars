package appwars.appwise.be.appwars.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import appwars.appwise.be.appwars.R;
import appwars.appwise.be.appwars.fragments.AppListFragment;
import appwars.appwise.be.appwars.fragments.FirstAppFragment;
import appwars.appwise.be.appwars.fragments.LogInWithFacebookFragment;
import appwars.appwise.be.appwars.fragments.SecondAppFragment;
import appwars.appwise.be.appwars.fragments.ThirdAppFragment;

public class MainActivity extends FragmentActivity {
    private String first_name;
    private String last_name;
    private FragmentPagerAdapter adapterViewPager;
    private List<String> appsFromList;
    private List<Drawable> appIcons;
    private ParseUser currentUser;
    private String userObjectId;
    private List<String> permissions;
    private TextView facebookLogInTextView;
    private View view;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        view = getWindow().getDecorView().getRootView();
        textView = (TextView) view.findViewById(R.id.welcome_textview);
        FrameLayout frame_layout = (FrameLayout) findViewById(R.id.frame_layout);
        permissions = new ArrayList<>();
        appsFromList = new ArrayList<>();
        appIcons = new ArrayList<>();
        addPermissionsToList();
        ParseUser user = ParseUser.getCurrentUser();
        if (user == null) {
            putTextFieldInvisible();
            selectFacebookLoginFragment(view);
        } else {
            putTextFieldVisible();
            selectAppListFragment(view);
        }
    }

    public void getFirstAndLastName() {
        Profile profile = Profile.getCurrentProfile();
        first_name = profile.getFirstName();
        last_name = profile.getLastName();
    }

    public void selectAppListFragment(View view) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, AppListFragment.newInstance());
        fragmentTransaction.commit();
    }

    public void selectFacebookLoginFragment(View view) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, LogInWithFacebookFragment.newInstance());
        fragmentTransaction.commit();
    }

    public void selectFirstAppFragment(View view) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, FirstAppFragment.newInstance());
        fragmentTransaction.commit();
    }

    public void selectSecondAppFragment(View view) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, SecondAppFragment.newInstance());
        fragmentTransaction.commit();
    }

    public void selectThirdAppFragment(View view) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, ThirdAppFragment.newInstance());
        fragmentTransaction.commit();
    }

    public void addAppIconToList(Drawable icon) {
        appIcons.add(icon);
    }

    public void removeIconFromList(Drawable icon) {
        appIcons.remove(icon);
    }

    public Drawable getAppIconFromList (int position) {
        return appIcons.get(position);
    }

    public void addAppNameToList(String name) {
        appsFromList.add(name);
    }

    public void removeAppNameFromList(String name) {
        appsFromList.remove(name);
    }

    public String getAppNameFromList(int position) {
        return appsFromList.get(position);
    }

    public String getUserObjectId(View view) {
        return userObjectId;
    }


    public void signInWithFacebook(final View view) {
        LoginManager loginManager = LoginManager.getInstance();
        ParseFacebookUtils.logInWithReadPermissionsInBackground(this, permissions, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException err) {
                if (user == null) {
                    Log.d("Appwars", "Uh oh. The user cancelled the Facebook login.");
                } else if (user.isNew()) {
                    Log.d("Appwars", "User signed up and logged in through Facebook!");
                } else {
                    ParseQuery<ParseObject> query = ParseQuery.getQuery("Vote");
                    query.include("User");
                    query.whereEqualTo("User", ParseUser.getCurrentUser());
                    query.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> list, ParseException e) {
                            if (e == null) {
                                if (list.size() < 3) {
                                    Log.d("Appwars", "User logged in through Facebook!");
                                    selectAppListFragment(view);
                                } else {
                                    Toast.makeText(getBaseContext(), "You've already voted.", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        AppEventsLogger.activateApp(getBaseContext());
    }

    @Override
    public void onPause() {
        super.onPause();
        AppEventsLogger.deactivateApp(getBaseContext());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ParseFacebookUtils.onActivityResult(requestCode, resultCode, data);
    }

    public void addPermissionsToList() {
        permissions.add("public_profile");
        permissions.add("email");
        permissions.add("user_status");
        permissions.add("user_friends");
    }

    public void putTextFieldInvisible() {
        textView.setVisibility(View.GONE);
    }

    public void putTextFieldVisible() {
        textView.setVisibility(View.VISIBLE);
    }


}
