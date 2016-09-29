package com.example.uddishverma.pg_app_beta;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class RegisterPGPageOne extends AppCompatActivity {

    public static final String TAG = "RegisterPGPageOne";

    EditText pgName, ownerName, contactNo, email, rent, depositAmount, extraFeatures, addressOne, locality,
            city, state, pinCode, nearbyInstitute;
    CheckBox wifi, ac, breakfast, lunch, dinner, parking, roWater, security, tv, hotWater, refrigerator;
    String preference;
    String genderPreference;
    ImageView nextBtn;

    String isEdit = " ";
    public static int editCalledFlag = 120;

    //************************************To get the intents from the edit PG Activity*********************************************
    String key;
    int editCheck;
    String pgId;
    //*****************************************************************************************************************************


    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_pgpage_one);

        //attaching the edit texts
        pgName = (EditText) findViewById(R.id.pg_name_et);
        ownerName = (EditText) findViewById(R.id.owner_name_et);
        contactNo = (EditText) findViewById(R.id.contactNumber_et);
        email = (EditText) findViewById(R.id.email_et);
        addressOne = (EditText) findViewById(R.id.address_line_one_et);
        locality = (EditText) findViewById(R.id.locality);
        city = (EditText) findViewById(R.id.city_et);
        state = (EditText) findViewById(R.id.state_et);
        pinCode = (EditText) findViewById(R.id.pincode_et);
        rent = (EditText) findViewById(R.id.rent_et);
        depositAmount = (EditText) findViewById(R.id.deposit_et);
        extraFeatures = (EditText) findViewById(R.id.extra_et);
        nearbyInstitute = (EditText) findViewById(R.id.nearby_inst);

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

        //Attaching the next arrow button which will take us to the image upload activity
        nextBtn = (ImageView) findViewById(R.id.next_button);

        //disabling keyboard when the register activity opens
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        nextBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                int check = checkForNullFields();

                if (check == 0) {

                    Intent intent = new Intent(getApplicationContext(), RegisterPG.class);
                    intent.putExtra("source", "registerPageOne");
                    intent.putExtra("PgId", pgId);
                    intent.putExtra("pgName", pgName.getText().toString());
                    intent.putExtra("ownerName", ownerName.getText().toString());
                    intent.putExtra("contactNo", contactNo.getText().toString());
                    intent.putExtra("email", email.getText().toString());
                    intent.putExtra("addressOne", addressOne.getText().toString());
                    intent.putExtra("locality", locality.getText().toString());
                    intent.putExtra("city", city.getText().toString());
                    intent.putExtra("state", state.getText().toString());
                    intent.putExtra("pinCode", pinCode.getText().toString());
                    intent.putExtra("rent", rent.getText().toString());
                    intent.putExtra("depositAmount", depositAmount.getText().toString());
                    intent.putExtra("extraFeatures", extraFeatures.getText().toString());
                    intent.putExtra("nearbyInstitute", nearbyInstitute.getText().toString());
                    intent.putExtra("preference", preference);
                    intent.putExtra("genderPreference", genderPreference);

                    intent.putExtra("wifi", wifi.isChecked());
                    intent.putExtra("ac", ac.isChecked());
                    intent.putExtra("breakfast", breakfast.isChecked());
                    intent.putExtra("lunch", lunch.isChecked());
                    intent.putExtra("dinner", dinner.isChecked());
                    intent.putExtra("parking", parking.isChecked());
                    intent.putExtra("roWater", roWater.isChecked());
                    intent.putExtra("security", security.isChecked());
                    intent.putExtra("tv", tv.isChecked());
                    intent.putExtra("hotWater", hotWater.isChecked());
                    intent.putExtra("refrigerator", refrigerator.isChecked());

                    if (isEdit.equals("comingFromEditActivity")) {
                        intent.putExtra("editCheckFlag", editCalledFlag);
                        Log.d(TAG, "onClick: EDIT SOURCE " + editCalledFlag);
                    }

                    startActivity(intent);

                }
            }
        });

        //**********************************************UPDATING THE PG***********************************************************

        Firebase.setAndroidContext(this);
        RegisterPG.firebaseRef = new Firebase("https://pgapp-c51ce.firebaseio.com/");

        //Getting firebase authorisation
        firebaseAuth = FirebaseAuth.getInstance();

        final FirebaseUser user = firebaseAuth.getCurrentUser();

        Intent i = getIntent();
        Bundle b = i.getExtras();

        if (b != null) {

            if (b.getString("source").equals("editPG")) {
                isEdit = "comingFromEditActivity";
                editCalledFlag = 2990;
                Log.d(TAG, "onCreate: INTENT FROM UPDATE ACTIVITY");
            }

            pgId = b.getString("PgId");
            key = b.getString("key");
            editCheck = b.getInt("flag");


            //Setting the previous PG images in the Register Layout(like shared preferences for the user to edit it
            RegisterPG.firebaseRef.child("PgDetails").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                    if (dataSnapshot.child("userUID").getValue().equals(user.getUid())) {
                    if (dataSnapshot.child("id").getValue().equals(pgId)) {
                        final PgDetails_POJO.PgDetails pgDetails;
                        pgDetails = dataSnapshot.getValue(PgDetails_POJO.PgDetails.class);


                        pgName.setText(pgDetails.getPgName());
                        ownerName.setText(pgDetails.getOwnerName());
                        contactNo.setText(String.valueOf((int) pgDetails.getContactNo()));
                        email.setText(pgDetails.getEmail());
                        addressOne.setText(pgDetails.getAddressOne());
                        locality.setText(pgDetails.getLocality());
                        city.setText(pgDetails.getCity());
                        state.setText(pgDetails.getState());
                        pinCode.setText(String.valueOf((int) pgDetails.getPinCode()));
                        rent.setText(String.valueOf((int) pgDetails.getRent()));
                        depositAmount.setText(String.valueOf((int) pgDetails.getDepositAmount()));
                        nearbyInstitute.setText(pgDetails.getNearbyInstitute());
                        extraFeatures.setText(pgDetails.getExtraFeatures());

                        wifi.setChecked(pgDetails.getWifi());
                        ac.setChecked(pgDetails.getAc());
                        breakfast.setChecked(pgDetails.getBreakfast());
                        lunch.setChecked(pgDetails.getLunch());
                        dinner.setChecked(pgDetails.getDinner());
                        parking.setChecked(pgDetails.getParking());
                        roWater.setChecked(pgDetails.getRoWater());
                        security.setChecked(pgDetails.getSecurity());
                        tv.setChecked(pgDetails.getTv());
                        hotWater.setChecked(pgDetails.getHotWater());
                        refrigerator.setChecked(pgDetails.getFridge());


                    }

                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
        }

        //************************************************************************************************************************
    }


    /**
     * Onclick method for the radio buttons
     * preference string contains which radioButton is selected
     *
     * @param view
     */
    public void preferenceRadioButton(View view) {

        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.radBtn_Individual:
                if (checked) {
                    preference = "Preference_Individual";
                    break;
                }
            case R.id.radBtn_Sharing:
                if (checked) {
                    preference = "Preference_Sharing";
                    break;
                }
            case R.id.rad_both_pref:
                if (checked) {
                    preference = "Preference_Both";
                    break;
                }
        }
        Log.d(TAG, "preferenceRadioButton: " + preference);
    }

    public void genderPreferenceRadioButton(View view) {

        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.radBtn_male:
                if (checked) {
                    genderPreference = "Preference_Male";
                    break;
                }
            case R.id.radBtn_female:
                if (checked) {
                    genderPreference = "Preference_Female";
                    break;
                }
            case R.id.rad_both_gender:
                if (checked) {
                    genderPreference = "Preference_BothGender";
                    break;
                }
        }
        Log.d(TAG, "preferenceRadioButton: " + genderPreference);
    }


    private int checkForNullFields() {

        if (pgName.getText().toString().matches("")) {
            Toast.makeText(RegisterPGPageOne.this, "Enter the PG Name!", Toast.LENGTH_SHORT).show();
            return 1;
        }

        if (ownerName.getText().toString().matches("")) {
            Toast.makeText(RegisterPGPageOne.this, "Enter the Owner's Name!", Toast.LENGTH_SHORT).show();
            return 1;
        }
        if (contactNo.getText().toString().matches("")) {
            Toast.makeText(RegisterPGPageOne.this, "Enter the Contact Number!", Toast.LENGTH_SHORT).show();
            return 1;
        }
        if (email.getText().toString().matches("")) {
            Toast.makeText(RegisterPGPageOne.this, "Enter the Email ID!", Toast.LENGTH_SHORT).show();
            return 1;
        }
        if (addressOne.getText().toString().matches("")) {
            Toast.makeText(RegisterPGPageOne.this, "Enter the Owner's Name!", Toast.LENGTH_SHORT).show();
            return 1;
        }
        if (locality.getText().toString().matches("")) {
            Toast.makeText(RegisterPGPageOne.this, "Enter the Locality!", Toast.LENGTH_SHORT).show();
            return 1;
        }

        // NOTE--> not adding constraints on address line two as it is optional
        if (city.getText().toString().matches("")) {
            Toast.makeText(RegisterPGPageOne.this, "Enter the Contact Number!", Toast.LENGTH_SHORT).show();
            return 1;
        }
        if (state.getText().toString().matches("")) {
            Toast.makeText(RegisterPGPageOne.this, "Enter the Email ID!", Toast.LENGTH_SHORT).show();
            return 1;
        }
        if (pinCode.getText().toString().matches("")) {
            Toast.makeText(RegisterPGPageOne.this, "Enter the PinCode!", Toast.LENGTH_SHORT).show();
            return 1;
        }
        if (nearbyInstitute.toString().matches("")) {
            Toast.makeText(RegisterPGPageOne.this, "Enter the nearby Institution!", Toast.LENGTH_SHORT).show();
            return 1;
        }
        if (rent.getText().toString().matches("")) {
            Toast.makeText(RegisterPGPageOne.this, "Enter the Rent!", Toast.LENGTH_SHORT).show();
            return 1;
        }
        if (depositAmount.toString().matches("")) {
            Toast.makeText(RegisterPGPageOne.this, "Enter the Deposit Amount!", Toast.LENGTH_SHORT).show();
            return 1;
        }
        return 0;
    }

    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        super.onBackPressed();
    }
}
