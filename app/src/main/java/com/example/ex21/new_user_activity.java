package com.example.ex21;

import static com.example.ex21.FBref.refUsers;

import android.database.sqlite.SQLiteDatabase;
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

public class new_user_activity extends AppCompatActivity {
    SQLiteDatabase db;
    ArrayList<String> ids = new ArrayList<>();
    EditText idET, fnameET, lnameET, companyET, phoneET;
    String id;
    int allValid;
    /**
     * @author Etay Sabag <itay45520@gmail.com>
     * @version    2.2
     * @since     4/5/2022
     *  activity for making a new user
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        idET = (EditText) findViewById(R.id.inputid);
        fnameET = (EditText) findViewById(R.id.inputfname);
        lnameET = (EditText) findViewById(R.id.inputlname);
        companyET = (EditText) findViewById(R.id.inputcompany);
        phoneET = (EditText) findViewById(R.id.inputphonenumber);
        idVerification();


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
        if ((ids.contains(idET.getText().toString()))) {
            idET.setError("Already Exists!");
        } else if(idET.getText().toString().length()!=9){
            idET.setError("Invalid!");
        }
        else {
            allValid++;
        }
        if (!(phoneET.getText().length() == 10)) {
            phoneET.setError("Must Be 10 Digits Long");
        } else {
            allValid++;
        }
        if (allValid == 5) {
            refUsers.child(idET.getText().toString()).setValue(new Users(lnameET.getText().toString(),fnameET.getText().toString(),companyET.getText().toString(),idET.getText().toString(),phoneET.getText().toString(),"1"));
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
    public void idVerification() {
        ids.clear();
        id = idET.getText().toString();
        refUsers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dS) {
                for (DataSnapshot data : dS.getChildren()) {
                    ids.add((String) data.getKey());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

}