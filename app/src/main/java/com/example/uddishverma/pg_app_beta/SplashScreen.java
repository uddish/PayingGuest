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

<<<<<<< HEAD
=======


>>>>>>> 30c6c9cbe342115abd8a83f2dbeb158ba1f265cb
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

<<<<<<< HEAD
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
=======

        
        Animation animationUtils = AnimationUtils.loadAnimation(getBaseContext(), R.anim.anim_splash_screen);
        imageView.startAnimation(animationUtils);
        animationUtils.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                finish();

                Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(intent);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
>>>>>>> 30c6c9cbe342115abd8a83f2dbeb158ba1f265cb
    }

}

