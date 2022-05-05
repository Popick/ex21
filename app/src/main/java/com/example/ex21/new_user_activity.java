package com.example.ex21;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class new_user_activity extends AppCompatActivity {
    SQLiteDatabase db;
    HelperDB hlp;

    EditText idET, fnameET, lnameET, companyET, phoneET;
    String id, a;
    int sum = 0, bikoret, num, allValid;
    ContentValues cv;
    /**
     * @author Etay Sabag <itay45520@gmail.com>
     * @version    1.6
     * @since     5/2/2022
     *  activity for making a new user
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);
        hlp = new HelperDB(this);

        idET = (EditText) findViewById(R.id.inputid);
        fnameET = (EditText) findViewById(R.id.inputfname);
        lnameET = (EditText) findViewById(R.id.inputlname);
        companyET = (EditText) findViewById(R.id.inputcompany);
        phoneET = (EditText) findViewById(R.id.inputphonenumber);

        cv = new ContentValues();

    }

    /**
     * The function takes in the input from the input fields, checks if the input is valid and adds
     * it to the data base.
     */
    public void add(View view) {
        allValid = 0;
        if (fnameET.getText().length() == 0) {
            fnameET.setError("Can't Be Empty");
        } else {
            allValid++;
        }
        if (lnameET.getText().length() == 0) {
            lnameET.setError("Can't Be Empty");
        } else {
            allValid++;
        }
        if (companyET.getText().length() == 0) {
            companyET.setError("Can't Be Empty");
        } else {
            allValid++;
        }
        if (!idVerification()) {
            idET.setError("Invalid ID");
        } else {
            allValid++;
        }
        if (!(phoneET.getText().length() == 10)) {
            phoneET.setError("Must Be 10 Digits Long");
        } else {
            allValid++;
        }
        if (allValid == 5) {

            cv.put(com.example.ex21.Users.FNAME, fnameET.getText().toString());
            cv.put(com.example.ex21.Users.LNAME, lnameET.getText().toString());
            cv.put(com.example.ex21.Users.COMPANY, companyET.getText().toString());
            cv.put(com.example.ex21.Users.ID, idET.getText().toString());
            cv.put(com.example.ex21.Users.PHONE, phoneET.getText().toString());
            cv.put(com.example.ex21.Users.ACTIVE, 1);

            db = hlp.getWritableDatabase();
            db.insert(com.example.ex21.Users.TABLE_USERS, null, cv);
            db.close();

            Toast.makeText(this, "Saved Successfully!", Toast.LENGTH_LONG).show();
            finish();
        }

    }

    /**
     * The function sends the user to the last activity.
     */
    public void back(View view) {
        finish();
    }

    /**
     * The function checks if an ID is valid.
     * @return true if ID is valid, false if the id is invalid.
     */
    public boolean idVerification() {
        sum = 0;
        id = idET.getText().toString();

        if (id.length() == 9) {
            for (int i = 0; i < 8; i++) {
                if (i % 2 == 0) {
                    num = Character.getNumericValue(id.charAt(i));
                    sum = sum + num;
                } else {
                    num = Character.getNumericValue(id.charAt(i));
                    if ((num * 2) > 9) {
                        a = Integer.toString(num * 2);
                        sum = sum + Character.getNumericValue(a.charAt(0))+Character.getNumericValue(a.charAt(1));
                    } else
                        sum = sum + (num * 2);
                }
            }
            bikoret = Character.getNumericValue(id.charAt(8));

            return (sum + bikoret) % 10 == 0;
        } else {
            return false;
        }
    }

}