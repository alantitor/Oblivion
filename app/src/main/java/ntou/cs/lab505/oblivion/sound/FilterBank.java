package ntou.cs.lab505.oblivion.sound;

import android.util.Log;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by alan on 5/8/15.
 */
public class FilterBank extends Thread{

    private boolean threadState;  // denote thread state.
    private LinkedBlockingQueue<short[]> inputDataQueue;
    private LinkedBlockingQueue<short[]> outputDataQueue;

    private ArrayList<short[]> soundVector = new ArrayList<short[]>();	// 記錄未處理訊號
    private static int FILTERORDER = 3;	 // 設定濾波器階數 階數愈高愈佳

    /**
     * Constructor
     */
    public FilterBank() {

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
