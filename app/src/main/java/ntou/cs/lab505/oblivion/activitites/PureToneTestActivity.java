package ntou.cs.lab505.oblivion.activitites;

import android.app.Activity;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import ntou.cs.lab505.oblivion.R;
import ntou.cs.lab505.oblivion.sound.PureToneTest;

public class PureToneTestActivity extends Activity {

    PureToneTest pureToneTest;
    EditText ETfreq;
    EditText ETsemi;
    EditText ETdb;
    EditText ETsec;
    EditText ETgain;
    EditText ETharm;
    EditText ETlowBand;
    EditText EThighBand;
    TextView infoBefore;
    TextView infoAfter;
    Button button;
    CheckBox leftCheckBox;
    CheckBox rightCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pure_tone_test);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        ETfreq = (EditText) findViewById(R.id.freq_activity_pure_tone_test);
        ETsemi = (EditText) findViewById(R.id.semitone_activity_pure_tone_test);
        ETdb = (EditText) findViewById(R.id.db_activity_pure_tone_test);
        ETsec = (EditText) findViewById(R.id.sec_activity_pure_tone_test);
        ETgain = (EditText) findViewById(R.id.gain_activity_pure_tone_test);
        ETharm = (EditText) findViewById(R.id.harmonics_activity_pure_tone_test);
        ETlowBand = (EditText) findViewById(R.id.lowBand_activity_pure_tone_test);
        EThighBand = (EditText) findViewById(R.id.highBand_activity_pure_tone_test);
        infoBefore = (TextView) findViewById(R.id.soundinfobefore_activity_pure_tone_test);
        infoAfter = (TextView) findViewById(R.id.soundinfoafter_activity_pure_tone_test);
        button = (Button) findViewById(R.id.button_activity_pure_tone_test);
        leftCheckBox = (CheckBox) findViewById(R.id.LChannel_activity_pure_tone_test);
        rightCheckBox = (CheckBox) findViewById(R.id.RChannel_activity_pure_tone_test);
    }

    public void StartTest(View view) {
        // call methods.
        testContent();
    }

    private void testContent() {

        int freqValue = Integer.parseInt(ETfreq.getText().toString());
        int semiValue = Integer.parseInt(ETsemi.getText().toString());
        int dbValue = Integer.parseInt(ETdb.getText().toString());
        int secValue = Integer.parseInt(ETsec.getText().toString());
        int gainValue = Integer.parseInt(ETgain.getText().toString());
        int harmValue = Integer.parseInt(ETharm.getText().toString());
        int lowBandValue = Integer.parseInt(ETlowBand.getText().toString());
        int highBandValue = Integer.parseInt(EThighBand.getText().toString());
        boolean leftChannel = leftCheckBox.isChecked();
        boolean rightChannel = rightCheckBox.isChecked();


        int channelValue;
        if (leftChannel == true && rightChannel == true) {
            channelValue = 3;
        } else if (leftChannel == true && rightChannel == false) {
            channelValue = 2;
        } else if (leftChannel == false && rightChannel == true) {
            channelValue = 1;
        } else {
            channelValue = 0;
        }


        pureToneTest = new PureToneTest(freqValue, dbValue, secValue, harmValue, semiValue, lowBandValue, highBandValue, gainValue, channelValue);
        pureToneTest.threadStart();
    }


    @Override
    public void onPause() {
        super.onPause();
    }

    private void saveToFile(short[] data, String fileName) {

        if (data == null) {
            return ;
        }

        File file = new File(Environment.getExternalStorageDirectory().toString() + "/Download/" + fileName + ".txt");
        FileOutputStream fOut = null;
        OutputStreamWriter fWriter = null;

        try {
            file.createNewFile();
            fOut = new FileOutputStream(file);
            fWriter = new OutputStreamWriter(fOut);
            for (int i = 0; i < data.length; i++) {
                fWriter.append(data[i] + ",");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
