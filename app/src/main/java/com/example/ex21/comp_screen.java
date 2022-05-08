package com.example.ex21;

import static com.example.ex21.FBref.refComps;

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

/**
 * @author Etay Sabag <itay45520@gmail.com>
 * @version   2
 * @since     4/5/2022
 *  activity for viewing all the companies
 */
public class comp_screen extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView lv;
    ImageButton addBtn;
    Intent newCompanyIntent, viewCompanyIntent, siUsers, siHome, siViewOrder, siCredits;
    ValueEventListener cmpListener;
    ArrayList<String> cmpList = new ArrayList<String>();
    ArrayList<Companies> cmpValues = new ArrayList<Companies>();
    ArrayAdapter<String> adp;
    AlertDialog.Builder adb;
    final String[] filterAD = {"Active", "Inactive"};
    final String[] sortAD = {"TAX ID", "Name"};
    String filter = "1";
    String sort = "taxid";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_screen);
        newCompanyIntent = new Intent(this, com.example.ex21.new_company_activity.class);
        viewCompanyIntent = new Intent(this, com.example.ex21.view_company_activity.class);
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
        Query query = refComps.orderByChild(sortPar);
        cmpListener = new ValueEventListener() {


            /**
             * This method will be called with a snapshot of the data at this location. It will also be called
             * each time that data changes.
             *
             * @param dS The current data at the location
             */

            @Override
            public void onDataChange(DataSnapshot dS) {
                cmpList.clear();
                cmpValues.clear();
                for (DataSnapshot data : dS.getChildren()) {
                    String str1 = (String) data.getKey();
                    Companies cmpTmp = data.getValue(Companies.class);
                    if (filterPar == null || cmpTmp.getACTIVE().equals(filterPar)){
                        cmpValues.add(cmpTmp);
                        cmpList.add(cmpTmp.getNAME() + " (id: " + str1 + ")");
                    }
                }
                adp = new ArrayAdapter<String>(comp_screen.this, R.layout.support_simple_spinner_dropdown_item, cmpList);
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
        query.addValueEventListener(cmpListener);
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
                    sort = "tax_ID";
                } else if (pos == 1) {
                    sort = "name";
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
        viewCompanyIntent.putExtra("cmp", cmpValues.get(position));
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

