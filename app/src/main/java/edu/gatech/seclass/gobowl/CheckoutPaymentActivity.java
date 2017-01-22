package edu.gatech.seclass.gobowl;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Toast;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import edu.gatech.seclass.services.CreditCardService;
import edu.gatech.seclass.services.PaymentService;


public class CheckoutPaymentActivity extends AppCompatActivity implements View.OnClickListener {

    public GoBowlDatabase gb;

    NumberPicker payers_numberPicker = null;
    Button btnCCScan;

    private int Payer_Count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_payment);

        Context context = this.getApplicationContext();
        gb = GoBowlDatabase.getInstance(context);

        Intent intent = getIntent();
        String str_player_count = intent.getStringExtra("player_count");
        int player_count = Integer.parseInt(str_player_count);

        // Score NumberPicker
        payers_numberPicker = (NumberPicker) findViewById(R.id.numberPickerPayerCount);
        payers_numberPicker.setMaxValue(player_count);
        payers_numberPicker.setMinValue(1);
        // Default long press is 300 ms
        payers_numberPicker.setOnLongPressUpdateInterval(10);
        //score_numberPicker.setWrapSelectorWheel(false);
        payers_numberPicker.setValue(player_count);

        btnCCScan = (Button) findViewById(R.id.btnCCScan);
        btnCCScan.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btnCCScan:
                System.out.println("Credit Card Scanner");

                if (payers_numberPicker.isEnabled()) {
                    payers_numberPicker.setEnabled(false);
                    Payer_Count = payers_numberPicker.getValue();
                }

                boolean cc_scanned=false;
                do {
                    String cc_read = CreditCardService.readCreditCard();
                    if (cc_read.contentEquals("ERR")) {
                        System.out.println("*** CreditCard Scan failure");
                    } else {
                        cc_scanned=true;
                        System.out.println("*** CreditCard Scanned: " + cc_read );
                        String firstName = "Michael";
                        String lastName = "McGarrah";
                        String ccNumber = "123412341234";
                        String expireDate = "12312016"; // Date type not string
                        SimpleDateFormat format = new SimpleDateFormat("MMddyyyy");
                        Date expirationDate = new Date();
                        try {
                            expirationDate = format.parse(expireDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        String securityCode = "123";
                        double amount = 12.50;

                        boolean cc_paid=false;
                        do {
                            cc_paid = PaymentService.processPayment(  firstName, lastName,
                                    ccNumber, expirationDate, securityCode, amount);
                            if (cc_paid) {
                                System.out.println("*** CreditCard Payment sent.");
                                Context context = getApplicationContext();
                                String text = firstName+" "+lastName;
                                text += " your Credit Card was Scanned";
                                text += " and Payment sent.";
                                text += "\nThank you for your business.";
                                text += "\nCome again.";
                                int duration = Toast.LENGTH_LONG;
                                Toast toast = Toast.makeText(context, text, duration);
                                toast.show();
                            } else {
                                System.out.println("*** CreditCard Payment failed. Retrying.");
                            }
                        } while(cc_paid!=true);
                    }
                } while(cc_scanned!=true);

                int payer_count = payers_numberPicker.getValue();
                if (payer_count==1) {
                    returnHome();
                } else {
                    payer_count--;
                    payers_numberPicker.setValue(payer_count);

                    Context context = getApplicationContext();
                    String text = "Press the scan button ";
                    text += payer_count + " more times ";
                    text += "to complete transaction.";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

                }

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
        Intent home_intent = new Intent(getApplicationContext(), CustomerActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(home_intent);
    }
}