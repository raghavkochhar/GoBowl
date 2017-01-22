package edu.gatech.seclass.gobowl;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import edu.gatech.seclass.services.ServicesUtils;

public class LandingActivity extends AppCompatActivity {

    private Intent intent;
    public static GoBowlDatabase gb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        gb = GoBowlDatabase.getInstance(this.getApplicationContext());

        // TODO: Comment this out before submitting final version
        ServicesUtils.resetCustomers();
        /*
        public static void ServicesUtils.addCustomer(   String firstName,
                                                        String lastName,
                                                        String ccNumber,
                                                        String expDate,
                                                        String securityCode,
                                                        String customerID);
         */
        ServicesUtils.addCustomer("Michael", "McGarrah", "678967896789", "12312016", "123", "0001");
        ServicesUtils.addCustomer("Sam",     "Hensley",  "123467896789", "09312017", "321", "0002");
        ServicesUtils.addCustomer("Cheryl",  "Lockett",  "678912346789", "11312016", "567", "0003");
        ServicesUtils.addCustomer("Bob",     "Young",    "568912341234", "10312016", "987", "0004");
        ServicesUtils.addCustomer("Tom",     "Miller",   "444412341234", "08312016", "021", "0005");
        ServicesUtils.addCustomer("Raghav",  "Kochhar",  "666612341234", "11312019", "204", "0006");

    }

    public void handleClick(View view) {

        switch(view.getId()) {
            case R.id.buttonCustomer:
                intent = new Intent(this, CustomerActivity.class);
                startActivity(intent);
                break;
            case R.id.buttonManager:
                intent = new Intent(this, ManagerActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}