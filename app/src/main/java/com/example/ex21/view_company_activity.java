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

/**
 * @author Etay Sabag <itay45520@gmail.com>
 * @version    1.4
 * @since     19/2/2022
 *  activity for viewing and editing a specific company
 */
public class view_company_activity extends AppCompatActivity {

    Intent gi;
    TextView nameTV, taxTV, sPhoneTV, mPhoneTV;
    Button greenBtn, redBtn, orangeBtn;
    SQLiteDatabase db;
    HelperDB hlp;
    Cursor crsr;
    ContentValues cv;
    String companyKeyId = "";
    AlertDialog.Builder adb;
    boolean isActive = true;
    int col1, col2, col3, col4, col5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_company);
        gi = getIntent();
        companyKeyId = gi.getStringExtra("id");


        redBtn = (Button) findViewById(R.id.redButton);
        greenBtn = (Button) findViewById(R.id.greenButton);
        orangeBtn = (Button) findViewById(R.id.orangeButton);
        nameTV = (TextView) findViewById(R.id.inputappetizer);
        taxTV = (TextView) findViewById(R.id.inputtax);
        mPhoneTV = (TextView) findViewById(R.id.inputmphone);
        sPhoneTV = (TextView) findViewById(R.id.inputsphone);

        hlp = new HelperDB(this);
        db = hlp.getWritableDatabase();
        db.close();


        fillCompany();
        cv = new ContentValues();

        if (!isActive) {
            orangeBtn.setText("Restore Comapny");
            orangeBtn.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.restore, 0);
        }
    }

    /**
    * The function loads the selected company's data to the all the text views on the screen.
    */
    public void fillCompany() {
        String selection = Companies.KEY_ID + "=?";
        String[] selectionArgs = {companyKeyId};
        db = hlp.getReadableDatabase();
        crsr = db.query(Companies.TABLE_COMPANIES, null, selection, selectionArgs, null, null, null);
        col1 = crsr.getColumnIndex(Companies.NAME);
        col2 = crsr.getColumnIndex(Companies.TAX_ID);
        col3 = crsr.getColumnIndex(Companies.MAIN_PHONE);
        col4 = crsr.getColumnIndex(Companies.SECONDARY_PHONE);
        col5 = crsr.getColumnIndex(Companies.ACTIVE);

        crsr.moveToFirst();
        nameTV.setText(crsr.getString(col1));
        taxTV.setText(crsr.getString(col2));
        mPhoneTV.setText(crsr.getString(col3));
        sPhoneTV.setText(crsr.getString(col4));
        isActive = crsr.getString(col5).equals("1");
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
            nameTV.setEnabled(false);
            taxTV.setEnabled(false);
            mPhoneTV.setEnabled(false);
            sPhoneTV.setEnabled(false);
            orangeBtn.setEnabled(false);
            fillCompany();
        }
    }
    /**
     * The function checks if in edit mode or not, if in edit mode it will save all the changes
     * else it enter you into edit mode
     * */
    public void editsave(View view) {
        if (greenBtn.getText().toString().equals("EDIT")) {
            greenBtn.setText("SAVE");
            redBtn.setText("CANCEL");
            nameTV.setEnabled(true);
            taxTV.setEnabled(true);
            mPhoneTV.setEnabled(true);
            sPhoneTV.setEnabled(true);
            orangeBtn.setEnabled(true);
        } else if (greenBtn.getText().toString().equals("SAVE")) {
            ContentValues cv = new ContentValues();
            db = hlp.getWritableDatabase();
            cv.put(Companies.NAME, nameTV.getText().toString());
            cv.put(Companies.TAX_ID, taxTV.getText().toString());
            cv.put(Companies.MAIN_PHONE, mPhoneTV.getText().toString());
            cv.put(Companies.SECONDARY_PHONE, sPhoneTV.getText().toString());
            db.update(Companies.TABLE_COMPANIES, cv, com.example.ex21.Users.KEY_ID + "=?", new String[]{companyKeyId});

            db.close();

            Toast.makeText(this, "Saved Successfully!", Toast.LENGTH_LONG).show();
            finish();
        }
    }
    /**
    The function will change the worker's employment state.
    **/
    public void delete(View view) {
        adb = new AlertDialog.Builder(this);
        adb.setTitle("Are You Sure?");
        if (isActive) adb.setMessage("Are you sure you want to delete the company?");
        else adb.setMessage("Are you sure you want to restore the company?");
        adb.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                ContentValues cv = new ContentValues();
                if (isActive) cv.put(Companies.ACTIVE, 0);
                else cv.put(Companies.ACTIVE, 1);
                db = hlp.getWritableDatabase();
                db.update(Companies.TABLE_COMPANIES, cv, Companies.KEY_ID + "=?", new String[]{companyKeyId});
                db.close();

                Toast.makeText(view_company_activity.this, "Deleted Successfully!", Toast.LENGTH_LONG).show();
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