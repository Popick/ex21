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
 * @version    1.3
 * @since     18/2/2022
 *  activity for viewing all the companies
 */
public class comp_screen extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView lv;
    SQLiteDatabase db;
    HelperDB hlp;
    ImageButton addBtn;
    Intent newCompanyIntent, viewCompanyIntent, siUsers, siHome, siViewOrder, siCredits;
    Cursor crsr;
    ArrayList<String> tbl;
    ArrayList<String> cardIDs;
    ArrayAdapter<String> adp;
    AlertDialog.Builder adb;
    final String[] filterAD = {"Active", "Inactive"};
    final String[] sortAD = {"Key ID", "Name A→Z", "Name Z→A", "Tax Number 0→9"};
    String filter = "1", sort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_screen);
        newCompanyIntent = new Intent(this, com.example.ex21.new_company_activity.class);
        viewCompanyIntent = new Intent(this, com.example.ex21.view_company_activity.class);
        hlp = new HelperDB(this);
        addBtn = (ImageButton) findViewById(R.id.addBtn);
        addBtn.setImageResource(R.drawable.addcomp);

        siUsers = new Intent(this, users_screen.class);
        siHome = new Intent(this, MainActivity.class);
        siViewOrder = new Intent(this, order_screen.class);
        siCredits = new Intent(this, credits_activity.class);

        lv = (ListView) findViewById(R.id.listview);
        lv.setOnItemClickListener(this);
        lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        update_comps(filter, sort);
    }

    @Override
    protected void onResume() {
        super.onResume();
        update_comps(filter, sort);
    }

    /**
     * The function loads all the restaurants to the List View on the screen.
     *
     * @param filterPar Description:  The parameter contains the information of how the user wants to
     *                  filter the restaurants that he requests from the query.
     * @param sortPar   Description:  The parameter contains the information of how the user wants to
     *                  sort the restaurants that he requests from the query.
     */
    public void update_comps(String filterPar, String sortPar) {
        db = hlp.getWritableDatabase();
        tbl = new ArrayList<>();
        cardIDs = new ArrayList<>();
        if (filterPar != null)
            crsr = db.query(Companies.TABLE_COMPANIES, null, Companies.ACTIVE + "=?", new String[]{filterPar}, null, null, sortPar);
        else
            crsr = db.query(Companies.TABLE_COMPANIES, null, null, null, null, null, sortPar);

        int col1 = crsr.getColumnIndex(Companies.KEY_ID);
        int col2 = crsr.getColumnIndex(Companies.NAME);

        crsr.moveToFirst();
        while (!crsr.isAfterLast()) {
            int key = crsr.getInt(col1);
            String name = crsr.getString(col2);
            String tmp = "" + key + ". " + name;
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
     *    The function sends you activity where you can add a new restaurant.
     */
    public void addNewItem(View view) {
        startActivity(newCompanyIntent);
    }

    /**
     *     The function sends you to the last activity.
     */
    public void back(View view) {
        finish();
    }

    /**
     * The function showing an Alert Dialog from which the user can choose his preferred sorting
     * type and then the function refills the restaurants in the List View with the preferred sorting type.
     */
    public void sort(View view) {
        adb = new AlertDialog.Builder(this);
        adb.setTitle("Sort Companies");
        adb.setItems(sortAD, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int pos) {
                if (pos == 0) {
                    sort = Companies.KEY_ID;
                } else if (pos == 1) {
                    sort = Companies.NAME;
                } else if (pos == 2) {
                    sort = Companies.NAME + " DESC";
                } else if (pos == 3) {
                    sort = Companies.TAX_ID;
                }
                update_comps(filter, sort);
            }
        });
        AlertDialog ad = adb.create();
        ad.show();
    }
    /**
     * The function showing an Alert Dialog from which the user can choose his preferred filtering
     * type and then the function refills the restaurants in the List View with the preferred filter type.
     */
    public void filter(View view) {
        String[] arr = {"0", "0"};
        adb = new AlertDialog.Builder(this);
        adb.setTitle("Filter Employees");
        adb.setMultiChoiceItems(filterAD, null, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int pos, boolean isChecked) {
                if (isChecked) arr[pos] = "1";
                else arr[pos] = "0";
            }
        });
        adb.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch ((arr[0] + arr[1])) {
                    case "01":
                        filter = "0";
                        break;
                    case "10":
                        filter = "1";
                        break;
                    case "11":
                        filter = null;
                        break;
                    default:
                        filter = "3";
                        break;
                }
                update_comps(filter, sort);
            }
        });
        adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        AlertDialog ad = adb.create();
        ad.show();
    }

    /**
     * The function sends the user to the activity where he can view and edit the selected restaurant.
     *
     * @param position Description  The parameter is the position of the clicked item
     *                 in the List View.
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        viewCompanyIntent.putExtra("id", cardIDs.get(position));
        startActivity(viewCompanyIntent);
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
                break;
            case "Orders":
                startActivity(siViewOrder);
                break;
            case "Credits":
                startActivity(siCredits);
                break;
        }
        return true;
    }

}

