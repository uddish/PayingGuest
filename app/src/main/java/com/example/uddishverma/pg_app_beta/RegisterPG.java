package com.example.uddishverma.pg_app_beta;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;
import com.firebase.client.Firebase;
import com.sackcentury.shinebuttonlib.ShineButton;
import android.os.Handler;

//************************************Class to Register PGs************************************************************
//TODO complete shineBtnClickListener() method
public class RegisterPG extends AppCompatActivity {

    static Firebase firebaseRef;

    public static final String TAG = "RegisterPG";

    EditText pgName,ownerName, contactNo, email, rent, depositAmount, extraFeatures, addressOne, addressTwo,
                city, state, pinCode;
    CheckBox wifi, ac, breakfast, lunch, dinner, parking, roWater, security, tv, hotWater, refrigerator;
    RadioButton individual, sharing, male, female;
    Button submitBtn;
    ShineButton shineButton;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_pg);

        shineButton = (ShineButton) findViewById(R.id.shine_button);
        shineButton.init(this);

        //attaching the edit texts
        pgName = (EditText) findViewById(R.id.pg_name_et);
        ownerName = (EditText) findViewById(R.id.owner_name_et);
        contactNo = (EditText) findViewById(R.id.contactNumber_et);
        email = (EditText) findViewById(R.id.email_et);
        addressOne = (EditText) findViewById(R.id.address_line_one_et);
        addressTwo = (EditText) findViewById(R.id.address_line_two_et);
        city = (EditText) findViewById(R.id.city_et);
        state = (EditText) findViewById(R.id.state_et);
        pinCode = (EditText) findViewById(R.id.pincode_et);
        rent = (EditText) findViewById(R.id.rent_et);
        depositAmount = (EditText) findViewById(R.id.deposit_et);
        extraFeatures = (EditText) findViewById(R.id.extra_et);

        //attaching the checkboxes
        wifi = (CheckBox) findViewById(R.id.chk_wifi);
        ac = (CheckBox) findViewById(R.id.chk_ac);
        breakfast = (CheckBox) findViewById(R.id.chk_breakfast);
        lunch = (CheckBox) findViewById(R.id.chk_lunch);
        dinner = (CheckBox) findViewById(R.id.chk_dinner);
        parking = (CheckBox) findViewById(R.id.chk_parking);
        roWater = (CheckBox) findViewById(R.id.chk_purifiedwater);
        security = (CheckBox) findViewById(R.id.chk_security);
        tv = (CheckBox) findViewById(R.id.chk_tv);
        hotWater = (CheckBox) findViewById(R.id.chk_hotwater);
        refrigerator = (CheckBox) findViewById(R.id.chk_refrigerator);

        //disabling keyboard when the register activity opens
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        Long timeStamp = System.currentTimeMillis()/1000;
        final String PgId = timeStamp.toString();

        Firebase.setAndroidContext(this);
        firebaseRef = new Firebase("https://pgapp-c51ce.firebaseio.com/");

        shineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int check = checkForNullFields();
                if (check == 0) {
                    PgDetails_POJO.PgDetails pgDetails = new PgDetails_POJO.PgDetails(PgId, pgName.getText().toString(), ownerName.getText().toString(),
                            Double.parseDouble(contactNo.getText().toString()), email.getText().toString(),
                            Double.parseDouble(rent.getText().toString()), Double.parseDouble(depositAmount.getText().toString()), extraFeatures.getText().toString(),
                            wifi.isChecked(), breakfast.isChecked(), parking.isChecked(), ac.isChecked(), lunch.isChecked(), dinner.isChecked(),
                            roWater.isChecked(),security.isChecked(), tv.isChecked(), hotWater.isChecked(), refrigerator.isChecked(),
                            addressOne.getText().toString(), addressTwo.getText().toString(),
                            city.getText().toString(), state.getText().toString(), Double.parseDouble(pinCode.getText().toString()));

                    firebaseRef.child("PgDetails").push().setValue(pgDetails);
                    Toast.makeText(RegisterPG.this, "DETAILS SUBMITTED", Toast.LENGTH_SHORT).show();
                    registerComplete();
                    shineBtnClickListener();
                }
            }
        });

    }

    private void shineBtnClickListener()    {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(RegisterPG.this, MainActivity.class);
                startActivity(i);
                // close this activity
                finish();
            }
        }, 1000);
    }

    private void registerComplete() {
        pgName.setText("");
        addressOne.setText("");
        ownerName.setText("");
        contactNo.setText("");
        email.setText("");
        addressOne.setText("");
        addressTwo.setText("");
        city.setText("");
        state.setText("");
        pinCode.setText("");
        rent.setText("");
        depositAmount.setText("");
        extraFeatures.setText("");
//        startActivity(new Intent(this, TempActivity.class));

        //******************************************For custom toast***********************************************
//        LayoutInflater inflater = getLayoutInflater();
//        View layout = inflater.inflate(R.layout.custom_toast,
//                (ViewGroup) findViewById(R.id.toast_layout_root));
//
//        ImageView image = (ImageView) layout.findViewById(R.id.image);
//        image.setImageResource(R.drawable.android);
//        TextView text = (TextView) layout.findViewById(R.id.text);
//        text.setText("Hello! This is a custom toast!");
//        Toast toast = new Toast(getApplicationContext());
//        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
//        toast.setDuration(Toast.LENGTH_LONG);
//        toast.setView(layout);
//        toast.show();

    }

    //This function checks for the null fields
    private int checkForNullFields() {
        Log.d(TAG, "checkForNullFields: NULL VALUE");
        Log.d(TAG, "checkForNullFields: " + pgName.getText().toString());

        if (pgName.getText().toString().matches("")) {
            Toast.makeText(RegisterPG.this, "Enter the PG Name!", Toast.LENGTH_SHORT).show();
            return 1;
        }

        if (ownerName.getText().toString().matches("")) {
            Toast.makeText(RegisterPG.this, "Enter the Owner's Name!", Toast.LENGTH_SHORT).show();
            return 1;
        }
        if (contactNo.getText().toString().matches("")) {
            Toast.makeText(RegisterPG.this, "Enter the Contact Number!", Toast.LENGTH_SHORT).show();
            return 1;
        }
        if (email.getText().toString().matches("")) {
            Toast.makeText(RegisterPG.this, "Enter the Email ID!", Toast.LENGTH_SHORT).show();
            return 1;
        }
        if (addressOne.getText().toString().matches("")) {
            Toast.makeText(RegisterPG.this, "Enter the Owner's Name!", Toast.LENGTH_SHORT).show();
            return 1;
        }
        // NOTE--> not adding constraints on address line two as it is optional
        if (city.getText().toString().matches("")) {
            Toast.makeText(RegisterPG.this, "Enter the Contact Number!", Toast.LENGTH_SHORT).show();
            return 1;
        }
        if (state.getText().toString().matches("")) {
            Toast.makeText(RegisterPG.this, "Enter the Email ID!", Toast.LENGTH_SHORT).show();
            return 1;
        }
        if (pinCode.getText().toString().matches("")) {
            Toast.makeText(RegisterPG.this, "Enter the Rent!", Toast.LENGTH_SHORT).show();
            return 1;
        }
        if (rent.getText().toString().matches("")) {
            Toast.makeText(RegisterPG.this, "Enter the Rent!", Toast.LENGTH_SHORT).show();
            return 1;
        }
        if (depositAmount.toString().matches("")) {
            Toast.makeText(RegisterPG.this, "Enter the Deposit Amount!", Toast.LENGTH_SHORT).show();
            return 1;
        }
        return 0;
    }
}
