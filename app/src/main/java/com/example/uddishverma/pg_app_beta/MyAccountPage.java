package com.example.uddishverma.pg_app_beta;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

//TODO complete the click events of addPg and deletePg

public class MyAccountPage extends AppCompatActivity {

    public static final String TAG = "MyAccountPage";

    TextView name, email, noOfPg;
    Button addPG, deletePG, signOut;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account_page);

        name = (TextView) findViewById(R.id.myacc_name);
        email = (TextView) findViewById(R.id.myacc_id);
        noOfPg = (TextView) findViewById(R.id.myacc_no);

        addPG = (Button) findViewById(R.id.myacc_addpg);
        deletePG = (Button) findViewById(R.id.myacc_deletepg);
        signOut = (Button) findViewById(R.id.myacc_signout);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        name.setText(user.getDisplayName().toString());
        email.setText(user.getEmail());
        Log.d(TAG, "onCreate: " + user.getPhotoUrl());

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                Toast.makeText(MyAccountPage.this, "You Are Signed Out!", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
