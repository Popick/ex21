package com.example.ex21;

import static com.example.ex21.FBref.refMeals;
import static com.example.ex21.FBref.refUsers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

/**
 * @author Etay Sabag <itay45520@gmail.com>
 * @version    2
 * @since     4/5/2022
 * first activity of making a new order
 */
public class new_order_activity extends AppCompatActivity {
    Intent si;
    int allValid, howmany = 0;
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

        howmany=0;
        refMeals.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dS) {
                for (DataSnapshot data : dS.getChildren()) {
                    howmany++;
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
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
            System.out.println("howwwwmanyyy "+howmany);
            si.putExtra("orderPos",howmany);
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

            refMeals.child(howmany+"").setValue(new Meals(appetizerET.getText().toString(),mainET.getText().toString(),sideET.getText().toString(),dessertET.getText().toString(),drinkET.getText().toString()));
            Toast.makeText(this, "Saved Successfully!", Toast.LENGTH_LONG).show();
            finish();

        }
    }

}