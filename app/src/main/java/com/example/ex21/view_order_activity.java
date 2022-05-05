package com.example.ex21;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * @author Etay Sabag <itay45520@gmail.com>
 * @version    1.4
 * @since     21/2/2022
 *  activity for viewing a specific order
 */
public class view_order_activity extends AppCompatActivity {
    Intent gi;
    TextView appetizerTV, mainTV, sideTV, dessertTV, drinksTV, userTV, restTV, dateTV;
    SQLiteDatabase db;
    HelperDB hlp;
    Cursor crsr;
    String userKeyId = "";
    int col1, col2, col3, col4, col5;
    Intent viewCompanyIntent, viewUserIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order);
        gi = getIntent();
        userKeyId = gi.getStringExtra("id");

        viewUserIntent = new Intent(this, com.example.ex21.view_user_activity.class);
        viewCompanyIntent = new Intent(this, com.example.ex21.view_company_activity.class);


        appetizerTV = (TextView) findViewById(R.id.appetizerTV);
        mainTV = (TextView) findViewById(R.id.mainTV);
        sideTV = (TextView) findViewById(R.id.sideTV);
        dessertTV = (TextView) findViewById(R.id.dessertTV);
        drinksTV = (TextView) findViewById(R.id.drinksTV);
        userTV = (TextView) findViewById(R.id.userTV);
        restTV = (TextView) findViewById(R.id.restTV);
        dateTV = (TextView) findViewById(R.id.dateTV);

        hlp = new HelperDB(this);


        fillOrder();
    }
    /**
     * The function loads the selected order's data to the all the text views on the screen.
     */
    public void fillOrder() {
        String selection = com.example.ex21.Meals.KEY_ID + "=?";
        String[] selectionArgs = {userKeyId};
        db = hlp.getReadableDatabase();
        crsr = db.query(com.example.ex21.Meals.TABLE_MEALS, null, selection, selectionArgs, null, null, null);
        col1 = crsr.getColumnIndex(com.example.ex21.Meals.APPETIZER);
        col2 = crsr.getColumnIndex(com.example.ex21.Meals.MAIN);
        col3 = crsr.getColumnIndex(com.example.ex21.Meals.SIDE);
        col4 = crsr.getColumnIndex(com.example.ex21.Meals.DESSERT);
        col5 = crsr.getColumnIndex(com.example.ex21.Meals.DRINK);

        crsr.moveToFirst();
        appetizerTV.setText("Appetizer: \n" + crsr.getString(col1));
        mainTV.setText("Main Dish: \n" + crsr.getString(col2));
        sideTV.setText("Side Meal: \n" + crsr.getString(col3));
        dessertTV.setText("Dessert: \n" + crsr.getString(col4));
        drinksTV.setText("Drinks: \n" + crsr.getString(col5));

        db.close();
        crsr.close();

        selection = com.example.ex21.Orders.KEY_ID + "=?";
        db = hlp.getReadableDatabase();
        crsr = db.query(com.example.ex21.Orders.TABLE_ORDERS, null, selection, selectionArgs, null, null, null);
        col1 = crsr.getColumnIndex(com.example.ex21.Orders.USER_NAME);
        col2 = crsr.getColumnIndex(com.example.ex21.Orders.USER_ID);
        col3 = crsr.getColumnIndex(com.example.ex21.Orders.RESTAURANT_NAME);
        col4 = crsr.getColumnIndex(com.example.ex21.Orders.RESTAURANT_ID);
        col5 = crsr.getColumnIndex(com.example.ex21.Orders.DATE);

        crsr.moveToFirst();
        userTV.setText("Worker Name: \n" + crsr.getString(col1));
        restTV.setText("Restaurant Name: \n" + crsr.getString(col3));
        dateTV.setText(crsr.getString(col5));

        viewUserIntent.putExtra("id", crsr.getString(col2));
        viewCompanyIntent.putExtra("id", crsr.getString(col4));

        db.close();
        crsr.close();

    }
    /**
     * The function sends the user back to the last activity.
     */
    public void back(View view) {
        finish();
    }
    /**
     * The function sends the user to the activity where he can edit and view the specific worker.
     */
    public void goUser(View view) {

        startActivity(viewUserIntent);


    }
    /**
     * The function sends the user to the activity where he can edit and view the specific restaurant.
     */
    public void goRest(View view) {
        startActivity(viewCompanyIntent);

    }
}