package ntou.cs.lab505.oblivion.sound;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.util.concurrent.LinkedBlockingQueue;

import ntou.cs.lab505.oblivion.device.Microphone;
import ntou.cs.lab505.oblivion.device.Speaker;
import ntou.cs.lab505.oblivion.parameters.SoundParameter;
import ntou.cs.lab505.oblivion.sound.frequenceshift.FrequencyShift;
import ntou.cs.lab505.oblivion.sqlite.FSAdapter;
import ntou.cs.lab505.oblivion.sqlite.IOSAdapter;

/**
 * Created by alan on 5/5/15.
 */
public class SoundService extends Service {

    // device state parameters
    private static boolean serviceState = false;
    // model objects
    private Microphone microphone;
    private FrequencyShift frequencyShift;
    private FilterBank filterBank;
    private Speaker speaker;
    // model data queue
    private LinkedBlockingQueue<short[]> microphoneQueue = new LinkedBlockingQueue<short[]>();
    private LinkedBlockingQueue<short[]> frequencyShiftQueue = new LinkedBlockingQueue<short[]>();
    private LinkedBlockingQueue<short[]> filterBankQueue = new LinkedBlockingQueue<short[]>();

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onCreate() {

        // get device setting.
        int deviceIn = 0;
        int deviceOut = 0;
        IOSAdapter iosAdapter = new IOSAdapter(this.getApplicationContext());
        iosAdapter.open();
        deviceIn = iosAdapter.getData().getDeviceIn();
        deviceOut = iosAdapter.getData().getDeviceOut();
        iosAdapter.close();

        if (deviceIn == 1) {  // use bluetooth mic.
            SoundParameter.frequency = 8000;
        } else {  // use default mic.
            SoundParameter.frequency = 16000;
        }

        // get frequency shift setting.
        int freqShift = 0;
        FSAdapter fsAdapter = new FSAdapter(this.getApplicationContext());
        fsAdapter.open();
        freqShift = fsAdapter.getData().getSemiTones();
        fsAdapter.close();

        // create model object.
        microphone = new Microphone();
        frequencyShift = new FrequencyShift(SoundParameter.frequency, freqShift, 0, 0, 0);
        filterBank = new FilterBank();
        if (SoundParameter.frequency == 8000) {
            speaker = new Speaker();
        } else {
            speaker = new Speaker(SoundParameter.frequency);
        }

        // set data queue.
        microphone.setOutputQueue(microphoneQueue);
        frequencyShift.setInputDataQueue(microphoneQueue);
        frequencyShift.setOutputDataQueue(frequencyShiftQueue);
        speaker.setInputDataQueue(frequencyShiftQueue);

        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // change service state.
        serviceState = true;
        // start thread.
        microphone.threadStart();
        frequencyShift.threadStart();
        filterBank.threadStart();
        speaker.threadStart();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        // change service state.
        serviceState = false;
        // stop thread.
        microphone.threadStop();
        frequencyShift.threadStop();
        filterBank.threadStop();
        speaker.threadStop();
        super.onDestroy();
    }

    public static boolean getServiceState() {
        return serviceState;
    }
}
