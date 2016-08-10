package com.example.uddishverma.pg_app_beta;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;

//************************************Class to Register PGs************************************************************
public class RegisterPG extends AppCompatActivity {

    EditText pgName, pgLocation, ownerName, contactNo, email, rent, depositAmount, extraFeatures;
    CheckBox wifi, ac, breakfast, lunch, dinner, parking, roWater, security, tv, hotWater, refrigerator;
    RadioButton individual, sharing, male, female;
    Button submitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_pg);

        submitBtn = (Button) findViewById(R.id.submit_btn);
        //attaching the edit texts
        pgName = (EditText) findViewById(R.id.pg_name_et);
        pgLocation = (EditText) findViewById(R.id.location_et);
        ownerName = (EditText) findViewById(R.id.owner_name_et);
        contactNo = (EditText) findViewById(R.id.contactNumber_et);
        email = (EditText) findViewById(R.id.email_et);
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


        Firebase.setAndroidContext(this);
        final Firebase ref = new Firebase("https://pgapp-c51ce.firebaseio.com/");

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PgDetails_POJO.PgDetails pgDetails = new PgDetails_POJO.PgDetails(pgName.getText().toString(), pgLocation.getText().toString(),
                        ownerName.getText().toString(), Double.parseDouble(contactNo.getText().toString()), email.getText().toString(),
                        Double.parseDouble(rent.getText().toString()), Double.parseDouble(depositAmount.getText().toString()), extraFeatures.getText().toString(),
                        wifi.isChecked(), breakfast.isChecked(), parking.isChecked());

                ref.push().setValue(pgDetails);
//                Toast.makeText(RegisterPG.this, "DETAILS SUBMITTED", Toast.LENGTH_SHORT).show();
                registerComplete();
            }
        });

    }

    private void registerComplete() {
        pgName.setText("");
        pgLocation.setText("");
        ownerName.setText("");
        contactNo.setText("");
        email.setText("");
        rent.setText("");
        depositAmount.setText("");
        extraFeatures.setText("");
        startActivity(new Intent(this, MainActivity.class));


        //******************************************For custom toast***********************************************
//        LayoutInflater inflater = getLayoutInflater();
//        View layout = inflater.inflate(R.layout.custom_toast,
//                (ViewGroup) findViewById(R.id.toast_layout_root));
//
//        ImageView image = (ImageView) layout.findViewById(R.id.image);
////        image.setImageResource(R.drawable.android);
//        TextView text = (TextView) layout.findViewById(R.id.text);
//        text.setText("Hello! This is a custom toast!");
//
//        Toast toast = new Toast(getApplicationContext());
//        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
//        toast.setDuration(Toast.LENGTH_LONG);
//        toast.setView(layout);
//        toast.show();

    }

//    private void checkForNullFields()   {
//        if(pgName.getText() == null || pgLocation.getText() == null || ownerName.getText() == null
//                || contactNo.getText() == null )
//    }
}
