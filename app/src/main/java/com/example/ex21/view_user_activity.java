package com.example.ex21;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class view_user_activity extends AppCompatActivity {
    Intent gi;
    TextView idTV, fnameTV, lnameTV, companyTV, phoneTV;
    Button greenBtn, redBtn, orangeBtn;
    SQLiteDatabase db;
    HelperDB hlp;
    Cursor crsr;
    ContentValues cv;
    String userKeyId = "";
    AlertDialog.Builder adb;
    boolean isActive = true;
    int col1, col2, col3, col4, col5,col6;
    /**
     * @author Etay Sabag <itay45520@gmail.com>
     * @version    1.6
     * @since     5/2/2022
     *  activity for viewing and editing a specific user
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user);
        gi = getIntent();
        userKeyId = gi.getStringExtra("id");


        redBtn = (Button) findViewById(R.id.redButton);
        greenBtn = (Button) findViewById(R.id.greenButton);
        orangeBtn = (Button) findViewById(R.id.orangeButton);
        idTV = (TextView) findViewById(R.id.inputid);
        fnameTV = (TextView) findViewById(R.id.inputfname);
        lnameTV = (TextView) findViewById(R.id.inputlname);
        companyTV = (TextView) findViewById(R.id.inputcompany);
        phoneTV = (TextView) findViewById(R.id.inputphonenumber);

        hlp = new HelperDB(this);
        db = hlp.getWritableDatabase();
        db.close();


        fillUser();
        cv = new ContentValues();

        if (!isActive){
            orangeBtn.setText("Restore Employee");
            orangeBtn.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.restore, 0);
        }
    }

    /**
     * The function loads the selected worker's data to the all the text views on the screen.
     */
    public void fillUser(){
        String selection = com.example.ex21.Users.KEY_ID + "=?";
        String[] selectionArgs = {userKeyId};
        db = hlp.getReadableDatabase();
        crsr = db.query(com.example.ex21.Users.TABLE_USERS, null, selection, selectionArgs, null, null, null);
        col1 = crsr.getColumnIndex(com.example.ex21.Users.FNAME);
        col2 = crsr.getColumnIndex(com.example.ex21.Users.LNAME);
        col3 = crsr.getColumnIndex(com.example.ex21.Users.COMPANY);
        col4 = crsr.getColumnIndex(com.example.ex21.Users.ID);
        col5 = crsr.getColumnIndex(com.example.ex21.Users.PHONE);
        col6 = crsr.getColumnIndex(com.example.ex21.Users.ACTIVE);

        crsr.moveToFirst();
        fnameTV.setText(crsr.getString(col1));
        lnameTV.setText(crsr.getString(col2));
        companyTV.setText(crsr.getString(col3));
        idTV.setText(crsr.getString(col4));
        phoneTV.setText(crsr.getString(col5));
        isActive = crsr.getString(col6).equals("1");
        db.close();
        crsr.close();
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
            fillUser();
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
            idTV.setEnabled(true);
            fnameTV.setEnabled(true);
            lnameTV.setEnabled(true);
            companyTV.setEnabled(true);
            phoneTV.setEnabled(true);
            orangeBtn.setEnabled(true);
        } else if (greenBtn.getText().toString().equals("SAVE")) {
            ContentValues cv = new ContentValues();
            db = hlp.getWritableDatabase();
            cv.put(com.example.ex21.Users.FNAME, fnameTV.getText().toString());
            cv.put(com.example.ex21.Users.LNAME, lnameTV.getText().toString());
            cv.put(com.example.ex21.Users.COMPANY, companyTV.getText().toString());
            cv.put(com.example.ex21.Users.ID, idTV.getText().toString());
            cv.put(com.example.ex21.Users.PHONE, phoneTV.getText().toString());
            db.update(com.example.ex21.Users.TABLE_USERS, cv, com.example.ex21.Users.KEY_ID + "=?", new String[]{userKeyId});

            db.close();

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
                ContentValues cv = new ContentValues();
                if(isActive) cv.put(com.example.ex21.Users.ACTIVE, 0);
                else cv.put(com.example.ex21.Users.ACTIVE, 1);
                db = hlp.getWritableDatabase();
                db.update(com.example.ex21.Users.TABLE_USERS, cv, com.example.ex21.Users.KEY_ID + "=?", new String[]{userKeyId});
                db.close();

                Toast.makeText(view_user_activity.this, "Deleted Successfully!", Toast.LENGTH_LONG).show();
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