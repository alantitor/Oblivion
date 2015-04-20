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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import ntou.cs.lab505.oblivion.R;

public class SettingActivity extends FragmentActivity implements ActionBar.TabListener {

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

    }

    /**
     * Called when a tab that is already selected is chosen again by the user. Some applications may use this action to return to the top level of a category.
     * @param tab
     * @param ft
     */
    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    public static class AppSectionsPagerAdapter extends FragmentPagerAdapter {

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
                    return new BankCutSectionFragment();
                case 4:
                    // gain setting fragment
                    return new GainSectionFragment();
                default:
                    // dummy fragment
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

    public static class IOSectionFragment extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_io, container, false);
            return rootView;
        }
    }

    public static class DefaultModeSectionFragment extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_defaultmode, container, false);
            return rootView;
        }
    }

    public static class FrequencyShiftSectionFragment extends Fragment implements SeekBar.OnSeekBarChangeListener {
        View rootView;
        TextView fsValue;
        SeekBar seekbar;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            this.rootView = inflater.inflate(R.layout.fragment_freqshift, container, false);
            this.fsValue = (TextView) rootView.findViewById(R.id.fsvalue_fragment_freqshift);
            this.seekbar = (SeekBar) rootView.findViewById(R.id.seekBar_fragment_freqshift);

            //fsValue.setText("12123");  // ??????????????????????????????????????????????????????????????????/

            return rootView;
        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }

    public static class BankCutSectionFragment extends Fragment implements SeekBar.OnSeekBarChangeListener {

        View rootView;
        SeekBar seekBar;
        TextView bankcutNumber;

         @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
             this.rootView = inflater.inflate(R.layout.fragment_bankcut, container, false);
             this.seekBar = (SeekBar) rootView.findViewById(R.id.seekBar_fragment_bankcut);
             this.bankcutNumber = (TextView) rootView.findViewById(R.id.bcnumber_fragment_bankcut);

             return rootView;
        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            bankcutNumber.setText(String.valueOf(progress));  // ??????????????????????????????????????????????????????????????
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            //
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            //
        }
    }

    public static class GainSectionFragment extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_gain, container, false);
            return rootView;
        }
    }

    public static class DummySectionFragment extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_dummy, container, false);
            return rootView;
        }
    }
}
