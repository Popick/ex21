package com.example.ex21;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

/**
 * @author Etay Sabag <itay45520@gmail.com>
 * @version    1.0
 * @since     25/2/2022
 *  Credits activity
 */
public class credits_activity extends AppCompatActivity {
    Intent siUsers,siCompanies,siOrder,siViewOrder,siHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);

        siUsers = new Intent(this, com.example.ex21.users_screen.class);
        siCompanies = new Intent(this, com.example.ex21.comp_screen.class);
        siOrder = new Intent(this, com.example.ex21.new_order_activity.class);
        siViewOrder = new Intent(this,order_screen.class);
        siHome = new Intent(this, com.example.ex21.MainActivity.class);

    }
    /**
     * Creates the menu in the activity.
     *
     * @return Returns True
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
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
                startActivity(siViewOrder);
                break;
            case "Credits":
                break;
        }

        return true;
    }
}