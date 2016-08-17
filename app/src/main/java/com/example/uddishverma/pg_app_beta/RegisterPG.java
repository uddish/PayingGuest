package com.example.uddishverma.pg_app_beta;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.firebase.client.Firebase;
import com.firebase.client.utilities.Base64;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.sackcentury.shinebuttonlib.ShineButton;
import com.squareup.picasso.Picasso;

import android.os.Handler;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

//************************************Class to Register PGs************************************************************

public class RegisterPG extends AppCompatActivity {

    static Firebase firebaseRef;
    StorageReference storageRef;

    public static final String TAG = "RegisterPG";

    private int PICK_IMAGE_REQUEST_ONE = 1;
    private int PICK_IMAGE_REQUEST_TWO = 2;
    private int PICK_IMAGE_REQUEST_THREE = 3;
    private int PICK_IMAGE_REQUEST_FOUR = 4;

    EditText pgName, ownerName, contactNo, email, rent, depositAmount, extraFeatures, addressOne, addressTwo,
            city, state, pinCode;
    CheckBox wifi, ac, breakfast, lunch, dinner, parking, roWater, security, tv, hotWater, refrigerator;
    RadioButton individual, sharing, male, female;
    ShineButton shineButton;
    ImageView imgUpload_1, imgUpload_2, imgUpload_3, imgUpload_4;
    String preference;
    String genderPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_pg);

        shineButton = (ShineButton) findViewById(R.id.shine_button);
        shineButton.init(this);

        //Getting fireBase storage instance to upload the images.

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

        //Attaching the uploaded images
        imgUpload_1 = (ImageView) findViewById(R.id.pg_img_one);
        imgUpload_2 = (ImageView) findViewById(R.id.pg_img_two);
        imgUpload_3 = (ImageView) findViewById(R.id.pg_img_three);
        imgUpload_4 = (ImageView) findViewById(R.id.pg_img_four);


        /**
         *  Handling the click events of the four images being uploaded
         *  Firing the intent
         */
        imgUpload_1.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                //If you want the user to choose something based on MIME type, use ACTION_GET_CONTENT.
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST_ONE);
            }
        });
        imgUpload_2.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setType("image/*");
                //If you want the user to choose something based on MIME type, use ACTION_GET_CONTENT.
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(i, "Select Picture"), PICK_IMAGE_REQUEST_TWO);
            }
        });
        imgUpload_3.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                //If you want the user to choose something based on MIME type, use ACTION_GET_CONTENT.
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST_THREE);
            }
        });
        imgUpload_4.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                //If you want the user to choose something based on MIME type, use ACTION_GET_CONTENT.
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST_FOUR);
            }
        });


        //disabling keyboard when the register activity opens
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        Long timeStamp = System.currentTimeMillis() / 1000;
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
                            roWater.isChecked(), security.isChecked(), tv.isChecked(), hotWater.isChecked(), refrigerator.isChecked(),
                            addressOne.getText().toString(), addressTwo.getText().toString(),
                            city.getText().toString(), state.getText().toString(), Double.parseDouble(pinCode.getText().toString()), preference, genderPreference);

                    firebaseRef.child("PgDetails").push().setValue(pgDetails);
                    Toast.makeText(RegisterPG.this, "DETAILS SUBMITTED", Toast.LENGTH_SHORT).show();
                    registerComplete();
                    shineBtnClickListener();
                }
            }
        });

    }
    //Uploading the image to FireBase Storage
    public void uploadImage(Uri downloadUrl)    {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageRef = storage.getReferenceFromUrl("gs://pgapp-c51ce.appspot.com");
        StorageReference imagesRef = storageRef.child("images");

//        StorageMetadata metadata = new StorageMetadata.Builder()
//                .setContentType("image/jpg")
//                .build();

//        Getting the data from ImageView as bytes
//        imgUpload_1.setDrawingCacheEnabled(true);
//        imgUpload_1.buildDrawingCache();
//        Bitmap bitmap = imgUpload_1.getDrawingCache();
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//        byte[] data = baos.toByteArray();
//        UploadTask uploadTask = imagesRef.putBytes(data);


        ContentResolver cr = getBaseContext().getContentResolver();
        try {
            InputStream inputStream = cr.openInputStream(downloadUrl);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data = baos.toByteArray();
            UploadTask uploadTask = imagesRef.putBytes(data);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


//        UUID.randomUUID().toString();
//        UploadTask uploadTask = imagesRef.child("images/").putFile(downloadUrl, metadata);
//        uploadTask.addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(RegisterPG.this, "Image Upload Failed", Toast.LENGTH_SHORT).show();
//            }
//        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                Uri downloadUrl = taskSnapshot.getDownloadUrl();
//                Log.d(TAG, "onSuccess: " + downloadUrl);
//            }
//        });
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


    /**
     * Receiving the intent sent when the two images are clicked
     * Attaching the Bitmap with the ImageViews
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST_ONE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            Picasso.with(this).load(uri).resize(600, 600).centerCrop().into(imgUpload_1);
            uploadImage(uri);
        }
        if (requestCode == PICK_IMAGE_REQUEST_TWO && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            Picasso.with(this).load(uri).resize(600, 600).centerCrop().into(imgUpload_2);
        }
        if (requestCode == PICK_IMAGE_REQUEST_THREE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            Picasso.with(this).load(uri).resize(600, 600).centerCrop().into(imgUpload_3);
        }
        if (requestCode == PICK_IMAGE_REQUEST_FOUR && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            Picasso.with(this).load(uri).resize(600, 600).centerCrop().into(imgUpload_4);
        }
    }

    private void shineBtnClickListener() {
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

    /**
     * Checks for the null fields in the RegisterPg Activity
     */
    private int checkForNullFields() {

        //TODO CHECK IF IMAGES ARE NULL OR NOT

//        if(imgUpload_1.getDrawable() != null)   {
//            Toast.makeText(RegisterPG.this, "Please Upload the Image!", Toast.LENGTH_SHORT).show();
//            return 1;
//        }

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
