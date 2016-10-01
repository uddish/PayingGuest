package com.example.uddishverma.pg_app_beta;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class AuthorisationActivity extends AppCompatActivity {

    ViewPager mViewPager;
    TabLayout mTabLayout;
    ViewPagerAdapter viewpageradapter;
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorisation);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null) {
            Toast.makeText(AuthorisationActivity.this,"You are already signed in.",Toast.LENGTH_SHORT).show();
            finish();
            startActivity(new Intent(AuthorisationActivity.this,MainActivity.class));
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("LOGIN/SIGN UP");
        setSupportActionBar(toolbar);

        //disabling keyboard when the register activity opens
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) findViewById(R.id.container);
        viewpageradapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewpageradapter.addfragment(new LoginFrag(), "Login");
        viewpageradapter.addfragment(new SignUpFrag(), "Signup");
        mViewPager.setAdapter(viewpageradapter);
        mTabLayout.setupWithViewPager(mViewPager);

    }
}
