package com.example.ex21;

import static com.example.ex21.FBref.refMeals;
import static com.example.ex21.FBref.refOrders;
import static com.example.ex21.FBref.refUsers;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author Etay Sabag <itay45520@gmail.com>
 * @version 2.2
 * @since 4/5/2022
 * activity for viewing all the orders
 */
public class order_screen extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView lv;
    Intent viewOrderIntent, siUsers, siHome, siCompanies, siCredits;
    ArrayList<String> tbl;
    ValueEventListener ordrListener;
    ArrayList<String> ordrList = new ArrayList<String>();
    ArrayList<Orders> ordrValues = new ArrayList<Orders>();
    ArrayList<String> ordrPositions = new ArrayList<String>();
    ArrayAdapter<String> adp;
    AlertDialog.Builder adb;
    final String[] sortAD = {"Date", "Name", "Restaurant"};
    String sort;
    ImageButton fltrBtn, addBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_screen);

        viewOrderIntent = new Intent(this, com.example.ex21.view_order_activity.class);

        siUsers = new Intent(this, com.example.ex21.order_screen.class);
        siHome = new Intent(this, com.example.ex21.MainActivity.class);
        siCompanies = new Intent(this, com.example.ex21.comp_screen.class);
        siCredits = new Intent(this, com.example.ex21.credits_activity.class);

        lv = (ListView) findViewById(R.id.listview);
        fltrBtn = (ImageButton) findViewById(R.id.filterBtn);
        addBtn = (ImageButton) findViewById(R.id.addBtn);
        fltrBtn.setVisibility(View.GONE);
        addBtn.setVisibility(View.GONE);
        lv.setOnItemClickListener(this);
        lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        update_Orders(sort);
    }

    @Override
    protected void onResume() {
        super.onResume();
        update_Orders(sort);
    }

    /**
     * The function loads all the orders to the List View on the screen.
     *
     * @param sortPar Description:  The parameter contains the information of how the user wants to
     *                sort the orders that he requests from the query.
     */
    public void update_Orders(String sortPar) {
        Query query;
        if (sortPar == null) {
            query = refOrders.orderByKey();
        } else {
            query = refOrders.orderByChild(sortPar);
        }

        ordrListener = new ValueEventListener() {


            /**
             * This method will be called with a snapshot of the data at this location. It will also be called
             * each time that data changes.
             *
             * @param dS The current data at the location
             */

            @Override
            public void onDataChange(DataSnapshot dS) {
                ordrList.clear();
                ordrValues.clear();
                for (DataSnapshot data : dS.getChildren()) {
                    String str1 = (String) data.getKey();
                    ordrPositions.add(str1);
                    Orders ordrTmp = data.getValue(Orders.class);
                    ordrValues.add(ordrTmp);
                    ordrList.add(ordrTmp.getUSER_NAME() + ", " + ordrTmp.getRESTAURANT_NAME() + " (At: " + ordrTmp.getDATE() + ")");

                }
                if (sortPar == null) {
                    Collections.reverse(ordrList);
                }
                adp = new ArrayAdapter<String>(order_screen.this, R.layout.support_simple_spinner_dropdown_item, ordrList);
                lv.setAdapter(adp);
            }


            /**
             * This method will be triggered in the event that this listener either failed at the server, or
             * is removed as a result of the security and Firebase Database rules. For more information on
             * securing your data, see: <a
             * href="https://firebase.google.com/docs/database/security/quickstart" target="_blank"> Security
             * Quickstart</a>
             *
             * @param error A description of the error that occurred
             */
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        };
        query.addValueEventListener(ordrListener);

    }

    /**
     * The function sends you to the last activity.
     */
    public void back(View view) {
        finish();
    }

    /**
     * The function showing an Alert Dialog from which the user can choose his preferred sorting
     * type and then the function refills the orders in the List View with the preferred sorting type.
     */
    public void sort(View view) {
        adb = new AlertDialog.Builder(this);
        adb.setTitle("Sort Employees");
        adb.setItems(sortAD, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int pos) {
                if (pos == 0) {
                    sort = null;
                } else if (pos == 1) {
                    sort = "user_NAME";
                } else if (pos == 2) {
                    sort = "restaurant_NAME";
                }
                update_Orders(sort);
            }
        });
        AlertDialog ad = adb.create();
        ad.show();
    }

    /**
     * The function sends the user to the activity where he can view the selected order.
     *
     * @param position Description  The parameter is the position of the clicked item
     *                 in the List View.
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        refMeals.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dS) {
                for (DataSnapshot data : dS.getChildren()) {
                    if (ordrPositions.get(position).equals((String) data.getKey())) {
                        viewOrderIntent.putExtra("order", ordrValues.get(position));
                        viewOrderIntent.putExtra("meal", data.getValue(Meals.class));
                    }
                }
                startActivity(viewOrderIntent);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    /**
     * Creates the menu in the activity.
     *
     * @return Returns True
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * The function sends the user to the activity that he chose in the menu.
     *
     * @return Returns True
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        String itemName = item.getTitle().toString();
        switch (itemName) {
            case "Home":
                startActivity(siHome);
                break;
            case "Users":
                startActivity(siUsers);
                break;
            case "Restaurants":
                startActivity(siCompanies);
                break;
            case "Orders":
                break;
            case "Credits":
                startActivity(siCredits);
                break;
        }
        return true;
    }
}
