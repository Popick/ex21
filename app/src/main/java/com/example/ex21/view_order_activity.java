package com.example.ex21;

import static com.example.ex21.FBref.refComps;
import static com.example.ex21.FBref.refMeals;
import static com.example.ex21.FBref.refUsers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

/**
 * @author Etay Sabag <itay45520@gmail.com>
 * @version 2
 * @since 4/5/2022
 *  activity for viewing a specific order
 */
public class view_order_activity extends AppCompatActivity {
    Intent gi;
    TextView appetizerTV, mainTV, sideTV, dessertTV, drinksTV, userTV, restTV, dateTV;
    Intent viewCompanyIntent, viewUserIntent;
    Orders order;
    Meals meal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order);
        gi = getIntent();
        order = (Orders) getIntent().getSerializableExtra("order");
        meal = (Meals) getIntent().getSerializableExtra("meal");


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



        fillOrder();
    }
    /**
     * The function loads the selecteSSd order's data to the all the text views on the screen.
     */
    public void fillOrder() {


        appetizerTV.setText("Appetizer: \n" + meal.getAPPETIZER());
        mainTV.setText("Main Dish: \n" + meal.getMAIN());
        sideTV.setText("Side Meal: \n" + meal.getSIDE());
        dessertTV.setText("Dessert: \n" + meal.getDESSERT());
        drinksTV.setText("Drinks: \n" + meal.getDRINK());


        userTV.setText("Worker Name: \n" + order.getUSER_NAME());
        restTV.setText("Restaurant Name: \n" + order.getRESTAURANT_NAME());
        dateTV.setText(order.getDATE());

        refUsers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dS) {
                for (DataSnapshot data : dS.getChildren()) {
                    if (order.getUSER_ID().equals((String) data.getKey())) {
                        viewUserIntent.putExtra("usr", data.getValue(Users.class));
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        refComps.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dS) {
                for (DataSnapshot data : dS.getChildren()) {
                    if (order.getRESTAURANT_ID().equals((String) data.getKey())) {
                        viewCompanyIntent.putExtra("cmp", data.getValue(Companies.class));
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });


    }
    /**
     *
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