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

public class EditPG extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    String pgKey;
    public static final int INITIAL_FLAG = 9001;
    public static final int FINAL_FLAG = 8001;
    int flag = INITIAL_FLAG;
    int count = 0;
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

        if (user != null) {
            Log.d(TAG, "onCreate: COUNT 1 " + count);

            Firebase.setAndroidContext(this);
            RegisterPG.firebaseRef = new Firebase("https://pgapp-c51ce.firebaseio.com/");

            RegisterPG.firebaseRef.child("PgDetails").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                    if (dataSnapshot != null && dataSnapshot.getValue() != null) {

                        if (dataSnapshot.child("userUID").getValue().equals(user.getUid())) {
                            count = 1;
                            Log.d(TAG, "onChildAdded: " + dataSnapshot.getKey());
                            pgKey = dataSnapshot.getKey();
                            flag = FINAL_FLAG;
                            dialog.dismiss();
                            Intent i = new Intent(getApplicationContext(), RegisterPGPageOne.class);
                            i.putExtra("flag", flag);
                            i.putExtra("key", pgKey);
                            i.putExtra("source", "editPG");
                            finish();
                            startActivity(i);
                            Log.d(TAG, "onChildAdded: COUNT 2 INSIDE LOOP " + count);
                        }
                        else    {
                            Log.d(TAG, "onChildAdded: COUNT 3 OUTSIDE 2 LOOP " + count);
                        }
                    }

//                    if(count == 0)  {
//                        dialog.dismiss();
//                        Log.d(TAG, "onChildAdded: COUNT " + count);
//                        Toast.makeText(EditPG.this, "No Pg Found!", Toast.LENGTH_SHORT).show();
//                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
//                    }
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

        Log.d(TAG, "onCreate: COUNT 4 LAST ONE " + count);

        if(user == null) {
            Toast.makeText(EditPG.this, "Please Sign In First!", Toast.LENGTH_SHORT).show();
        }
    }
}
