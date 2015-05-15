package ntou.cs.lab505.oblivion.sound;

import android.util.Log;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

import ntou.cs.lab505.oblivion.parameters.type.BandCut;
import ntou.cs.lab505.oblivion.parameters.type.FilterBankUnit;
import ntou.cs.lab505.oblivion.parameters.type.GainAdd;
import ntou.cs.lab505.oblivion.sound.IIRFilter.IIR;

/**
 * Created by alan on 5/8/15.
 */
public class FilterBank extends Thread{

    private boolean threadState;  // denote thread state.
    private LinkedBlockingQueue<short[]> inputDataQueue;
    private LinkedBlockingQueue<short[]> outputDataQueue;

    // 記錄濾波器個數
    private int filterBankNumber;
    // 內建預設頻帶切割
    private IIR iir_band1;
    private IIR iir_band2;
    private IIR iir_band3;
    private IIR iir_band4;
    private IIR iir_band5;
    // 動態頻帶切割
    private IIR[] iir_bands = null;
    // 動態增益補償 左右耳
    private Gain[] gainL40;
    private Gain[] gainL60;
    private Gain[] gainL80;
    private Gain[] gainR40;
    private Gain[] gainR60;
    private Gain[] gainR80;

    private ArrayList<short[]> soundVector = new ArrayList<short[]>();	// 記錄未處理訊號
    private static int FILTERORDER = 3;	 // 設定濾波器階數 階數愈高愈佳

    /**
     * Constructor
     */
    public FilterBank(ArrayList<BandCut> bandCutArrayList, ArrayList<GainAdd> gainAddArrayList) {

        initialIIR();

        filterBankNumber = bandCutArrayList.size();

        iir_bands = new IIR[filterBankNumber];
        gainL40 = new Gain[filterBankNumber];
        gainL60 = new Gain[filterBankNumber];
        gainL80 = new Gain[filterBankNumber];
        gainR40 = new Gain[filterBankNumber];
        gainR60 = new Gain[filterBankNumber];
        gainR80 = new Gain[filterBankNumber];

        for (int i = 0; i < filterBankNumber; i++) {
            gainL40[i] = new Gain(gainAddArrayList.get(i).getL40());
            gainL60[i] = new Gain(gainAddArrayList.get(i).getL60());
            gainL80[i] = new Gain(gainAddArrayList.get(i).getL80());
            gainR40[i] = new Gain(gainAddArrayList.get(i).getR40());
            gainR60[i] = new Gain(gainAddArrayList.get(i).getR60());
            gainR80[i] = new Gain(gainAddArrayList.get(i).getR80());

            iir_bands[i] = new IIR(FILTERORDER, (double) bandCutArrayList.get(i).getLowBand(), (double) bandCutArrayList.get(i).getHighBand());
        }
    }

    public FilterBank(ArrayList<FilterBankUnit> dataList) {

        initialIIR();

        filterBankNumber = dataList.size();



    }

    /**
     * 內建預設頻帶切割
     */
    public void initialIIR() {
        this.iir_band1 = new IIR(FILTERORDER, 143.0, 280.0);
        this.iir_band2 = new IIR(FILTERORDER, 281.0, 561.0);
        this.iir_band3 = new IIR(FILTERORDER, 561.0, 1120.0);
        this.iir_band4 = new IIR(FILTERORDER, 1110.0, 2240.0);
        this.iir_band5 = new IIR(FILTERORDER, 2230.0, 3540.0);
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

        while (threadState) {


        }

        Log.d("FilterBank", "process stop");
    }

}
