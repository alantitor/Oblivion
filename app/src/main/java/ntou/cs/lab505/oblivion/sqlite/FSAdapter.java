package ntou.cs.lab505.oblivion.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import ntou.cs.lab505.oblivion.parameters.Record;

/**
 * Created by alan on 4/27/15.
 */
public class FSAdapter {

    Context mCtx;
    DBHelper mDbHelper;
    SQLiteDatabase mDb;

    public FSAdapter(Context context) {
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

    public void saveData(int value) {
        String[] projection = {TableContract._ID,
                                TableContract.T_FREQSHIFT_USERID};
        String selection = TableContract.T_FREQSHIFT_USERID + " = ?";
        String[] selectionArgs = {String.valueOf(Record.USERID)};
        String sortOrder = "";
        Cursor c = mDb.query(TableContract.TABLE_FREQSHIFT, projection, selection, selectionArgs, null, null, sortOrder);
        c.moveToFirst();

        if (c.getCount() != 1) {
            ContentValues insertValues = new ContentValues();
            insertValues.put(TableContract.T_FREQSHIFT_USERID, Record.USERID);
            insertValues.put(TableContract.T_FREQSHIFT_SEMITONE, value);
            insertValues.put(TableContract.T_FREQSHIFT_STATE, 1);
            mDb.insert(TableContract.TABLE_FREQSHIFT, null, insertValues);
        } else {
            long db_id = Long.parseLong(c.getString(c.getColumnIndex(TableContract._ID)));
            mDb.execSQL("UPDATE " + TableContract.TABLE_FREQSHIFT
                        + " SET " + TableContract.T_FREQSHIFT_SEMITONE + " = " + value
                        + " WHERE " + TableContract._ID + " = " + db_id);
        }
    }

    public String getData() {

        String[] projection = {TableContract.T_FREQSHIFT_USERID,
                                TableContract.T_FREQSHIFT_SEMITONE};
        String selection = TableContract.T_FREQSHIFT_USERID + " = ? ";
        String[] selectionArgs = {String.valueOf(Record.USERID)};
        String sortOrder = "";
        Cursor c = mDb.query(TableContract.TABLE_FREQSHIFT, projection, selection, selectionArgs, null, null, sortOrder);
        c.moveToFirst();

        int value = 0;
        String data;

        if (c.getCount() == 1) {
            value = Integer.parseInt(c.getString(c.getColumnIndex(TableContract.T_FREQSHIFT_SEMITONE)));
            data = "p1:" + value;
        } else {
            data = "";
        }

        return data;
    }
}
