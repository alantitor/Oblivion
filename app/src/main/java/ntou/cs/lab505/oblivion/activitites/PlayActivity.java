package ntou.cs.lab505.oblivion.activitites;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import ntou.cs.lab505.oblivion.R;
import ntou.cs.lab505.oblivion.sound.SoundService;

public class PlayActivity extends Activity {

    ImageButton startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        // set volume buttons enable
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        // check data state.
        startButton = (ImageButton) findViewById(R.id.start_activity_play);


    }

    /**
     *
     * @param view
     */
    public void startService(View view) {
        if (SoundService.getServiceState() == false) {
            // change button image.
            changeButtonImage(1);
            // start service.
            Intent service = new Intent(PlayActivity.this, SoundService.class);
            startService(service);
        } else {
            // change button image.
            changeButtonImage(0);
            // stop service
            Intent service = new Intent(PlayActivity.this, SoundService.class);
            stopService(service);
        }
    }

    public void changeButtonImage(int state) {
        if (state == 0) {  // stop to play
            startButton.setImageResource(R.drawable.ic_play_orange);
        } else if (state == 1) {  // play to stop
            startButton.setImageResource(R.drawable.ic_pause_orange);
        } else {
            //
        }
    }

    @Override
    public void onPause() {
        super.onPause();


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
