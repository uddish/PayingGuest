package com.example.uddishverma.pg_app_beta;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;

/**
 * Opens up in the starting of the app
 * Checks if the user is logged in or not and
 * opens the activity according to it
 */

public class SplashScreen extends Activity {

    SharedPreferences shared;

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        shared = getSharedPreferences("ShaPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        boolean  firstTime = shared.getBoolean("first", true);

        if(firstTime) {
            editor.putBoolean("first",false);
            editor.apply();
//            Intent intent = new Intent(SplashScreen.this, IntroActivity.class);
//            startActivity(intent);
            TaskStackBuilder.create(getApplicationContext())
                        .addNextIntentWithParentStack(new Intent(getApplicationContext(), MainActivity.class))
                        .addNextIntent(new Intent(getApplicationContext(), IntroActivity.class))
                        .startActivities();

                finish();
        }
        else
        {
            Intent intent = new Intent(SplashScreen.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//                TaskStackBuilder.create(getApplicationContext())
//                        .addNextIntentWithParentStack(new Intent(getApplicationContext(), MainActivity.class))
//                        .addNextIntent(new Intent(getApplicationContext(), IntroActivity.class))
//                        .startActivities();
//
//                finish();
//            }
//        }, 1500);

    }
}

