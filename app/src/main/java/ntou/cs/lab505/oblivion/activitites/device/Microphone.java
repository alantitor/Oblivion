package ntou.cs.lab505.oblivion.activitites.device;

import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.util.Log;

import java.util.concurrent.LinkedBlockingQueue;

import ntou.cs.lab505.oblivion.parameters.SoundParameter;

/**
 * Created by alan on 5/5/15.
 */
public class Microphone extends Thread {

    private boolean threadState;  // denote thread state.
    private int recordBufSize;
    private LinkedBlockingQueue<short[]> outputDataQueue;
    private AudioRecord audioRecord;  // record object.

    public Microphone() {
        recordBufSize = AudioRecord.getMinBufferSize(SoundParameter.frequency,
                SoundParameter.channelConfiguration,
                SoundParameter.audioEncoding);
        audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC,
                SoundParameter.frequency,
                SoundParameter.channelConfiguration,
                SoundParameter.audioEncoding,
                recordBufSize);
    }

    /**
     * set data queue.  this queu used to save processed data.
     * @param outputDataQueue
     */
    public void setOutputQueue(LinkedBlockingQueue<short[]> outputDataQueue) {
        this.outputDataQueue = outputDataQueue;
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

    /**
     * thread content.
     */
    public void run() {
        Log.d("Microphone", "process start");
        try {
            short[] microphoneBuf = new short[recordBufSize];
            long dataSum = 0;

            // start record sound.
            audioRecord.startRecording();

            // loop
            while (threadState) {
                int buffReadResult = audioRecord.read(microphoneBuf, 0, recordBufSize);

                // skip empty data.
                dataSum = 0;
                for (int i = 0; i < buffReadResult; i++) {
                    dataSum += microphoneBuf[i];
                }

                if (dataSum == 0) {
                    continue;
                }

                // check buffer contains data or not.
                if (buffReadResult > 0) {
                    // temp buffer used to resize microphoneBuffer.
                    short[] tempBuff = new short[buffReadResult];
                    // copy data to temp buffer.
                    System.arraycopy(microphoneBuf, 0, tempBuff, 0, buffReadResult);
                    // output data.
                    outputDataQueue.add(tempBuff);
                }
            }

            // stop microphone record.
            audioRecord.release();
        } catch (Throwable e) {
            // do nothing
        }

        Log.d("Microphone", "process stop");
    }
}
