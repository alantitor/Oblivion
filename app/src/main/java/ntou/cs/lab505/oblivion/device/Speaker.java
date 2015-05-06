package ntou.cs.lab505.oblivion.device;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.util.Log;

import java.util.concurrent.LinkedBlockingQueue;

import ntou.cs.lab505.oblivion.parameters.SoundParameter;

/**
 * Created by alan on 5/5/15.
 */
public class Speaker extends Thread {

    private boolean threadState;
    private int speakerBufSize;
    private AudioTrack audioTrack;
    private LinkedBlockingQueue<short[]> inputDataQueue;

    private int sampleRate = SoundParameter.frequency;

    public Speaker() {
        speakerBufSize = AudioTrack.getMinBufferSize(sampleRate,
                                                        SoundParameter.channelConfiguration,
                                                        SoundParameter.audioEncoding);
        audioTrack = new AudioTrack(AudioManager.STREAM_VOICE_CALL,
                                    sampleRate,
                                    SoundParameter.channelConfiguration,
                                    SoundParameter.audioEncoding,
                                    speakerBufSize,
                                    AudioTrack.MODE_STATIC);
    }

    public Speaker(int sampleRate) {
        this.sampleRate = sampleRate;

        speakerBufSize = AudioTrack.getMinBufferSize(sampleRate,
                                                        AudioFormat.CHANNEL_CONFIGURATION_STEREO,
                                                        SoundParameter.audioEncoding);
        audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
                                    sampleRate,
                                    AudioFormat.CHANNEL_CONFIGURATION_MONO,
                                    SoundParameter.audioEncoding,
                                    speakerBufSize,
                                AudioTrack.MODE_STREAM);
    }

    public void setInputDataQueue(LinkedBlockingQueue<short[]> inputDataQueue) {
        this.inputDataQueue = inputDataQueue;
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
        Log.d("Speaker", "process start");
        short[] tempBuff;
        // start audio.
        audioTrack.play();

        // loop.
        while (threadState) {
            tempBuff = inputDataQueue.poll();
            if (tempBuff != null) {
                audioTrack.write(tempBuff, 0, tempBuff.length);
            }
        }

        audioTrack.stop();
        audioTrack.release();
        Log.d("Speaker", "process stop");
    }
}
