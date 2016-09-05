package com.example.uddishverma.pg_app_beta;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String TAG = "MainActivity";

    Toolbar toolbar;

    FirebaseAuth firebaseAuth;
    FirebaseUser user;

    TextView navName, navEmail;
    CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        firebaseAuth = FirebaseAuth.getInstance();

        user = firebaseAuth.getCurrentUser();

        if(firebaseAuth.getCurrentUser() != null) {
            Log.d(TAG, "onCreate: USER " + user.getEmail());
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        navName = (TextView) header.findViewById(R.id.account_name);
        navEmail = (TextView) header.findViewById(R.id.account_email);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinate_layout);

        //Setting the name in navigation drawer
        if(user != null)    {
            navName.setText(user.getDisplayName().toString());
            navEmail.setText(user.getEmail().toString());

            Snackbar.make(coordinatorLayout, "Howdy " + user.getDisplayName().toString() + "!", Snackbar.LENGTH_LONG).show();

        }
    }



    //This function opens the register pg activity
    public void openRegisterPgActivity(View view)   {

        if(firebaseAuth.getCurrentUser() == null)   {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setView(R.layout.signin_alert_dialog);
            builder.setPositiveButton("SIGN IN", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(getApplicationContext(), AuthorisationActivity.class));
                }
            });

            builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.create().show();
        }
        else {
            Intent i = new Intent(this, RegisterPG.class);
            startActivity(i);
        }
    }
    //This function opens the find pg activity
    public void openFindPgActivity(View view)   {
        Intent i = new Intent(this, FindPGActivity.class);
        startActivity(i);
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
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
            if(firebaseAuth.getCurrentUser() == null)   {
                startActivity(new Intent(getApplicationContext(), AuthorisationActivity.class));
            }
            else
                startActivity(new Intent(getApplicationContext(), MyAccountPage.class));

        } else if (id == R.id.nav_pg) {

        } else if (id == R.id.nav_editPg) {

        } else if (id == R.id.nav_deletePg) {

        } else if(id == R.id.nav_logout) {
            if (firebaseAuth.getCurrentUser() != null)  {
                firebaseAuth.signOut();
            Toast.makeText(MainActivity.this, "You are logged out!", Toast.LENGTH_SHORT).show();
        }
            else
                Toast.makeText(MainActivity.this, "Please SignIn First", Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
