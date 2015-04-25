package ntou.cs.lab505.oblivion.activitites.sectionfragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ntou.cs.lab505.oblivion.R;

/**
 * Created by alan on 4/24/15.
 */
public class GainSectionFragment extends Fragment {

    private int stateFlag;

    /**
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_gain, container, false);
        return rootView;
    }

    /**
     *
     */
    @Override
    public void onPause() {
        super.onPause();


    }

    public void updataFlag(String data) {
        stateFlag = Integer.valueOf(data);
        Log.d("GS", "data update: " + stateFlag);
    }
}