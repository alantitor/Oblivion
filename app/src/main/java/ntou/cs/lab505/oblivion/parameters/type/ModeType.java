package ntou.cs.lab505.oblivion.parameters.type;

/**
 * Created by alan on 2015/5/7.
 */
public class ModeType {

    private int modeType;

    public ModeType() {
        this.modeType = 0;
    }

    public ModeType(int modeType) {
        this.modeType = modeType;
    }

    public void saveData(int modeType) {
        this.modeType = modeType;
    }

    public int getModeType() {
        return this.modeType;
    }
}
