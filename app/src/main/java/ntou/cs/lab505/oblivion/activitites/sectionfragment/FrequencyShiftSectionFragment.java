package ntou.cs.lab505.oblivion.activitites.sectionfragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import ntou.cs.lab505.oblivion.R;
import ntou.cs.lab505.oblivion.sqlite.FSAdapter;

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
        this.seekbar.setOnSeekBarChangeListener(this);

        String data;
        FSAdapter fsAdapter = new FSAdapter(this.getActivity().getApplicationContext());
        fsAdapter.open();
        data = fsAdapter.getData();
        fsAdapter.close();

        // load data.
        if (data.length() != 0) {
            this.seekBarValue = Integer.parseInt(data.split(":")[1]);
            this.seekbar.setProgress(this.seekBarValue + 24);
        }

        return rootView;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        this.seekBarValue = progress - 24;
        fsValue.setText(String.valueOf(this.seekBarValue));
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

    @Override
    public void onPause() {
        super.onPause();

        FSAdapter fsAdapter = new FSAdapter(this.getActivity().getApplicationContext());
        fsAdapter.open();
        fsAdapter.saveData(seekBarValue);
        fsAdapter.close();
    }
}