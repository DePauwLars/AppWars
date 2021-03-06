package appwars.appwise.be.appwars.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import appwars.appwise.be.appwars.R;
import appwars.appwise.be.appwars.fragments.AppListFragment;
import appwars.appwise.be.appwars.fragments.FirstAppFragment;
import appwars.appwise.be.appwars.fragments.LogInWithFacebookFragment;
import appwars.appwise.be.appwars.fragments.SecondAppFragment;
import appwars.appwise.be.appwars.fragments.ThirdAppFragment;
import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends FragmentActivity {
    private boolean listFragIsShowed;
    private FragmentManager fm;

    private String app1;
    private String app2;
    private String app3;

    private int a1_a1;
    private int a2_a1;
    private int a3_a1;

    private int a1_a2;
    private int a2_a2;
    private int a3_a2;

    private int a1_a3;
    private int a2_a3;
    private int a3_a3;

    private List<String> appsFromList;
    private List<Drawable> appIcons;

    private List<String> permissions;

    @Bind(R.id.welcome_textview)
    TextView textView;

    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        view = getWindow().getDecorView().getRootView();
        ButterKnife.bind(this);
        fm = getSupportFragmentManager();
        checkNumberOfVotesForUser();

        textView.setVisibility(View.VISIBLE);
        permissions = new ArrayList<>();
        appsFromList = new ArrayList<>();
        appIcons = new ArrayList<>();
        listFragIsShowed = false;
        ParseUser user = ParseUser.getCurrentUser();

        if (user == null) {
            selectFacebookLoginFragment(view);
        } else {
            selectAppListFragment(view);
            listFragIsShowed = true;
        }
    }

    public void selectAppListFragment(View view) {
        listFragIsShowed = true;
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, AppListFragment.newInstance(), AppListFragment.class.getSimpleName());
        fragmentTransaction.commit();
    }

    public void selectFacebookLoginFragment(View view) {
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, LogInWithFacebookFragment.newInstance());
        fragmentTransaction.commit();
    }

    public void selectFirstAppFragment(View view) {
        listFragIsShowed = false;
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, FirstAppFragment.newInstance());
        fragmentTransaction.addToBackStack(FirstAppFragment.class.getSimpleName());
        fragmentTransaction.commit();
    }

    public void selectSecondAppFragment(View view) {
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, SecondAppFragment.newInstance());
        fragmentTransaction.addToBackStack(SecondAppFragment.class.getSimpleName());
        fragmentTransaction.commit();
    }

    public void selectThirdAppFragment(View view) {
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, ThirdAppFragment.newInstance());
        fragmentTransaction.addToBackStack(ThirdAppFragment.class.getSimpleName());
        fragmentTransaction.commit();
    }


    public void addAppIconToList(Drawable icon) {
        appIcons.add(icon);
    }

    public void removeIconFromList(Drawable icon) {
        appIcons.remove(icon);
    }

    public Drawable getAppIconFromList(int position) {
        return appIcons.get(position);
    }

    public void addAppNameToList(String name) {
        appsFromList.add(name);
    }

    public void initializeAppsFromListToZero() {
        appsFromList = new ArrayList<>();
    }

    public void initializeAppIconsFromListToZero() {
        appIcons = new ArrayList<>();
    }

    public void removeAppNameFromList(String name) {
        appsFromList.remove(name);
    }

    public String getAppNameFromList(int position) {
        return appsFromList.get(position);
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

    public void putTextFieldInvisible() {
        textView.setVisibility(View.GONE);
    }

    public void putTextFieldVisible() {
        textView.setVisibility(View.VISIBLE);
    }

    public void commitAnswersToParse() {

        ParseObject voteForUser1 = ParseObject.create("Vote");
        voteForUser1.put("User", ParseUser.getCurrentUser());
        voteForUser1.put("appname", app1);
        voteForUser1.put("design", a1_a1);
        voteForUser1.put("performance", a1_a2);
        voteForUser1.put("intuitivity", a1_a3);
        voteForUser1.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                Log.i(app1, "data has been saved for " + app1);
            }
        });
        ParseObject voteForUser2 = ParseObject.create("Vote");
        voteForUser2.put("User", ParseUser.getCurrentUser());
        voteForUser2.put("appname", app2);
        voteForUser2.put("design", a2_a1);
        voteForUser2.put("performance", a2_a2);
        voteForUser2.put("intuitivity", a2_a3);
        voteForUser2.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                Log.i(app2, "data has been saved for " + app2);
            }
        });
        ParseObject voteForUser3 = ParseObject.create("Vote");
        voteForUser3.put("User", ParseUser.getCurrentUser());
        voteForUser3.put("appname", app3);
        voteForUser3.put("design", a3_a1);
        voteForUser3.put("performance", a3_a2);
        voteForUser3.put("intuitivity", a3_a3);
        voteForUser3.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                Log.i(app3, "data has been saved for " + app3);
            }
        });
    }

    public void checkNumberOfVotesForUser() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Vote");
        query.include("User");
        query.whereEqualTo("User", ParseUser.getCurrentUser());
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(final List<ParseObject> list, ParseException e) {
                if (e == null) {
                    if (list.size() > 0) {
                        Intent intent = new Intent(getBaseContext(), EndActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    } else {
                    }

                } else {
                    e.printStackTrace();
                }
            }
        });
    }

    public void setDataForFirstApp(String appname, int answer1, int answer2, int answer3) {
        app1 = appname;
        a1_a1 = answer1;
        a1_a2 = answer2;
        a1_a3 = answer3;
    }

    public void setDataForSecondApp(String appname, int answer1, int answer2, int answer3) {
        app2 = appname;
        a2_a1 = answer1;
        a2_a2 = answer2;
        a2_a3 = answer3;
    }

    public void setDataForThirdApp(String appname, int answer1, int answer2, int answer3) {
        app3 = appname;
        a3_a1 = answer1;
        a3_a2 = answer2;
        a3_a3 = answer3;
    }
}
