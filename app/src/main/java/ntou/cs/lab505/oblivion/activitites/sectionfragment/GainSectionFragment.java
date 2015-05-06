package ntou.cs.lab505.oblivion.activitites.sectionfragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import ntou.cs.lab505.oblivion.parameters.type.GainAdd;
import ntou.cs.lab505.oblivion.R;
import ntou.cs.lab505.oblivion.sqlite.GSAdapter;

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

        // get item number.
        GSAdapter gsAdapter = new GSAdapter(this.getActivity().getApplicationContext());
        gsAdapter.open();
        ArrayList<GainAdd> list = gsAdapter.getData();
        this.itemNumber = list.size();
        gsAdapter.close();

        // add view.
        LayoutInflater layoutInflater = (LayoutInflater) getActivity().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout border = (LinearLayout) this.rootView.findViewById(R.id.draw_fragment_gain);
        border.removeAllViews();

        for (int i = 0; i < this.itemNumber; i++) {
            // get object reference.
            View view = layoutInflater.inflate(R.layout.view_gain, null);
            // label
            TextView viewLabel = (TextView) view.findViewById(R.id.label_gain_view);
            viewLabel.append(String.valueOf(i + 1));
            // button.
            ImageButton l40PlusButton = (ImageButton) view.findViewById(R.id.l40_plus_view_gain);
            ImageButton l60PlusButton = (ImageButton) view.findViewById(R.id.l60_plus_view_gain);
            ImageButton l80PlusButton = (ImageButton) view.findViewById(R.id.l80_plus_view_gain);
            ImageButton r40PlusButton = (ImageButton) view.findViewById(R.id.r40_plus_view_gain);
            ImageButton r60PlusButton = (ImageButton) view.findViewById(R.id.r60_plus_view_gain);
            ImageButton r80PlusButton = (ImageButton) view.findViewById(R.id.r80_plus_view_gain);
            ImageButton l40MinusButton = (ImageButton) view.findViewById(R.id.l40_minus_view_gain);
            ImageButton l60MinusButton = (ImageButton) view.findViewById(R.id.l60_minus_view_gain);
            ImageButton l80MinusButton = (ImageButton) view.findViewById(R.id.l80_minus_view_gain);
            ImageButton r40MinusButton = (ImageButton) view.findViewById(R.id.r40_minus_view_gain);
            ImageButton r60MinusButton = (ImageButton) view.findViewById(R.id.r60_minus_view_gain);
            ImageButton r80MinusButton = (ImageButton) view.findViewById(R.id.r80_minus_view_gain);

            final TextView l40Value = (TextView) view.findViewById(R.id.l40_value_view_gain);
            final TextView l60Value = (TextView) view.findViewById(R.id.l60_value_view_gain);
            final TextView l80Value = (TextView) view.findViewById(R.id.l80_value_view_gain);
            final TextView r40Value = (TextView) view.findViewById(R.id.r40_value_view_gain);
            final TextView r60Value = (TextView) view.findViewById(R.id.r60_value_view_gain);
            final TextView r80Value = (TextView) view.findViewById(R.id.r80_value_view_gain);

            // set value.
            l40Value.setText(String.valueOf(list.get(i).getL40()));
            l60Value.setText(String.valueOf(list.get(i).getL60()));
            l80Value.setText(String.valueOf(list.get(i).getL80()));
            r40Value.setText(String.valueOf(list.get(i).getR40()));
            r60Value.setText(String.valueOf(list.get(i).getR60()));
            r80Value.setText(String.valueOf(list.get(i).getR80()));

            // set listener.
            // left plus button.
            l40PlusButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int value = Integer.parseInt(l40Value.getText().toString());
                    if (value < 60) {
                        l40Value.setText(String.valueOf(value + 1));
                    }
                }
            });
            l60PlusButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int value = Integer.parseInt(l60Value.getText().toString());
                    if (value < 60) {
                        l60Value.setText(String.valueOf(value + 1));
                    }
                }
            });
            l80PlusButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int value = Integer.parseInt(l80Value.getText().toString());
                    if (value < 60) {
                        l80Value.setText(String.valueOf(value + 1));
                    }
                }
            });
            // right plus button.
            r40PlusButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int value = Integer.parseInt(r40Value.getText().toString());
                    if (value < 60) {
                        r40Value.setText(String.valueOf(value + 1));
                    }
                }
            });
            r60PlusButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int value = Integer.parseInt(r60Value.getText().toString());
                    if (value < 60) {
                        r60Value.setText(String.valueOf(value + 1));
                    }
                }
            });
            r80PlusButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int value = Integer.parseInt(r80Value.getText().toString());
                    if (value < 60) {
                        r80Value.setText(String.valueOf(value + 1));
                    }
                }
            });
            // left minus button.
            l40MinusButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int value = Integer.parseInt(l40Value.getText().toString());
                    if (value > -60) {
                        l40Value.setText(String.valueOf(value - 1));
                    }
                }
            });
            l60MinusButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int value = Integer.parseInt(l60Value.getText().toString());
                    if (value > -60) {
                        l60Value.setText(String.valueOf(value - 1));
                    }
                }
            });
            l80MinusButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int value = Integer.parseInt(l80Value.getText().toString());
                    if (value > -60) {
                        l80Value.setText(String.valueOf(value - 1));
                    }
                }
            });
            // right minus button.
            r40MinusButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int value = Integer.parseInt(r40Value.getText().toString());
                    if (value > -60) {
                        r40Value.setText(String.valueOf(value - 1));
                    }
                }
            });
            r60MinusButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int value = Integer.parseInt(r60Value.getText().toString());
                    if (value > -60) {
                        r60Value.setText(String.valueOf(value - 1));
                    }
                }
            });
            r80MinusButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int value = Integer.parseInt(r80Value.getText().toString());
                    if (value > -60) {
                        r80Value.setText(String.valueOf(value - 1));
                    }
                }
            });

            // add to border.
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

        // save data.
        LinearLayout border = (LinearLayout) this.rootView.findViewById(R.id.draw_fragment_gain);
        ArrayList<GainAdd> list = new ArrayList<>();

        int l40, l60, l80, r40, r60, r80;
        //TextView tvl40, tvl60, tvl80, tvr40, tvr60, tvr80;

        for (int i = 0; i < border.getChildCount(); i++) {
            View view = border.getChildAt(i);

            TextView tvl40 = (TextView) view.findViewById(R.id.l40_value_view_gain);
            TextView tvl60 = (TextView) view.findViewById(R.id.l60_value_view_gain);
            TextView tvl80 = (TextView) view.findViewById(R.id.l80_value_view_gain);
            TextView tvr40 = (TextView) view.findViewById(R.id.r40_value_view_gain);
            TextView tvr60 = (TextView) view.findViewById(R.id.r60_value_view_gain);
            TextView tvr80 = (TextView) view.findViewById(R.id.r80_value_view_gain);

            l40 = Integer.parseInt(tvl40.getText().toString());
            l60 = Integer.parseInt(tvl60.getText().toString());
            l80 = Integer.parseInt(tvl80.getText().toString());
            r40 = Integer.parseInt(tvr40.getText().toString());
            r60 = Integer.parseInt(tvr60.getText().toString());
            r80 = Integer.parseInt(tvr80.getText().toString());

            //Log.d("gs", l40 + "," + l60 + "," + l80 + "," + r40  + "," + r60  + "," + r80);

            GainAdd ga = new GainAdd(l40, l60, l80, r40, r60, r80);
            list.add(ga);
        }

        border.removeAllViews();

        GSAdapter gsAdapter = new GSAdapter(this.getActivity().getApplicationContext());
        gsAdapter.open();
        gsAdapter.saveData(list);
        gsAdapter.close();
    }
}