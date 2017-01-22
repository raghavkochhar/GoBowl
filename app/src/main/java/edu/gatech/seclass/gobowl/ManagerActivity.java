package edu.gatech.seclass.gobowl;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import edu.gatech.seclass.services.PrintingService;

public class ManagerActivity extends AppCompatActivity {

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);
    }

    public void handleClick(View view) {

        switch(view.getId()) {
            case R.id.btnShowCust:
                intent = new Intent(this, ListCustomerActivity.class);
                startActivity(intent);
                break;
            case R.id.btnYearEnd:
                //PrintingService.printCard()
                //intent = new Intent(this, ManagerActivity.class);
                //startActivity(intent);
                //break;
            default:
                Context context = getApplicationContext();
                CharSequence text = "Unimplemented feature";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                break;
        }
    }

}
