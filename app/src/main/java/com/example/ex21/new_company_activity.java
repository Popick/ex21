package com.example.ex21;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * @author Etay Sabag <itay45520@gmail.com>
 * @version    1.4
 * @since     19/2/2022
 *  activity for making a new order
 */
public class new_company_activity extends AppCompatActivity {

    SQLiteDatabase db;
    HelperDB hlp;

    EditText nameET, taxET, mPhoneET, sPhoneET;
    int allValid;
    ContentValues cv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_company);

        hlp = new HelperDB(this);
        db = hlp.getWritableDatabase();
        db.close();


        nameET = (EditText) findViewById(R.id.inputappetizer);
        taxET = (EditText) findViewById(R.id.inputtax);
        mPhoneET = (EditText) findViewById(R.id.inputmphone);
        sPhoneET = (EditText) findViewById(R.id.inputsphone);

        cv = new ContentValues();

    }

    /**
     * The function takes in the input from the input fields, checks if the input is valid and adds
     * it to the data base.
     */
    public void add(View view) {
        allValid = 0;
        if (nameET.getText().length() == 0) {
            nameET.setError("Can't Be Empty");
        } else {
            allValid++;
        }
        if (taxET.getText().length() == 10) {
            taxET.setError("Can't Be Empty");
        } else {
            allValid++;
        }
        if (!(mPhoneET.getText().length() == 10)) {
            mPhoneET.setError("Must Be 10 Digits Long");
        } else {
            allValid++;
        }if (!(sPhoneET.getText().length() == 10)) {
            sPhoneET.setError("Must Be 10 Digits Long");
        } else {
            allValid++;
        }
        if (allValid == 4) {

            cv.put(Companies.TAX_ID, taxET.getText().toString());
            cv.put(Companies.NAME, nameET.getText().toString());
            cv.put(Companies.MAIN_PHONE, mPhoneET.getText().toString());
            cv.put(Companies.SECONDARY_PHONE, sPhoneET.getText().toString());
            cv.put(Companies.ACTIVE, "1");

            db = hlp.getWritableDatabase();
            db.insert(Companies.TABLE_COMPANIES, null, cv);
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

}