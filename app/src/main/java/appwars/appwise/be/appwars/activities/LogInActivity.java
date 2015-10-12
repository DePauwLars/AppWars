package appwars.appwise.be.appwars.activities;


import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.facebook.FacebookSdk;
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

public class LogInActivity extends AppCompatActivity {
    private List<String> permissions;
    private TextView facebookLogInTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_log_in);
        FacebookSdk.sdkInitialize(getApplicationContext());
        facebookLogInTextView = (TextView) findViewById(R.id.facebookLogInTextView);
        permissions = new ArrayList<>();
        addPermissionsToList();
        signInWithFacebook();

    }

    public void signInWithFacebook() {
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
                                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                                    startActivity(intent);
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
    protected void onResume() {
        super.onResume();
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        AppEventsLogger.deactivateApp(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ParseFacebookUtils.onActivityResult(requestCode, resultCode, data);
    }

    public void addPermissionsToList() {

        permissions.add("public_profile");
        permissions.add("email");
        permissions.add("user_status");
        permissions.add("user_friends");
    }
}
