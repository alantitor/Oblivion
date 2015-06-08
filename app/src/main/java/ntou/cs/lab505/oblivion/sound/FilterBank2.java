package ntou.cs.lab505.oblivion.sound;

import android.util.Log;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

import ntou.cs.lab505.oblivion.parameters.type.FilterBankUnit;
import ntou.cs.lab505.oblivion.sound.IIRFilter.IIR;

/**
 * Created by alan on 6/8/15.
 */
public class FilterBank2 extends Thread {

    private boolean threadState;  // denote thread state.
    private LinkedBlockingQueue<short[]> inputDataQueue;
    private LinkedBlockingQueue<short[]> outputDataQueue;
    int channelType = 0;

    public static int filterOrder = 3;  // 設定濾波器階數. 階數愈高愈佳.
    private int filterBankNumber;  // 記錄濾波器個數(頻帶數).

    // 動態頻帶切割
    private IIR[] iirBands = null;

    // 動態增益補償 左右耳
    private Gain[] gain40L;
    private Gain[] gain60L;
    private Gain[] gain80L;
    private Gain[] gain40R;
    private Gain[] gain60R;
    private Gain[] gain80R;


    /**
     * constructor.
     */
    public FilterBank2(ArrayList<FilterBankUnit> dataArrayList, int channelType) {
        this.filterBankNumber = dataArrayList.size();
        this.channelType = channelType;
        iirBands = new IIR[filterBankNumber];

        for (int i = 0; i < filterBankNumber; i++) {
            // gain add values.
            gain40L[i] = new Gain(dataArrayList.get(i).getGainAdd().getL40());
            gain60L[i] = new Gain(dataArrayList.get(i).getGainAdd().getL60());
            gain80L[i] = new Gain(dataArrayList.get(i).getGainAdd().getL80());
            gain40R[i] = new Gain(dataArrayList.get(i).getGainAdd().getR40());
            gain60R[i] = new Gain(dataArrayList.get(i).getGainAdd().getR60());
            gain80R[i] = new Gain(dataArrayList.get(i).getGainAdd().getR80());
            // band cut width.
            iirBands[i] = new IIR(this.filterOrder, dataArrayList.get(i).getBandCut().getLowBand(), dataArrayList.get(i).getBandCut().getHighBand());
        }
    }

    public FilterBank2(int lowBand, int highBand, int gainValue, int channelType) {
        this.filterBankNumber = 1;
        this.channelType = channelType;

        iirBands[0] = new IIR(this.filterOrder, lowBand, highBand);
        gain40L[0] = new Gain(gainValue);
        gain60L[0] = new Gain(gainValue);
        gain80L[0] = new Gain(gainValue);
        gain40R[0] = new Gain(gainValue);
        gain60R[0] = new Gain(gainValue);
        gain80R[0] = new Gain(gainValue);
    }

    public void setInputDataQueue(LinkedBlockingQueue<short[]> inputDataQueue) {
        this.inputDataQueue = inputDataQueue;
    }

    public void setOutputDataQueue(LinkedBlockingQueue<short[]> outputDataQueue) {
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
            } catch (InterruptedException e) {
                e.printStackTrace();
            }





        }

        Log.d("FilterBank", "process stop");
    }





}
