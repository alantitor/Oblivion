package ntou.cs.lab505.oblivion.sqlite;

import android.provider.BaseColumns;

/**
 * Created by alan on 4/9/15.
 */
public final class TableContract implements BaseColumns{

    // sqlite db information
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "ha";
    public static final String TYPE_NULL = "NULL";
    public static final String TYPE_INTEGER = "INTEGER";
    public static final String TYPE_REAL = "REAL";
    public static final String TYPE_TEXT = "TEXT";
    public static final String TYPE_BLOB = "BLOB";
    // sqlite table name
    public static final String TABLE_USER_ACCOUNT = "user_account";
    public static final String TABLE_SETTING = "setting";
    // sqlite db columns name
    // user_account table
    public static final String T_UA_USERNAME = "user_name";
    public static final String T_UA_USERACCOUNT = "user_account";
    public static final String T_UA_USERPASSWORD = "user_password";
    public static final String T_UA_STATE = "state";
    // setting table
    public static final String T_S_USERID = "user_id";
    public static final String T_S_USEIT = "use_it";
    public static final String T_S_STATE = "state";

}
