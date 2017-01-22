package edu.gatech.seclass.gobowl;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ListCustomerActivity extends AppCompatActivity {

    public GoBowlDatabase gb;
    private ListView listView;
    private SimpleCursorAdapter adapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch(id) {
            case R.id.add_record:
                Intent modify_intent = new Intent(getApplicationContext(), ModifyCustomerActivity.class);
                modify_intent.putExtra("id", "");
                modify_intent.putExtra("fname", "");
                modify_intent.putExtra("lname", "");
                modify_intent.putExtra("email", "");
                startActivity(modify_intent);
                break;
            case R.id.export_records:
                // DB GET CURSOR CODE HERE
                //exportToFile(cursor);
                //break;
            default:
                Context context = getApplicationContext();
                CharSequence text = "Unimplemented feature";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_list_customer);

        Context context = this.getApplicationContext();
        gb = GoBowlDatabase.getInstance(context);

        DBCustomer cust = new DBCustomer(gb);
        String tableName = cust.getTableName();
        System.out.println("getTableName: " + tableName);
        long custCount = cust.getCustomerCount();
        System.out.println("getTableName: " + custCount);
        String sqliteVersion = gb.getSQLiteVersion();
        System.out.println("SQLite Version: " + sqliteVersion);

        String selectQuery = "SELECT "
                + DBCustomer.COLUMN_CUSTOMER_ID + ", "
                + "printf('0x%04X', "+ DBCustomer.COLUMN_CUSTOMER_ID +") as hex_id, "
                + DBCustomer.COLUMN_CUSTOMER_FNAME + ", "
                + DBCustomer.COLUMN_CUSTOMER_LNAME + ", "
                + DBCustomer.COLUMN_CUSTOMER_EMAIL
                + " FROM " + tableName;

        final String[] c = new String[] {DBCustomer.COLUMN_CUSTOMER_ID,
                                         "hex_id",
                                         DBCustomer.COLUMN_CUSTOMER_FNAME,
                                         DBCustomer.COLUMN_CUSTOMER_LNAME,
                                         DBCustomer.COLUMN_CUSTOMER_EMAIL};
        final int[] t = new int[] { R.id.cust_id, R.id.hex_id, R.id.fname, R.id.lname, R.id.email };

        Cursor cursor = gb.getObject(selectQuery);
        // This method works if you do not use aggregate sql functions like printf()
        //Cursor cursor = gb.getObject(tableName, c);

        listView = (ListView)findViewById(R.id.listViewCust);
        listView.setEmptyView(findViewById(R.id.emptyCust));

        // Next line blows up for no reason
        adapter = new SimpleCursorAdapter(this, R.layout.activity_view_customer, cursor, c, t, 0);

        adapter.notifyDataSetChanged();

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long viewId) {

                TextView idTextView = (TextView) view.findViewById(R.id.cust_id);
                TextView hexidTextView = (TextView) view.findViewById(R.id.hex_id);
                TextView fnameTextView = (TextView) view.findViewById(R.id.fname);
                TextView lnameTextView = (TextView) view.findViewById(R.id.lname);
                TextView emailTextView = (TextView) view.findViewById(R.id.email);

                String id = idTextView.getText().toString();
                String hexid = hexidTextView.getText().toString();
                String fname = fnameTextView.getText().toString();
                String lname = lnameTextView.getText().toString();
                String email = emailTextView.getText().toString();

                Intent modify_intent = new Intent(getApplicationContext(), ModifyCustomerActivity.class);
                modify_intent.putExtra("id", id);
                modify_intent.putExtra("hexid", hexid);
                modify_intent.putExtra("fname", fname);
                modify_intent.putExtra("lname", lname);
                modify_intent.putExtra("email", email);

                startActivity(modify_intent);
            }
        }
        );
    }
}
