package ntou.cs.lab505.oblivion.math;

/**
 * Created by alan on 5/8/15.
 */
public class CalculateDb {

    /*
     * 計算音量
     * data - 欲計算的資料
     * return 音量
     */
    private static int calculatedb(short[] data)
    {
        short min = data[0];
        double sum = 0;

        for(int i = 0; i < data.length; i++)
        {
            sum += Math.pow(data[i], 2);
        }
        sum = 10 * Math.log10(sum / data.length);

        return (int)sum;
    }
}
