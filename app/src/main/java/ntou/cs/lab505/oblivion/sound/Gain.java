package ntou.cs.lab505.oblivion.sound;

/**
 * Created by alan on 5/8/15.
 */
public class Gain {
    private double gain;

    public Gain(double gain) {
        this.gain = Math.pow(10, gain/20);
    }

    public short[] process(short[] data) {
        for (int i = 0; i < data.length; i++) {
            int temp = data[i];
            temp = (int) (temp * this.gain);

            // check overflow.
            if (temp > Short.MAX_VALUE) {
                data[i] = Short.MAX_VALUE;
            } else if (temp < Short.MIN_VALUE) {
                data[i] = Short.MIN_VALUE;
            } else {
                data[i] = (short) temp;
            }
        }

        return data;
    }
}
