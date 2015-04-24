package ntou.cs.lab505.oblivion.activitites.sectionfragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import ntou.cs.lab505.oblivion.R;

/**
 * Created by alan on 4/24/15.
 */

public class FrequencyShiftSectionFragment extends Fragment implements SeekBar.OnSeekBarChangeListener {
    // objects
    private View rootView;
    private TextView fsValue;
    private SeekBar seekbar;
    // parameters
    public int seekBarValue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.rootView = inflater.inflate(R.layout.fragment_freqshift, container, false);
        this.fsValue = (TextView) rootView.findViewById(R.id.fsvalue_fragment_freqshift);
        this.seekbar = (SeekBar) rootView.findViewById(R.id.seekBar_fragment_freqshift);
        this.seekBarValue = 0;

        // add listener.
        seekbar.setOnSeekBarChangeListener(this);

        return rootView;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        seekBarValue = progress - 24;
        fsValue.setText(String.valueOf(seekBarValue));
        //Log.d("SettingActivity", "FrequencyShiftSectionFragment, " + "seekbar onProgressChanged:  " + seekValue);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        //
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        //
    }

    OnFSDataListener mCallback;

    public interface OnFSDataListener {
        public void OnFSDataPass(String data);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mCallback = (OnFSDataListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFSDataPass");
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        String data = seekBarValue + "";
        mCallback.OnFSDataPass(data);
    }
}