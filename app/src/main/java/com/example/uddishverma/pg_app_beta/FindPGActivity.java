package com.example.uddishverma.pg_app_beta;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

//************************************Class to Find PGs************************************************************
public class FindPGActivity extends AppCompatActivity {

    public static final String TAG = "FindPGActivity";

    RecyclerView mrecyclerView;
    RecyclerView.Adapter madapter;
    RecyclerView.LayoutManager layoutManager;
    private ArrayList<PgDetails_POJO.PgDetails> cardDetails;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_layout);


/**
 * To add Swipe Back Layout, extend the class to SwipeBackActivity
 * setDragEdge(SwipeBackLayout.DragEdge.LEFT);
 * compile 'com.github.liuguangqiang.swipeback:library:1.0.2@aar' in GRADLE
 */

        toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mrecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        cardDetails = new ArrayList<>();
        layoutManager = new LinearLayoutManager(this);
        mrecyclerView.setLayoutManager(layoutManager);
        mrecyclerView.setHasFixedSize(true);
        madapter = new PgDetailsAdapter(cardDetails,this);
        mrecyclerView.setAdapter(madapter);

       //Adding progress dialogue while the cards are loading
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Please Wait...");
        pd.show();

        Firebase.setAndroidContext(this);

        RegisterPG.firebaseRef = new Firebase("https://pgapp-c51ce.firebaseio.com/");

        Log.d(TAG, "onCreate: " + RegisterPG.firebaseRef.orderByChild("pinCode").equalTo("4354"));

        RegisterPG.firebaseRef.child("PgDetails").addChildEventListener(new ChildEventListener() {


            @JsonIgnoreProperties
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                if (dataSnapshot != null && dataSnapshot.getValue() != null) {
                    Log.d(TAG, "onChildAdded: " + dataSnapshot.child("PgDetails").getValue());
                        PgDetails_POJO.PgDetails model = dataSnapshot
                                .getValue(PgDetails_POJO.PgDetails.class);
                        cardDetails.add(model);
//                      mrecyclerView.scrollToPosition(cardDetails.size() - 1);
//                      madapter.notifyItemInserted(cardDetails.size() - 1);
                        madapter.notifyDataSetChanged();

                    //Stopping the progress dialogue
                    pd.dismiss();
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu_toolbar,menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int res_id=item.getItemId();
        if(res_id==R.id.filter)
        {
            Toast.makeText(getApplicationContext(),"filter",Toast.LENGTH_LONG).show();
        }

        return true;
    }
}