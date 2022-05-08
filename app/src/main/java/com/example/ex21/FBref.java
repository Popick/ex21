package com.example.ex21;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * @author Etay Sabag <itay45520@gmail.com>
 * @version 2
 * @since 4/5/2022
 * Helper to the data base class.
 */

public class FBref {
    public static FirebaseDatabase FBDB = FirebaseDatabase.getInstance("https://ex21-93240-default-rtdb.europe-west1.firebasedatabase.app");
    public static DatabaseReference refUsers=FBDB.getReference("Users");
    public static DatabaseReference refComps=FBDB.getReference("Companies");
    public static DatabaseReference refOrders=FBDB.getReference("Orders");
    public static DatabaseReference refMeals=FBDB.getReference("Meals");


}
