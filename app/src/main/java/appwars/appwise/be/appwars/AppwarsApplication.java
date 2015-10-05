package appwars.appwise.be.appwars;

import android.app.Application;
import android.content.Intent;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.parse.Parse;
import com.parse.ParseFacebookUtils;
import com.parse.ParseInstallation;

/**
 * Created by Lakkedelakke on 5/10/2015.
 */
public class AppwarsApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "EwpRSwAKLtGHsde2mE6jVgdVPU6GhlLsUTW0eoby", "1nzDVOacRcGJaVJgOMYiIt9c0yC9fTJCtga26vQU");
        ParseFacebookUtils.initialize(this);
        FacebookSdk.sdkInitialize(getApplicationContext());
        ParseInstallation.getCurrentInstallation().saveInBackground();
    }

}
