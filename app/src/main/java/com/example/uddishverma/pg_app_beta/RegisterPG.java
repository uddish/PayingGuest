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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.utilities.Base64;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import android.os.Handler;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import cn.pedant.SweetAlert.SweetAlertDialog;

//************************************Class to Register PGs************************************************************


public class RegisterPG extends AppCompatActivity {

    static Firebase firebaseRef;
    static StorageReference storageRef;

    public static final String TAG = "RegisterPG";
    callUploadWhenBtnPressed cuwbp = new callUploadWhenBtnPressed();
    String image1, image2, image3, image4;

    SweetAlertDialog mdialog;

    String source;                  //Source tell us from which activity the intent is coming from

    //************************************To get the intents from the edit PG Activity*********************************************
    String key;
    int editCheck;
    String pgIdForEditing = null;
    //*****************************************************************************************************************************

    FirebaseAuth firebaseAuth;

    String userUID;

    private int PICK_IMAGE_REQUEST_ONE = 1;
    private int PICK_IMAGE_REQUEST_TWO = 2;
    private int PICK_IMAGE_REQUEST_THREE = 3;
    private int PICK_IMAGE_REQUEST_FOUR = 4;

    EditText pgName, ownerName, contactNo, email, rent, depositAmount, extraFeatures, addressOne,
            city, state, pinCode;
    CheckBox wifi, ac, breakfast, lunch, dinner, parking, roWater, security, tv, hotWater, refrigerator;
    Button submitButton;
    ImageView  imgUpload_1, imgUpload_2, imgUpload_3, imgUpload_4;
    Button imgUploadBtn_1, imgUploadBtn_2, imgUploadBtn_3, imgUploadBtn_4, cancelImage1, cancelImage2, cancelImage3, cancelImage4;
    String preference;
    String genderPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_pg);


        submitButton = (Button) findViewById(R.id.submit_btn);

        //attaching the edit texts
        pgName = (EditText) findViewById(R.id.pg_name_et);
        ownerName = (EditText) findViewById(R.id.owner_name_et);
        contactNo = (EditText) findViewById(R.id.contactNumber_et);
        email = (EditText) findViewById(R.id.email_et);
        addressOne = (EditText) findViewById(R.id.address_line_one_et);
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

        //Upload Buttons For Images
        imgUploadBtn_1 = (Button) findViewById(R.id.pg_img_btn_one);
        imgUploadBtn_2 = (Button) findViewById(R.id.pg_img_btn_two);
        imgUploadBtn_3 = (Button) findViewById(R.id.pg_img_btn_three);
        imgUploadBtn_4 = (Button) findViewById(R.id.pg_img_btn_four);

        //Attaching the delete buttons for the images
        cancelImage1 = (Button) findViewById(R.id.cancel_image_one);
        cancelImage2 = (Button) findViewById(R.id.cancel_image_two);
        cancelImage3 = (Button) findViewById(R.id.cancel_image_three);
        cancelImage4 = (Button) findViewById(R.id.cancel_image_four);

        cancelImage1.setClickable(false);
        cancelImage2.setClickable(false);
        cancelImage3.setClickable(false);
        cancelImage4.setClickable(false);


        /**
         *  Handling the click events of the four images being uploaded
         *  Firing the intent
         */
        imgUploadBtn_1.setOnClickListener(new View.OnClickListener() {
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
        imgUploadBtn_2.setOnClickListener(new View.OnClickListener() {
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
        imgUploadBtn_3.setOnClickListener(new View.OnClickListener() {
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
        imgUploadBtn_4.setOnClickListener(new View.OnClickListener() {
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


        //Creating a unique ID for each PG
        Long timeStamp = System.currentTimeMillis() / 1000;
        final String PgId = timeStamp.toString();

        Firebase.setAndroidContext(this);
        firebaseRef = new Firebase("https://pgapp-c51ce.firebaseio.com/");

        //Getting firebase authorisation
        firebaseAuth = FirebaseAuth.getInstance();

        //Getting the unique UID for each person who SignsIn in the app and sending it to firebase database
        final FirebaseUser user = firebaseAuth.getCurrentUser();
        if (firebaseAuth.getCurrentUser() != null) {
            userUID = user.getUid();
        }


//*************************************************************************************************************
        //Receiving the key and flag from the Edit PG Activity so that it can be checked and edit here
        Intent i = getIntent();

        Bundle b = i.getExtras();
        //Intent is coming from RegisterPGPageOne Activity
        if (b != null) {

//          Checking if the EditPG Activity has been clicked
            if (RegisterPGPageOne.editCalledFlag == 2990) {

                Log.d(TAG, "onCreate: INTENT FROM UPDATE ACTIVITY");
                pgIdForEditing = b.getString("PgId");
                Log.d(TAG, "onCreate: PG ID " + pgIdForEditing);
                key = b.getString("key");
                editCheck = b.getInt("flag");

                //Setting the previous PG images in the Register Layout(like shared preferences for the user to edit it
                firebaseRef.child("PgDetails").addChildEventListener(new ChildEventListener() {

                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//
                        if (dataSnapshot.child("id").getValue().equals(pgIdForEditing)) {
                            final PgDetails_POJO.PgDetails pgDetails;
                            pgDetails = dataSnapshot.getValue(PgDetails_POJO.PgDetails.class);

                            Picasso.with(getApplicationContext()).load(pgDetails.getPgImageOne()).fit().into(imgUpload_1);
                            Picasso.with(getApplicationContext()).load(pgDetails.getPgImageTwo()).fit().into(imgUpload_2);
                            Picasso.with(getApplicationContext()).load(pgDetails.getPgImageThree()).fit().into(imgUpload_3);
                            Picasso.with(getApplicationContext()).load(pgDetails.getPgImageFour()).fit().into(imgUpload_4);


                            //Deleting the previous images from the firebase reference
                            final FirebaseStorage storage = FirebaseStorage.getInstance();


                            /**
                             * Adding the click events of the cancelImage button
                             * After clicking on this button, the images are deleted from the firebase reference
                             */

                            cancelImage1.setVisibility(View.VISIBLE);
                            cancelImage2.setVisibility(View.VISIBLE);
                            cancelImage3.setVisibility(View.VISIBLE);
                            cancelImage4.setVisibility(View.VISIBLE);

                            cancelImage1.setClickable(true);
                            cancelImage2.setClickable(true);
                            cancelImage3.setClickable(true);
                            cancelImage4.setClickable(true);

                            imgUploadBtn_1.setClickable(false);
                            imgUploadBtn_2.setClickable(false);
                            imgUploadBtn_3.setClickable(false);
                            imgUploadBtn_4.setClickable(false);
                            imgUploadBtn_1.setVisibility(View.INVISIBLE);
                            imgUploadBtn_2.setVisibility(View.INVISIBLE);
                            imgUploadBtn_3.setVisibility(View.INVISIBLE);
                            imgUploadBtn_4.setVisibility(View.INVISIBLE);

                            cancelImage1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    storageRef = storage.getReferenceFromUrl(pgDetails.getPgImageOne());
                                    final StorageReference imageone = storageRef;

                                    Log.d(TAG, "onChildAdded: IMAGE ONE URI " + storageRef);

                                    imageone.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d(TAG, "onSuccess: IMAGE DELETED SUCCESSFULLY");

                                            //After deleting, starting the gallery intent
                                            Intent intent = new Intent();
                                            intent.setType("image/*");
                                            //If you want the user to choose something based on MIME type, use ACTION_GET_CONTENT.
                                            intent.setAction(Intent.ACTION_GET_CONTENT);
                                            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST_ONE);

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.d(TAG, "onFailure: IMAGE DELETE FAILED");
                                            Toast.makeText(RegisterPG.this, "Image Can Not Be Deleted!", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            });

                            //Cancel Button 2
                            cancelImage2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    storageRef = storage.getReferenceFromUrl(pgDetails.getPgImageTwo());
                                    StorageReference imagetwo = storageRef;

                                    Log.d(TAG, "onChildAdded: IMAGE TWO URI " + storageRef);

                                    imagetwo.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d(TAG, "onSuccess: IMAGE DELETED SUCCESSFULLY");

                                            //After deleting, starting the gallery intent
                                            Intent i = new Intent();
                                            i.setType("image/*");
                                            //If you want the user to choose something based on MIME type, use ACTION_GET_CONTENT.
                                            i.setAction(Intent.ACTION_GET_CONTENT);
                                            startActivityForResult(Intent.createChooser(i, "Select Picture"), PICK_IMAGE_REQUEST_TWO);

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.d(TAG, "onFailure: IMAGE DELETE FAILED");
                                            Toast.makeText(RegisterPG.this, "Image Can Not Be Deleted!", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            });

                            //Cancel Button 2
                            cancelImage3.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    storageRef = storage.getReferenceFromUrl(pgDetails.getPgImageThree());
                                    StorageReference imagethree = storageRef;

                                    Log.d(TAG, "onChildAdded: IMAGE THREE URI " + storageRef);

                                    imagethree.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d(TAG, "onSuccess: IMAGE DELETED SUCCESSFULLY");

                                            Intent intent = new Intent();
                                            intent.setType("image/*");
                                            //If you want the user to choose something based on MIME type, use ACTION_GET_CONTENT.
                                            intent.setAction(Intent.ACTION_GET_CONTENT);
                                            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST_THREE);

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.d(TAG, "onFailure: IMAGE DELETE FAILED");
                                            Toast.makeText(RegisterPG.this, "Image Can Not Be Deleted!", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            });

                            //Cancel Button 2
                            cancelImage4.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    storageRef = storage.getReferenceFromUrl(pgDetails.getPgImageFour());
                                    StorageReference imagefour = storageRef;

                                    Log.d(TAG, "onChildAdded: IMAGE FOUR URI " + storageRef);

                                    imagefour.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d(TAG, "onSuccess: IMAGE DELETED SUCCESSFULLY");

                                            Intent intent = new Intent();
                                            intent.setType("image/*");
                                            //If you want the user to choose something based on MIME type, use ACTION_GET_CONTENT.
                                            intent.setAction(Intent.ACTION_GET_CONTENT);
                                            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST_FOUR);

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.d(TAG, "onFailure: IMAGE DELETE FAILED");
                                            Toast.makeText(RegisterPG.this, "Image Can Not Be Deleted!", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            });

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
        }
//**************************************************************************************************************

        //Checking if the intent is from registerPageOne Activity
        Intent registerIntent = getIntent();
        final Bundle bun = registerIntent.getExtras();
        if (bun.getString("source").equals("registerPageOne")) {
            Log.d(TAG, "onClick: NAME FROM INTENT " + bun.getString("pgName"));

        }

        submitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //Checking if the images are null before pushing them into firebase

                if (cuwbp.downloadUrl1 != null && cuwbp.downloadUrl2 != null &&
                        cuwbp.downloadUrl3 != null && cuwbp.downloadUrl4 != null) {


                    image1 = (cuwbp.downloadUrl1).toString();
                    image2 = (cuwbp.downloadUrl2).toString();
                    image3 = (cuwbp.downloadUrl3).toString();
                    image4 = (cuwbp.downloadUrl4).toString();


//******************************************* SENDING THE DETAILS TO THE FIREBASE DATABASE****************************************

                    PgDetails_POJO.PgDetails pgDetails = new PgDetails_POJO.PgDetails(PgId, bun.getString("pgName"), bun.getString("ownerName"),
                            Double.parseDouble(bun.getString("contactNo")), bun.getString("email"),
                            Double.parseDouble(bun.getString("rent")), Double.parseDouble(bun.getString("depositAmount")), bun.getString("extraFeatures"),
                            bun.getBoolean("wifi"), bun.getBoolean("breakfast"), bun.getBoolean("parking"), bun.getBoolean("ac"), bun.getBoolean("lunching"), bun.getBoolean("dinner"),
                            bun.getBoolean("roWater"), bun.getBoolean("security"), bun.getBoolean("tv"), bun.getBoolean("hotWater"), bun.getBoolean("refrigerator"),
                            bun.getString("addressOne"), bun.getString("locality"),
                            bun.getString("city"), bun.getString("state"), Double.parseDouble(bun.getString("pinCode")), bun.getString("preference"), bun.getString("genderPreference"),
                            image1, image2, image3, image4, userUID, bun.getString("nearbyInstitute"));


                    //UPDATING THE PG
                    if (editCheck == EditPG.FINAL_FLAG) {
                        Log.d(TAG, "onClick: INSIDE UPDATE PG LOG");
                        firebaseRef.child("PgDetails").child(key).setValue(pgDetails);
                        Toast.makeText(RegisterPG.this, "Details Updated!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterPG.this, MainActivity.class));
                        finish();
                    }


                    //ADDING A NEW PG
                    else {
                        Log.d(TAG, "onClick: INSIDE REGISTER PG LOG");
                        firebaseRef.child("PgDetails").push().setValue(pgDetails);
                        Toast.makeText(RegisterPG.this, "Details Submitted!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterPG.this, MainActivity.class));
                        finish();
                    }

                } else {

                    /**
                     * This function is called when the images are null
                     * We show a progress Dialog for 8 seconds and then a dialog box is shown
                     * when selected, we again check if the images are null or not
                     */
                    mdialog = new SweetAlertDialog(RegisterPG.this, SweetAlertDialog.PROGRESS_TYPE);
                    mdialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                    mdialog.setTitleText("Please Wait");
                    mdialog.setCancelable(false);
                    mdialog.show();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // This method will be executed once the animation of the shine button is over
                            // Start your app main activity
                            mdialog.dismiss();
                            new SweetAlertDialog(RegisterPG.this, SweetAlertDialog.SUCCESS_TYPE)
                                    .setTitleText("COFIRM")
                                    .setContentText("Are You Sure You Want To Submit These Details?")
                                    .setCancelText("CANCEL")
                                    .setConfirmText("SUBMIT")
                                    .showCancelButton(true)
                                    .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sDialog) {
                                            sDialog.cancel();
                                        }
                                    })
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            sweetAlertDialog.dismiss();
                                            if (cuwbp.downloadUrl1 != null && cuwbp.downloadUrl2 != null &&
                                                    cuwbp.downloadUrl3 != null && cuwbp.downloadUrl4 != null) {


                                                image1 = (cuwbp.downloadUrl1).toString();
                                                image2 = (cuwbp.downloadUrl2).toString();
                                                image3 = (cuwbp.downloadUrl3).toString();
                                                image4 = (cuwbp.downloadUrl4).toString();


//******************************************* SENDING THE DETAILS TO THE FIREBASE DATABASE****************************************

                                                PgDetails_POJO.PgDetails pgDetails = new PgDetails_POJO.PgDetails(PgId, bun.getString("pgName"), bun.getString("ownerName"),
                                                        Double.parseDouble(bun.getString("contactNo")), bun.getString("email"),
                                                        Double.parseDouble(bun.getString("rent")), Double.parseDouble(bun.getString("depositAmount")), bun.getString("extraFeatures"),
                                                        bun.getBoolean("wifi"), bun.getBoolean("breakfast"), bun.getBoolean("parking"), bun.getBoolean("ac"), bun.getBoolean("lunching"), bun.getBoolean("dinner"),
                                                        bun.getBoolean("roWater"), bun.getBoolean("security"), bun.getBoolean("tv"), bun.getBoolean("hotWater"), bun.getBoolean("refrigerator"),
                                                        bun.getString("addressOne"), bun.getString("locality"),
                                                        bun.getString("city"), bun.getString("state"), Double.parseDouble(bun.getString("pinCode")), bun.getString("preference"), bun.getString("genderPreference"),
                                                        image1, image2, image3, image4, userUID, bun.getString("nearbyInstitute"));


                                                //UPDATING THE PG
                                                if (editCheck == EditPG.FINAL_FLAG) {
                                                    Log.d(TAG, "onClick: INSIDE UPDATE PG LOG");
                                                    firebaseRef.child("PgDetails").child(key).setValue(pgDetails);
                                                    Toast.makeText(RegisterPG.this, "Details Updated!", Toast.LENGTH_SHORT).show();
                                                    startActivity(new Intent(RegisterPG.this, MainActivity.class));
                                                    finish();
                                                }


                                                //ADDING A NEW PG
                                                else {
                                                    Log.d(TAG, "onClick: INSIDE REGISTER PG LOG");
                                                    firebaseRef.child("PgDetails").push().setValue(pgDetails);
                                                    Toast.makeText(RegisterPG.this, "Details Submitted!", Toast.LENGTH_SHORT).show();
                                                    startActivity(new Intent(RegisterPG.this, MainActivity.class));
                                                    finish();
                                                }
                                            }
                                            else    {
                                                Toast.makeText(RegisterPG.this, "Please Press the Submit Button Again!", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    })
                                    .show();
                        }
                    }, 7000);

                }
            }
        });
    }

    //Uploading the image to FireBase Storage
    public void uploadImage(Uri downloadUrl, final int imageNumber) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageRef = storage.getReferenceFromUrl("gs://pgapp-c51ce.appspot.com");

        StorageReference imagesRef = storageRef.child("PgImages").child(UUID.randomUUID().toString());

        ContentResolver cr = getBaseContext().getContentResolver();
        try {
            InputStream inputStream = cr.openInputStream(downloadUrl);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
            byte[] data = baos.toByteArray();
            Log.d(TAG, "uploadImage: image size " + data.length);
            UploadTask uploadTask = imagesRef.putBytes(data);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(RegisterPG.this, "ERROR IN UPLOADING!", Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    Log.d(TAG, "onSuccess: Download Url : " + downloadUrl);
                    if (imageNumber == 1)
                        cuwbp.downloadUrl1 = downloadUrl;
                    if (imageNumber == 2)
                        cuwbp.downloadUrl2 = downloadUrl;
                    if (imageNumber == 3)
                        cuwbp.downloadUrl3 = downloadUrl;
                    if (imageNumber == 4)
                        cuwbp.downloadUrl4 = downloadUrl;
                }
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * This class contains the Uri and the download Url  of the image
     * which is fetched in the  onClick method of the button
     * which then saves the data in the POJO class object and saves it in the database.
     *
     * @return uri
     */

    public class callUploadWhenBtnPressed {

        Uri url1, url2, url3, url4;
        Uri downloadUrl1, downloadUrl2, downloadUrl3, downloadUrl4;

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
            Picasso.with(this).load(uri).fit().into(imgUpload_1);
            cuwbp.url1 = uri;
            uploadImage(uri, 1);
        }
        if (requestCode == PICK_IMAGE_REQUEST_TWO && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            Picasso.with(this).load(uri).fit().into(imgUpload_2);
            cuwbp.url2 = uri;
            uploadImage(uri, 2);
        }
        if (requestCode == PICK_IMAGE_REQUEST_THREE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            Picasso.with(this).load(uri).fit().into(imgUpload_3);
            cuwbp.url3 = uri;
            uploadImage(uri, 3);
        }
        if (requestCode == PICK_IMAGE_REQUEST_FOUR && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            Picasso.with(this).load(uri).fit().into(imgUpload_4);
            cuwbp.url4 = uri;
            uploadImage(uri, 4);
        }
    }



    }


