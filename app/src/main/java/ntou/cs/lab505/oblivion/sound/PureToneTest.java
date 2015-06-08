package ntou.cs.lab505.oblivion.sound;

import android.util.Log;

import java.util.concurrent.LinkedBlockingQueue;

import ntou.cs.lab505.oblivion.device.Speaker;
import ntou.cs.lab505.oblivion.sound.frequenceshift.FrequencyShift;

/**
 * Created by alan on 6/8/15.
 */
public class PureToneTest extends Thread {

    private boolean threadState;

    int sampleRate = 16000;

    PureToneGeneration pureToneGeneration;
    Speaker speaker;
    FrequencyShift frequencyShift;

    LinkedBlockingQueue<short[]> pureToneQueue = new LinkedBlockingQueue<short[]>();
    LinkedBlockingQueue<short[]> freqShiftQueue = new LinkedBlockingQueue<short[]>();

    int freqValue;
    int semiValue;
    int dbValue;
    int secValue;
    int gainValue;
    int harmValue;
    int lowBandValue;
    int highBandValue;
    int channelValue;

    public PureToneTest(int freqValue, int dbValue, int secValue, int harmValue, int semiValue, int lowBandValue, int highBandValue, int gainValue, int channelValue) {
        this.freqValue = freqValue;
        this.semiValue = semiValue;
        this.dbValue = dbValue;
        this.secValue = secValue;
        this.gainValue = gainValue;
        this.harmValue = harmValue;
        this.lowBandValue = lowBandValue;
        this.highBandValue = highBandValue;
        this.channelValue = channelValue;
    }

    public void threadStart() {
        this.threadState = true;
        this.start();
    }

    public void threadStop() {
        this.threadState = false;
        this.interrupt();
    }

    public void run() {
        Log.d("PureToneTest", "process start");

        // create object.
        short[] soundVector = new short[PureToneGeneration.pureToneExpectedSize(sampleRate, secValue)];
        pureToneGeneration = new PureToneGeneration(sampleRate);
        frequencyShift = new FrequencyShift(sampleRate, 1, semiValue, 0, 0);


        // setting speaker channels.
        if (channelValue == 3) {
            speaker = new Speaker(sampleRate, 2, 0);
        } else if (channelValue == 2) {
            speaker = new Speaker(sampleRate, 2, 0);
        } else if (channelValue == 1) {
            speaker = new Speaker(sampleRate, 2, 0);
        } else {
            speaker = new Speaker(sampleRate, 1, 0);
        }


        // generate puret tone.
        for (int count = 1; count <= harmValue; count++) {
            int tempFreq = freqValue * count;
            if (tempFreq > 7000) {
                // wrong message.
                break;
            }

            short[] tempSoundVector = pureToneGeneration.generate(tempFreq, secValue, dbValue);

            for (int trace = 0; trace < soundVector.length; trace++) {
                int tt = soundVector[trace] + tempSoundVector[trace];

                if (tt > Short.MAX_VALUE) {
                    soundVector[trace] = Short.MAX_VALUE;
                } else if (tt < Short.MIN_VALUE) {
                    soundVector[trace] = Short.MIN_VALUE;
                } else {
                    soundVector[trace] += tempSoundVector[trace];
                }
            }
        }


        soundVector = soundChannelProcess(soundVector, channelValue);



        // ************************
        // filterBank test.
        FilterBank2 filterBank;
        filterBank = new FilterBank2(500, 1000, 5, 0);


        // ************************


        // setting queues.
        pureToneQueue.add(soundVector);
        frequencyShift.setInputDataQueue(pureToneQueue);
        frequencyShift.setOutputDataQueue(freqShiftQueue);
        speaker.setInputDataQueue(freqShiftQueue);


        speaker.threadStart();
        frequencyShift.threadStart();

        try {
            sleep(secValue * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        speaker.threadStop();
        frequencyShift.threadStop();
        Log.d("PureToneTest", "process stop");
        this.threadStop();
    }

    private short[] soundChannelProcess(short[] data, int type) {
        // handle channels
        short[] outputSoundVector = null;

        if (type == 3) {
            outputSoundVector = new short[data.length * 2];
            for (int i = 0; i < data.length; i++) {
                outputSoundVector[i * 2] = data[i];
                outputSoundVector[i * 2 + 1] = data[i];
            }
        } else if (type == 1) {
            outputSoundVector = new short[data.length * 2];
            for (int i = 0; i < data.length; i++) {
                outputSoundVector[i * 2] = data[i];
                outputSoundVector[i * 2 + 1] = 0;
            }
        } else if (type == 2) {
            outputSoundVector = new short[data.length * 2];
            for (int i = 0; i < data.length; i++) {
                outputSoundVector[i * 2] = 0;
                outputSoundVector[i * 2 + 1] = data[i];
            }
        } else {
            outputSoundVector = data;
        }

        return outputSoundVector;
    }
}
