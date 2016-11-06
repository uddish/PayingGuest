package app.paying.guest.release;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.uddishverma.pg_app_beta.R;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by UddishVerma on 28/09/16.
 * This activity shows the multiple PGs added by the user
 * which can be further selected for editing
 */

public class MultiplePGEdit extends AppCompatActivity {

    public static final String TAG = "FindPGActivity";

    RecyclerView mrecyclerView;
    RecyclerView.Adapter madapter;
    RecyclerView.LayoutManager layoutManager;
    private ArrayList<PgDetails_POJO.PgDetails> cardDetails;
    Button filterButton;
    Intent filterActivityIntent;

    public static final int INITIAL_FLAG = 9001;
    public static final int FINAL_FLAG = 8001;
    int flag = INITIAL_FLAG;

    //To check is the internet is connected
    boolean isInternetConnected = false;

    Toolbar toolbar;

    long noOfChildren;
    long count = 0;
    int countFind = 0;

    FirebaseAuth firebaseAuth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_find_pg);

        filterActivityIntent = new Intent(this, FilterActivity.class);

        //Checking if the internet is connected
        isNetworkConnected();

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        filterButton = (Button) findViewById(R.id.filter);

        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(filterActivityIntent);
            }
        });


        mrecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        cardDetails = new ArrayList<>();
        layoutManager = new LinearLayoutManager(this);
        mrecyclerView.setLayoutManager(layoutManager);
        mrecyclerView.setHasFixedSize(true);
        madapter = new MultiplePGEditAdapter(cardDetails, this);
        mrecyclerView.setAdapter(madapter);


        if (isInternetConnected) {
            //Adding progress dialogue while the cards are loading
            final ProgressDialog pd = new ProgressDialog(this);
            pd.setMessage("Please Wait...");
//            pd.setCancelable(false);
            pd.show();

            Firebase.setAndroidContext(this);

            RegisterPG.firebaseRef = new Firebase("https://pgapp-c51ce.firebaseio.com/");

            if (MainActivity.noOfChildren == 0) {
                Log.d(TAG, "onCreate: NUMBER OF CHILDREN FROM MY ACCOUNT " + MyAccountPage.noOfChildrenTwo);
                MainActivity.noOfChildren = MyAccountPage.noOfChildrenTwo;
            }


            RegisterPG.firebaseRef.child("PgDetails").addChildEventListener(new ChildEventListener() {

                @JsonIgnoreProperties
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                    if (dataSnapshot != null && dataSnapshot.getValue() != null) {
                        Log.d(TAG, "onChildAdded: " + dataSnapshot.child("PgDetails").getValue());

                        //Getting the PGs of corresponding to the User's UID
                        if (dataSnapshot.child("userUID").getValue().equals(user.getUid())) {
                            PgDetails_POJO.PgDetails model = dataSnapshot
                                    .getValue(PgDetails_POJO.PgDetails.class);
                            flag = FINAL_FLAG;
                            cardDetails.add(model);
                            madapter.notifyDataSetChanged();

                            pd.dismiss();
                        } else {
                            countFind++;
                            Log.d(TAG, "onChildAdded: COUNTFIND " + countFind);

                            //Checking if we have reached the end of the database and didn't find any PG
                            if (countFind == MainActivity.noOfChildren) {
                                pd.dismiss();
                                Log.d(TAG, "onChildAdded: COUNT " + count);
                                Toast.makeText(getApplicationContext(), "No Pg Found!", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            }
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
    }


    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        super.onBackPressed();
    }


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
