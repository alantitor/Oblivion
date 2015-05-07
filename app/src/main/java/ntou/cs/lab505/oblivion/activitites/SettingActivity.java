package ntou.cs.lab505.oblivion.activitites;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import ntou.cs.lab505.oblivion.R;
import ntou.cs.lab505.oblivion.activitites.sectionfragment.AppSectionsPagerAdapter;
import ntou.cs.lab505.oblivion.activitites.sectionfragment.DefaultModeSectionFragment;
import ntou.cs.lab505.oblivion.parameters.type.ModeType;
import ntou.cs.lab505.oblivion.sqlite.MSAdapter;


public class SettingActivity extends FragmentActivity implements ActionBar.TabListener, DefaultModeSectionFragment.OnDMDataListener{

    AppSectionsPagerAdapter mAppSectionsPagerAdapter;
    ViewPager mViewPager;
    int fragmentModeState = 0;

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
        mViewPager.setOffscreenPageLimit(1);
        mViewPager.setAdapter(mAppSectionsPagerAdapter);  // set adapter.
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

        // get mode type value from db.
        MSAdapter msAdapter = new MSAdapter(getApplicationContext());
        msAdapter.open();
        ModeType modeType = msAdapter.getData();
        msAdapter.close();
        this.fragmentModeState = modeType.getModeType();
    }

    /**
     * Called when a tab enters the selected state.
     * @param tab
     * @param ft
     */
    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        // When the given tab is selected, switch to the corresponding page in the ViewPager.
        if (this.fragmentModeState != 3 && (tab.getPosition() == 3 || tab.getPosition() == 4)) {
            mViewPager.setCurrentItem(-1);
        } else {
            mViewPager.setCurrentItem(tab.getPosition());
        }
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
     * the data transmission interface.
     * @param data
     */
    @Override
    public void OnDMDataPass(String data) {
        this.fragmentModeState = Integer.parseInt(data);
    }
}