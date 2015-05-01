package ntou.cs.lab505.oblivion.activitites.sectionfragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

import ntou.cs.lab505.oblivion.Parameters.type.BandCut;
import ntou.cs.lab505.oblivion.R;
import ntou.cs.lab505.oblivion.sqlite.BSAdapter;

/**
 * Created by alan on 4/24/15.
 */

public class BandCutSectionFragment extends Fragment implements SeekBar.OnSeekBarChangeListener {

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

        // load data.
        BSAdapter bsAdapter = new BSAdapter(this.getActivity().getApplicationContext());
        bsAdapter.open();
        ArrayList<BandCut> list = bsAdapter.getData();
        this.seekBarValue = list.size();
        bsAdapter.close();

        // set seekbar progress.
        seekBar.setProgress(list.size());

        // add view.
        LayoutInflater layoutInflater = (LayoutInflater) getActivity().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout border = (LinearLayout) rootView.findViewById(R.id.draw_fragment_bankcut);
        //ArrayList<View> views = new ArrayList<View>(this.seekBarValue);
        border.removeAllViews();

        for (int count = 0; count < this.seekBarValue; count++) {
            View view = layoutInflater.inflate(R.layout.view_bandcut, null);
            TextView viewLabel = (TextView) view.findViewById(R.id.label_view_bandcut);
            viewLabel.append(String.valueOf(count + 1));
            EditText lowBand = (EditText) view.findViewById(R.id.lowBand_view_bandcut);
            lowBand.setText(String.valueOf(list.get(count).getLowBand()));
            EditText highBand = (EditText) view.findViewById(R.id.highBand_view_bandcut);
            highBand.setText(String.valueOf(list.get(count).getHighBand()));
            //views.add(view);
            border.addView(view);
        }

        return this.rootView;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        this.seekBarValue = progress;
        this.bankcutNumber.setText(String.valueOf(this.seekBarValue));

        // draw view
        LayoutInflater layoutInflater = (LayoutInflater) getActivity().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout border = (LinearLayout) this.rootView.findViewById(R.id.draw_fragment_bankcut);
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
    public void onPause() {
        super.onPause();

        // save data.
        LayoutInflater layoutInflater = (LayoutInflater) getActivity().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout border = (LinearLayout) rootView.findViewById(R.id.draw_fragment_bankcut);
        ArrayList<BandCut> list = new ArrayList<BandCut>();

        for (int i = 0; i < border.getChildCount(); i++) {
            View view = border.getChildAt(i);
            EditText lowBand = (EditText) view.findViewById(R.id.lowBand_view_bandcut);
            EditText highBand = (EditText) view.findViewById(R.id.highBand_view_bandcut);

            if (lowBand.getText().toString().length() == 0 && highBand.getText().toString().length() == 0) {
                continue;
            }

            int low = Integer.parseInt(lowBand.getText().toString());
            int hight = Integer.parseInt(highBand.getText().toString());
            BandCut bc = new BandCut(low, hight);
            list.add(bc);
        }

        BSAdapter bsAdapter = new BSAdapter(this.getActivity().getApplicationContext());
        bsAdapter.open();
        bsAdapter.saveData(list);
        bsAdapter.close();
    }
}
