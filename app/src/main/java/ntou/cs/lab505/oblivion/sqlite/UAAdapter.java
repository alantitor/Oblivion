package ntou.cs.lab505.oblivion.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import ntou.cs.lab505.oblivion.parameters.Record;

/**
 * Created by alan on 4/9/15.
 */
public class UAAdapter {

    Context mCtx;
    DBHelper mDbHelper;
    SQLiteDatabase mDb;

    public UAAdapter(Context context) {
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

    public boolean autoLoginAccount() {

        String[] projection = {TableContract._ID,
                               TableContract.T_S_USERID,
                               TableContract.T_S_USEIT,
                               TableContract.T_S_STATE};
        String selection = TableContract.T_S_USEIT + " = ? ";
        String[] selectionArgs = {"1"};
        String sortOrder = "";
        Cursor c = mDb.query(TableContract.TABLE_SETTING, projection, selection, selectionArgs, null, null, sortOrder);
        c.moveToFirst();

        if (c.getCount() != 1) {
            Log.d("UAAdapter", "setting table \"USEIT\" value not correct");
            return false;
        }

        Record.USER_SETTING_ID = Long.parseLong(c.getString(c.getColumnIndex(TableContract._ID)));
        Record.USERID = Long.parseLong(c.getString(c.getColumnIndex(TableContract.T_S_USERID)));

        String[] projection2 = {TableContract._ID,
                                TableContract.T_UA_USERNAME};
        String selection2 = TableContract._ID + " = ? ";
        String[] selectionArgs2 = {Record.USERID + ""};
        String sortOrder2 = "";
        c = mDb.query(TableContract.TABLE_USER_ACCOUNT, projection2, selection2, selectionArgs2, null, null, sortOrder2);
        c.moveToFirst();

        if (c.getCount() != 1) {
            Log.d("UAAdapter", "user_account table \"_ID\" value not correct");
            return false;
        }
        Record.USERNAME = c.getString(c.getColumnIndex(TableContract.T_UA_USERNAME));

        return true;
    }

    /**
     * check user account state and save relatively data.
     * @param account
     * @param password
     * @return
     */
    public boolean checkAccount(String account, String password) {
        /**
         * (1) process input data.
         * (2) query database to find account id in "user_account" table.
         * (3) query database to find setting id in "setting" table.
         */

        boolean checkFlag = false;


        // preprocessing data.

        /*
         *
         *
         *
         */


        // query database
        String[] projection = {TableContract._ID,
                TableContract.T_UA_USERACCOUNT,
                TableContract.T_UA_USERPASSWORD,
                TableContract.T_UA_USERNAME,
                TableContract.T_UA_STATE};
        String selection = TableContract.T_UA_USERACCOUNT + " = ? AND " + TableContract.T_UA_USERPASSWORD + " = ? ";
        String[] selectionArgs = {account, password};
        String sortOrder = "";
        Cursor c = mDb.query(TableContract.TABLE_USER_ACCOUNT, projection, selection, selectionArgs, null, null, sortOrder);
        c.moveToFirst();

        // check data is exist or not.
        if (c.getCount() == 0) {
            Log.d("UAAdapter", "account not found");
            return false;
        } else {
            // check data state.
            int db_state = Integer.parseInt(c.getString(c.getColumnIndex(TableContract.T_UA_STATE)));
            if (db_state == 0) {
                Log.d("UAAdapter", "account unavailable");
                return false;
            }

            // process data.
            long db_id = Long.parseLong((c.getString(c.getColumnIndex(TableContract._ID))));
            String db_username = c.getString(c.getColumnIndex(TableContract.T_UA_USERNAME));
            String db_account = c.getString(c.getColumnIndex(TableContract.T_UA_USERACCOUNT));
            String db_password = c.getString(c.getColumnIndex(TableContract.T_UA_USERPASSWORD));

            if (account.equals(db_account) == false && password.equals(db_password) == false) {
                Log.d("UAAdapter", "account input data wrong");
                return false;
            }

            // record data.
            Record.USERID = db_id;  // save user id to global variable.
            Record.USERNAME = db_username;  // saver user name to global variable.
            checkFlag = true;  // <--!!!
        }


        // reset "setting" table.
        String[] projection2 = {TableContract._ID,
                                TableContract.T_S_USEIT};
        String selection2 = TableContract.T_S_USEIT + " = ? ";
        String[] selectionArgs2 = {"1"};
        String sortOrder2 = "";
        c = mDb.query(TableContract.TABLE_SETTING, projection2, selection2, selectionArgs2, null, null, sortOrder2);
        c.moveToFirst();
        while (c.isAfterLast() == false) {
            Long tID = Long.parseLong(c.getString(c.getColumnIndex(TableContract._ID)));
            mDb.execSQL("UPDATE " + TableContract.TABLE_SETTING
                        + " SET " + TableContract.T_S_USEIT + " = 0 "
                        + " WHERE " + TableContract._ID + " = " + tID);
            Log.d("UAAdapter", "change setting USEIT value of " + tID);
            c.moveToNext();
        }


        // handle "setting" table.
        String[] projection3 = {TableContract._ID,
                                TableContract.T_S_USERID,
                                TableContract.T_S_USEIT,
                                TableContract.T_S_STATE};
        String selection3 = TableContract.T_S_USERID + " = ? ";
        String[] selectionArgs3 = {Long.toString(Record.USERID)};
        String sortOrder3 = "";
        c = mDb.query(TableContract.TABLE_SETTING, projection3, selection3, selectionArgs3, null, null, sortOrder3);
        c.moveToFirst();

        long setting_id = -1;
        if (c.getCount() == 0) {
            // create data.
            Log.d("UAAdapter", "create setting data");
            ContentValues insertValues = new ContentValues();
            insertValues.put(TableContract.T_S_USERID, Record.USERID);
            insertValues.put(TableContract.T_S_USEIT, 1);
            insertValues.put(TableContract.T_S_STATE, 1);
            setting_id = mDb.insert(TableContract.TABLE_SETTING, null, insertValues);
            Record.USER_SETTING_ID = setting_id;
            checkFlag = true;
        } else {
            int db_state = Integer.parseInt(c.getString(c.getColumnIndex(TableContract.T_S_STATE)));
            if (db_state == 0) {
                Log.d("UAAdapter", "setting table state = 0");
                return false;
            }

            // change state.
            setting_id = Long.parseLong(c.getString(c.getColumnIndex(TableContract._ID)));
            mDb.execSQL("UPDATE " + TableContract.TABLE_SETTING
                        + " SET " + TableContract.T_S_USEIT + " = 1 "
                        + " WHERE " + TableContract.T_S_USERID + " = " + setting_id);
            // record data.
            Record.USER_SETTING_ID = setting_id;
            Log.d("UAAdapter", "setting_id: " + setting_id);
            checkFlag = true;
        }


        return checkFlag;
    }

    public boolean register(String userName, String account, String password) {

        boolean checkFlag = false;

        // write code.

        return checkFlag;
    }

    public void logout() {

        // reset "setting" table.
        String[] projection = {TableContract._ID,
                TableContract.T_S_USEIT};
        String selection = TableContract.T_S_USEIT + " = ? ";
        String[] selectionArgs = {"1"};
        String sortOrder = "";
        Cursor c = mDb.query(TableContract.TABLE_SETTING, projection, selection, selectionArgs, null, null, sortOrder);
        c.moveToFirst();
        while (c.isAfterLast() == false) {
            Long tID = Long.parseLong(c.getString(c.getColumnIndex(TableContract._ID)));
            mDb.execSQL("UPDATE " + TableContract.TABLE_SETTING
                    + " SET " + TableContract.T_S_USEIT + " = 0 "
                    + " WHERE " + TableContract._ID + " = " + tID);
            Log.d("UAAdapter", "change setting USEIT value of " + tID);
            c.moveToNext();
        }

        // clear global values.
        Record.USERID = -1;
        Record.USERNAME = "";
        Record.USER_SETTING_ID = -1;

        return ;
    }
}
