package com.example.uddishverma.pg_app_beta;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class FindPGActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    int [] imageId={R.drawable.pg1,R.drawable.pg2,R.drawable.pg3,R.drawable.pg4};
    String[] name={"AMBER","BASANT","CHARIOT","DOON"};
    String[] location={"Noida","Pitampura","Kohat Enclave","Dwarka Sector 18-B"};
    Boolean[] wifi={true,true,false,true};
    Boolean[] breakfast={true,false,true,true};

    ArrayList<PgDetails> list=new ArrayList<PgDetails>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_pg);

        for(int i=0;i<4;i++)
        {
            PgDetails pObj=new PgDetails(imageId[i],name[i],location[i],wifi[i],breakfast[i]);
            list.add(pObj);
        }

        recyclerView= (RecyclerView) findViewById(R.id.recycler_view);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter=new PgDetailsAdapter(list);
        recyclerView.setAdapter(adapter);




    }
}