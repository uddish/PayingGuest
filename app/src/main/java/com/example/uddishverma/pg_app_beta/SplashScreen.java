package com.example.uddishverma.pg_app_beta;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.TaskStackBuilder;


/**
 * Opens up in the starting of the app
 * Checks if the user is logged in or not and
 * opens the activity according to it
 */

public class SplashScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        TaskStackBuilder.create(this)
                .addNextIntentWithParentStack(new Intent(this, MainActivity.class))
                .addNextIntent(new Intent(this, IntroActivity.class))
                .startActivities();
    }

    @Override
    protected void onStart() {
        super.onStart();



    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

}

