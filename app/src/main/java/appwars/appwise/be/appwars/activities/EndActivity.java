package appwars.appwise.be.appwars.activities;

import android.app.Application;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import com.facebook.login.LoginManager;

import appwars.appwise.be.appwars.R;
import appwars.appwise.be.appwars.fragments.ThirdAppFragment;

public class EndActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();

    }

    public void logOut(View view) {
        LoginManager.getInstance().logOut();
    }

}
