package com.reazr.house801;

/*
* http://guides.codepath.com/android/Local-Databases-with-SQLiteOpenHelper
* http://www.open-open.com/lib/view/open1328168315031.html
* http://www.cnblogs.com/Excellent/archive/2011/11/19/2254888.html
* */
import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by susan on 15-11-3.
 */
public class DatabaseHelper extends SQLiteOpenHelper{

    private static DatabaseHelper sInstance;

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
                    KEY_CONNECTOR_ID + " INTEGER PRIMARY KEY, " +
                    KEY_CONNECTOR_TYPE + " INTEGER, " +
                    KEY_CONNECTOR_HOST + " TEXT, " +
                    KEY_CONNECTOR_PORT + " INTEGER" +
                ")";
        db.execSQL(CREATE_CONNECTOR_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {


        }
    }

    public static synchronized DatabaseHelper getsInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    public long insertConnector(Connector connector) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_CONNECTOR_TYPE, connector.type);
        contentValues.put(KEY_CONNECTOR_HOST, connector.host);
        contentValues.put(KEY_CONNECTOR_PORT, connector.port);
        long connectorId = -1;

        db.beginTransaction();
        try {
            connectorId = db.insertOrThrow(TABLE_CONNECTOR, null, contentValues);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        } finally {
            db.endTransaction();
        }

        return connectorId;
    }

    public long updateConnector(Connector connector) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_CONNECTOR_ID, connector.cid);
        contentValues.put(KEY_CONNECTOR_TYPE, connector.type);
        contentValues.put(KEY_CONNECTOR_HOST, connector.host);
        contentValues.put(KEY_CONNECTOR_PORT, connector.port);

        return db.update(TABLE_CONNECTOR, contentValues, "id=?", new String[]{ String.valueOf(connector.cid) });
    }

    public long deleteConnector(Connector connector) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        long cid = -1;
        try {
            cid= db.delete(TABLE_CONNECTOR, "id=?", new String[]{ String.valueOf(connector.cid) });
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }

        return cid;
    }

    public List<Connector> getAllConnector() {
        List<Connector> connectors = new ArrayList<Connector>();
        String query = String.format("select * from %s", TABLE_CONNECTOR);
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    Connector connector = new Connector();
                    connector.cid = cursor.getInt(cursor.getColumnIndex(KEY_CONNECTOR_ID));
                    connector.type = cursor.getInt(cursor.getColumnIndex(KEY_CONNECTOR_TYPE));
                    connector.host = cursor.getString(cursor.getColumnIndex(KEY_CONNECTOR_HOST));
                    connector.port = cursor.getInt(cursor.getColumnIndex(KEY_CONNECTOR_PORT));

                    connectors.add(connector);
                } while (cursor.moveToFirst());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null && cursor.isClosed()) {
                cursor.close();
            }
        }

        return connectors;
    }


}
