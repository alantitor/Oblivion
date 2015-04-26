package ntou.cs.lab505.oblivion.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

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
    public void saveData() {

    }

    /**
     *
     */
    public void deleteData() {

    }

    /**
     *
     */
    public void getData() {

    }
}
