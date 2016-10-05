package com.example.uddishverma.pg_app_beta;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String TAG = "MainActivity";

    Toolbar toolbar;

    FirebaseAuth firebaseAuth;
    FirebaseUser user;

    TextView navName, navEmail;
    CoordinatorLayout coordinatorLayout;

    GoogleApiClient mGoogleApiClient;

    static long noOfChildren;

    Boolean doublepress = false;

//    ProgressDialog progressDialog;

    SweetAlertDialog pDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        firebaseAuth = FirebaseAuth.getInstance();

//        progressDialog = new ProgressDialog(this);
        pDialog = new SweetAlertDialog(getApplicationContext(), SweetAlertDialog.PROGRESS_TYPE);

        user = firebaseAuth.getCurrentUser();

        if (firebaseAuth.getCurrentUser() != null) {
            Log.d(TAG, "onCreate: USER " + user.getEmail());
            Log.d(TAG, "onCreate: USER " + user.getUid());
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        navName = (TextView) header.findViewById(R.id.textview_username);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinate_layout);


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
            @Override
            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                Toast.makeText(MainActivity.this, "Google play services error..", Toast.LENGTH_SHORT).show();
            }
        }).addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        //Setting the name in navigation drawer
        if (user != null) {
            navName.setText(user.getDisplayName().toString());
//            navEmail.setText(user.getEmail().toString());

            Snackbar.make(coordinatorLayout, "Howdy " + user.getDisplayName().toString() + "!", Snackbar.LENGTH_LONG).show();

        }
    }


    //This function opens the register pg activity
    public void openRegisterPgActivity(View view) {

        if (firebaseAuth.getCurrentUser() == null) {

           // NEW ALERT DIALOG
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Not Signed In!")
                    .setContentText("Please SignIn Before Registering!")
                    .setCancelText("CANCEL")
                    .setConfirmText("SIGN IN")
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
                            startActivity(new Intent(getApplicationContext(), AuthorisationActivity.class));
                            sweetAlertDialog.dismiss();
                        }
                    })
                    .show();
        }
        else {

            Intent i = new Intent(this, RegisterPGPageOne.class);
            startActivity(i);
        }
    }

    //This function opens the find pg activity
    public void openFindPgActivity(View view) {
        Intent i = new Intent(this, FindPGActivity.class);
        startActivity(i);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            if (doublepress) {
                finish();
                super.onBackPressed();
                return;
            }
            doublepress = true;
            Toast.makeText(MainActivity.this, "Press Again To Exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doublepress = false;
                }
            }, 2000);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_account) {

            if (firebaseAuth.getCurrentUser() == null) {
                startActivity(new Intent(getApplicationContext(), AuthorisationActivity.class));
            }
            else
                startActivity(new Intent(getApplicationContext(), MyAccountPage.class));

        }
        else if (id == R.id.nav_pg) {

// ********************************Counting the number of Pgs first in the firebase ***************************************
            if (user == null) {
                Toast.makeText(this, "Please Sign In First!", Toast.LENGTH_SHORT).show();
            } else {

                final SweetAlertDialog mdialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
                mdialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                mdialog.setTitleText("Please Wait");
                mdialog.setCancelable(false);
                mdialog.show();
                Firebase.setAndroidContext(this);

                RegisterPG.firebaseRef = new Firebase("https://pgapp-c51ce.firebaseio.com/");

                // ********************************Counting the number of Pgs first in the firebase ************************
                RegisterPG.firebaseRef.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        if (dataSnapshot != null && dataSnapshot.getValue() != null) {
                            Log.d(TAG, "onChildAdded: NUMBER OF CHILDREN " + dataSnapshot.getChildrenCount());
                            noOfChildren = dataSnapshot.getChildrenCount();
                            mdialog.dismiss();
                            startActivity(new Intent(getApplicationContext(), MyRegisteredPGInfo.class));
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

            // ****************************************************************************************************************


        } else if (id == R.id.nav_editPg) {

            if (user == null) {
                Toast.makeText(this, "Please Sign In First!", Toast.LENGTH_SHORT).show();
            } else {

                final SweetAlertDialog mDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
                mDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                mDialog.setTitleText("Please Wait");
                mDialog.setCancelable(false);
                mDialog.show();

                Firebase.setAndroidContext(this);
//
                RegisterPG.firebaseRef = new Firebase("https://pgapp-c51ce.firebaseio.com/");
//
//                // ********************************Counting the number of Pgs first in the firebase **************************
                RegisterPG.firebaseRef.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        if (dataSnapshot != null && dataSnapshot.getValue() != null) {
                            Log.d(TAG, "onChildAdded: NUMBER OF CHILDREN " + dataSnapshot.getChildrenCount());
                            noOfChildren = dataSnapshot.getChildrenCount();

//                            //Starting the Multiple Pg Edit Activity which will further allow user to choose a particular PG
                            finish();
                            mDialog.dismiss();
                            startActivity(new Intent(getApplicationContext(), MultiplePGEdit.class));
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


        }  else if (id == R.id.nav_help) {

            if (firebaseAuth.getCurrentUser() != null) {
                firebaseAuth.signOut();
                user = null;

                Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {

                    }
                });

                Toast.makeText(MainActivity.this, "You are logged out!", Toast.LENGTH_SHORT).show();

                if(LoginFrag.t == 1)    {
                    LoginManager.getInstance().logOut();
                    LoginFrag.t = 0;
                    Toast.makeText(MainActivity.this, "You are logged out!", Toast.LENGTH_SHORT).show();
                }
            }

            else
                Toast.makeText(MainActivity.this, "Please SignIn First", Toast.LENGTH_SHORT).show();
        }

        else if (id == R.id.nav_invite) {
            /**
             * open sharing intent to send
             * a custom link of our app in the playstore .
             *
             * and side by side we can open an activity and show a QR code
             * of our app in the playstore .

             */
        }

        else if (id == R.id.nav_help) {

                /**
                 * OUR EMAIL ID'S

                 */
           }
        else if (id == R.id.nav_rateus) {
            /**
             * Our Playstore link comment section
             */
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
