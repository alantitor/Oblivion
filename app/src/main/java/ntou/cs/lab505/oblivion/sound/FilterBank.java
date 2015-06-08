package ntou.cs.lab505.oblivion.sound;

import android.util.Log;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

import ntou.cs.lab505.oblivion.parameters.SoundParameter;
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
        // initial IIR object.
        initialIIR();
        // get filterBank number
        filterBankNumber = bandCutArrayList.size();

        iir_bands = new IIR[filterBankNumber];
        gainL40 = new Gain[filterBankNumber];
        gainL60 = new Gain[filterBankNumber];
        gainL80 = new Gain[filterBankNumber];
        gainR40 = new Gain[filterBankNumber];
        gainR60 = new Gain[filterBankNumber];
        gainR80 = new Gain[filterBankNumber];

        for (int i = 0; i < filterBankNumber; i++) {
            // gain add value.
            gainL40[i] = new Gain(gainAddArrayList.get(i).getL40());
            gainL60[i] = new Gain(gainAddArrayList.get(i).getL60());
            gainL80[i] = new Gain(gainAddArrayList.get(i).getL80());
            gainR40[i] = new Gain(gainAddArrayList.get(i).getR40());
            gainR60[i] = new Gain(gainAddArrayList.get(i).getR60());
            gainR80[i] = new Gain(gainAddArrayList.get(i).getR80());
            // band cut width.
            iir_bands[i] = new IIR(FILTERORDER, (double) bandCutArrayList.get(i).getLowBand(), (double) bandCutArrayList.get(i).getHighBand());
        }
    }

    public FilterBank(ArrayList<FilterBankUnit> dataArrayList) {

        // initial IIR object.
        initialIIR();
        // get filterBank number.
        filterBankNumber = dataArrayList.size();

        iir_bands = new IIR[filterBankNumber];
        gainL40 = new Gain[filterBankNumber];
        gainL60 = new Gain[filterBankNumber];
        gainL80 = new Gain[filterBankNumber];
        gainR40 = new Gain[filterBankNumber];
        gainR60 = new Gain[filterBankNumber];
        gainR80 = new Gain[filterBankNumber];

        for (int i = 0; i < filterBankNumber; i++) {
            // gain add values.
            gainL40[i] = new Gain(dataArrayList.get(i).getGainAdd().getL40());
            gainL60[i] = new Gain(dataArrayList.get(i).getGainAdd().getL60());
            gainL80[i] = new Gain(dataArrayList.get(i).getGainAdd().getL80());
            gainR40[i] = new Gain(dataArrayList.get(i).getGainAdd().getR40());
            gainR60[i] = new Gain(dataArrayList.get(i).getGainAdd().getR60());
            gainR80[i] = new Gain(dataArrayList.get(i).getGainAdd().getR80());
            // band cut width.
            iir_bands[i] = new IIR(FILTERORDER, dataArrayList.get(i).getBandCut().getLowBand(), dataArrayList.get(i).getBandCut().getHighBand());
        }
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

        short[] inputVector = null;
        while (threadState) {
            // buffer
            try {
                // take data from queue.
                inputVector = inputDataQueue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            short[] channelL = new short[inputVector.length];
            short[] channelR = new short[inputVector.length];
            /**
             * if system is 1 channel, only use channelL.
             * if system is 2 channel, use both channelL and channelR.
             */
            System.arraycopy(inputVector, 0, channelL, 0, inputVector.length);

            if (iir_bands != null) {  // if dynamic band cut was initialed.
                short[][] tempBands;
                short[][] tempBandsL;
                short[][] tempBandsR;
                tempBands = new short[iir_bands.length][];  // original signal;
                tempBandsL = new short[iir_bands.length][];  // left channel signal;
                tempBandsR = new short[iir_bands.length][];  // right channel signal;

                for (int i = 0; i < iir_bands.length; i++) {
                    tempBands[i] = iir_bands[i].process(channelL.clone());  // process original signal.
                    int db = Gain.calculateDb(tempBands[i]);  // calculate db.
                    tempBandsL[i] = autoGain(i, tempBands[i].clone(), db, 0);  	// 依照不同音量及左右耳給與不同增益處理. 0左耳.
                    tempBandsR[i] = autoGain(i, tempBands[i].clone(), db, 1);  	// 依照不同音量及左右耳給與不同增益處理. 1右耳.
                }

                    /*
                     * 左右耳分別加總
                     * 把各頻帶處理過的訊號組合成原始訊號
                     */
                for (int j = 0; j < channelL.length; j++) {
                    short sumL = 0;
                    short sumR = 0;

                    for (int k = 0; k < iir_bands.length; k++) {
                        sumL += tempBandsL[k][j];
                        sumR += tempBandsR[k][j];
                    }

                    channelL[j] = sumL;
                    channelR[j] = sumR;
                }
            } else {  // initial dynamic band cut.
                // use default Filterbank.
                int db;
                short[] temp1 = iir_band1.process(channelL.clone());
                short[] temp2 = iir_band2.process(channelL.clone());
                short[] temp3 = iir_band3.process(channelL.clone());
                short[] temp4 = iir_band4.process(channelL.clone());
                short[] temp5 = iir_band5.process(channelL.clone());

                db = Gain.calculateDb(temp1);
                temp1 = autoGain(0, temp1, db, 0);
                db = Gain.calculateDb(temp2);
                temp2 = autoGain(0, temp2, db, 0);
                db = Gain.calculateDb(temp3);
                temp3 = autoGain(0, temp3, db, 0);
                db = Gain.calculateDb(temp4);
                temp4 = autoGain(0, temp4, db, 0);
                db = Gain.calculateDb(temp5);
                temp5 = autoGain(0, temp5, db, 0);

                /*
                 * 把各頻帶處理過的訊號組合成原始訊號
                 */
                for (int i = 0; i < channelL.length; i++) {
                    channelL[i] = (short) (temp1[i] + temp2[i] + temp3[i] + temp4[i] + temp5[i]);
                }
            }


            //判斷目前取樣頻率 做為判斷使用左右耳助聽器 或 單耳助聽器
            // output data.
            // check channels number.
            if (SoundParameter.frequency == 8000) {  // 1 channels.
                outputDataQueue.add(channelL);
            } else {  // 2 channels.
                //若為雙耳則需將訊號 LRLR填入再送出
                short[] outputBuff = new short[channelL.length * 2];
                for (int i = 0; i < outputBuff.length / 2; i++) {
                    outputBuff[i * 2] = channelL[i];
                    outputBuff[i * 2 + 1] = channelR[i];
                }
                outputDataQueue.add(outputBuff);
            }
        }

        Log.d("FilterBank", "process stop");
    }

    /*
     * 增益補償運算
     * i - 頻帶編號
     * data - 訊號數值
     * db - 訊號音量
     * LorR - 左或右
     * return 處理完的訊號
     */
    private short[] autoGain(int i, short[] data, int db, int LorR)
    {
        //判斷目前取樣頻率 做為判斷使用左右耳助聽器 或 單耳助聽器
        if(SoundParameter.frequency == 8000)
        {
            //進行單耳增益處理
            short[] result = new short[data.length];

            if(db >= 40 && db < 60)
                result = gainL40[i].process(data.clone());
            else if(db>=60&&db<80)
                result = gainL60[i].process(data.clone());
            else if(db>=80)
                result = gainL80[i].process(data.clone());
            else
                result = data.clone();

            return result;
        }
        else
        {
            //進行雙耳增益處理
            short[] result;
            short[] resultR;

            if(db >= 40 && db < 60)
            {
                result  = gainL40[i].process(data.clone());
                resultR = gainR40[i].process(data.clone());
            }
            else if(db >= 60 && db < 80)
            {
                result  = gainL60[i].process(data.clone());
                resultR = gainR60[i].process(data.clone());
            }
            else if(db >= 80)
            {
                result  = gainL80[i].process(data.clone());
                resultR = gainR80[i].process(data.clone());
            }
            else
            {
                result  = data.clone();
                resultR = data.clone();
            }

            if(LorR == 0)
                return result;
            else
                return resultR;
        }
    }
}