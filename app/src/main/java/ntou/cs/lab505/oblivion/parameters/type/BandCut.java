package ntou.cs.lab505.oblivion.parameters.type;

/**
 * Created by alan on 4/29/15.
 */
public class BandCut {

    private int lowBand;
    private int highBand;

    public BandCut() {
        this.lowBand = 0;
        this.highBand = 0;
    }

    public BandCut(int lowBand, int highBand) {
        this.lowBand = lowBand;
        this.highBand = highBand;
    }

    public void saveData(int lowBand, int highBand) {
        this.lowBand = lowBand;
        this.highBand = highBand;
    }

    public String getData() {
        return this.lowBand + "," + this.highBand;
    }

    public int getLowBand() {
        return this.lowBand;
    }

    public int getHighBand() {
        return this.highBand;
    }
}
