package ntou.cs.lab505.oblivion.activitites.sectionfragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by alan on 5/3/15.
 */
public class AppSectionsPagerAdapter extends FragmentStatePagerAdapter {

    // tabs name vector.
    private String[] SECTIONTABS = {"I/O", "Mode","Frequency Shift", "Band", "Gain"};

    public AppSectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
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
