package com.example.ex21;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

/**
 * @author Etay Sabag <itay45520@gmail.com>
 * @version    1.2
 * @since     21/2/2022
 *  activity for viewing all the orders
 */
public class order_screen extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView lv;
    SQLiteDatabase db;
    HelperDB hlp;
    Intent viewOrderIntent, siUsers, siHome, siCompanies, siCredits;
    Cursor crsr;
    ArrayList<String> tbl;
    ArrayList<String> cardIDs;
    ArrayAdapter<String> adp;
    AlertDialog.Builder adb;
    final String[] sortAD = {"Date", "Name A→Z", "Restaurant A→Z"};
    String sort;
    ImageButton fltrBtn, addBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_screen);

        viewOrderIntent = new Intent(this, com.example.ex21.view_order_activity.class);
        hlp = new HelperDB(this);

        siUsers = new Intent(this, com.example.ex21.users_screen.class);
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
     * @param sortPar   Description:  The parameter contains the information of how the user wants to
     *                  sort the orders that he requests from the query.
     */
    public void update_Orders(String sortPar) {
        db = hlp.getWritableDatabase();
        tbl = new ArrayList<>();
        cardIDs = new ArrayList<>();
        crsr = db.query(com.example.ex21.Orders.TABLE_ORDERS, null, null, null, null, null, sortPar);

        int col1 = crsr.getColumnIndex(com.example.ex21.Orders.KEY_ID);
        int col2 = crsr.getColumnIndex(com.example.ex21.Orders.RESTAURANT_NAME);
        int col3 = crsr.getColumnIndex(com.example.ex21.Orders.USER_NAME);
        int col4 = crsr.getColumnIndex(com.example.ex21.Orders.DATE);

        crsr.moveToFirst();
        while (!crsr.isAfterLast()) {
            int key = crsr.getInt(col1);
            String rName = crsr.getString(col2);
            String uName = crsr.getString(col3);
            String date = crsr.getString(col4);
            String tmp = "" + key + ". User: " + uName + ", Shop: " + rName + ", At " + date;
            tbl.add(tmp);
            cardIDs.add(key + "");
            crsr.moveToNext();
        }
        crsr.close();
        db.close();
        adp = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, tbl);
        lv.setAdapter(adp);
    }

    /**
     *    The function sends you to the last activity.
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
                    sort = com.example.ex21.Orders.KEY_ID;
                } else if (pos == 1) {
                    sort = com.example.ex21.Orders.USER_NAME;
                } else if (pos == 2) {
                    sort = com.example.ex21.Orders.RESTAURANT_NAME;
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
        viewOrderIntent.putExtra("id", cardIDs.get(position));

        startActivity(viewOrderIntent);
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
