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
    public static final String TABLE_IO = "io_setting";
    public static final String TABLE_MODE = "mode_setting";
    public static final String TABLE_FREQSHIFT = "freqshift_setting";
    public static final String TABLE_BAND = "band_setting";
    public static final String TABLE_GAIN = "gain_setting";
    // sqlite db columns name
    // user_account table content
    public static final String T_UA_USERNAME = "user_name";
    public static final String T_UA_USERACCOUNT = "user_account";
    public static final String T_UA_USERPASSWORD = "user_password";
    public static final String T_UA_STATE = "state";
    // setting table content
    public static final String T_S_USERID = "user_id";
    public static final String T_S_USEIT = "use_it";
    public static final String T_S_STATE = "state";
    // io_setting table content
    public static final String T_IO_USERID = "user_id";
    public static final String T_IO_INPUT = "input";
    public static final String T_IO_OUTPUT = "output";
    public static final String T_IO_STATE = "state";
    // mode_setting table content
    public static final String T_MODE_USERID = "user_id";
    public static final String T_MODE_MODE = "mode";
    public static final String T_MODE_STATE = "state";
    // freqshift_setting table content
    public static final String T_FREQSHIFT_USERID = "user_id";
    public static final String T_FREQSHIFT_SEMITONE = "semitone";
    public static final String T_FREQSHIFT_STATE = "state";
    // band_setting table content
    public static final String T_BAND_USERID = "user_id";
    public static final String T_BAND_GROUPID = "group_id";
    public static final String T_BAND_LOWBAND = "lowband";
    public static final String T_BAND_HIGHBAND = "highband";
    public static final String T_BAND_STATE = "state";
    // gain_setting table content
    public static final String T_GAIN_USERID = "user_id";
    public static final String T_GAIN_GROUPID = "group_id";
    public static final String T_GAIN_L40 = "l40";
    public static final String T_GAIN_L60 = "l60";
    public static final String T_GAIN_L80 = "l80";
    public static final String T_GAIN_r40 = "r40";
    public static final String T_GAIN_r60 = "r60";
    public static final String T_GAIN_r80 = "r80";
    public static final String T_GAIN_STATE = "state";
}
