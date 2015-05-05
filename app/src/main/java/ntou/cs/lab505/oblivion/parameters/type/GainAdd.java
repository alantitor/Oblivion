package ntou.cs.lab505.oblivion.parameters.type;

/**
 * Created by alan on 4/30/15.
 */
public class GainAdd {

    private int groupId;
    private int l40;
    private int l60;
    private int l80;
    private int r40;
    private int r60;
    private int r80;

    public GainAdd(int l40, int l60, int l80, int r40, int r60, int r80) {
        this.l40 = l40;
        this.l60 = l60;
        this.l80 = l80;
        this.r40 = r40;
        this.r60 = r60;
        this.r80 = r80;
    }

    public void saveData(int l40, int l60, int l80, int r40, int r60, int r80) {
        this.l40 = l40;
        this.l60 = l60;
        this.l80 = l80;
        this.r40 = r40;
        this.r60 = r60;
        this.r80 = r80;
    }

    public int getL40() {
        return this.l40;
    }

    public int getL60() {
        return this.l60;
    }

    public int getL80() {
        return this.l80;
    }

    public int getR40() {
        return this.r40;
    }

    public int getR60() {
        return this.r60;
    }

    public int getR80() {
        return this.r80;
    }
}
