package com.example.ex21;

import static com.example.ex21.FBref.refComps;
import static com.example.ex21.FBref.refUsers;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * @author Etay Sabag <itay45520@gmail.com>
 * @version    2
 * @since     4/5/2022
 *  activity for making a new order
 */
public class new_company_activity extends AppCompatActivity {

    EditText nameET, taxET, mPhoneET, sPhoneET;
    int allValid;
    ArrayList<String> taxes = new ArrayList<>();
    String tax;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_company);


        nameET = (EditText) findViewById(R.id.inputappetizer);
        taxET = (EditText) findViewById(R.id.inputtax);
        mPhoneET = (EditText) findViewById(R.id.inputmphone);
        sPhoneET = (EditText) findViewById(R.id.inputsphone);

        TaxVerification();
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
        if ((taxes.contains(taxET.getText().toString()))) {
            taxET.setError("Already Exists!");
        } else if(taxET.getText().toString().length()!=9){
            taxET.setError("Invalid!");
        }
        else {
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
            refComps.child(taxET.getText().toString()).setValue(new Companies(nameET.getText().toString(),mPhoneET.getText().toString(),sPhoneET.getText().toString(),taxET.getText().toString(),"1"));
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

    public void TaxVerification() {
        taxes.clear();
        tax = taxET.getText().toString();
        refComps.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dS) {
                for (DataSnapshot data : dS.getChildren()) {
                    taxes.add((String) data.getKey());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

}