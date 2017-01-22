package edu.gatech.seclass.gobowl;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.Date;

/**
 * Created by c on 7/13/16.
 */
public class DBCustomerSession extends DBObject {
    private long id;
    private long sessionId;
    private long customerId;
    private int score;

    public static final String TABLE_NAME = "customer_session";

    // SESSION Table - column names
    public static final String COLUMN_CS_ID = "_id";
    public static final String COLUMN_CS_SESSIONID = "session_id";
    public static final String COLUMN_CS_CUSTOMERID = "customer_id";
    public static final String COLUMN_CS_SCORE = "score";

    /*
    CREATE TABLE customer_session
            ( _id INTEGER PRIMARY KEY,
              session_id INTEGER,
              customer_id INTEGER,
              score INTEGER,
            );
    */

    public static final String CREATE_TABLE_CUSTOMERSESSION = "CREATE TABLE "
            + TABLE_NAME + "(" + COLUMN_CS_ID + " INTEGER PRIMARY KEY,"
            + COLUMN_CS_SESSIONID + " INTEGER,"
            + COLUMN_CS_CUSTOMERID + " INTEGER,"
            + COLUMN_CS_SCORE + " INTEGER DEFAULT 0" + ")";   // valid range of score is 0-300 in bowling

    public static final String INSERT_TABLE_CUSTOMERSESSION = "INSERT INTO "
            + TABLE_NAME + "("
            + COLUMN_CS_SESSIONID + ","
            + COLUMN_CS_CUSTOMERID + ","
            + COLUMN_CS_SCORE
            + ") VALUES "
            + "(1, 1, 90)," // Session 1 with first three players
            + "(1, 2, 80),"
            + "(1, 3, 80),"
            + "(2, 1, 120)," // Session 2 with mixed three players
            + "(2, 3, 100),"
            + "(2, 6, 0),"
            + "(3, 1, 100)," // Session 3 with six players
            + "(3, 2, 100),"
            + "(3, 3, 100),"
            + "(3, 4, 100),"
            + "(3, 5, 0),"
            + "(3, 6, 110)";


    private GoBowlDatabase myDb;

    /**
     * Session
     * @param db
     *
     * pass in initialized database
     * the object will initialize itself to something default
     * remember to populate any values after the constructor is called
     *
     */
    public DBCustomerSession(GoBowlDatabase db) {
        myDb = db;
    }

    /* IMPLEMENT ABSTRACT METHODS */

    public String getTableName() {
        return TABLE_NAME;
    }

    public ContentValues getValues() {
        ContentValues valueList = new ContentValues();

        valueList.put(COLUMN_CS_SESSIONID, sessionId);
        valueList.put(COLUMN_CS_CUSTOMERID, customerId);
        valueList.put(COLUMN_CS_SCORE, score);

        return valueList;
    }

    /* BUSINESS LOGIC METHODS */


    /* GETTERS AND SETTERS */

    public long create(long sessionId, long customerId) {
        this.sessionId = sessionId;
        this.customerId = customerId;

        long id = myDb.addObject(this);
        this.sessionId = id;

        return id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSessionId() {
        String selectQuery = "SELECT  * FROM " + getTableName()
                + " WHERE " + DBCustomerSession.COLUMN_CS_ID + " = " + this.id;

        Cursor c = myDb.getObject(selectQuery);

        if (c != null)
            c.moveToFirst();

        sessionId = c.getInt(c.getColumnIndex(COLUMN_CS_SESSIONID));

        c.close();

        return sessionId;
    }

    public int setSessionId(long sessionId) {
        this.sessionId = sessionId;

        ContentValues values = new ContentValues();
        values.put(COLUMN_CS_SESSIONID, sessionId);

        int open = myDb.updateObject(getTableName(), COLUMN_CS_SESSIONID, sessionId, values);

        return open;
    }

    public long getCustomerId() {
        String selectQuery = "SELECT  * FROM " + getTableName()
                + " WHERE " + DBCustomerSession.COLUMN_CS_ID + " = " + this.id;

        Cursor c = myDb.getObject(selectQuery);

        if (c != null)
            c.moveToFirst();

        customerId = c.getInt(c.getColumnIndex(COLUMN_CS_CUSTOMERID));
        c.close();

        return customerId;
    }

    public int setCustomerId(long customerId) {
        this.customerId = customerId;
        ContentValues values = new ContentValues();
        values.put(COLUMN_CS_CUSTOMERID, customerId);

        int open = myDb.updateObject(getTableName(), COLUMN_CS_CUSTOMERID, customerId, values);

        return open;
    }

    public int getScore() {
        String selectQuery = "SELECT  * FROM " + getTableName()
                + " WHERE " + DBCustomerSession.COLUMN_CS_ID + " = " + this.id;

        Cursor c = myDb.getObject(selectQuery);

        if (c != null)
            c.moveToFirst();

        score = c.getInt(c.getColumnIndex(COLUMN_CS_SCORE));
        c.close();

        return score;
    }

    public int setScore(int score) {
        this.score = score;

        ContentValues values = new ContentValues();
        values.put(COLUMN_CS_SCORE, customerId);

        int open = myDb.updateObject(getTableName(), COLUMN_CS_SCORE, customerId, values);

        return open;
    }

    public int getCustomerCountBySession(int sessionId) {

        String selectQuery = "SELECT count(*) as cust_count FROM " + getTableName()
                + " WHERE " + DBCustomerSession.COLUMN_CS_SESSIONID+ " = " + sessionId;

        Cursor c = myDb.getObject(selectQuery);

        if (c != null)
            c.moveToFirst();

        int CustomerCount = c.getInt(c.getColumnIndex("cust_count"));

        //c.close();

        return CustomerCount;
    }

}
