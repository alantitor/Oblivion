package ntou.cs.lab505.oblivion.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.AvoidXfermode;

import ntou.cs.lab505.oblivion.parameters.Record;
import ntou.cs.lab505.oblivion.parameters.type.ModeType;

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

    public void saveData(ModeType modeType) {
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
            insertValues.put(TableContract.T_MODE_MODE, modeType.getModeType());
            insertValues.put(TableContract.T_MODE_STATE, 1);
            mDb.insert(TableContract.TABLE_MODE, null, insertValues);
        } else {
            long db_id = Long.parseLong(c.getString(c.getColumnIndex(TableContract._ID)));
            mDb.execSQL("UPDATE " + TableContract.TABLE_MODE
                        + " SET " + TableContract.T_MODE_MODE + " = " + modeType.getModeType()
                        + " WHERE " + TableContract._ID + " = " + db_id);
        }
    }

    public ModeType getData() {

        String[] projection = {TableContract.T_MODE_USERID,
                                TableContract.T_MODE_MODE};
        String selection = TableContract.T_MODE_USERID + " = ?";
        String[] selectionArgs = {String.valueOf(Record.USERID)};
        String sortOrder = "";
        Cursor c = mDb.query(TableContract.TABLE_MODE, projection, selection, selectionArgs, null, null, sortOrder);
        c.moveToFirst();

        int value = 0;
        ModeType modeType = new ModeType();

        if (c.getCount() == 1) {
            value = Integer.parseInt((c.getString(c.getColumnIndex(TableContract.T_MODE_MODE))));
            modeType.saveData(value);
        }

        return modeType;
    }
}
