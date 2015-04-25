package ntou.cs.lab505.oblivion.activitites.sectionfragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

import ntou.cs.lab505.oblivion.R;

/**
 * Created by alan on 4/24/15.
 */

public class BandCutSectionFragment extends Fragment implements SeekBar.OnSeekBarChangeListener, DefaultModeSectionFragment.OnDMDataListener {

    private int stateFlag = 0;

    private View rootView;
    private SeekBar seekBar;
    private TextView bankcutNumber;
    int seekBarValue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.rootView = inflater.inflate(R.layout.fragment_bandcut, container, false);
        this.bankcutNumber = (TextView) rootView.findViewById(R.id.bcnumber_fragment_bankcut);
        this.seekBar = (SeekBar) rootView.findViewById(R.id.seekBar_fragment_bankcut);
        // Add listener.  This step is very important.
        seekBar.setOnSeekBarChangeListener(this);

        return rootView;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        seekBarValue = progress;
        bankcutNumber.setText(String.valueOf(seekBarValue));

        // draw view
        LayoutInflater layoutInflater = (LayoutInflater) getActivity().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout border = (LinearLayout) rootView.findViewById(R.id.draw_fragment_bankcut);
        ArrayList<View> views = new ArrayList<View>(progress);
        // clear views
        border.removeAllViews();
        // create view object
        for (int count = 0; count < progress; count++) {
            View view = layoutInflater.inflate(R.layout.view_bandcut, null);
            TextView viewLabel = (TextView) view.findViewById(R.id.label_view_bandcut);
            //viewLabel.setText(String.valueOf("頻帶" + ( count + 1)));
            viewLabel.append(String.valueOf(count + 1));
            views.add(view);
        }
        // add view to layout
        for (int count = 0; count < views.size(); count++) {
            border.addView(views.get(count));
        }
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
    public void onStart() {
        super.onStart();

        // load data.
    }

    public void updataFlag(String data) {
        stateFlag = Integer.valueOf(data);
        Log.d("BC", "data update: " + stateFlag);
    }

    @Override
    public void onPause() {
        super.onPause();

        // save data.
    }

    @Override
    public void OnDMDataPass(String data) {

    }
}
