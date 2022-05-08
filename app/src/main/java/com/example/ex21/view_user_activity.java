package com.example.ex21;

import static com.example.ex21.FBref.refUsers;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;


public class view_user_activity extends AppCompatActivity {
    Intent gi;
    TextView idTV, fnameTV, lnameTV, companyTV, phoneTV;
    Button greenBtn, redBtn, orangeBtn;
    ContentValues cv;
    ArrayList<Users> usrValues = new ArrayList<Users>();
    Users user;
    String userPos = "";
    AlertDialog.Builder adb;
    boolean isActive = true;

    /**
     * @author Etay Sabag <itay45520@gmail.com>
     * @version 2
     * @since 4/5/2022
     *  activity for viewing and editing a specific user
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user);
        gi = getIntent();
        user = (Users) getIntent().getSerializableExtra("usr");


        redBtn = (Button) findViewById(R.id.redButton);
        greenBtn = (Button) findViewById(R.id.greenButton);
        orangeBtn = (Button) findViewById(R.id.orangeButton);
        idTV = (TextView) findViewById(R.id.inputid);
        fnameTV = (TextView) findViewById(R.id.inputfname);
        lnameTV = (TextView) findViewById(R.id.inputlname);
        companyTV = (TextView) findViewById(R.id.inputcompany);
        phoneTV = (TextView) findViewById(R.id.inputphonenumber);




        fillUser();

        if (!isActive){
            orangeBtn.setText("Restore Employee");
            orangeBtn.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.restore, 0);
        }
    }

    /**
     * The function loads the selected worker's data to the all the text views on the screen.
     */
    public void fillUser(){
        String[] selectionArgs = {userPos};

        fnameTV.setText(user.getFNAME());
        lnameTV.setText(user.getLNAME());
        companyTV.setText(user.getCOMPANY());
        idTV.setText(user.getID());
        phoneTV.setText(user.getPHONE());
        isActive = user.getACTIVE().equals("1");


    }
    /**
     * The function checks if in edit mode or not, if in edit mode it will cancel all the changes
     * else it will go back to the last activity
     */
    public void back(View view) {
        if (redBtn.getText().toString().equals("BACK")) {
            finish();
        } else if (redBtn.getText().toString().equals("CANCEL")) {
            greenBtn.setText("EDIT");
            redBtn.setText("BACK");
            idTV.setEnabled(false);
            fnameTV.setEnabled(false);
            lnameTV.setEnabled(false);
            companyTV.setEnabled(false);
            phoneTV.setEnabled(false);
            orangeBtn.setEnabled(false);
//            fillUser();
        }
    }
    /**
     * The function checks if in edit mode or not, if in edit mode it will save all the changes
     * else it enter you into edit mode
     */
    public void editsave(View view) {
        if (greenBtn.getText().toString().equals("EDIT")) {
            greenBtn.setText("SAVE");
            redBtn.setText("CANCEL");
            fnameTV.setEnabled(true);
            lnameTV.setEnabled(true);
            companyTV.setEnabled(true);
            phoneTV.setEnabled(true);
            orangeBtn.setEnabled(true);
        } else if (greenBtn.getText().toString().equals("SAVE")) {

            user.setFNAME(fnameTV.getText().toString());
            user.setLNAME(lnameTV.getText().toString());
            user.setCOMPANY(companyTV.getText().toString());
            user.setPHONE(phoneTV.getText().toString());

            refUsers.child(user.getID()).setValue(user);


            Toast.makeText(this, "Saved Successfully!", Toast.LENGTH_LONG).show();
            finish();
        }
    }
    /**
     * The function will change the worker's employment state.
     */
    public void delete(View view) {
        adb = new AlertDialog.Builder(this);
        adb.setTitle("Are You Sure?");
        if(isActive) adb.setMessage("Are you sure you want to delete the employee?");
        else adb.setMessage("Are you sure you want to restore the employee?");
        adb.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if(isActive) user.setACTIVE("0");
                else user.setACTIVE("1");

                refUsers.child(user.getID()).setValue(user);

                if(isActive) Toast.makeText(view_user_activity.this, "Deleted Successfully!", Toast.LENGTH_LONG).show();
                else Toast.makeText(view_user_activity.this, "Restored Successfully!", Toast.LENGTH_LONG).show();
                finish();
            }
        });
        adb.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.cancel();
            }
        });
        AlertDialog ad = adb.create();
        ad.show();
    }
}