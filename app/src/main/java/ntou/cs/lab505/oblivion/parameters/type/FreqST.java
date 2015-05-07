package ntou.cs.lab505.oblivion.parameters.type;

/**
 * Created by alan on 2015/5/7.
 */
public class FreqST {
    private int semiTones;

    public FreqST() {
        this.semiTones = 0;
    }

    public FreqST(int semiTones) {
        this.semiTones = semiTones;
    }

    public void saveData(int semiTones) {
        this.semiTones = semiTones;
    }

    public int getSemiTones() {
        return semiTones;
    }
}
