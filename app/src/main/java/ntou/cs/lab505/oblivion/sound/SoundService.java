package ntou.cs.lab505.oblivion.sound;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by alan on 5/5/15.
 */
public class SoundService extends Service {

    // device state parameters
    private static boolean serviceState = false;
    // model objects
    //private Microphone microphone;
    //private FrequencyShift frequencyShift;
    //private FilterBank filterBank;
    //private Speaker speaker;
    // model data queue
    //private LinkedBlockingQueue<short[]> microphoneQueue = new LinkedBlockingQueue<short[]>();
    //private LinkedBlockingQueue<short[]> frequencyShiftQueue = new LinkedBlockingQueue<short[]>();
    //private LinkedBlockingQueue<short[]> filterBankQueue = new LinkedBlockingQueue<short[]>();

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
        //int deviceIn = 0;
        //int deviceOut = 0;
        //IOSAdapter iosAdapter = new IOSAdapter(this.getApplicationContext());
        //iosAdapter.open();
        //deviceIn = iosAdapter.getData().getDeviceIn();
        //deviceOut = iosAdapter.getData().getDeviceOut();
        //iosAdapter.close();
        //Log.d("SoundService", "deviceIn: " + deviceIn);


        /*
        if (deviceIn == 1) {  // use bluetooth mic.
            Log.d("SoundService", "use bluetooth mic");
            SoundParameter.frequency = 8000;
        } else {  // use default mic.
            Log.d("SoundService", "use default mic");
            SoundParameter.frequency = 16000;
        }
        */

        // get frequency shift setting.
        //int freqShift = 0;
        //FSAdapter fsAdapter = new FSAdapter(this.getApplicationContext());
        //fsAdapter.open();
        //freqShift = fsAdapter.getData().getSemiTones();
        //fsAdapter.close();

        // get mode setting.
        //int modeType = 0;
        //MSAdapter msAdapter = new MSAdapter(this.getApplicationContext());
        //msAdapter.open();
        //modeType = msAdapter.getData().getModeType();
        //msAdapter.close();


        // create model object.
        //microphone = new Microphone();
        //frequencyShift = new FrequencyShift(SoundParameter.frequency, 1, freqShift, 0, 0);

        /*
        if (modeType == 0) {
            filterBank = new FilterBank(FilterBankParameter.getLowSetting());
        } else if (modeType == 1) {
            filterBank = new FilterBank(FilterBankParameter.getHighSetting());
        } else if (modeType == 2) {
            filterBank = new FilterBank(FilterBankParameter.getMixSetting());
        } else if (modeType == 3) {
            BSAdapter bsAdapter = new BSAdapter(this.getApplicationContext());
            GSAdapter gsAdapter = new GSAdapter(this.getApplicationContext());
            bsAdapter.open();
            gsAdapter.open();

            if (bsAdapter.getDataCount() == 0) {
                filterBank = new FilterBank(FilterBankParameter.getDefaultSetting());
            } else {
                filterBank = new FilterBank(bsAdapter.getData(), gsAdapter.getData());
            }

            bsAdapter.close();
            gsAdapter.close();
        } else {
            filterBank = new FilterBank(FilterBankParameter.getDefaultSetting());
        }
        */

        /*
        if (SoundParameter.frequency == 8000) {
            speaker = new Speaker();
        } else {
            speaker = new Speaker(SoundParameter.frequency);
        }
        */


        // set data queue.
        //microphone.setOutputQueue(microphoneQueue);
        //frequencyShift.setInputDataQueue(microphoneQueue);
        //frequencyShift.setOutputDataQueue(frequencyShiftQueue);
        //filterBank.setInputDataQueue(frequencyShiftQueue);
        //filterBank.setOutputDataQueue(filterBankQueue);
        //speaker.setInputDataQueue(microphoneQueue);
        //speaker.setInputDataQueue(frequencyShiftQueue);
        //speaker.setInputDataQueue(filterBankQueue);

        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // change service state.
        serviceState = true;
        // start thread.
        //microphone.threadStart();
        //frequencyShift.threadStart();
        //filterBank.threadStart();
        //speaker.threadStart();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        // change service state.
        serviceState = false;
        // stop thread.
        //microphone.threadStop();
        //frequencyShift.threadStop();
        //filterBank.threadStop();
        //speaker.threadStop();
        super.onDestroy();
    }

    public static boolean getServiceState() {
        return serviceState;
    }
}
