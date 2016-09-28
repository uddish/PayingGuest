package com.example.uddishverma.pg_app_beta;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

//TODO complete the click events of addPg and deletePg

/**
 * This activity gets called when the user clicks on the My Account section in the navigation drawer
 */
public class MyAccountPage extends AppCompatActivity {

    public static final String TAG = "MyAccountPage";

    TextView name, email, noOfPg;
    Button addPG, editPG, signOut, deleteAccount;
    int count = 0;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account_page);

        name = (TextView) findViewById(R.id.myacc_name);
        email = (TextView) findViewById(R.id.myacc_id);
        noOfPg = (TextView) findViewById(R.id.myacc_no);

        addPG = (Button) findViewById(R.id.myacc_addpg);
        editPG = (Button) findViewById(R.id.myacc_editpg);
        signOut = (Button) findViewById(R.id.myacc_signout);
        deleteAccount = (Button) findViewById(R.id.myacc_deleteacc);

        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = firebaseAuth.getCurrentUser();

        name.setText(user.getDisplayName().toString());
        email.setText(user.getEmail());
        Log.d(TAG, "onCreate: " + user.getPhotoUrl());

        addPG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(getApplicationContext(), RegisterPGPageOne.class));
            }
        });

        editPG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(getApplicationContext(), MultiplePGEdit.class));
            }
        });

        //Setting the number of PGs

//        if (user != null) {
//
//            Firebase.setAndroidContext(this);
//            RegisterPG.firebaseRef = new Firebase("https://pgapp-c51ce.firebaseio.com/");
//
//            RegisterPG.firebaseRef.child("PgDetails").addChildEventListener(new ChildEventListener() {
//                @Override
//                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//
//                    if (dataSnapshot != null && dataSnapshot.getValue() != null) {
//
//                        if (dataSnapshot.child("userUID").getValue().equals(user.getUid())) {
//                            count++;
//                        }
//                    }
//                }
//
//                @Override
//                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//                }
//
//                @Override
//                public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//                }
//
//                @Override
//                public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//                }
//
//                @Override
//                public void onCancelled(FirebaseError firebaseError) {
//
//                }
//            });
//        }
//
        //Setting the click events on the sign out button
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                Toast.makeText(MyAccountPage.this, "You Are Signed Out!", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        //Setting click events on the Delete Account button
        deleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterPG.firebaseRef  = new Firebase("https://pgapp-c51ce.firebaseio.com/");
//                RegisterPG.firebaseRef.removeUser();
            }
        });

    }
}
