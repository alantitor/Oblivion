package ntou.cs.lab505.oblivion.parameters.type;

/**
 * Created by alan on 5/11/15.
 */
public class FilterBankUnit {

    BandCut bandCut;
    GainAdd gainAdd;

    public FilterBankUnit() {
        this.bandCut = new BandCut();
        this.gainAdd = new GainAdd();
    }

    public FilterBankUnit(BandCut bandCut, GainAdd gainAdd) {
        this.bandCut = bandCut;
        this.gainAdd = gainAdd;
    }

    public BandCut getBandCut() {
        return this.bandCut;
    }

    public GainAdd getGainAdd() {
        return this.gainAdd;
    }
}
