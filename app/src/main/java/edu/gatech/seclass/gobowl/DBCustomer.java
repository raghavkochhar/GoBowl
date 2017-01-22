package edu.gatech.seclass.gobowl;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Created by mcgarrah on 7/8/2016.
 */

public class DBCustomer extends DBObject {

    private long customerId; // should be unique
    private String fname;
    private String lname;
    private String email;


    public static final String TABLE_NAME = "customer";

    // SESSION Table - column names
    public static final String COLUMN_CUSTOMER_ID = "_id";
    public static final String COLUMN_CUSTOMER_FNAME = "fname";
    public static final String COLUMN_CUSTOMER_LNAME = "lname";
    public static final String COLUMN_CUSTOMER_EMAIL = "email";

    /*
    CREATE TABLE customer
            ( _id INTEGER PRIMARY KEY,
              name TEXT,
              email TEXT
            );
    */

    public static final String CREATE_TABLE_CUSTOMER = "CREATE TABLE "
            + TABLE_NAME + " ("
            + COLUMN_CUSTOMER_ID + " INTEGER PRIMARY KEY,"
            + COLUMN_CUSTOMER_FNAME + " TEXT,"
            + COLUMN_CUSTOMER_LNAME + " TEXT,"
            + COLUMN_CUSTOMER_EMAIL + " TEXT" + ")";

    public static final String INSERT_TABLE_CUSTOMER = "INSERT INTO "
            + TABLE_NAME + "("
            + COLUMN_CUSTOMER_FNAME + ","
            + COLUMN_CUSTOMER_LNAME + ","
            + COLUMN_CUSTOMER_EMAIL
            + ") VALUES "
            + "('Michael', 'McGarrah','mcgarrah@gatech.edu'),"
            + "('Sam', 'Hensley','cshensley@gmail.com'),"
            + "('Cheryl', 'Lockett','clockett6@gatech.edu'),"
            + "('Bob', 'Young','young@ncsu.edu'),"
            + "('Tom', 'Miller','tkm@ncsu.edu'),"
            + "('Raghav', 'Kochhar','rkochhar6@gatech.edu')";

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
    public DBCustomer(GoBowlDatabase db) {
        myDb = db;
    }

    /* IMPLEMENT ABSTRACT METHODS */

    public String getTableName() {
        return TABLE_NAME;
    }

    public long getCustomerId() {
        return customerId;
    }

    public ContentValues getValues() {
        ContentValues valueList = new ContentValues();

        valueList.put(COLUMN_CUSTOMER_FNAME, fname);
        valueList.put(COLUMN_CUSTOMER_LNAME, lname);
        valueList.put(COLUMN_CUSTOMER_EMAIL, email);

        return valueList;
    }

    /* BUSINESS LOGIC METHODS */
    public String get(String customerId) {

        DBCustomer cust = new DBCustomer(myDb);

        System.out.println("DBCUSTOMER: customer id " + customerId);

        int customerIdAsInt = Integer.parseInt(customerId, 16);

        System.out.println("DBCUSTOMER: customer id " + customerId + " " + customerIdAsInt + " " + getTableName());

        String selectQuery = "SELECT * FROM " + getTableName()
                + " WHERE " + DBCustomer.COLUMN_CUSTOMER_ID + " = " + customerIdAsInt;

        System.out.println("DBCUSTOMER: select : " + selectQuery);

        Cursor c = myDb.getObject(selectQuery);

        String name = null;

        if (c.getCount() > 0) {
            c.moveToFirst();

            fname = c.getString(c.getColumnIndex(COLUMN_CUSTOMER_FNAME));
            lname = c.getString(c.getColumnIndex(COLUMN_CUSTOMER_LNAME));
            name = fname + " " + lname;
        } else {
            name = null;
        }

        return name;

    }


    public long getId() {
        return customerId;
    }

    /* GETTERS AND SETTERS */

    public String getFName() {

        String selectQuery = "SELECT * FROM " + getTableName()
                + " WHERE " + DBCustomer.COLUMN_CUSTOMER_ID + " = " + this.getCustomerId();

        Cursor c = LandingActivity.gb.getObject(selectQuery);

        if (c != null)
            c.moveToFirst();

        fname = c.getString(c.getColumnIndex(COLUMN_CUSTOMER_FNAME));

        return fname;
    }

    public void setFName(String fname) {
        this.fname = fname;
    }

    public String getLName() {

        String selectQuery = "SELECT * FROM " + getTableName()
                + " WHERE " + DBCustomer.COLUMN_CUSTOMER_ID + " = " + this.getCustomerId();

        Cursor c = LandingActivity.gb.getObject(selectQuery);

        if (c != null)
            c.moveToFirst();

        lname = c.getString(c.getColumnIndex(COLUMN_CUSTOMER_LNAME));

        return lname;
    }

    public void setLName(String lname) {
        this.lname = lname;
    }

    public String getEmail() {

        String selectQuery = "SELECT  * FROM " + getTableName()
                + " WHERE " + DBCustomer.COLUMN_CUSTOMER_ID + " = " + this.getCustomerId();

        Cursor c = LandingActivity.gb.getObject(selectQuery);

        if (c != null)
            c.moveToFirst();

        email = c.getString(c.getColumnIndex(COLUMN_CUSTOMER_EMAIL));

        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getCustomerCount() {
        return myDb.getTableRowCount(this.getTableName());
    }

}
