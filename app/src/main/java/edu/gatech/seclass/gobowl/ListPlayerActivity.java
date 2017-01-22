package edu.gatech.seclass.gobowl;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ListPlayerActivity extends AppCompatActivity implements View.OnClickListener {

    public GoBowlDatabase gb;
    private DBCustomerSession cs;
    private ListView listView;
    private SimpleCursorAdapter adapter;
    private Button btnPay;

    private int session_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_list_player);

        btnPay = (Button) findViewById(R.id.btnPay);
        btnPay.setOnClickListener(this);

        Context context = this.getApplicationContext();
        gb = GoBowlDatabase.getInstance(context);

        Intent intent = getIntent();
        String lane_id = intent.getStringExtra("lane_id");
        String lane_session_id = intent.getStringExtra("lane_session_id");
        String lane_owner_id = intent.getStringExtra("lane_owner_id");
        String lane_owner_fname = intent.getStringExtra("lane_owner_fname");
        String lane_owner_lname = intent.getStringExtra("lane_owner_lname");

        session_id = Integer.parseInt(lane_session_id);

        DBCustomer cust = new DBCustomer(gb);

        cs = new DBCustomerSession(gb);
        String tableName = cs.getTableName();
        System.out.println("getTableName: " + tableName);
        String sqliteVersion = gb.getSQLiteVersion();
        System.out.println("SQLite Version: " + sqliteVersion);

        // TODO: Join CS and Cust tables to get the Player Names and Score listed
        String selectQuery = "SELECT "
                + DBCustomerSession.TABLE_NAME+"."+DBCustomerSession.COLUMN_CS_ID + ", "
                + DBCustomerSession.COLUMN_CS_CUSTOMERID + ", "
                + DBCustomerSession.COLUMN_CS_SESSIONID + ", "
                + DBCustomerSession.COLUMN_CS_SCORE + ", "
                + DBCustomer.TABLE_NAME+"."+DBCustomer.COLUMN_CUSTOMER_FNAME + " AS fname, "
                + DBCustomer.TABLE_NAME+"."+DBCustomer.COLUMN_CUSTOMER_LNAME + " AS lname"
                + " FROM " + tableName +","
                + DBCustomer.TABLE_NAME
                + " WHERE "
                + DBCustomerSession.COLUMN_CS_CUSTOMERID + "="
                + DBCustomer.TABLE_NAME+"."+DBCustomer.COLUMN_CUSTOMER_ID
                + " AND " + DBCustomerSession.COLUMN_CS_SESSIONID + "="
                + lane_session_id ;

        final String[] c = new String[] {   DBCustomerSession.TABLE_NAME+"."+DBCustomerSession.COLUMN_CS_ID,
                                            DBCustomerSession.COLUMN_CS_CUSTOMERID,
                                            DBCustomerSession.COLUMN_CS_SESSIONID,
                                            DBCustomerSession.COLUMN_CS_SCORE,
                                            "fname",
                                            "lname"};
        final int[] t = new int[] { R.id.player_cs_id, R.id.player_cust_id, R.id.player_session_id,
                                    R.id.player_score, R.id.player_fname, R.id.player_lname};

        Cursor cursor = gb.getObject(selectQuery);
        // This method works if you do not use aggregate sql functions like printf()
        //Cursor cursor = gb.getObject(tableName, c);

        listView = (ListView)findViewById(R.id.listViewPlayer);
        listView.setEmptyView(findViewById(R.id.emptyPlayer));

        // Next line blows up for no reason
        adapter = new SimpleCursorAdapter(this, R.layout.activity_view_player, cursor, c, t, 0);

        adapter.notifyDataSetChanged();

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long viewId) {

                TextView tv_player_cs_id = (TextView)view.findViewById(R.id.player_cs_id);
                TextView tv_player_cust_id = (TextView)view.findViewById(R.id.player_cust_id);
                TextView tv_player_session_id = (TextView)view.findViewById(R.id.player_session_id);
                TextView tv_player_score = (TextView)view.findViewById(R.id.player_score);
                TextView tv_player_fname = (TextView)view.findViewById(R.id.player_fname);
                TextView tv_player_lname = (TextView)view.findViewById(R.id.player_lname);

                String player_cs_id = tv_player_cs_id.getText().toString();
                String player_cust_id = tv_player_cust_id.getText().toString();
                String player_session_id = tv_player_session_id.getText().toString();
                String player_score = tv_player_score.getText().toString();
                String player_fname = tv_player_fname.getText().toString();
                String player_lname = tv_player_lname.getText().toString();

                tv_player_fname.setTypeface(null, Typeface.BOLD_ITALIC);
                tv_player_lname.setTypeface(null, Typeface.BOLD_ITALIC);

                //italic

                // TODO: Pass the selected customer name and cs_id
                Intent modify_intent = new Intent(getApplicationContext(), ModifyPlayerActivity.class);
                modify_intent.putExtra("player_cs_id", player_cs_id);
                modify_intent.putExtra("player_cust_id", player_cust_id);
                modify_intent.putExtra("player_session_id", player_session_id);
                modify_intent.putExtra("player_fname", player_fname);
                modify_intent.putExtra("player_lname", player_lname);
                modify_intent.putExtra("player_score", player_score);

                startActivity(modify_intent);
            }
        }
        );
    }

    @Override
    public void onClick(View view) {

        switch(view.getId()) {
            case R.id.btnPay:
                Intent intent = new Intent(getApplicationContext(), CheckoutPaymentActivity.class);
                int player_count = cs.getCustomerCountBySession(session_id);
                intent.putExtra("player_count", String.valueOf(player_count));
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
