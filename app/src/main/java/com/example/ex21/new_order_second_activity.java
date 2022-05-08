package com.example.ex21;

import static com.example.ex21.FBref.refComps;
import static com.example.ex21.FBref.refOrders;
import static com.example.ex21.FBref.refUsers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * @author Etay Sabag <itay45520@gmail.com>
 * @version 2
 * @since 4/5/2022
 * second activity of making a new order
 */
public class new_order_second_activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Intent gi;
    Spinner sp;
    int orderPos;
    ArrayList<String> tbl;
    ArrayList<String> restIDList,restNameList;
    ArrayAdapter<String> adp;
    EditText userIdET;
    String user_order_key_id, user_order_name, restaurant_order_key_id, restaurant_order_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order_second);

        sp = (Spinner) findViewById(R.id.spinnerComp);
        userIdET = (EditText) findViewById(R.id.userIdInput);

        orderPos = getIntent().getIntExtra("orderPos", -1);

        sp.setOnItemSelectedListener(this);

        tbl = new ArrayList<>();
        restIDList = new ArrayList<>();
        restNameList = new ArrayList<>();
        tbl.add("Choose Restaurant:");
        restIDList.add("0");
        restNameList.add("0");

        refComps.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dS) {
                for (DataSnapshot data : dS.getChildren()) {
                    String str1 = (String) data.getKey();
                    Companies cmpTmp = data.getValue(Companies.class);
                    if (cmpTmp.getACTIVE().equals("1")) {
                        tbl.add(cmpTmp.getNAME());
                        restIDList.add(str1);
                        restNameList.add(cmpTmp.getNAME());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });


        adp = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, tbl);
        sp.setAdapter(adp);
    }

    /**
     * The function checks if the inputs are okay and if so it saves the data in the orders data base
     * and sends you back to the last activity.
     */
    public void finish(View view) {
        int[] flag = {0};

        refUsers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dS) {
                for (DataSnapshot data : dS.getChildren()) {
                    String str1 = (String) data.getKey();
                    Users usrTmp = data.getValue(Users.class);
                    if (usrTmp.getACTIVE().equals("1")) {
                        System.out.println(str1);
                        if (str1.equals(userIdET.getText().toString())) {
                            flag[0] = 1;
                            user_order_name = usrTmp.getFNAME();
                        } else if (str1.equals(userIdET.getText().toString())) {
                            flag[0] = 2;
                        }
                    }
                }
                if (flag[0] == 0) {
                    Toast.makeText(new_order_second_activity.this, "User Doesn't exists", Toast.LENGTH_LONG).show();
                } else if (flag[0] == 2) {
                    Toast.makeText(new_order_second_activity.this, "User Doesn't work", Toast.LENGTH_LONG).show();
                } else {
                    user_order_key_id = userIdET.getText().toString();
                }
                if(user_order_key_id!=null && restaurant_order_key_id!=null) {
                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy HH:mm");
                    refOrders.child(orderPos+"").setValue(new Orders(restaurant_order_key_id,restaurant_order_name,user_order_key_id,user_order_name,formatter.format(calendar.getTime())));



                    setResult(RESULT_OK, gi);
                    finish();
                }
                else{
                    Toast.makeText(new_order_second_activity.this, "ERROR", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


    }

    /**
     * The function sends you back to the last activity.
     */
    public void back(View view) {
        setResult(RESULT_CANCELED, gi);
        finish();
    }

    /**
     * The function sets the selected restaurant.
     */
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (i == 0) {
            restaurant_order_key_id = null;
            restaurant_order_name = null;
        } else {
            restaurant_order_key_id = restIDList.get(i);
            restaurant_order_name = restNameList.get(i);
            System.out.println("i: "+i+"others: "+restaurant_order_key_id+" "+restaurant_order_name);

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}