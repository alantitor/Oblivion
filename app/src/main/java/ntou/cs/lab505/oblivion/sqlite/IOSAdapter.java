package ntou.cs.lab505.oblivion.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import ntou.cs.lab505.oblivion.Parameters.Record;

/**
 * Created by alan on 2015/4/26.
 */
public class IOSAdapter {

    Context mCtx;
    DBHelper mDbHelper;
    SQLiteDatabase mDb;

    public IOSAdapter(Context context) {
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

    /**
     *
     */
    public void saveData(int value1, int value2) {

        String[] projection = {TableContract._ID,
                                TableContract.T_IO_USERID};
        String selection = TableContract.T_IO_USERID + " = ?";
        String[] selectionArgs = {String.valueOf(Record.USERID)};
        String sortOrder = "";
        Cursor c = mDb.query(TableContract.TABLE_IO, projection, selection, selectionArgs, null, null, sortOrder);
        c.moveToFirst();

        if (c.getCount() != 1) {  // insert new data.
            //Log.d("IOSAdapter", "insert data in db.");
            ContentValues insertValues = new ContentValues();
            insertValues.put(TableContract.T_IO_USERID, Record.USERID);
            insertValues.put(TableContract.T_IO_INPUT, value1);
            insertValues.put(TableContract.T_IO_OUTPUT, value2);
            insertValues.put(TableContract.T_IO_STATE, 1);
            mDb.insert(TableContract.TABLE_IO, null, insertValues);
        } else {  // update.
            //CLog.d("IOSAdapter", "update data in db.");
            long db_id = Long.parseLong(c.getString(c.getColumnIndex(TableContract._ID)));
            mDb.execSQL("UPDATE " + TableContract.TABLE_IO
                        + " SET " + TableContract.T_IO_INPUT + " = " + value1
                        + " , " + TableContract.T_IO_OUTPUT + " = " + value2
                        + " WHERE " + TableContract._ID + " = " + db_id);
        }
    }

    /**
     *
     */
    public void deleteData() {

    }

    /**
     *
     */
    public String getData() {

        String[] projection = {TableContract.T_IO_USERID,
                                TableContract.T_IO_INPUT,
                                TableContract.T_IO_OUTPUT};
        String selection = TableContract.T_IO_USERID + " = ? ";
        String[] selectionArgs = {String.valueOf(Record.USERID)};
        String sortOrder = "";
        Cursor c = mDb.query(TableContract.TABLE_IO, projection, selection, selectionArgs, null, null, sortOrder);
        c.moveToFirst();

        int value1 = 0;
        int value2 = 0;
        String data;

        if (c.getCount() == 1) {
            value1 = Integer.parseInt(c.getString(c.getColumnIndex(TableContract.T_IO_INPUT)));
            value2 = Integer.parseInt(c.getString(c.getColumnIndex(TableContract.T_IO_OUTPUT)));
            data = "p1:" + value1 + ",p2:" + value2;
        } else {
            data = "";
        }

        return data;
    }
}
