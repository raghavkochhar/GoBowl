package edu.gatech.seclass.gobowl;

/**
 * Created by c on 7/8/16.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class GoBowlDatabase extends SQLiteOpenHelper {
    // instance
    private static GoBowlDatabase myInstance;

    // Database Version
    // TODO: After every change to the database structure this increments
    private static final int DATABASE_VERSION = 5;

    // Database Name
    private static final String DATABASE_NAME = "gobowl";


    // ***********************
    // Set up the singleton pattern
    // ***********************

    public static synchronized GoBowlDatabase getInstance(Context context) {

        // See notes 1
        if (myInstance == null) {
            myInstance = new GoBowlDatabase(context.getApplicationContext());
        }
        return myInstance;
    }

    private GoBowlDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // ***********************
    // Table Create
    // ***********************

    @Override
    public void onCreate(SQLiteDatabase db) {

        // creating required tables

        db.execSQL(DBCustomer.CREATE_TABLE_CUSTOMER);
        db.execSQL(DBSession.CREATE_TABLE_SESSION);
        db.execSQL(DBCustomerSession.CREATE_TABLE_CUSTOMERSESSION);
        db.execSQL(DBLane.CREATE_TABLE_LANE);

        // adding test data to tables
        db.execSQL(DBCustomer.INSERT_TABLE_CUSTOMER);
        db.execSQL(DBSession.INSERT_TABLE_SESSION);
        db.execSQL(DBCustomerSession.INSERT_TABLE_CUSTOMERSESSION);
        db.execSQL(DBLane.INSERT_TABLE_LANE);

    }

    // ***********************
    // Table Management
    // ***********************

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + DBCustomer.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DBSession.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DBCustomerSession.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DBLane.TABLE_NAME);

        // create new tables
        onCreate(db);
    }

    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }


    // **********************
    // Table Methods
    // **********************

    public long addObject(DBObject myObject) {
        SQLiteDatabase db = this.getWritableDatabase();

        long row_id = db.insert(myObject.getTableName(), null, myObject.getValues());

        return row_id;
    }

    public int updateObject(String tableName, String pkColumnName, long pkColumnValue, ContentValues values) {
        SQLiteDatabase db = this.getWritableDatabase();

        int rc = db.update(tableName, values, pkColumnName + " = ?",
                new String[] { String.valueOf(pkColumnValue) });

        return rc;
    }

    public Cursor getObject(String selectQuery) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery(selectQuery, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    public Cursor getObject(String tableName, String[] columns) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.query(tableName, columns, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    public void deleteObject(String tableName, String pkColumnName, int pkColumnValue) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(tableName, pkColumnName + " = ?",
                new String[] { String.valueOf(pkColumnValue) });

    }

    public void clearTable(String tableName) {
        SQLiteDatabase db = this.getWritableDatabase();

        int count = db.delete(tableName, null, null);

    }

    public String getTableAsText(String tableName) {
        String text = "";
        String selectQuery = "SELECT  * FROM " + tableName;
        SQLiteDatabase db = this.getReadableDatabase();

        int count = getTableRowCount(tableName);

        text += ("There are " + count + " items in " + tableName + "\n\n");

        Cursor cursor = db.rawQuery(selectQuery, null);
        String cols = "";

        int numCols = cursor.getColumnCount();
        for (int i=0; i < numCols; i++) {
            cols += (cursor.getColumnName(i) + " ");
        }
        text += (cols + "\n\n");

        if (cursor.moveToFirst()) {
            do {
                String line = "";
                for (int i=0; i < numCols; i++) {
                    line += (cursor.getString(i) + " ");
                }
                text += (line + "\n");
            } while (cursor.moveToNext());
        }

        cursor.close();

        return text;
    }

    public int getTableRowCount(String tableName) {
        String countQuery = "SELECT  * FROM " + tableName;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();

        return count;
    }

    public String getSQLiteVersion() {
        String versionQuery = "select sqlite_version() AS sqlite_version";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(versionQuery, null);

        String sqliteVersion = "";
        while(cursor.moveToNext()){
            sqliteVersion += cursor.getString(0);
        }
        return sqliteVersion;
    }

}
