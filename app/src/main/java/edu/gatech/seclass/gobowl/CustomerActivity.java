package edu.gatech.seclass.gobowl;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.content.Intent;

public class CustomerActivity extends AppCompatActivity {

    private Intent intent;
    private GoBowlDatabase gb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        gb = GoBowlDatabase.getInstance(this.getApplicationContext());
    }

    public void handleClick(View view) {

        switch(view.getId()) {
            case R.id.btnRequestLane:
                intent = new Intent(this, LaneRequestActivity.class);
                startActivity(intent);
                break;
            case R.id.btnCheckout:
                intent = new Intent(this, ListLaneActivity.class);
                startActivity(intent);
                break;
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
