package com.example.uddishverma.pg_app_beta;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

//************************************Class to Find PGs************************************************************
public class FindPGActivity extends AppCompatActivity {

    public static final String TAG = "FindPGActivity";

    RecyclerView mrecyclerView;
    RecyclerView.Adapter madapter;
    RecyclerView.LayoutManager layoutManager;
    private ArrayList<PgDetails_POJO.PgDetails> cardDetails;
    Button filterButton;
    Intent filterActivityIntent;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_find_pg);

        filterActivityIntent = new Intent(this, FilterActivity.class);


/**
 * To add Swipe Back Layout, extend the class to SwipeBackActivity
 * setDragEdge(SwipeBackLayout.DragEdge.LEFT);
 * compile 'com.github.liuguangqiang.swipeback:library:1.0.2@aar' in GRADLE
 */

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
        madapter = new PgDetailsAdapter(cardDetails, this);
        mrecyclerView.setAdapter(madapter);

        Intent i = getIntent();
        Bundle b = i.getExtras();
        if (b != null) {
            if (b.getString("source").equals("filter")) {
                Log.d(TAG, "onCreate: ARRAY LIST " + i.getStringArrayListExtra("list"));
                ArrayList<String> arr = i.getStringArrayListExtra("list");
                Log.d(TAG, "onCreate: element 0 " + arr.get(0));
            }
        }

        final SweetAlertDialog mDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        mDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        mDialog.setTitleText("Please Wait");
//        mDialog.setCancelable(false);
        mDialog.show();

        Firebase.setAndroidContext(this);

        RegisterPG.firebaseRef = new Firebase("https://pgapp-c51ce.firebaseio.com/");

        Log.d(TAG, "onCreate: " + RegisterPG.firebaseRef.orderByChild("ac").equalTo("true"));

        RegisterPG.firebaseRef.child("PgDetails").addChildEventListener(new ChildEventListener() {


            @JsonIgnoreProperties
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                if (dataSnapshot != null && dataSnapshot.getValue() != null) {
                    Log.d(TAG, "onChildAdded: " + dataSnapshot.child("PgDetails").getValue());

                    /**
                     * This statement will be used to query from the firebase wrt to a particular POJO field
                     * The below log statement displays the city anit value
                     * Log.d(TAG, "onChildAdded: KEY VALUE : " + (dataSnapshot.child("city")));
                     *
                     * And this is used tp display cardViews where CITY = DELHI
                     * Log.d(TAG, "onChildAdded: KEY VALUE : " + (dataSnapshot.child("city").getValue().equals("delhi")));
                     */

                    Log.d(TAG, "onChildAdded: KEY VALUE : " + (dataSnapshot.child("city").getValue().equals("delhi")));
                    PgDetails_POJO.PgDetails model = dataSnapshot
                            .getValue(PgDetails_POJO.PgDetails.class);
                    cardDetails.add(model);
//                      mrecyclerView.scrollToPosition(cardDetails.size() - 1);
//                      madapter.notifyItemInserted(cardDetails.size() - 1);
                    madapter.notifyDataSetChanged();

                    //Stopping the progress dialogue
                    mDialog.dismiss();
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