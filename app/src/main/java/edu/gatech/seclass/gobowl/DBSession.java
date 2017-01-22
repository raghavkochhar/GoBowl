package edu.gatech.seclass.gobowl;

import android.content.ContentValues;
import android.database.Cursor;
import java.util.Date;

/**
 * Created by c on 7/13/16.
 */
public class DBSession extends DBObject {

    private long sessionId; // should be unique
    private int laneRequesterId;
    //private int bowlDate;
    private long bowlDateStart;
    private long bowlDateStop;
    private int bowlBill;
    private int lane;
    //private boolean isCheckedOut;

    public static final String TABLE_NAME = "session";

    // SESSION Table - column names
    public static final String COLUMN_SESSION_ID = "_id";
    public static final String COLUMN_SESSION_LANE_OWNER = "lane_owner";
    //public static final String COLUMN_SESSION_DATETIME = "datetime";
    public static final String COLUMN_SESSION_STARTTIME = "start_time";
    public static final String COLUMN_SESSION_STOPTIME = "stop_time";
    public static final String COLUMN_SESSION_BILL = "bill";
    public static final String COLUMN_SESSION_LANE = "lane";
    //public static final String COLUMN_SESSION_STATUS = "checkout_status";

    /*
    CREATE TABLE session
            ( sessionid INTEGER PRIMARY KEY,
              laneRequestId INTEGER,
              bowldate INTEGER,
              bowlbill INTEGER,
              assignedLane INTEGER,
              ischeckedout INTEGER
            );
    */

    public static final String CREATE_TABLE_SESSION = "CREATE TABLE "
            + TABLE_NAME + "(" + COLUMN_SESSION_ID + " INTEGER PRIMARY KEY,"
            + COLUMN_SESSION_LANE_OWNER + " INTEGER,"
            //+ COLUMN_SESSION_DATETIME + " INTEGER,"
            + COLUMN_SESSION_STARTTIME + " INTEGER,"
            + COLUMN_SESSION_STOPTIME + " INTEGER,"
            + COLUMN_SESSION_BILL + " INTEGER,"
            + COLUMN_SESSION_LANE + " INTEGER" + ")";

    public static final String INSERT_TABLE_SESSION = "INSERT INTO "
            + TABLE_NAME + "("
            + COLUMN_SESSION_LANE_OWNER + ","
//            + COLUMN_SESSION_DATETIME + ","
            + COLUMN_SESSION_STARTTIME + ","
            + COLUMN_SESSION_STOPTIME + ","
            + COLUMN_SESSION_BILL + ","
            + COLUMN_SESSION_LANE
//            + COLUMN_SESSION_STATUS
            + ") VALUES "
            + "(1, 1468548883070, 1468548883075, 10, 1),"  // fastest games on record
            + "(2, 1468548883071, 1468548883076, 15, 2),"
            + "(3, 1468548883072, 1468548883077, 10, 4),"
            + "(4, 1468548883073, 1468548883078, 20, 3),"
            + "(5, 1468548883074, 1468548883079, 30, 5)";

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
    public DBSession(GoBowlDatabase db) {
        myDb = db;
    }

    /* IMPLEMENT ABSTRACT METHODS */

    public String getTableName() {
        return TABLE_NAME;
    }

    public ContentValues getValues() {
        ContentValues valueList = new ContentValues();

        valueList.put(COLUMN_SESSION_LANE_OWNER, laneRequesterId);
        valueList.put(COLUMN_SESSION_STARTTIME, bowlDateStart);
        valueList.put(COLUMN_SESSION_STOPTIME, bowlDateStop);
        //valueList.put(COLUMN_SESSION_DATETIME, bowlDate);
        valueList.put(COLUMN_SESSION_BILL, bowlBill);
        valueList.put(COLUMN_SESSION_LANE, lane);
        //valueList.put(COLUMN_SESSION_STATUS, isCheckedOut);

        return valueList;
    }

    /* BUSINESS LOGIC METHODS */


