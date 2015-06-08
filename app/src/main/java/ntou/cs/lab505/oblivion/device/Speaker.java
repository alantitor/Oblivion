package ntou.cs.lab505.oblivion.device;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
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

    private int sampleRate;
    private int type;


    // Speaker can't use.  It will crash.
    public Speaker() {
        speakerBufSize = AudioTrack.getMinBufferSize(sampleRate,
                                                        SoundParameter.channelConfiguration,
                                                        SoundParameter.audioEncoding);
        audioTrack = new AudioTrack(AudioManager.STREAM_VOICE_CALL,  // AudioManager.STREAM_VOICE_CALL
                                    sampleRate,
                                    SoundParameter.channelConfiguration,
                                    SoundParameter.audioEncoding,
                                    speakerBufSize,
                                    AudioTrack.MODE_STREAM);  // AudioTrack.MODE_STATIC
    }

    public Speaker(int sampleRate) {
        this.sampleRate = sampleRate;
        speakerBufSize = AudioTrack.getMinBufferSize(sampleRate,
                                                        AudioFormat.CHANNEL_CONFIGURATION_STEREO,
                                                        SoundParameter.audioEncoding);
        audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
                                    sampleRate,
                                    AudioFormat.CHANNEL_CONFIGURATION_STEREO,
                                    SoundParameter.audioEncoding,
                                    speakerBufSize,
                                    AudioTrack.MODE_STREAM);
    }

    /**
     *
     * @param sampleRate
     * @param channelNum 1: one channel. 2: two channels.
     * @param type 0: line. 1: wireless. 2: save to file.
     */
    public Speaker(int sampleRate, int channelNum, int type) {
        this.sampleRate = sampleRate;
        this.type = type;

        if (channelNum == 1) {
            speakerBufSize = AudioTrack.getMinBufferSize(sampleRate,
                                                            AudioFormat.CHANNEL_CONFIGURATION_MONO,
                                                            AudioFormat.ENCODING_PCM_16BIT);
            audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
                                            sampleRate,
                                            AudioFormat.CHANNEL_CONFIGURATION_MONO,
                                            AudioFormat.ENCODING_PCM_16BIT,
                                            speakerBufSize,
                                            AudioTrack.MODE_STREAM);
        } else if (channelNum == 2) {
            speakerBufSize = AudioTrack.getMinBufferSize(sampleRate,
                                                            AudioFormat.CHANNEL_CONFIGURATION_STEREO,
                                                            AudioFormat.ENCODING_PCM_16BIT);
            audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
                                        sampleRate,
                                        AudioFormat.CHANNEL_CONFIGURATION_STEREO,
                                        AudioFormat.ENCODING_PCM_16BIT,
                                        speakerBufSize,
                                        AudioTrack.MODE_STREAM);
        } else {
            //
        }
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

    public boolean isThreadState() {
        return this.threadState;
    }

    public void run() {
        Log.d("Speaker", "process start");
        short[] tempBuff;

        if (type == 0) {
            // start audio.
            audioTrack.play();

            while (threadState) {
                tempBuff = inputDataQueue.poll();
                if (tempBuff != null && tempBuff.length != 0) {
                    audioTrack.write(tempBuff, 0, tempBuff.length);
                }
            }

            // release audio.
            audioTrack.stop();
            audioTrack.release();
        } else if (type == 1) {
            //
        } else if (type == 2) {
            this.file = new File(Environment.getExternalStorageDirectory().toString() + "/Download/" + "speaker.txt");
            this.fOut = null;
            this.fWriter = null;

            while (threadState) {
                tempBuff = inputDataQueue.poll();
                if (tempBuff != null && tempBuff.length != 0) {
                    saveVectorToFile(tempBuff);
                }
            }
        } else {
            //
        }

        Log.d("Speaker", "process stop");
    }

    File file;
    FileOutputStream fOut;
    OutputStreamWriter fWriter;

    private void saveVectorToFile(short[] data) {

        if (data == null) {
            return ;
        }

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

    private void saveVectorToWmv(short[] data, String fineName) {

    }
}
