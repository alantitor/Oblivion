package ntou.cs.lab505.oblivion.activitites.sectionfragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import ntou.cs.lab505.oblivion.R;
import ntou.cs.lab505.oblivion.sqlite.BSAdapter;

/**
 * Created by alan on 4/24/15.
 */
public class GainSectionFragment extends Fragment {

    private View rootView;
    private int itemNumber;

    /**
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // get rootview.
        this.rootView = inflater.inflate(R.layout.fragment_gain, container, false);

        // refresh screen
        LinearLayout llScreen = (LinearLayout) this.rootView.findViewById(R.id.border_fragment_gain);
        llScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("gs", "click ok");
            }
        });

        // get item number.
        BSAdapter bsAdapter = new BSAdapter(this.getActivity().getApplicationContext());
        bsAdapter.open();
        this.itemNumber = bsAdapter.getDataCount();
        bsAdapter.close();

        // add view.
        LayoutInflater layoutInflater = (LayoutInflater) getActivity().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout border = (LinearLayout) this.rootView.findViewById(R.id.draw_fragment_gain);

        for (int i = 0; i < this.itemNumber; i++) {
            View view = layoutInflater.inflate(R.layout.view_gain, null);



            border.addView(view);
        }

        return rootView;
    }

    /**
     *
     */
    @Override
    public void onPause() {
        super.onPause();


    }
}