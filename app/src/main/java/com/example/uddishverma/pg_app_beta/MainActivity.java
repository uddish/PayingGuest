package com.example.uddishverma.pg_app_beta;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button registerPGBtn, findPGBtn;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        registerPGBtn = (Button) findViewById(R.id.register_pg_btn);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

    }

    //This function opens the register pg activity
    public void openRegisterPgActivity(View view)   {
        Intent i = new Intent(this, RegisterPG.class);
        startActivity(i);
    }
    //This function opens the find pg activity
    public void openFindPgActivity(View view)   {
        Intent i = new Intent(this, FindPGActivity.class);
        startActivity(i);
    }

//    @Override
//    public void onBackPressed() {
//        onStop();
//    }
}
