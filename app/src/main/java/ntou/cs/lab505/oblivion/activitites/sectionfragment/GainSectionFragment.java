package ntou.cs.lab505.oblivion.activitites.sectionfragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ntou.cs.lab505.oblivion.R;

/**
 * Created by alan on 4/24/15.
 */
public class GainSectionFragment extends Fragment {

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

    OnGainDataListener mCallback;

    // Container Activity must implement this interface
    public interface OnGainDataListener {
        public void OnGainDataPass(String data);
    }

    /**
     *
     * @param activity
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnGainDataListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnGainDataPass");
        }
    }

    /**
     *
     */
    @Override
    public void onPause() {
        super.onPause();

        String data = "";
        mCallback.OnGainDataPass(data);
    }
}