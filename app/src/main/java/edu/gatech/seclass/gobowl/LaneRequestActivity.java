package edu.gatech.seclass.gobowl;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.SeekBar.*;
import android.widget.Toast;
import edu.gatech.seclass.services.QRCodeService;
import java.util.Date;
import java.util.ArrayList;
import android.widget.ArrayAdapter;

public class LaneRequestActivity extends AppCompatActivity implements View.OnClickListener {
    private Intent intent;
    private GoBowlDatabase gb;
    private int numberOfPlayersAdded = 0;
    private int numberOfPlayersSelected = 0;
    private DBSession session;
    private ArrayList<String> playerList = new ArrayList<String>();
    private ArrayAdapter<String> adapter;
    private ListView playerListView;
    private Context context;

    private Button laneScanBtn, doneBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lane_request);

        context = this.getApplicationContext();
        gb = GoBowlDatabase.getInstance(context);
        session = new DBSession(gb);

        Date bowlDate = new Date();
        session.create(bowlDate);

        final TextView numberOfPlayers = (TextView) findViewById(R.id.numberPlayers);
        final SeekBar numPlaySeekBar = (SeekBar) findViewById(R.id.numPlaySeekBar);

        numberOfPlayers.setText(Integer.toString(0));

        numPlaySeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            int count = 1;

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (count <= numPlaySeekBar.getMax()) {
                    count = numPlaySeekBar.getMax();

                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                count = progress;
                numberOfPlayers.setText(Integer.toString(count));
                numberOfPlayersSelected = count;
            }
        });


        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, playerList);

        playerListView = (ListView) findViewById(R.id.playerListView);
        playerListView.setAdapter(adapter);

        System.out.println("LANEREQUESTACTIVITY : onCreate end");

        laneScanBtn = (Button) findViewById(R.id.laneScanBtn);
        laneScanBtn.setOnClickListener(this);

        doneBtn = (Button) findViewById(R.id.doneBtn);
        doneBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        CharSequence text = "";
        int duration;
        Toast toast;

        System.out.println("LANEREQUESTACTIVITY : handleClick begin");

        switch(view.getId()) {
            case R.id.laneScanBtn:

                System.out.println("LANEREQUESTACTIVITY : in laneScanBtn");

                if (numberOfPlayersAdded < numberOfPlayersSelected) {
                    // scan card
                    String hexId = QRCodeService.scanQRCode();
                    System.out.println("LANEREQUESTACTIVITY : in laneScanBtn hexid " + hexId);

                    if (hexId.equals("ERR") == false) {

                        DBCustomer customer = new DBCustomer(gb);
                        DBCustomerSession custsess = new DBCustomerSession(gb);

                        String name = customer.get(hexId); // display name in list

                        if (name != null) {
                            System.out.println("LANEREQUESTACTIVITY : CustomerName: " + name);

                            // add customer name to list_view
                            // check for duplicate entries?
                            // delete option is to use the back button

                            // set session.lanerequesterid = hexId if 1st scan
                            if (numberOfPlayersAdded == 0) {
                                session.setLaneRequesterId(Integer.parseInt(hexId, 16));

                            }

                            // only add customer if not already in list
                            int pos = adapter.getPosition(name);
                            System.out.println("position of name is " + pos);
                            if (pos == -1) {
                                adapter.add(name);
                                numberOfPlayersAdded++;
                            } else {
                                Context context = getApplicationContext();
                                text = "Duplicate scan.";
                                duration = Toast.LENGTH_LONG;
                                toast = Toast.makeText(context, text, duration);
                                toast.show();
                            }

                            // add customer_id and session_id to customer_session
                            custsess.create(session.getId(), customer.getId());

                        }
                        System.out.println("LANEREQUESTACTIVITY : numplayadd " + numberOfPlayersAdded);

                    } else {
                        // do something reasonable for the ERR
                        Context context = getApplicationContext();
                        text = "The scan failed.  Try again.";
                        duration = Toast.LENGTH_LONG;
                        toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }

                }

                System.out.println("ITEMS IN LIST " + adapter.getCount());

                if (adapter.getCount() == 0) {
                    Context context = getApplicationContext();
                    text = "Use the slider to select number of players first, then press Scan.";
                    duration = Toast.LENGTH_LONG;
                    toast = Toast.makeText(context, text, duration);
                    toast.show();
                }

                break;

            case R.id.showLaneBtn:
                // grab free lane
                DBLane lane = new DBLane(gb);
                long theLane = lane.getEmptyLane();
                String gotLane = "";

                /*
                lane.setStatus(1, true);
                lane.setStatus(2, true);
                lane.setStatus(3, true);
                lane.setStatus(4, true);
                lane.setStatus(5, true);
                lane.setStatus(6, true);
                lane.setStatus(7, true);
                lane.setStatus(8, true);
                lane.setStatus(9, true);
                lane.setStatus(10, true);
*/

                //System.out.println("The lane in the session " + session.getLane());
                long laneFromSession = session.getLane();

                if (laneFromSession == 0) { // allow the selection of 1 lane

                    if (theLane > 0) {
                        gotLane = "You have been assigned:\n\nLane " + theLane;

                        // store the lane
                        boolean isEmpty = false;
                        lane.setStatus(theLane, isEmpty);
                        session.setLane(session.getId(), theLane);

                        String lanes = gb.getTableAsText(lane.getTableName());
                        String sessions = gb.getTableAsText(session.getTableName());

                        //System.out.println("*** LANES *** " + lane.getLaneId() + "\n" + lanes);
                        //System.out.println("*** SESSIONS *** " + session.getId() + "\n" + sessions);
                    } else {
                        gotLane = "No lanes are available.  Please try again later.";
                    }
                } else {
                    gotLane = "You have been assigned:\n\nLane " + session.getLane();
                }

                // show the lane
                context = getApplicationContext();
                text = gotLane;
                duration = Toast.LENGTH_LONG;
                toast = Toast.makeText(context, text, duration);
                toast.show();

                break;

            case R.id.doneBtn:

                int a = adapter.getCount();
                int laneRequester = session.getLaneRequesterId();
                if ((a > 0) &&  (laneRequester > 0)) {
                    this.finish();
                } else {
                    context = getApplicationContext();
                    text = "Please scan cards and/or select show lane";
                    duration = Toast.LENGTH_SHORT;
                    toast = Toast.makeText(context, text, duration);
                    toast.show();
                }

                break;

            default:
                context = getApplicationContext();
                text = "Unimplemented feature";
                duration = Toast.LENGTH_SHORT;
                toast = Toast.makeText(context, text, duration);
                toast.show();
                break;
        }

    }
}
