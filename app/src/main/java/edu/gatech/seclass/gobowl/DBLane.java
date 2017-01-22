package edu.gatech.seclass.gobowl;

import android.database.Cursor;
import android.content.ContentValues;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by mcgarrah on 7/13/2016.
 */
public class DBLane extends DBObject {

    private long _id; // should be unique
    private int sessionId;
    private int startDateTimestamp;
    private int stopDateTimestamp;
    private boolean isReserved;

    public static final String TABLE_NAME = "lane";

    // SESSION Table - column names
    public static final String COLUMN_LANE_ID = "_id";
    public static final String COLUMN_LANE_SESSION_ID = "session_id";
    public static final String COLUMN_LANE_START_DATETIME = "start_datetime";
    public static final String COLUMN_LANE_END_DATETIME = "end_datetime";
    public static final String COLUMN_LANE_STATUS = "lane_reserved";


    public static final String CREATE_TABLE_LANE = "CREATE TABLE "
            + TABLE_NAME + "(" + COLUMN_LANE_ID + " INTEGER PRIMARY KEY,"
            + COLUMN_LANE_SESSION_ID + " INTEGER,"
            + COLUMN_LANE_START_DATETIME + " DATETIME,"
            + COLUMN_LANE_END_DATETIME + " DATETIME,"
            + COLUMN_LANE_STATUS + " INTEGER DEFAULT 0" + ")";

    public static final String INSERT_TABLE_LANE = "INSERT INTO "
            + TABLE_NAME + "("
            + COLUMN_LANE_SESSION_ID + ","
            + COLUMN_LANE_START_DATETIME + ","
            + COLUMN_LANE_END_DATETIME + ","
            + COLUMN_LANE_STATUS
            + ") VALUES "
            + "(1, 150, 200, 1),"
            + "(2, 5000, 5300, 0),"
            + "(3, 5000, 5200, 1),"
            + "(4, 5000, 5100, 0),"
            + "(5, 10000,10100, 1),"
            + "(6, 0, 0, 0),"
            + "(7, 0, 0, 0),"
            + "(8, 0, 0, 0),"
            + "(9, 0, 0, 0),"
            + "(10, 0, 0, 0)";

    private GoBowlDatabase myDb;

    public DBLane(GoBowlDatabase db) {
        myDb = db;

    }

    public String getTableName() {
        return TABLE_NAME;
    }

    public ContentValues getValues() {
        ContentValues valueList = new ContentValues();

        valueList.put(COLUMN_LANE_SESSION_ID, sessionId);
        valueList.put(COLUMN_LANE_START_DATETIME, startDateTimestamp);
        valueList.put(COLUMN_LANE_END_DATETIME, stopDateTimestamp);
        valueList.put(COLUMN_LANE_STATUS, isReserved);

        return valueList;
    }

    public long getLaneId() {
        return _id;
    }


    public int getSessionId() {

        String selectQuery = "SELECT  * FROM " + getTableName()
                + " WHERE " + DBLane.COLUMN_LANE_ID + " = " + this.getLaneId();

        Cursor c = LandingActivity.gb.getObject(selectQuery);

        if (c != null)
            c.moveToFirst();

        sessionId = c.getInt(c.getColumnIndex(COLUMN_LANE_SESSION_ID));

        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public int getStartTime() {

        String selectQuery = "SELECT  * FROM " + getTableName()
                + " WHERE " + DBLane.COLUMN_LANE_ID + " = " + this.getLaneId();

        Cursor c = LandingActivity.gb.getObject(selectQuery);

        if (c != null)
            c.moveToFirst();

        startDateTimestamp = c.getInt(c.getColumnIndex(COLUMN_LANE_START_DATETIME));

        return startDateTimestamp;
    }

    public void setStartTime(int startDateTimestamp) {
        this.startDateTimestamp = startDateTimestamp;
    }

    public int getStopTime() {

        String selectQuery = "SELECT  * FROM " + getTableName()
                + " WHERE " + DBLane.COLUMN_LANE_ID + " = " + this.getLaneId();

        Cursor c = LandingActivity.gb.getObject(selectQuery);

        if (c != null)
            c.moveToFirst();

        stopDateTimestamp = c.getInt(c.getColumnIndex(COLUMN_LANE_END_DATETIME));

        return stopDateTimestamp;
    }

    public void setStopTime(int stopDateTimestamp) {
        this.stopDateTimestamp = stopDateTimestamp;
    }


    public boolean getStatus() {

        String selectQuery = "SELECT  * FROM " + getTableName()
                + " WHERE " + DBLane.COLUMN_LANE_ID + " = " + this.getLaneId();

        Cursor c = LandingActivity.gb.getObject(selectQuery);

        if (c != null)
            c.moveToFirst();

        isReserved = (c.getInt(c.getColumnIndex(COLUMN_LANE_STATUS))==1);

        return isReserved;
    }

    public void setStatus(boolean isReserved) {
        this.isReserved = isReserved;
    }

    public int setStatus(long laneId, boolean isReserved) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_LANE_STATUS, isReserved);

