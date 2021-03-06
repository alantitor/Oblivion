package ntou.cs.lab505.oblivion.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import ntou.cs.lab505.oblivion.parameters.Record;
import ntou.cs.lab505.oblivion.parameters.type.DeviceIO;

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
    public void saveData(DeviceIO deviceIo) {

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
            insertValues.put(TableContract.T_IO_INPUT, deviceIo.getDeviceIn());
            insertValues.put(TableContract.T_IO_OUTPUT, deviceIo.getDeviceOut());
            insertValues.put(TableContract.T_IO_STATE, 1);
            mDb.insert(TableContract.TABLE_IO, null, insertValues);
        } else {  // update.
            //Log.d("IOSAdapter", "update data in db.");
            long db_id = Long.parseLong(c.getString(c.getColumnIndex(TableContract._ID)));
            mDb.execSQL("UPDATE " + TableContract.TABLE_IO
                        + " SET " + TableContract.T_IO_INPUT + " = " + deviceIo.getDeviceIn()
                        + " , " + TableContract.T_IO_OUTPUT + " = " + deviceIo.getDeviceOut()
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
    public DeviceIO getData() {

        String[] projection = {TableContract.T_IO_USERID,
                                TableContract.T_IO_INPUT,
                                TableContract.T_IO_OUTPUT};
        String selection = TableContract.T_IO_USERID + " = ? ";
        String[] selectionArgs = {String.valueOf(Record.USERID)};
        String sortOrder = "";
        Cursor c = mDb.query(TableContract.TABLE_IO, projection, selection, selectionArgs, null, null, sortOrder);
        c.moveToFirst();

        int inputType = 0;
        int outputType = 0;
        DeviceIO deviceIO = new DeviceIO();

        if (c.getCount() == 1) {
            inputType = Integer.parseInt(c.getString(c.getColumnIndex(TableContract.T_IO_INPUT)));
            outputType = Integer.parseInt(c.getString(c.getColumnIndex(TableContract.T_IO_OUTPUT)));
            deviceIO.saveData(inputType, outputType);
        }

        return deviceIO;
    }
}
