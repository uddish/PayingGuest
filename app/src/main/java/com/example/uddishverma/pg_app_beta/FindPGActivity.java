package com.example.uddishverma.pg_app_beta;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

    ArrayList<PgDetails_POJO.PgDetails> pgDetailsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_pg);


/**
 * To add Swipe Back Layout, extend the class to SwipeBackActivity
 * setDragEdge(SwipeBackLayout.DragEdge.LEFT);
 * compile 'com.github.liuguangqiang.swipeback:library:1.0.2@aar' in GRADLE
 */



        mrecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        cardDetails = new ArrayList<>();
        layoutManager = new LinearLayoutManager(this);
        mrecyclerView.setLayoutManager(layoutManager);
        mrecyclerView.setHasFixedSize(true);
        madapter = new PgDetailsAdapter(cardDetails);
        mrecyclerView.setAdapter(madapter);

       //Adding progress dialogue while the cards are loading
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Please Wait...");
        pd.show();

        Firebase.setAndroidContext(this);

        RegisterPG.firebaseRef = new Firebase("https://pgapp-c51ce.firebaseio.com/");

        RegisterPG.firebaseRef.child("PgDetails").addChildEventListener(new ChildEventListener() {



            @JsonIgnoreProperties
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                if (dataSnapshot != null && dataSnapshot.getValue() != null) {
                    Log.d(TAG, "onChildAdded: " + dataSnapshot.getValue());
                    Log.d(TAG, "onChildAdded: " + dataSnapshot.getKey());
                        PgDetails_POJO.PgDetails model = dataSnapshot.getValue(PgDetails_POJO.PgDetails.class);
                    Log.d(TAG, "Pg ID: " + dataSnapshot.getValue(PgDetails_POJO.PgDetails.class).getId());
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
}