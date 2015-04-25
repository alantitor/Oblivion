package ntou.cs.lab505.oblivion.activitites.sectionfragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import ntou.cs.lab505.oblivion.R;

/**
 * Created by alan on 4/24/15.
 */
public class DefaultModeSectionFragment extends Fragment {
    // objects
    private View rootView;
    private RadioButton rB1;  // 低頻音加強.  0
    private RadioButton rB2;  // 高頻音加強.  1
    private RadioButton rB3;  // 混合性加強.  2
    private RadioButton rB4;  // 自訂.  3
    // parameters
    public int selectedValue;  // denote selected button.

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.rootView = inflater.inflate(R.layout.fragment_defaultmode, container, false);
        this.rB1 = (RadioButton) rootView.findViewById(R.id.radioButton1_fragment_defaultmode);
        this.rB2 = (RadioButton) rootView.findViewById(R.id.radioButton2_fragment_defaultmode);
        this.rB3 = (RadioButton) rootView.findViewById(R.id.radioButton3_fragment_defaultmode);
        this.rB4 = (RadioButton) rootView.findViewById(R.id.radioButton4_fragment_defaultmode);
        this.selectedValue = -1;

        // add listener
        rB1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedValue = 0;
                rB1.setChecked(true);
                rB2.setChecked(false);
                rB3.setChecked(false);
                rB4.setChecked(false);
                mCallback.OnDMDataPass(String.valueOf(selectedValue));
            }
        });

        rB2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedValue = 1;
                rB1.setChecked(false);
                rB2.setChecked(true);
                rB3.setChecked(false);
                rB4.setChecked(false);
                mCallback.OnDMDataPass(String.valueOf(selectedValue));
            }
        });

        rB3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedValue = 2;
                rB1.setChecked(false);
                rB2.setChecked(false);
                rB3.setChecked(true);
                rB4.setChecked(false);
                mCallback.OnDMDataPass(String.valueOf(selectedValue));
            }
        });

        rB4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedValue = 3;
                rB1.setChecked(false);
                rB2.setChecked(false);
                rB3.setChecked(false);
                rB4.setChecked(true);
                mCallback.OnDMDataPass(String.valueOf(selectedValue));
            }
        });

        return rootView;
    }

    OnDMDataListener mCallback;

    public interface OnDMDataListener {
        public void OnDMDataPass(String data);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mCallback = (OnDMDataListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnBCDataPass");
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        // save data.
    }
}