        int open = myDb.updateObject(getTableName(), COLUMN_LANE_ID, laneId, values);

        return open;

    }


    // Business Logic


    public long getEmptyLane() {

        // TODO: Handle the case where there is no available lane

        String selectQuery = "SELECT  * FROM " + getTableName()
                + " WHERE " + DBLane.COLUMN_LANE_STATUS + " != 0"
                + " ORDER BY RANDOM() LIMIT 1";
        Cursor c = LandingActivity.gb.getObject(selectQuery);

        if (c != null)
            c.moveToFirst();

        long emptyLane;
        if (c.getCount() <= 0) {
            emptyLane = -1;
        } else {
            emptyLane = c.getInt(c.getColumnIndex(COLUMN_LANE_ID));
        }

        return emptyLane;
    }

    public Cursor getActiveLanes() {

        DBSession session = new DBSession(this.myDb);
        DBCustomer cust = new DBCustomer(this.myDb);

        String selectQuery = "SELECT * "
                            + " FROM " + this.getTableName() + ","
                                        + session.getTableName() + ","
                                        + cust.getTableName()
                            + " WHERE " + this.getTableName()+"."+ COLUMN_LANE_SESSION_ID + " = "
                                        + session.getTableName()+"."+ DBSession.COLUMN_SESSION_ID
                            + " AND " + session.getTableName()+"."+ DBSession.COLUMN_SESSION_LANE_OWNER + " = "
                                        + cust.getTableName()+"."+ DBCustomer.COLUMN_CUSTOMER_ID
                            + " AND " + COLUMN_LANE_STATUS + " = 1";

        Cursor c = LandingActivity.gb.getObject(selectQuery);

        if (c != null)
            c.moveToFirst();

        return c;
    }

    public long getLaneCount() {
        return myDb.getTableRowCount(this.getTableName());
    }

    public static int getLaneCostPerHour(Date timestamp) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(timestamp);

        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int day = cal.get(Calendar.DAY_OF_WEEK);

        int cost = 0;

        if (day == 1) {  // 1 = SUNDAY
            cost = 10;
        } else if (day == 4) { // 4 = Wednesday
            cost = 30;
        } else {
            if ((hour >= 8) && (hour <= 16 )) {  // hour 0 - 23, 9a = 8, 5p = 16
                cost = 20;
            } else {
                cost = 25;  // making wild assumptions about the timestamp here...
            }

        }

        return cost;

    }

    // TODO: SIMPLIFYING ASSUMPTION: time on the resolution of an hour is ok, round accordingly
    public static int getTotalLaneCost(Date startTime, Date stopTime) {
        Calendar start = Calendar.getInstance();
        start.setTime(startTime);

        Calendar stop = Calendar.getInstance();
        stop.setTime(stopTime);

        int startHour = start.get(Calendar.HOUR_OF_DAY);
        int stopHour = stop.get(Calendar.HOUR_OF_DAY);

        int startMinute = start.get(Calendar.MINUTE);
        int stopMinute = start.get(Calendar.MINUTE);

        int minuteDiff = Math.abs(stopMinute - startMinute);  // TODO: this isn't correct...
        int adjust = 0;
        if (minuteDiff > 15) {
            adjust = 1;
        }

        int elapsedTime = stopHour - startHour + adjust;

        int costPerHour = DBLane.getLaneCostPerHour(startTime);
        int cost = elapsedTime * costPerHour;

        return cost;
    }

}
