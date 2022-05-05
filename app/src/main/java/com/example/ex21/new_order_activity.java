package com.example.ex21;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * @author Etay Sabag <itay45520@gmail.com>
 * @version    1.2
 * @since     19/2/2022
 * first activity of making a new order
 */
public class new_order_activity extends AppCompatActivity {
    Intent si;
    SQLiteDatabase db;
    HelperDB hlp;
    ContentValues cv;
    int allValid;
    EditText appetizerET,mainET,sideET,dessertET,drinkET;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order);

        si = new Intent(this, new_order_second_activity.class);
        appetizerET = (EditText) findViewById(R.id.inputappetizer);
        mainET = (EditText) findViewById(R.id.inputmdish);
        sideET = (EditText) findViewById(R.id.inputsmeal);
        dessertET = (EditText) findViewById(R.id.inputdessert);
        drinkET = (EditText) findViewById(R.id.inputdrink);

        hlp = new HelperDB(this);
        cv = new ContentValues();
    }

    /**
     * The function checks if the input is okay, if so it launches the next activity activity.
     */
    public void next(View view) {
        allValid = 0;
        if (appetizerET.getText().length() == 0) {
            appetizerET.setError("Can't Be Empty");
        } else {
            allValid++;
        }
        if (mainET.getText().length() == 0) {
            mainET.setError("Can't Be Empty");
        } else {
            allValid++;
        }
        if (sideET.getText().length() == 0) {
            sideET.setError("Can't Be Empty");
        } else {
            allValid++;
        }
        if (dessertET.getText().length() == 0) {
            dessertET.setError("Can't Be Empty");
        } else {
            allValid++;
        }
        if (drinkET.getText().length() == 0) {
            drinkET.setError("Can't Be Empty");
        } else {
            allValid++;
        }
        if (allValid == 5) {
            startActivityForResult(si,1);
        }
    }
    /**
     * The function sends the user to the last activity.
     */
    public void back(View view) {
        finish();
    }
    /**
     * When returning from the second new order activity The function takes in the input from the
     * input fields, and adds it to the meals data base.
     */
    @Override
    protected void onActivityResult(int source, int good, @Nullable Intent data_back) {
        super.onActivityResult(source, good, data_back);
        if (good==RESULT_OK && source == 1){
            cv.put(com.example.ex21.Meals.APPETIZER, appetizerET.getText().toString());
            cv.put(com.example.ex21.Meals.MAIN, mainET.getText().toString());
            cv.put(com.example.ex21.Meals.SIDE, sideET.getText().toString());
            cv.put(com.example.ex21.Meals.DESSERT, dessertET.getText().toString());
            cv.put(com.example.ex21.Meals.DRINK,drinkET.getText().toString());

            db = hlp.getWritableDatabase();
            db.insert(com.example.ex21.Meals.TABLE_MEALS, null, cv);
            db.close();

            finish();
        }
    }

}