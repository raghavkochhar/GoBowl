package edu.gatech.seclass.gobowl;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

public class ModifyPlayerActivity extends AppCompatActivity implements View.OnClickListener {

    private GoBowlDatabase gb;
    private DBCustomerSession cs;

    private EditText editPlayerFName, editPlayerLName, editPlayerScore;
    private Button btnUpdateScore;

    private int _id;

    NumberPicker score_numberPicker = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_player);

        btnUpdateScore = (Button) findViewById(R.id.btnUpdateScore);
        btnUpdateScore.setOnClickListener(this);

        Context context = this.getApplicationContext();
        gb = GoBowlDatabase.getInstance(context);
        cs = new DBCustomerSession(gb);

        Intent intent = getIntent();
        String player_cs_id = intent.getStringExtra("player_cs_id");
        _id = Integer.parseInt(player_cs_id);
        //String player_cust_id = intent.getStringExtra("player_cust_id");
        String player_session_id = intent.getStringExtra("player_session_id");
        String player_fname = intent.getStringExtra("player_fname");
        String player_lname = intent.getStringExtra("player_lname");
        //String player_score = intent.getStringExtra("player_score");

        editPlayerFName = (EditText)findViewById(R.id.score_editFName);
        editPlayerLName = (EditText)findViewById(R.id.score_editLName);
        editPlayerScore = (EditText)findViewById(R.id.score_editScore);

        editPlayerFName.setText(player_fname);
        editPlayerLName.setText(player_lname);

        String selectQuery = "SELECT "
                + DBCustomerSession.TABLE_NAME+"."+DBCustomerSession.COLUMN_CS_ID + ", "
                + DBCustomerSession.COLUMN_CS_CUSTOMERID + ", "
                + DBCustomerSession.COLUMN_CS_SCORE + ", "
                + DBCustomer.TABLE_NAME+"."+DBCustomer.COLUMN_CUSTOMER_FNAME + " AS fname, "
                + DBCustomer.TABLE_NAME+"."+DBCustomer.COLUMN_CUSTOMER_LNAME + " AS lname"
                + " FROM " + DBCustomerSession.TABLE_NAME +","
                + DBCustomer.TABLE_NAME
                + " WHERE "
                + DBCustomerSession.COLUMN_CS_CUSTOMERID + "="
                + DBCustomer.TABLE_NAME+"."+DBCustomer.COLUMN_CUSTOMER_ID
                + " AND " + DBCustomerSession.TABLE_NAME+"."+DBCustomerSession.COLUMN_CS_ID + "="
                + player_cs_id ;

        final String[] c = new String[] {
                DBCustomerSession.TABLE_NAME+"."+DBCustomerSession.COLUMN_CS_ID,
                DBCustomerSession.COLUMN_CS_CUSTOMERID,
                DBCustomerSession.COLUMN_CS_SCORE,
                "fname",
                "lname"};

        Cursor cursor = gb.getObject(selectQuery);

        if (cursor != null)
            cursor.moveToFirst();

        String player_score = cursor.getString(cursor.getColumnIndex(cs.COLUMN_CS_SCORE));

        editPlayerScore.setText(player_score);

        // Score NumberPicker
        score_numberPicker = (NumberPicker)findViewById(R.id.score_numberPicker);
        score_numberPicker.setMaxValue(300);
        score_numberPicker.setMinValue(0);
        // Default long press is 300 ms
        score_numberPicker.setOnLongPressUpdateInterval(10);
        //score_numberPicker.setWrapSelectorWheel(false);
        score_numberPicker.setValue(Integer.valueOf(player_score));

    }

    @Override
    public void onClick(View v) {
        String score;

        switch (v.getId()) {

            case R.id.btnUpdateScore:
                //score = editPlayerScore.getText().toString();
                int num_score = score_numberPicker.getValue();
                score = String.valueOf(num_score);

                ContentValues values = new ContentValues();
                values.put(DBCustomerSession.COLUMN_CS_SCORE, score);
                gb.updateObject(cs.getTableName(), DBCustomerSession.COLUMN_CS_ID, _id, values);

                this.finish();

                //this.onBackPressed();
                //this.returnHome();
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
    public void returnHome() {
        Intent home_intent = new Intent(getApplicationContext(), ListPlayerActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(home_intent);
    }
}