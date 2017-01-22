package edu.gatech.seclass.gobowl;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.gatech.seclass.services.PrintingService;

public class ModifyCustomerActivity extends AppCompatActivity implements View.OnClickListener {

    private GoBowlDatabase gb;
    private DBCustomer cust;

    private EditText editFName, editLName, editEmail;
    private Button btnUpdate, btnDelete, btnAdd, btnPrint;

    private int _id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_customer);

        Context context = this.getApplicationContext();
        gb = GoBowlDatabase.getInstance(context);
        cust = new DBCustomer(gb);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String fname = intent.getStringExtra("fname");
        String lname = intent.getStringExtra("lname");
        String email = intent.getStringExtra("email");

        editFName = (EditText)findViewById(R.id.editFName);
        editLName = (EditText)findViewById(R.id.editLName);
        editEmail = (EditText)findViewById(R.id.editEmail);

        editFName.setText(fname);
        editLName.setText(lname);
        editEmail.setText(email);

        btnAdd = (Button)findViewById(R.id.btnAdd);
        btnUpdate = (Button)findViewById(R.id.btnUpdate);
        btnDelete = (Button)findViewById(R.id.btnDelete);
        btnPrint = (Button)findViewById(R.id.btnPrint);

        if (id.isEmpty())
        {
            btnAdd.setVisibility(View.VISIBLE);
            btnUpdate.setVisibility(View.GONE);
            btnDelete.setVisibility(View.GONE);
            btnPrint.setVisibility(View.GONE);
        } else {
            _id = Integer.parseInt(id);
        }

        btnAdd.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnPrint.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String fname, lname, email;
        boolean printed;
        switch (v.getId()) {
            case R.id.btnAdd:
                fname = editFName.getText().toString();
                lname = editLName.getText().toString();
                email = editEmail.getText().toString();
                cust.setFName(fname);
                cust.setLName(lname);
                cust.setEmail(email);
                long id = gb.addObject(cust);

                do {
                    printed = PrintingService.printCard(fname, lname, Long.toHexString(id));
                    if (printed) {
                        System.out.println("*** PrintCard: " + id + ": " + fname + " " + lname);
                        Context context = getApplicationContext();
                        CharSequence text = "Printed Card #"+Long.toHexString(id)+": "+fname+" "+lname;
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    } else {
                        System.out.println("*** PrintCard failure");
                    }
                } while(printed!=true);

                this.returnHome();
                break;
            case R.id.btnUpdate:
                fname = editFName.getText().toString();
                lname = editLName.getText().toString();
                email = editEmail.getText().toString();

                ContentValues values = new ContentValues();
                values.put(DBCustomer.COLUMN_CUSTOMER_FNAME, fname);
                values.put(DBCustomer.COLUMN_CUSTOMER_LNAME, lname);
                values.put(DBCustomer.COLUMN_CUSTOMER_EMAIL, email);

                gb.updateObject(cust.getTableName(), DBCustomer.COLUMN_CUSTOMER_ID, _id, values);

                do {
                    printed = PrintingService.printCard(fname, lname, Long.toHexString(_id));
                    if (printed) {
                        System.out.println("*** PrintCard: " + _id + ": " + fname + " " + lname);
                        Context context = getApplicationContext();
                        CharSequence text = "Printed Card #"+Long.toHexString(_id)+": "+fname+" "+lname;
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    } else {
                        System.out.println("*** PrintCard failure");
                    }
                } while(printed!=true);

                this.returnHome();
                break;

            case R.id.btnDelete:
                gb.deleteObject(cust.getTableName(), DBCustomer.COLUMN_CUSTOMER_ID, _id);
                this.returnHome();
                break;

            case R.id.btnPrint:
                fname = editFName.getText().toString();
                lname = editLName.getText().toString();
                do {
                    printed = PrintingService.printCard(fname, lname, Long.toHexString(_id));
                    if (printed) {
                        System.out.println("*** PrintCard: " + _id + ": " + fname + " " + lname);
                        Context context = getApplicationContext();
                        CharSequence text = "Printed Card #"+Long.toHexString(_id)+": "+fname+" "+lname;
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    } else {
                        System.out.println("*** PrintCard failure");
                    }
                } while(printed!=true);

                this.returnHome();
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
        Intent home_intent = new Intent(getApplicationContext(), ListCustomerActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(home_intent);
    }

}
