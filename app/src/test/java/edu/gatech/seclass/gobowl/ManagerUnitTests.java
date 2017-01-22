package edu.gatech.seclass.gobowl;

import org.junit.Test;

import static org.junit.Assert.*;

import android.content.Context;
import android.view.View;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ManagerUnitTests {

    private DBCustomer dbc;
    private GoBowlDatabase myDb;
    private Context context;

    @Before
    public void setUp() throws Exception {
        DBCustomer dbc = new DBCustomer(myDb);
        this.context = context.getApplicationContext();
    }

    @Test
    public void addNewCustomerTest() throws Exception {

        DBCustomer dbc = new DBCustomer(myDb);
        GoBowlDatabase myDB = GoBowlDatabase.getInstance(context.getApplicationContext());

        dbc.setFName("Jon");
        dbc.setLName("Doe");
        dbc.setEmail("jondoe@gmail.com");

        myDb.addObject(dbc);

        long newID = dbc.getId();

        assertTrue(newID > 0);

    }
}