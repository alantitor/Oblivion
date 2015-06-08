package ntou.cs.lab505.oblivion.sound.frequenceshift;

import android.util.Log;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by alan on 3/16/15.
 */
public class FrequencyShift extends Thread {

    private boolean threadState;  // denote thread state.
    private LinkedBlockingQueue<short[]> inputDataQueue;
    private LinkedBlockingQueue<short[]> outputDataQueue;

    private JNISoundTouch soundtouch = new JNISoundTouch();  // sound process object
    private int sampleRate;
    private int channels;
    private int pitchSemiTones;
    private float rateChange;
    private float tempoChange;


    /**
     * constructor
     */
    public FrequencyShift() {
        this.sampleRate = 16000;
        this.channels = 1;
        this.pitchSemiTones = 0;
        this.rateChange = 0.0f;
        this.tempoChange = 0.0f;
    }

    public FrequencyShift(int sampleRate, int channels, int pitchSemiTones, int rateChange, int tempoChange) {
        this.sampleRate = sampleRate;
        this.channels = channels;
        this.pitchSemiTones = pitchSemiTones;
        this.rateChange = rateChange;
        this.tempoChange = tempoChange;
    }

    /**
     * set data queue.  this queue is the source for processing.
     * @param inputDataQueue
     */
    public void setInputDataQueue(LinkedBlockingQueue<short[]> inputDataQueue) {
        this.inputDataQueue = inputDataQueue;
    }

    /**
     * set data queue.  this queue used to sve processed data.
     * @param outputDataQueue
     */
    public void setOutputDataQueue(LinkedBlockingQueue<short[]> outputDataQueue) {
        this.outputDataQueue = outputDataQueue;
    }

    /**
     *
     * @param sampleRate
     * @param channels
     * @param pitchSemiTones
     * @param rateChange
     * @param tempoChange
     */
    public void setSoundParameters(int sampleRate, int channels, int pitchSemiTones, float rateChange, float tempoChange) {
        this.sampleRate = sampleRate;
        this.channels = channels;
        this.pitchSemiTones = pitchSemiTones;
        this.rateChange = rateChange;
        this.tempoChange = tempoChange;
    }

    /**
     * control thread state.  start thread.
     */
    public void threadStart() {
        this.threadState = true;
        this.start();
    }

    /**
     * control thread state.  stop thread.
     */
    public void threadStop() {
        this.threadState = false;
        this.interrupt();
    }

    public boolean isThreadState() {
        return this.threadState;
    }

    /**
     * thread content.
     */
    public void run() {
        Log.d("FrequencyShift", "process start");

        // set sound parameters
        soundtouch.setSampleRate(sampleRate);
        soundtouch.setChannels(channels);
        soundtouch.setPitchSemiTones(pitchSemiTones);  // Changes the sound pitch or key while keeping the original tempo (speed).
        soundtouch.setRateChange(rateChange);  // Changes both tempo and pitch together as if a vinyl disc was played at different RPM rate.
        soundtouch.setTempoChange(tempoChange);  // Changes the sound to play at faster or slower tempo than originally without affecting the sound pitch.

        short[] tempBuff;
        short[] tempBuff2;

        try {
            while (threadState) {
                tempBuff = inputDataQueue.take();

                if (tempBuff != null) {
                    //Log.d("FrequencyShift", "data 1 length: " + tempBuff.length);

                    // put data to soundtouch library
                    //long startTime = System.currentTimeMillis();
                    //long startTime = System.nanoTime();
                    soundtouch.putSamples(tempBuff, tempBuff.length);

                    // receive data from soundtouch library
                    do {
                        tempBuff2 = soundtouch.receiveSamples();
                        //Log.d("FrequencyShift", "data 2 length: " + tempBuff2.length);
                        outputDataQueue.add(tempBuff2);
                    } while (tempBuff2.length > 0);

                    //long stopTime = System.currentTimeMillis();
                    //long stopTime = System.nanoTime();
                    //Log.d("FrequencyShift", "delay: " + (stopTime - startTime));
                }
            }
        } catch (Throwable e) {
            // do nothing
        }

        Log.d("FrequencyShift", "process stop");
    }
}


/**
 * How to use NDK:
 *
 * (1)
 *      just include library from exist package.
 *
 * (2)
 *      add ndk compile command at 'build.gradle'
 *              ndk {
 *                   moduleName "soundtouch"
 *               }
 *      write the correct package path in c++ interface code
 *              extern "C" JNIEXPORT void JNICALL Java_ntou_cs_lab505_hearingaid_sound_frequenceshift_JNISoundTouch_setSampleRate() {}
 *
 *              add extern "C"
 *              denote JNICALL_MY_PACKAGE_PATH_......() {}
 */