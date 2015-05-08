package ntou.cs.lab505.oblivion.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import ntou.cs.lab505.oblivion.parameters.Record;
import ntou.cs.lab505.oblivion.parameters.type.GainAdd;

/**
 * Created by alan on 4/30/15.
 */
public class GSAdapter {

    Context mCtx;
    DBHelper mDbHelper;
    SQLiteDatabase mDb;

    public GSAdapter(Context context) {
        this.mCtx = context;
    }

    public DBHelper open() {
        this.mDbHelper = new DBHelper(this.mCtx);
        this.mDb = this.mDbHelper.getWritableDatabase();
        return this.mDbHelper;
    }

    public void close() {
        this.mDbHelper.close();
    }

    public void saveData(ArrayList<GainAdd> list) {

        String[] projection = {TableContract.T_GAIN_USERID,
                                TableContract.T_GAIN_GROUPID,
                                TableContract.T_GAIN_L40,
                                TableContract.T_GAIN_L60,
                                TableContract.T_GAIN_L80,
                                TableContract.T_GAIN_R40,
                                TableContract.T_GAIN_R60,
                                TableContract.T_GAIN_R80,
                                TableContract.T_GAIN_STATE};
        String selection = TableContract.T_BAND_USERID + " = ? ";
        String[] selectionArgs = {String.valueOf(Record.USERID)};
        String sortOrder = "";
        Cursor c = mDb.query(TableContract.TABLE_GAIN, projection, selection, selectionArgs, null, null,sortOrder);
        c.moveToFirst();

        // delete old data in db.
        if (c.getCount() > 0) {
            mDb.delete(TableContract.TABLE_GAIN, TableContract.T_GAIN_USERID + " = ? ", new String[]{String.valueOf(Record.USERID)});
        }

        for(int i= 0; i < list.size(); i++) {
            GainAdd ga = list.get(i);  // get object from list.
            ContentValues insertValues = new ContentValues();
            insertValues.put(TableContract.T_GAIN_USERID, Record.USERID);
            insertValues.put(TableContract.T_GAIN_GROUPID, i);
            insertValues.put(TableContract.T_GAIN_L40, ga.getL40());
            insertValues.put(TableContract.T_GAIN_L60, ga.getL60());
            insertValues.put(TableContract.T_GAIN_L80, ga.getL80());
            insertValues.put(TableContract.T_GAIN_R40, ga.getR40());
            insertValues.put(TableContract.T_GAIN_R60, ga.getR60());
            insertValues.put(TableContract.T_GAIN_R80, ga.getR80());
            insertValues.put(TableContract.T_GAIN_STATE, 1);
            mDb.insert(TableContract.TABLE_GAIN, null, insertValues);
        }
    }

    public ArrayList getData() {

        int BandNumber = 0;
        int GainNumber = 0;

        // calculate Band Number
        BSAdapter bsAdapter = new BSAdapter(mCtx);
        bsAdapter.open();
        BandNumber = bsAdapter.getDataCount();
        bsAdapter.close();

        String[] projection = {TableContract.T_GAIN_USERID,
                                TableContract.T_GAIN_GROUPID,
                                TableContract.T_GAIN_L40,
                                TableContract.T_GAIN_L60,
                                TableContract.T_GAIN_L80,
                                TableContract.T_GAIN_R40,
                                TableContract.T_GAIN_R60,
                                TableContract.T_GAIN_R80,
                                TableContract.T_GAIN_STATE};
        String selection = TableContract.T_GAIN_USERID + " = ? ";
        String[] selectionArgs = {String.valueOf(Record.USERID)};
        String sortOrder = "";
        Cursor c = mDb.query(TableContract.TABLE_GAIN, projection, selection, selectionArgs, null,  null, sortOrder);
        c.moveToFirst();

        // create data list.
        int l40;
        int l60;
        int l80;
        int r40;
        int r60;
        int r80;
        ArrayList<GainAdd> list = new ArrayList<>();

        if (BandNumber == GainNumber) {
            while (c.isAfterLast() == false) {
                l40 = Integer.parseInt(c.getString(c.getColumnIndex(TableContract.T_GAIN_L40)));
                l60 = Integer.parseInt(c.getString(c.getColumnIndex(TableContract.T_GAIN_L60)));
                l80 = Integer.parseInt(c.getString(c.getColumnIndex(TableContract.T_GAIN_L80)));
                r40 = Integer.parseInt(c.getString(c.getColumnIndex(TableContract.T_GAIN_R40)));
                r60 = Integer.parseInt(c.getString(c.getColumnIndex(TableContract.T_GAIN_R60)));
                r80 = Integer.parseInt(c.getString(c.getColumnIndex(TableContract.T_GAIN_R80)));

                //Log.d("GsAdapter", l40 + "," + l60 + "," + l80 + "," + r40 + "," + r60 + "," + r80 + ",");
                GainAdd ga = new GainAdd(l40, l60, l80, r40, r60, r80);
                list.add(ga);
                c.moveToNext();
            }
        } else {  // return empty data.
            GainAdd gainAdd = new GainAdd();
            for (int i = 0; i < BandNumber; i++) {
                    list.add(gainAdd);
            }
        }

        return list;
    }
}