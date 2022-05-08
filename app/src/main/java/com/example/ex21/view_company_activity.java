package com.example.ex21;

import static com.example.ex21.FBref.refComps;
import static com.example.ex21.FBref.refUsers;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * @author Etay Sabag <itay45520@gmail.com>
 * @version 2
 * @since 4/5/2022
 *  activity for viewing and editing a specific company
 */
public class view_company_activity extends AppCompatActivity {

    Intent gi;
    TextView nameTV, taxTV, sPhoneTV, mPhoneTV;
    Button greenBtn, redBtn, orangeBtn;
    AlertDialog.Builder adb;
    boolean isActive = true;
    Companies comp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_company);
        gi = getIntent();
        comp = (Companies) getIntent().getSerializableExtra("cmp");


        redBtn = (Button) findViewById(R.id.redButton);
        greenBtn = (Button) findViewById(R.id.greenButton);
        orangeBtn = (Button) findViewById(R.id.orangeButton);
        nameTV = (TextView) findViewById(R.id.inputappetizer);
        taxTV = (TextView) findViewById(R.id.inputtax);
        mPhoneTV = (TextView) findViewById(R.id.inputmphone);
        sPhoneTV = (TextView) findViewById(R.id.inputsphone);


        fillCompany();

        if (!isActive) {
            orangeBtn.setText("Restore Comapny");
            orangeBtn.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.restore, 0);
        }
    }

    /**
    * The function loads the selected company's data to the all the text views on the screen.
    */
    public void fillCompany() {

        nameTV.setText(comp.getNAME());
        taxTV.setText(comp.getTAX_ID());
        mPhoneTV.setText(comp.getMAIN_PHONE());
        sPhoneTV.setText(comp.getSECONDARY_PHONE());
        isActive = comp.getACTIVE().equals("1");
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
            mPhoneTV.setEnabled(true);
            sPhoneTV.setEnabled(true);
            orangeBtn.setEnabled(true);
        } else if (greenBtn.getText().toString().equals("SAVE")) {

            comp.setNAME(nameTV.getText().toString());
            comp.setMAIN_PHONE(mPhoneTV.getText().toString());
            comp.setSECONDARY_PHONE(sPhoneTV.getText().toString());

            refComps.child(comp.getTAX_ID()).setValue(comp);


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
                if (isActive) comp.setACTIVE("0");
                else comp.setACTIVE("1");

                refComps.child(comp.getTAX_ID()).setValue(comp);


                if (isActive) Toast.makeText(view_company_activity.this, "Deleted Successfully!", Toast.LENGTH_LONG).show();
                else Toast.makeText(view_company_activity.this, "Restored Successfully!", Toast.LENGTH_LONG).show();
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