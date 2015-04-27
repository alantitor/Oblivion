package ntou.cs.lab505.oblivion.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import ntou.cs.lab505.oblivion.Parameters.Record;

/**
 * Created by alan on 4/27/15.
 */
public class MSAdapter {
    Context mCtx;
    DBHelper mDbHelper;
    SQLiteDatabase mDb;

    public MSAdapter(Context context) {
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
                                TableContract.T_MODE_USERID};
        String selection = TableContract.T_MODE_USERID + " = ?";
        String[] selectionArgs = {String.valueOf(Record.USERID)};
        String sortOrder = "";
        Cursor c = mDb.query(TableContract.TABLE_MODE, projection, selection, selectionArgs, null, null, sortOrder);
        c.moveToFirst();

        if (c.getCount() != 1) {
            ContentValues insertValues = new ContentValues();
            insertValues.put(TableContract.T_MODE_USERID, Record.USERID);
            insertValues.put(TableContract.T_MODE_MODE, value);
            insertValues.put(TableContract.T_MODE_STATE, 1);
            mDb.insert(TableContract.TABLE_MODE, null, insertValues);
        } else {
            long db_id = Long.parseLong(c.getString(c.getColumnIndex(TableContract._ID)));
            mDb.execSQL("UPDATE " + TableContract.TABLE_MODE
                        + " SET " + TableContract.T_MODE_MODE + " = " + value
                        + " WHERE " + TableContract._ID + " = " + db_id);
        }
    }

    public String getData() {

        String[] projection = {TableContract.T_MODE_USERID,
                                TableContract.T_MODE_MODE};
        String selection = TableContract.T_MODE_USERID + " = ?";
        String[] selectionArgs = {String.valueOf(Record.USERID)};
        String sortOrder = "";
        Cursor c = mDb.query(TableContract.TABLE_MODE, projection, selection, selectionArgs, null, null, sortOrder);
        c.moveToFirst();

        String data = "p1:" + c.getString(c.getColumnIndex(TableContract.T_MODE_MODE));

        return data;
    }
}
