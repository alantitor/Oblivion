package ntou.cs.lab505.oblivion.parameters;

import java.util.ArrayList;

import ntou.cs.lab505.oblivion.parameters.type.BandCut;
import ntou.cs.lab505.oblivion.parameters.type.FilterBankUnit;
import ntou.cs.lab505.oblivion.parameters.type.GainAdd;

/**
 * Study HearingAid_2Channels PrefSetting class.
 */
public class FilterBankParameter {

    public static ArrayList getLowSetting() {

        ArrayList<FilterBankUnit> filterBankUnitArrayList = new ArrayList<>();
        FilterBankUnit filterBankUnit;

        filterBankUnit = new FilterBankUnit(new BandCut(176, 353), new GainAdd(8, 15, 5, 8, 15, 5));
        filterBankUnitArrayList.add(filterBankUnit);
        filterBankUnit = new FilterBankUnit(new BandCut(353, 707), new GainAdd(5, 15, 6, 5, 15, 6));
        filterBankUnitArrayList.add(filterBankUnit);
        filterBankUnit = new FilterBankUnit(new BandCut(707, 3500), new GainAdd(8, 15, 8, 8, 15, 8));
        filterBankUnitArrayList.add(filterBankUnit);

        return filterBankUnitArrayList;
    }

    public static ArrayList getHighSetting() {

        ArrayList<FilterBankUnit> filterBankUnitArrayList = new ArrayList<>();
        FilterBankUnit filterBankUnit;

        filterBankUnit = new FilterBankUnit(new BandCut(88, 707), new GainAdd(4, 13, 5, 4, 13, 5));
        filterBankUnitArrayList.add(filterBankUnit);
        filterBankUnit = new FilterBankUnit(new BandCut(707, 1414), new GainAdd(2, 8, 4, 2, 8, 4));
        filterBankUnitArrayList.add(filterBankUnit);
        filterBankUnit = new FilterBankUnit(new BandCut(1414 ,2828), new GainAdd(2, 5, 3, 2, 5, 3));
        filterBankUnitArrayList.add(filterBankUnit);
        filterBankUnit = new FilterBankUnit(new BandCut(2828, 3500), new GainAdd(3, 8, 2 ,3 ,8, 2));
        filterBankUnitArrayList.add(filterBankUnit);

        return filterBankUnitArrayList;
    }

    public static ArrayList getMixSetting() {

        ArrayList<FilterBankUnit> filterBankUnitArrayList = new ArrayList<>();
        FilterBankUnit filterBankUnit;

        filterBankUnit = new FilterBankUnit(new BandCut(176, 353), new GainAdd(2, 7, -2, 2, 7, -2));
        filterBankUnitArrayList.add(filterBankUnit);
        filterBankUnit = new FilterBankUnit(new BandCut(353, 707), new GainAdd(5, 10, 7, 5, 10, 7));
        filterBankUnitArrayList.add(filterBankUnit);
        filterBankUnit = new FilterBankUnit(new BandCut(707, 1414), new GainAdd(3, 8, 2, 3, 8, 2));
        filterBankUnitArrayList.add(filterBankUnit);
        filterBankUnit = new FilterBankUnit(new BandCut(1414, 2828), new GainAdd(6, 13, 8, 6, 13, 8));
        filterBankUnitArrayList.add(filterBankUnit);
        filterBankUnit = new FilterBankUnit(new BandCut(2828, 3500), new GainAdd(2, 7, 4, 2, 7, 4));
        filterBankUnitArrayList.add(filterBankUnit);


        return filterBankUnitArrayList;
    }

    public static ArrayList getDefaultSetting() {

        ArrayList<FilterBankUnit> filterBankUnitArrayList = new ArrayList<>();
        FilterBankUnit filterBankUnit;

        filterBankUnit = new FilterBankUnit(new BandCut(176, 353), new GainAdd());
        filterBankUnitArrayList.add(filterBankUnit);
        filterBankUnit = new FilterBankUnit(new BandCut(353, 707), new GainAdd());
        filterBankUnitArrayList.add(filterBankUnit);
        filterBankUnit = new FilterBankUnit(new BandCut(707, 1414), new GainAdd());
        filterBankUnitArrayList.add(filterBankUnit);
        filterBankUnit = new FilterBankUnit(new BandCut(1414, 2828), new GainAdd());
        filterBankUnitArrayList.add(filterBankUnit);
        filterBankUnit = new FilterBankUnit(new BandCut(2828, 3500), new GainAdd());
        filterBankUnitArrayList.add(filterBankUnit);

        return filterBankUnitArrayList;
    }

    public static ArrayList getDefaultSetting2() {

        ArrayList<FilterBankUnit> filterBankUnitArrayList = new ArrayList<>();
        FilterBankUnit filterBankUnit;

        filterBankUnit = new FilterBankUnit(new BandCut(143, 280), new GainAdd());
        filterBankUnitArrayList.add(filterBankUnit);
        filterBankUnit = new FilterBankUnit(new BandCut(281, 561), new GainAdd());
        filterBankUnitArrayList.add(filterBankUnit);
        filterBankUnit = new FilterBankUnit(new BandCut(561, 1120), new GainAdd());
        filterBankUnitArrayList.add(filterBankUnit);
        filterBankUnit = new FilterBankUnit(new BandCut(1110, 2240), new GainAdd());
        filterBankUnitArrayList.add(filterBankUnit);
        filterBankUnit = new FilterBankUnit(new BandCut(2230, 3540), new GainAdd());
        filterBankUnitArrayList.add(filterBankUnit);

        return filterBankUnitArrayList;
    }
}


