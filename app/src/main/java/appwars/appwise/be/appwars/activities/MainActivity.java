package appwars.appwise.be.appwars.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
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
import android.widget.TextView;

import com.facebook.Profile;


import java.util.ArrayList;
import java.util.List;

import appwars.appwise.be.appwars.AppListAdapter;
import appwars.appwise.be.appwars.R;
import appwars.appwise.be.appwars.fragments.AppListFragment;
import appwars.appwise.be.appwars.fragments.TestFragment;

public class MainActivity extends FragmentActivity {
    private String first_name;
    private String last_name;
    private FragmentPagerAdapter adapterViewPager;
    private PagerTabStrip pagerHeader;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView welcome_textview = (TextView) findViewById(R.id.welcome_textview);
        pagerHeader = (PagerTabStrip) findViewById(R.id.pager_header);

        getFirstAndLastName();
        welcome_textview.setText("Welcome " + first_name + ", \nplease follow the walkthrough.");
        final ViewPager vpPager = (ViewPager) findViewById(R.id.viewpager);
        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);
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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 3;


        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:

                    return new AppListFragment();
                case 1:
                    return new TestFragment();

                case 2: // Fragment # 1 - This will show SecondFragment
                    return new TestFragment();
                default:
                    return null;
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "Page " + position;
        }
    }

}
