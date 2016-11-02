package app.paying.guest.alpha;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uddishverma.pg_app_beta.R;
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

/**
 * This activity gets called when the user clicks on the My Account section in the navigation drawer
 */

public class MyAccountPage extends AppCompatActivity {

    public static final String TAG = "MyAccountPage";

    TextView name, email;
    Button signOut;
    TextView userImage;
    TextView myPgs, addPg, editPg;
    int count = 0;
    static long noOfChildrenTwo;
    FirebaseAuth firebaseAuth;
    GoogleApiClient mGoogleApiClient;
    FirebaseUser user;

    boolean isInternetConnected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account_page);

        name = (TextView) findViewById(R.id.myacc_name);
        email = (TextView) findViewById(R.id.myacc_id);

        addPg = (TextView) findViewById(R.id.myacc_addpg);
        editPg = (TextView) findViewById(R.id.myacc_editpg);
        signOut = (Button) findViewById(R.id.myacc_signout);
        myPgs = (TextView) findViewById(R.id.myacc_mypg);

        userImage = (TextView) findViewById(R.id.user_profile_photo);

        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = firebaseAuth.getCurrentUser();

        name.setText(user.getDisplayName().toString());
        email.setText(user.getEmail());
        userImage.setText((user.getDisplayName()).substring(0, 1));


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
            @Override
            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                Toast.makeText(MyAccountPage.this, "Google play services error..", Toast.LENGTH_SHORT).show();
            }
        }).addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        addPg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(getApplicationContext(), RegisterPGPageOne.class));
            }
        });

        Firebase.setAndroidContext(this);
        RegisterPG.firebaseRef = new Firebase("https://pgapp-c51ce.firebaseio.com/");


        editPg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isNetworkConnected();
                if (isInternetConnected) {

                    if (MainActivity.noOfChildren != 0) {
                        startActivity(new Intent(getApplicationContext(), MultiplePGEdit.class));
                    }

                    //Triggered when Number of Children from MainActivity are 0.
                    // Finding the number of Pgs and and then starting the edit activity
                    else {
                        Log.d(TAG, "onClick: Children are 0");
                        final SweetAlertDialog mdialog = new SweetAlertDialog(MyAccountPage.this, SweetAlertDialog.PROGRESS_TYPE);
                        mdialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                        mdialog.setTitleText("Please Wait");
                        mdialog.setCancelable(false);
                        mdialog.show();

                        RegisterPG.firebaseRef.addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                if (dataSnapshot != null && dataSnapshot.getValue() != null) {
                                    Log.d(TAG, "onChildAdded: NUMBER OF CHILDREN " + dataSnapshot.getChildrenCount());
                                    noOfChildrenTwo = dataSnapshot.getChildrenCount();

//                              Starting the Multiple Pg Edit Activity which will further allow user to choose a particular PG
                                    mdialog.dismiss();
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

            }

        });


        myPgs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                isNetworkConnected();
                if (isInternetConnected) {

                    if (MainActivity.noOfChildren != 0) {
                        startActivity(new Intent(getApplicationContext(), MyRegisteredPGInfo.class));
                    } else {
                        final SweetAlertDialog mdialog = new SweetAlertDialog(MyAccountPage.this, SweetAlertDialog.PROGRESS_TYPE);
                        mdialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                        mdialog.setTitleText("Please Wait");
                        mdialog.setCancelable(false);
                        mdialog.show();

                        RegisterPG.firebaseRef.addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                if (dataSnapshot != null && dataSnapshot.getValue() != null) {
                                    Log.d(TAG, "onChildAdded: NUMBER OF CHILDREN " + dataSnapshot.getChildrenCount());
                                    MainActivity.noOfChildren = dataSnapshot.getChildrenCount();

//                              Starting the Multiple Pg Edit Activity which will further allow user to choose a particular PG
                                    mdialog.dismiss();
                                    startActivity(new Intent(getApplicationContext(), MyRegisteredPGInfo.class));
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
            }
        });


        //Setting the click events on the sign out button
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firebaseAuth.signOut();

                Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {

                    }
                });

                Toast.makeText(MyAccountPage.this, "You are logged out!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MyAccountPage.this, MainActivity.class));
                finish();

                if (LoginFrag.t == 1) {
                    LoginManager.getInstance().logOut();
                    LoginFrag.t = 0;
                    Toast.makeText(MyAccountPage.this, "You are logged out!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MyAccountPage.this, MainActivity.class));
                    finish();
                }
            }
        });


    }

    //To check if the internet is connected
    private void isNetworkConnected() {

        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            isInternetConnected = true;
        } else {
            isInternetConnected = false;
            new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("No Internet")
                    .setContentText("Please Check Your Internet Connection!")
                    .show();
        }
    }

}
