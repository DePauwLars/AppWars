package appwars.appwise.be.appwars.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Profile;


import java.util.ArrayList;
import java.util.List;

import appwars.appwise.be.appwars.AppListAdapter;
import appwars.appwise.be.appwars.Counter;
import appwars.appwise.be.appwars.R;
import appwars.appwise.be.appwars.fragments.AppListFragment;
import appwars.appwise.be.appwars.fragments.FirstAppFragment;
import appwars.appwise.be.appwars.fragments.TestFragment;

public class MainActivity extends FragmentActivity {
    private String first_name;
    private String last_name;
    private FragmentPagerAdapter adapterViewPager;
    private FrameLayout frameLayout;
    private Button button_start;
    private Button go_to_next_frag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView welcome_textview = (TextView) findViewById(R.id.welcome_textview);
        frameLayout = (FrameLayout) findViewById(R.id.fragment_place);
        getFirstAndLastName();
        go_to_next_frag = (Button) findViewById(R.id.go_to_next_frag);
        go_to_next_frag.setVisibility(View.GONE);
        button_start = (Button) findViewById(R.id.button_start);
        welcome_textview.setText("Welcome " + first_name + ", \nplease follow the walkthrough.");

        go_to_next_frag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Counter.count == 3) {
                    selectFragment(v);

                } else {
                    Toast.makeText(getBaseContext(), "Select 3 apps to continue.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

        public void selectFragment(View view) {
        Fragment fragment;
        if (view == button_start) {
            fragment = new AppListFragment();
        } else {
            fragment = new TestFragment();
        }
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_place, fragment);
        fragmentTransaction.commit();
        button_start.setVisibility(View.GONE);
        go_to_next_frag.setVisibility(View.VISIBLE);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void getFirstAndLastName() {
        Profile profile = Profile.getCurrentProfile();
        first_name = profile.getFirstName();
        last_name = profile.getLastName();
    }


}
