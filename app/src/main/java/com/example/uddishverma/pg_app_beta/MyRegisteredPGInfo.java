package com.example.uddishverma.pg_app_beta;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Thi activity is called when the user selects MY PG from the navigation drawer
 * It directs the user to the fragment and shows his/her PG's information
 */
public class MyRegisteredPGInfo extends AppCompatActivity {

    public static final String TAG = "MyRegisteredPGInfo";

    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    PgDetails_POJO.PgDetails details;
    ProgressDialog pd;
    int flag = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_registered_pginfo);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        if (user != null) {

            pd = new ProgressDialog(this);
            pd.setMessage("Please Wait...");
            pd.show();

            final String UID = user.getUid();
            Log.d(TAG, "onCreate: UID " + UID);

            Firebase.setAndroidContext(this);
            RegisterPG.firebaseRef = new Firebase("https://pgapp-c51ce.firebaseio.com/");

            RegisterPG.firebaseRef.child("PgDetails").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                    if (dataSnapshot != null && dataSnapshot.getValue() != null) {

                        Log.d(TAG, "onChildAdded: KEY VALUE : " + (dataSnapshot.child("userUID").getValue().equals(UID)));

                        if (dataSnapshot.child("userUID").getValue().equals(UID)) {
                            details = dataSnapshot.getValue(PgDetails_POJO.PgDetails.class);

                            flag = 0;

                            pd.dismiss();

                            Intent intent = new Intent(getApplicationContext(), FragmentCaller.class);

                            intent.putExtra("PG ID", details.getId());
                            intent.putExtra("PG Name", details.getPgName());
                            intent.putExtra("OWNER NAME", details.getOwnerName());
                            intent.putExtra("CONTACT NO", details.getContactNo());
                            intent.putExtra("EMAIL", details.getEmail());
                            intent.putExtra("INSTITUTE", details.getNearbyInstitute());
                            intent.putExtra("WIFI", details.getWifi());
                            intent.putExtra("AC", details.getAc());
                            intent.putExtra("REFRIGERATOR", details.getFridge());
                            intent.putExtra("PARKING", details.getParking());
                            intent.putExtra("TV", details.getTv());
                            intent.putExtra("LUNCH", details.getLunch());
                            intent.putExtra("DINNER", details.getDinner());
                            intent.putExtra("BREAKFAST", details.getBreakfast());
                            intent.putExtra("RO", details.getRoWater());
                            intent.putExtra("HOT WATER", details.getHotWater());
                            intent.putExtra("SECURITY", details.getSecurity());
                            intent.putExtra("RENT", details.getRent());
                            intent.putExtra("DEPOSIT", details.getDepositAmount());
                            intent.putExtra("ADDRESS", details.getAddressOne());
                            intent.putExtra("LOCALITY", details.getLocality());
                            intent.putExtra("CITY", details.getCity());
                            intent.putExtra("STATE", details.getState());
                            intent.putExtra("PINCODE", details.getPinCode());
                            intent.putExtra("EXTRAFEATURES", details.getExtraFeatures());
                            //Sending the intents for the Pg Images which are to be attached to the View Pager
                            intent.putExtra("IMAGE_ONE", details.getPgImageOne());
                            intent.putExtra("IMAGE_TWO", details.getPgImageTwo());
                            intent.putExtra("IMAGE_THREE", details.getPgImageThree());
                            intent.putExtra("IMAGE_FOUR", details.getPgImageFour());

                            finish();
                            startActivity(intent);
                        }
//                         else {
//                            Log.d(TAG, "onChildAdded: ELSE STATEMENT CALLED");
//                            pd.dismiss();
//                            Toast.makeText(MyRegisteredPGInfo.this, "No PG found Under Your Account!", Toast.LENGTH_SHORT).show();
//                            finish();
//                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
//                        }
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


        } else {
            Toast.makeText(MyRegisteredPGInfo.this, "PG not Found. Please SignIn!", Toast.LENGTH_SHORT).show();
            finish();
            startActivity(new Intent(getApplicationContext(), AuthorisationActivity.class));
        }
    }

//    public void noPg() {
//
//        if (flag == 1)  {
//            Log.d(TAG, "onCreate: FLAG = 1");
//            pd.dismiss();
//            Toast.makeText(MyRegisteredPGInfo.this, "No PG found Under Your Account!", Toast.LENGTH_SHORT).show();
//            finish();
//            startActivity(new Intent(getApplicationContext(), MainActivity.class));
//        }
//    }
}
