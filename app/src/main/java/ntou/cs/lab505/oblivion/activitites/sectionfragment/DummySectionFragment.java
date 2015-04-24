package ntou.cs.lab505.oblivion.activitites.sectionfragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ntou.cs.lab505.oblivion.R;

/**
 * Created by alan on 4/24/15.
 */
public class DummySectionFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_dummy, container, false);
        return rootView;
    }
}