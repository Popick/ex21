package com.example.ex21;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

/**
 * @author Etay Sabag <itay45520@gmail.com>
 * @version 1.4
 * @since 5/2/2022
 * The main activity of the app.
 */
public class MainActivity extends AppCompatActivity {


    Intent siUsers, siCompanies, siOrder, siViewOrder, siCredits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        siUsers = new Intent(this, com.example.ex21.users_screen.class);
        siCompanies = new Intent(this, com.example.ex21.comp_screen.class);
        siViewOrder = new Intent(this, order_screen.class);
        siCredits = new Intent(this, credits_activity.class);
        siOrder = new Intent(this, com.example.ex21.new_order_activity.class);
    }

    /**
     * The function sends you to the users table activity.
     */
    public void usersClick(View view) {
        startActivity(siUsers);
    }

    /**
     *    The function sends you to the companies table activity.
     */
    public void companiesClick(View view) {
        startActivity(siCompanies);
    }

    /**
     * The function sends you to activity where you can make a new order.
     */
    public void newOrderClick(View view) {
        startActivity(siOrder);
    }

    /**
     * The function sends you to the orders table activity.
     */
    public void viewOrdersClick(View view) {
        startActivity(siViewOrder);
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

                break;
            case "Users":
                startActivity(siUsers);
                break;
            case "Restaurants":
                startActivity(siCompanies);
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

