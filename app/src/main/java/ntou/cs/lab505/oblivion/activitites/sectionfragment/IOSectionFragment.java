package ntou.cs.lab505.oblivion.activitites.sectionfragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import ntou.cs.lab505.oblivion.R;

/**
 * Created by alan on 4/24/15.
 */
public class IOSectionFragment extends Fragment {
    // objects
    private View rootView;
    private RadioGroup rg1;
    private RadioGroup rg2;
    // parameters
    public int rg1Value;
    public int rg2Value;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        this.rootView = inflater.inflate(R.layout.fragment_io, container, false);
        this.rg1 = (RadioGroup) rootView.findViewById(R.id.radiogroup1_fragment_io);
        this.rg2 = (RadioGroup) rootView.findViewById(R.id.radiogroup2_fragment_io);
        this.rg1Value = 0;
        this.rg2Value = 0;
        // add listener.
        this.rg1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio1_fragment_io:
                        rg1Value = 0;
                        break;
                    case R.id.radio2_fragment_io:
                        rg1Value = 1;
                        break;
                    case R.id.radio3_fragment_io:
                        rg1Value = 2;
                        break;
                    default:
                        rg1Value = 0;
                        break;
                }
            }
        });

        this.rg2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio4_fragment_io:
                        rg2Value = 0;
                        break;
                    case R.id.radio5_fragment_io:
                        rg2Value = 1;
                        break;
                    case R.id.radio6_fragment_io:
                        rg2Value = 2;
                        break;
                    default:
                        rg2Value = 0;
                        break;
                }
            }
        });

        // load data.


        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();

        // save data.
    }

}