    /* GETTERS AND SETTERS */
    public long create(Date createTimestamp) {
        bowlBill = 0;
        bowlDateStart = createTimestamp.getTime();
        bowlDateStop = 0;
        lane = 0;
        laneRequesterId = 0;
        //isCheckedOut = false;

        long id = myDb.addObject(this);
        this.sessionId = id;

        return id;
    }

    public long getId() {
        return this.sessionId;
    }

    public int getLaneRequesterId() {

        String selectQuery = "SELECT  * FROM " + getTableName()
                + " WHERE " + DBSession.COLUMN_SESSION_ID + " = " + this.getSessionId();

        Cursor c = myDb.getObject(selectQuery);

        if (c != null)
            c.moveToFirst();

        laneRequesterId = c.getInt(c.getColumnIndex(COLUMN_SESSION_LANE_OWNER));

        c.close();

        return laneRequesterId;
    }


    /**
     *
     * @param laneRequesterId
     * @return
     *
     *         String updateQuery = "UPDATE " + getTableName() +
     *         " SET " + COLUMN_SESSION_LANEOWNER + " = " + laneRequesterId +
     *         " WHERE " + COLUMN_SESSION_ID + " = " + this.getSessionId();
     */
    public int setLaneRequesterId(int laneRequesterId) {
        this.laneRequesterId = laneRequesterId;  // i think this is unnecessary, only session_id needed

        ContentValues values = new ContentValues();
        values.put(COLUMN_SESSION_LANE_OWNER, laneRequesterId);

        int open = myDb.updateObject(getTableName(), COLUMN_SESSION_ID, this.getSessionId(), values);

        return open;
    }

    public int setLane(long sessionId, long lane) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_SESSION_LANE, lane);

        int open = myDb.updateObject(getTableName(), COLUMN_SESSION_ID, sessionId, values);

        return open;
    }

    public long getLane() {

        String selectQuery = "SELECT  * FROM " + getTableName()
                + " WHERE " + DBSession.COLUMN_SESSION_ID + " = " + this.getSessionId();

        Cursor c = myDb.getObject(selectQuery);

        if (c != null)
            c.moveToFirst();

        lane = c.getInt(c.getColumnIndex(COLUMN_SESSION_LANE));

        return lane;
    }

    public long getBowlDateStart() {

        String selectQuery = "SELECT  * FROM " + getTableName()
                + " WHERE " + DBSession.COLUMN_SESSION_ID + " = " + this.getSessionId();

        Cursor c = myDb.getObject(selectQuery);

        if (c != null)
            c.moveToFirst();

        bowlDateStart = c.getLong(c.getColumnIndex(COLUMN_SESSION_STARTTIME));

        return bowlDateStart;
    }

    public void setBowlDateStart(int bowlDate) {
        this.bowlDateStart = bowlDate;
    }

    public long getBowlDateStop() {

        String selectQuery = "SELECT  * FROM " + getTableName()
                + " WHERE " + DBSession.COLUMN_SESSION_ID + " = " + this.getSessionId();

        Cursor c = myDb.getObject(selectQuery);

        if (c != null)
            c.moveToFirst();

        bowlDateStart = c.getLong(c.getColumnIndex(COLUMN_SESSION_STOPTIME));

        return bowlDateStart;
    }

    public void setBowlDateStop(int bowlDate) {
        this.bowlDateStop = bowlDate;
    }

    public int getBowlBill() {

        String selectQuery = "SELECT  * FROM " + getTableName()
                + " WHERE " + DBSession.COLUMN_SESSION_ID + " = " + this.getSessionId();

        Cursor c = myDb.getObject(selectQuery);

        if (c != null)
            c.moveToFirst();

        bowlBill = c.getInt(c.getColumnIndex(COLUMN_SESSION_BILL));

        return bowlBill;
    }

    public void setBowlBill(int bowlBill) {
        this.bowlBill = bowlBill;
    }


    public long getSessionId() {
        return sessionId;
    }

    public int getNumberOfSessions() {
        return myDb.getTableRowCount(this.getTableName());
    }



}
