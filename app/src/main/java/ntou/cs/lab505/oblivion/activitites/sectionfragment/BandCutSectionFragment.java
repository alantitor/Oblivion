package ntou.cs.lab505.oblivion.activitites.sectionfragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

import ntou.cs.lab505.oblivion.R;
import ntou.cs.lab505.oblivion.parameters.type.BandCut;
import ntou.cs.lab505.oblivion.sqlite.BSAdapter;

/**
 * Created by alan on 4/24/15.
 */

public class BandCutSectionFragment extends Fragment {

    private View rootView;
    private SeekBar seekBar;
    private TextView bandcutNumber;
    private int seekBarValue;
    private LinearLayout border;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("BC", "onCreateView");
        this.rootView = inflater.inflate(R.layout.fragment_bandcut, container, false);
        //this.rootView = new View(getActivity().getApplicationContext());
        this.bandcutNumber = (TextView) this.rootView.findViewById(R.id.bcnumber_fragment_bankcut);
        this.seekBar = (SeekBar) this.rootView.findViewById(R.id.seekBar_fragment_bankcut);
        //this.seekBar.setOnSeekBarChangeListener(this);  // Add listener.  This step is very important.
        this.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.d("BC", "onProgressChanged");
                seekBarValue = progress;
                bandcutNumber.setText(String.valueOf(progress));

                // clear views
                border.removeAllViews();

                // draw view
                //LayoutInflater layoutInflater = (LayoutInflater) getActivity().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                // create view object
                for (int count = 0; count < progress; count++) {
                    View view = inflater.inflate(R.layout.view_bandcut, null);
                    TextView viewLabel = (TextView) view.findViewById(R.id.label_view_bandcut);
                    viewLabel.append(String.valueOf(count + 1));
                    EditText lowBand = (EditText) view.findViewById(R.id.lowBand_view_bandcut);
                    lowBand.setText("0");
                    EditText highBand = (EditText) view.findViewById(R.id.highBand_view_bandcut);
                    highBand.setText("0");
                    border.addView(view);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        this.border = (LinearLayout) this.rootView.findViewById(R.id.draw_fragment_bankcut);
        this.border.removeAllViews();

        // load data.
        BSAdapter bsAdapter = new BSAdapter(this.getActivity().getApplicationContext());
        bsAdapter.open();
        ArrayList<BandCut> list = bsAdapter.getData();
        this.seekBarValue = list.size();
        bsAdapter.close();

        // set SeekBar progress.
        this.seekBar.setProgress(list.size());

        // add view.
        for (int count = 0; count < this.seekBarValue; count++) {
            View view = inflater.inflate(R.layout.view_bandcut, null);

            TextView viewLabel = (TextView) view.findViewById(R.id.label_view_bandcut);
            viewLabel.append(String.valueOf(count + 1));

            EditText lowBand = (EditText) view.findViewById(R.id.lowBand_view_bandcut);
            lowBand.setText(String.valueOf(list.get(count).getLowBand()));
            EditText highBand = (EditText) view.findViewById(R.id.highBand_view_bandcut);
            highBand.setText(String.valueOf(list.get(count).getHighBand()));

            border.addView(view);
        }

        return this.rootView;
    }

    /*
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        Log.d("BC", "onProgressChanged");
        this.seekBarValue = progress;
        this.bandcutNumber.setText(String.valueOf(progress));

        // clear views
        this.border.removeAllViews();

        // draw view
        LayoutInflater layoutInflater = (LayoutInflater) getActivity().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // create view object
        for (int count = 0; count < progress; count++) {
            View view = layoutInflater.inflate(R.layout.view_bandcut, null);
            TextView viewLabel = (TextView) view.findViewById(R.id.label_view_bandcut);
            viewLabel.append(String.valueOf(count + 1));
            EditText lowBand = (EditText) view.findViewById(R.id.lowBand_view_bandcut);
            lowBand.setText("0");
            EditText highBand = (EditText) view.findViewById(R.id.highBand_view_bandcut);
            highBand.setText("0");
            border.addView(view);
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
    */

    @Override
    public void onResume() {
        Log.d("BC", "onResume");
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.d("BC", "onPause");
        super.onPause();
    }

    @Override
    public void onDestroy() {
        Log.d("BC", "onDestroy");
        super.onDestroy();

        // save data.
        //LayoutInflater layoutInflater = (LayoutInflater) getActivity().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout border = (LinearLayout) rootView.findViewById(R.id.draw_fragment_bankcut);
        ArrayList<BandCut> list = new ArrayList<>();

        for (int i = 0; i < border.getChildCount(); i++) {
            View view = border.getChildAt(i);
            EditText lowBand = (EditText) view.findViewById(R.id.lowBand_view_bandcut);
            EditText highBand = (EditText) view.findViewById(R.id.highBand_view_bandcut);

            if (lowBand.getText().toString().length() == 0 || highBand.getText().toString().length() == 0) {
                continue;
            }

            int low = Integer.parseInt(lowBand.getText().toString());
            int high = Integer.parseInt(highBand.getText().toString());
            BandCut bc = new BandCut(low, high);
            list.add(bc);
        }

        border.removeAllViews();

        BSAdapter bsAdapter = new BSAdapter(this.getActivity().getApplicationContext());
        bsAdapter.open();
        bsAdapter.saveData(list);
        bsAdapter.close();

        this.border.removeAllViews();
    }
}
