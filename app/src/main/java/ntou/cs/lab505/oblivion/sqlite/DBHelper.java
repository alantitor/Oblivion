package ntou.cs.lab505.oblivion.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by alan on 4/9/15.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String CS = " , ";  // comma sep
    private static final String WS = " ";  // white space


    public DBHelper(Context context) {
        super(context, TableContract.DATABASE_NAME + ".db", null, TableContract.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /*
         * be careful of sql create table command.
         * don't add "CS" at end of command.
         */
        // create sqlite "user_account" table command
        String SQL_CREATE_TABLE_UA =
                "CREATE TABLE" + WS +  TableContract.TABLE_USER_ACCOUNT + " ( " +
                TableContract._ID + WS + TableContract.TYPE_INTEGER + WS + " PRIMARY KEY " + CS +
                TableContract.T_UA_USERNAME + WS + TableContract.TYPE_TEXT + CS +
                TableContract.T_UA_USERACCOUNT + WS + TableContract.TYPE_TEXT + CS +
                TableContract.T_UA_USERPASSWORD + WS + TableContract.TYPE_TEXT + CS +
                TableContract.T_UA_STATE + WS + TableContract.TYPE_INTEGER +
                " )";
        String SQL_CREATE_TABLE_SETTING =
                "CREATE TABLE" + WS + TableContract.TABLE_SETTING + " ( " +
                TableContract._ID + WS + TableContract.TYPE_INTEGER + WS + " PRIMARY KEY " + CS +
                TableContract.T_S_USERID + WS + TableContract.TYPE_INTEGER + CS +
                TableContract.T_S_USEIT + WS + TableContract.TYPE_INTEGER + CS +
                TableContract.T_S_STATE + WS + TableContract.TYPE_INTEGER +
                " )";
        String SQL_CREATE_TABLE_IO_SETTING =
                "CREATE TABLE" + WS + TableContract.TABLE_IO + " ( " +
                TableContract._ID + WS + TableContract.TYPE_INTEGER + WS + " PRIMARY KEY " + CS +
                TableContract.T_IO_USERID + WS + TableContract.TYPE_INTEGER + CS +
                TableContract.T_IO_INPUT + WS + TableContract.TYPE_INTEGER + CS +
                TableContract.T_IO_OUTPUT + WS + TableContract.TYPE_INTEGER + CS +
                TableContract.T_IO_STATE + WS + TableContract.TYPE_INTEGER +
                " )";
        String SQL_CREATE_TABLE_MODE_SETTING =
                "CREATE TABLE" + WS + TableContract.TABLE_MODE + " ( " +
                TableContract._ID + WS + TableContract.TYPE_INTEGER + WS + " PRIMARY KEY " + CS +
                TableContract.T_MODE_USERID + WS + TableContract.TYPE_INTEGER + CS +
                TableContract.T_MODE_MODE + WS + TableContract.TYPE_INTEGER + CS +
                TableContract.T_MODE_STATE + WS + TableContract.TYPE_INTEGER +
                " )";
        String SQL_CREATE_TABLE_FREQSHIFT_SETTING =
                "CREATE TABLE" + WS + TableContract.TABLE_FREQSHIFT + " ( " +
                TableContract._ID + WS + TableContract.TYPE_INTEGER + WS + " PRIMARY KEY " + CS +
                TableContract.T_FREQSHIFT_USERID + WS + TableContract.TYPE_INTEGER + CS +
                TableContract.T_FREQSHIFT_SEMITONE + WS + TableContract.TYPE_INTEGER + CS +
                TableContract.T_FREQSHIFT_STATE + WS + TableContract.TYPE_INTEGER +
                " )";

        // exclude sqlite commands
        db.execSQL(SQL_CREATE_TABLE_UA);
        db.execSQL(SQL_CREATE_TABLE_SETTING);
        db.execSQL(SQL_CREATE_TABLE_IO_SETTING);
        db.execSQL(SQL_CREATE_TABLE_MODE_SETTING);
        db.execSQL(SQL_CREATE_TABLE_FREQSHIFT_SETTING);

        // initial db table columns
        // initial user_account table columns
        ContentValues values = new ContentValues();
        values.put(TableContract.T_UA_USERNAME, "user_001");  // root account
        values.put(TableContract.T_UA_USERACCOUNT, "aa");
        values.put(TableContract.T_UA_USERPASSWORD, "aa");
        values.put(TableContract.T_UA_STATE, 1);
        db.insert(TableContract.TABLE_USER_ACCOUNT, null, values);
        values = new ContentValues();
        values.put(TableContract.T_UA_USERNAME, "user_002");  // root account
        values.put(TableContract.T_UA_USERACCOUNT, "bb");
        values.put(TableContract.T_UA_USERPASSWORD, "bb");
        values.put(TableContract.T_UA_STATE, 1);
        db.insert(TableContract.TABLE_USER_ACCOUNT, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS" + WS;
        db.execSQL(SQL_DELETE_ENTRIES + TableContract.TABLE_USER_ACCOUNT);
        db.execSQL(SQL_DELETE_ENTRIES + TableContract.TABLE_SETTING);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
