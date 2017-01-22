package edu.gatech.seclass.gobowl;

import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

/**
 * Created by c on 7/16/16.
 */

    /*
    ﻿firstname, lastname, ccnum, expiredate, seccode
    ﻿
    John#Doe#1234123412341234#12312016#111#100

            ﻿
    public static boolean PaymentService.processPayment(String firstName
                                                        String lastName,
                                                        String ccNumber,
                                                        Date expirationDate,
                                                        String securityCode,
                                                        double amount)
                                                        */
public class CreditCard {
    String firstName;
    String lastName;
    String ccNum;
    Date expirationDate;
    String securityCode;
    double amount;

    public void parse(String inString) {
        System.out.println("CREDITCARD " + inString);

        String[] x = inString.split("#");

        System.out.println("CREDITCARD " + x);

        firstName = x[0];
        lastName = x[1];
        ccNum = x[2];

        SimpleDateFormat formatter = new SimpleDateFormat("MMddyyyy");
        String expDateString = x[3];  //12312016

        try {

            expirationDate = formatter.parse(expDateString);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        securityCode = x[4];
        //amount = Double.parseDouble(x[5]);

    }


    public String toString() {
        String obj = firstName + " " + lastName + " " + ccNum;
        obj = obj + expirationDate + " " + securityCode + " " + amount;
        return obj;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getCcNum() {
        return ccNum;
    }

    public void setCcNum(String ccNum) {
        this.ccNum = ccNum;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
