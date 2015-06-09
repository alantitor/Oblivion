package ntou.cs.lab505.oblivion.sound;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.concurrent.LinkedBlockingQueue;

import ntou.cs.lab505.oblivion.sound.IIRFilter.IIR2;

/**
 * Created by alan on 6/8/15.
 */
public class FilterBank2 extends Thread {

    private boolean threadState;  // denote thread state.
    private LinkedBlockingQueue<short[]> inputDataQueue;
    private LinkedBlockingQueue<short[][]> outputDataQueue;

    public static int filterOrder = 3;  // 設定濾波器階數. 階數愈高愈佳.
    private int filterBankNumber;  // 記錄濾波器個數(頻帶數).

    // 動態頻帶切割
    private IIR2[] iirBands = null;



    public FilterBank2(int sampleRate, int lowBand, int highBand) {
        this.filterBankNumber = 1;
        iirBands = new IIR2[1];
        iirBands[0] = new IIR2(this.filterOrder, sampleRate, lowBand, highBand);
    }

    public void setInputDataQueue(LinkedBlockingQueue<short[]> inputDataQueue) {
        this.inputDataQueue = inputDataQueue;
    }

    public void setOutputDataQueue(LinkedBlockingQueue<short[][]> outputDataQueue) {
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
        Log.d("FilterBank", "process start");
        short[] inputVector = null;

        while (threadState) {
            // buffer
            try {
                // take data from queue.
                inputVector = inputDataQueue.take();
                if (inputVector == null || inputVector.length == 0) {
                    continue;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            //Log.d("FilterBank", "vector length" + inputVector.length);

            // process.
            short[][] tempBands;
            tempBands = new short[iirBands.length][];

            for (int i = 0; i < iirBands.length; i++) {
                tempBands[i] = iirBands[i].process(inputVector);
                saveVectorToFile(tempBands[i], "filterBank" + i);
            }

            outputDataQueue.add(tempBands);
        }

        Log.d("FilterBank", "process stop");
    }

    private void saveVectorToFile(short[] data, String fileName) {
        File file = new File(Environment.getExternalStorageDirectory().toString() + "/Download/" + fileName + ".txt");
        FileOutputStream fOut;
        OutputStreamWriter fWriter;

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



}
