package com.example.uddishverma.pg_app_beta;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * This class allows the user to edit his/her registered PG
 * First we count the number of PGs in the MainActivity navDrawer using a static int(MainActivity.noOfChildren)
 * Then in the second firebase query we compare the values and if we reach at the end of the list we display "No Pg Found"
 */

public class EditPG extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    String pgKey;
    public static final int INITIAL_FLAG = 9001;
    public static final int FINAL_FLAG = 8001;
    int flag = INITIAL_FLAG;
    long count = 0;
    int countFind = 0;
    ProgressDialog dialog;

    public static final String TAG = "EditPG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pg);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        dialog = new ProgressDialog(this);
        dialog.setMessage("Please Wait...");
        dialog.show();

/**
 * If intent is coming from the MyAccount page
 * We need to search the PG by its unique key not UID
 */
        Intent intent = getIntent();
        Bundle b = intent.getExtras();

        if (b.getString("source").equals("MultiplePGEditApapter")) {
            final String pgId = b.getString("PG ID");
            Log.d(TAG, "onCreate: ID " + pgId);
            Log.d(TAG, "onClick: ID FROM INTENT " + b.getString("PG ID"));

            RegisterPG.firebaseRef.child("PgDetails").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                    if (dataSnapshot != null && dataSnapshot.getValue() != null) {

                        if (dataSnapshot.child("id").getValue().equals(pgId)) {
                            Log.d(TAG, "onChildAdded: " + dataSnapshot.getKey());
                            pgKey = dataSnapshot.getKey();
                            flag = FINAL_FLAG;
                            dialog.dismiss();
                            Intent i = new Intent(getApplicationContext(), RegisterPGPageOne.class);
                            i.putExtra("flag", flag);
                            i.putExtra("key", pgKey);
                            i.putExtra("PgId", pgId);
                            i.putExtra("source", "editPG");
                            finish();
                            startActivity(i);
                        }

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
//        else {
//
//            Firebase.setAndroidContext(this);
//            RegisterPG.firebaseRef = new Firebase("https://pgapp-c51ce.firebaseio.com/");
//
//            if (user != null) {
////        ******************************************************************************************************************
//
//
////      ****************************************Comparing The UID with PG's UID  ********************************************
//                RegisterPG.firebaseRef.child("PgDetails").addChildEventListener(new ChildEventListener() {
//                    @Override
//                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//
//                        if (dataSnapshot != null && dataSnapshot.getValue() != null) {
//
//                            if (dataSnapshot.child("userUID").getValue().equals(user.getUid())) {
//                                Log.d(TAG, "onChildAdded: " + dataSnapshot.getKey());
//                                pgKey = dataSnapshot.getKey();
//                                flag = FINAL_FLAG;
//                                dialog.dismiss();
//                                Intent i = new Intent(getApplicationContext(), RegisterPGPageOne.class);
//                                i.putExtra("flag", flag);
//                                i.putExtra("key", pgKey);
//                                i.putExtra("source", "editPG");
//                                finish();
//                                startActivity(i);
//                            } else {
//                                countFind++;
//                                Log.d(TAG, "onChildAdded: COUNTFIND " + countFind);
//
//                                //Checking if we have reached the end of the database and didn't find any PG
//                                if (countFind == MainActivity.noOfChildren) {
//                                    dialog.dismiss();
//                                    Log.d(TAG, "onChildAdded: COUNT " + count);
//                                    Toast.makeText(EditPG.this, "No Pg Found!", Toast.LENGTH_SHORT).show();
//                                    finish();
//                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
//                                }
//                            }
//
//                        }
//
//                    }
//
//                    @Override
//                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//                    }
//
//                    @Override
//                    public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//                    }
//
//                    @Override
//                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//                    }
//
//                    @Override
//                    public void onCancelled(FirebaseError firebaseError) {
//
//                    }
//                });
//            }
//
//            if (user == null) {
//                Toast.makeText(EditPG.this, "Please Sign In First!", Toast.LENGTH_SHORT).show();
//                finish();
//                startActivity(new Intent(getApplicationContext(), MainActivity.class));
//            }
//
//        }
    }

}
