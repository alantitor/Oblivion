package ntou.cs.lab505.oblivion.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import ntou.cs.lab505.oblivion.Parameters.Record;
import ntou.cs.lab505.oblivion.Parameters.type.BandCut;

/**
 * Created by alan on 4/29/15.
 */
public class BSAdapter {

    Context mCtx;
    DBHelper mDbHelper;
    SQLiteDatabase mDb;

    public BSAdapter(Context context) {
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

    public void saveData(ArrayList<BandCut> list) {
        // check data state in db.
        String[] projection = {TableContract.T_BAND_USERID,
                                TableContract.T_BAND_GROUPID,
                                TableContract.T_BAND_LOWBAND,
                                TableContract.T_BAND_HIGHBAND};
        String selection = TableContract.T_BAND_USERID + " = ? ";
        String[] selectionArgs = {String.valueOf(Record.USERID)};
        String sortOrder = "";
        Cursor c = mDb.query(TableContract.TABLE_BAND, projection, selection, selectionArgs, null, null, sortOrder);
        c.moveToFirst();

        if (c.getCount() > 0) {
            // found old data.  delete data.
            mDb.delete(TableContract.TABLE_BAND, TableContract.T_BAND_USERID + " = ? ", new String[] {String.valueOf(Record.USERID)});
        }

        // insert data to db.
        for (int i = 0; i < list.size(); i++) {
            BandCut bc = list.get(i);
            Log.d("bsa", "save data" + bc.getData());
            ContentValues insertValues = new ContentValues();
            insertValues.put(TableContract.T_BAND_USERID, Record.USERID);
            insertValues.put(TableContract.T_BAND_GROUPID, i);
            insertValues.put(TableContract.T_BAND_LOWBAND, bc.getLowBand());
            insertValues.put(TableContract.T_BAND_HIGHBAND, bc.getHighBand());
            insertValues.put(TableContract.T_BAND_STATE, 1);
            mDb.insert(TableContract.TABLE_BAND, null, insertValues);
        }
    }

    public ArrayList getData() {
        String[] projection = {TableContract.T_BAND_USERID,
                                TableContract.T_BAND_GROUPID,
                                TableContract.T_BAND_LOWBAND,
                                TableContract.T_BAND_HIGHBAND};
        String selection = TableContract.T_BAND_USERID + " = ? ";
        String[] selectionArgs = {String.valueOf(Record.USERID)};
        String sortOrder = "";
        Cursor c = mDb.query(TableContract.TABLE_BAND, projection, selection, selectionArgs, null, null, sortOrder);
        c.moveToFirst();

        int low = 0;
        int high = 0;
        int groupId = -1;
        ArrayList<BandCut> list = new ArrayList<BandCut>();

        while (c.isAfterLast() == false) {
            low = Integer.parseInt(c.getString(c.getColumnIndex(TableContract.T_BAND_LOWBAND)));
            high = Integer.parseInt((c.getString(c.getColumnIndex(TableContract.T_BAND_HIGHBAND))));
            BandCut bc = new BandCut(low, high);
            list.add(bc);
            c.moveToNext();
        }

        return list;
    }
}
