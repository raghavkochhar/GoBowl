package edu.gatech.seclass.gobowl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import android.content.ContentValues;

/**
 * Created by c on 7/8/16.
 */
public abstract class DBObject {

    // Abstract classes object must implement

    public abstract String getTableName();
    public abstract ContentValues getValues();

    // Utility Classes

    public String getCurrentDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
}
