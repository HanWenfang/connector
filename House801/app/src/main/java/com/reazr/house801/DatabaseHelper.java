package com.reazr.house801;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

/**
 * Created by susan on 15-11-3.
 */
public class DatabaseHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "reazr";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_CONNECTOR = "connector";
    private static final String KEY_CONNECTOR_ID = "id";
    private static final String KEY_CONNECTOR_TYPE = "type";// tcp -> 1, http -> 2, web -> 3, udp -> 4
    private static final String KEY_CONNECTOR_HOST = "host";
    private static final String KEY_CONNECTOR_PORT = "port";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONNECTOR_TABLE = "CREATE TABLE " + TABLE_CONNECTOR +
                "( " +
                    KEY_CONNECTOR_ID + " INTEGER PRIMARY KEY," +
                    KEY_CONNECTOR_HOST + " TEXT," +
                    KEY_CONNECTOR_PORT + " INTEGER" +
                ")";
        db.execSQL(CREATE_CONNECTOR_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {

            
        }
    }
}
