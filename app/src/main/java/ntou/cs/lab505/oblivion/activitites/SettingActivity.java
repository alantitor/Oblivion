package ntou.cs.lab505.oblivion.activitites;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import ntou.cs.lab505.oblivion.R;
import ntou.cs.lab505.oblivion.activitites.sectionfragment.*;


public class SettingActivity extends FragmentActivity implements ActionBar.TabListener,
                                                                GainSectionFragment.OnGainDataListener,
                                                                IOSectionFragment.OnIODataListener,
                                                                DefaultModeSectionFragment.OnDMDataListener,
                                                                FrequencyShiftSectionFragment.OnFSDataListener,
                                                                BandCutSectionFragment.OnBCDataListener {

    AppSectionsPagerAdapter mAppSectionsPagerAdapter;
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        mAppSectionsPagerAdapter = new AppSectionsPagerAdapter(getSupportFragmentManager());
        final ActionBar actionBar = getActionBar();

        actionBar.setHomeButtonEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));

        mViewPager = (ViewPager) findViewById(R.id.pager_activity_setting);
        mViewPager.setAdapter(mAppSectionsPagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // When swiping between different app sections, select the corresponding tab.
                // We can also use ActionBar.Tab#select() to do this if we have a reference to the
                // Tab.
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mAppSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by the adapter.
            // Also specify this Activity object, which implements the TabListener interface, as the
            // listener for when this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                             .setText(mAppSectionsPagerAdapter.getPageTitle(i))
                             .setTabListener(this));
        }


    }

    /**
     * Called when a tab enters the selected state.
     * @param tab
     * @param ft
     */
    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        // When the given tab is selected, switch to the corresponding page in the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    /**
     * Called when a tab exits the selected state.
     * @param tab
     * @param ft
     */
    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
        //
    }

    /**
     * Called when a tab that is already selected is chosen again by the user. Some applications may use this action to return to the top level of a category.
     * @param tab
     * @param ft
     */
    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
        //
    }

    /**
     *
     * @param data
     */
    @Override
    public void OnGainDataPass(String data) {
       //Log.d("OngainDataPass", data);
    }

    @Override
    public void OnIODataPass(String data) {
        //Log.d("OnIODataPass", data);
    }

    @Override
    public void OnDMDataPass(String data) {
        //Log.d("OnDMDataPass", data);
    }

    @Override
    public void OnFSDataPass(String data) {
        //Log.d("OnFSDataPass", data);
    }

    @Override
    public void OnBCDataPass(String data) {
        //Log.d("OnBCDataPass", data + "12");
    }

    public static class AppSectionsPagerAdapter extends FragmentPagerAdapter {

        // tabs name string.
        private String[] SECTIONTABS = {"I/O", "Default Mode","Frequency Shift", "Bane", "Gain"};

        public AppSectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * A {@link FragmentPagerAdapter} that returns a fragment corresponding to one of the primary
         * sections of the app.
         */
        @Override
        public Fragment getItem(int i) {

            switch (i) {
                case 0:
                    // I/O setting fragment
                    return new IOSectionFragment();
                case 1:
                    // model setting fragment
                    return new DefaultModeSectionFragment();
                case 2:
                    // frequency shift setting fragment
                    return new FrequencyShiftSectionFragment();
                case 3:
                    // bank cut setting fragment
                    return new BandCutSectionFragment();
                case 4:
                    // gain setting fragment
                    //return new GainSectionFragment();
                    return new GainSectionFragment();
                default:
                    // dummy fragment
                    //return new DummySectionFragment();
                    return new DummySectionFragment();
            }
        }

        @Override
        public int getCount() {
            return SECTIONTABS.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return SECTIONTABS[position];
        }
    }
}

/**
 * Reference:
 *
 * Passing data between a fragment and its container activity:
 *      http://stackoverflow.com/questions/9343241/passing-data-between-a-fragment-and-its-container-activity
 *
 * http://developer.android.com/training/basics/fragments/communicating.html
 * http://developer.android.com/guide/components/fragments.html#CommunicatingWithActivity
 */
