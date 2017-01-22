package edu.gatech.seclass.gobowl;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class ListLaneActivity extends AppCompatActivity {

    public GoBowlDatabase gb;
    private ListView listView;
    private SimpleCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_list_lane);

        Context context = this.getApplicationContext();
        gb = GoBowlDatabase.getInstance(context);

        DBLane lane = new DBLane(gb);
        String tableName = lane.getTableName();
        System.out.println("getTableName: " + tableName);
        long laneCount = lane.getLaneCount();
        System.out.println("getRowCount: " + laneCount);
        String sqliteVersion = gb.getSQLiteVersion();
        System.out.println("SQLite Version: " + sqliteVersion);

        DBSession session = new DBSession(gb);
        DBCustomer cust = new DBCustomer(gb);

        Cursor cursor = lane.getActiveLanes();

        listView = (ListView)findViewById(R.id.listViewLane);
        listView.setEmptyView(findViewById(R.id.emptyLane));

        ViewGroup headerView = (ViewGroup)getLayoutInflater().inflate(R.layout.activity_view_lane_header,listView,false);
        listView.addHeaderView(headerView);

        final String[] c = new String[] {   DBLane.COLUMN_LANE_ID,
                                            DBLane.COLUMN_LANE_STATUS,
                                            DBLane.COLUMN_LANE_SESSION_ID,
                                            session.getTableName()+"."+ DBSession.COLUMN_SESSION_LANE_OWNER,
                                            cust.getTableName()+"."+ DBCustomer.COLUMN_CUSTOMER_FNAME,
                                            cust.getTableName()+"."+ DBCustomer.COLUMN_CUSTOMER_LNAME };

        final int[] t = new int[] { R.id.lane_id, R.id.lane_status, R.id.lane_session_id,
                                    R.id.lane_owner_id, R.id.lane_owner_fname, R.id.lane_owner_lname};

        // This line blowing up for no reason means a mismatch in c and t parameters
        adapter = new SimpleCursorAdapter(this, R.layout.activity_view_lane, cursor, c, t, 0);

        adapter.notifyDataSetChanged();

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long viewId) {

                    TextView TextView_lane_id = (TextView) view.findViewById(R.id.lane_id);
                    TextView TextView_lane_status = (TextView) view.findViewById(R.id.lane_status);
                    TextView TextView_lane_owner_id = (TextView) view.findViewById(R.id.lane_owner_id);
                    TextView TextView_lane_session_id = (TextView) view.findViewById(R.id.lane_session_id);
                    TextView TextView_lane_owner_fname = (TextView) view.findViewById(R.id.lane_owner_fname);
                    TextView TextView_lane_owner_lname = (TextView) view.findViewById(R.id.lane_owner_lname);

                    String lane_id = TextView_lane_id.getText().toString();
                    String lane_status = TextView_lane_status.getText().toString();
                    String lane_owner_id = TextView_lane_owner_id.getText().toString();
                    String lane_session_id = TextView_lane_session_id.getText().toString();
                    String lane_owner_fname = TextView_lane_owner_fname.getText().toString();
                    String lane_owner_lname = TextView_lane_owner_lname.getText().toString();

                    /*
                    Context context = getApplicationContext();
                    String text = "lane_id: " + lane_id;
                    text += "\nlane_status:" + lane_status;
                    text += "\nlane_owner_id:" + lane_owner_id;
                    text += "\nlane_session_id:" + lane_session_id;
                    text += "\nlane_owner_fname:" + lane_owner_fname;
                    text += "\nlane_owner_lname:" + lane_owner_lname;

                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    */

                    Intent modify_intent = new Intent(getApplicationContext(), ListPlayerActivity.class);
                    modify_intent.putExtra("lane_id", lane_id);
                    modify_intent.putExtra("lane_session_id", lane_session_id);
                    modify_intent.putExtra("lane_owner_id", lane_owner_id);
                    modify_intent.putExtra("lane_owner_fname", lane_owner_fname);
                    modify_intent.putExtra("lane_owner_lname", lane_owner_lname);

                    startActivity(modify_intent);
                }
            }
        );
    }
}
