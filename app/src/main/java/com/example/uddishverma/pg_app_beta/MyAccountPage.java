package com.example.uddishverma.pg_app_beta;

import android.app.ProgressDialog;
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

    TextView name, email;
    Button addPG, editPG, signOut, deleteAccount, myPgs;
    int count = 0;
    static long noOfChildrenTwo;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account_page);

        name = (TextView) findViewById(R.id.myacc_name);
        email = (TextView) findViewById(R.id.myacc_id);

        addPG = (Button) findViewById(R.id.myacc_addpg);
        editPG = (Button) findViewById(R.id.myacc_editpg);
        signOut = (Button) findViewById(R.id.myacc_signout);
        myPgs = (Button) findViewById(R.id.myacc_mypg);

        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = firebaseAuth.getCurrentUser();

        final ProgressDialog pd = new ProgressDialog(this);

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

        Firebase.setAndroidContext(this);
        RegisterPG.firebaseRef = new Firebase("https://pgapp-c51ce.firebaseio.com/");

        editPG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (MainActivity.noOfChildren != 0) {
                    startActivity(new Intent(getApplicationContext(), MultiplePGEdit.class));
                }


                //Triggered when Number of Children from MainActivity are 0.
                // Finding the number of Pgs and and then starting the edit activity
                else {
                    Log.d(TAG, "onClick: Children are 0");
                    pd.setMessage("Please Wait...");
                    pd.setCancelable(false);
                    pd.show();

                    RegisterPG.firebaseRef.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            if (dataSnapshot != null && dataSnapshot.getValue() != null) {
                                Log.d(TAG, "onChildAdded: NUMBER OF CHILDREN " + dataSnapshot.getChildrenCount());
                                noOfChildrenTwo = dataSnapshot.getChildrenCount();

//                              Starting the Multiple Pg Edit Activity which will further allow user to choose a particular PG
                                pd.dismiss();
                                startActivity(new Intent(getApplicationContext(), MultiplePGEdit.class));
                                finish();

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
        });


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


    }

}
