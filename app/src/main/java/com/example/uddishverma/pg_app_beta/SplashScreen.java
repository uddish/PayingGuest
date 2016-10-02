package com.example.uddishverma.pg_app_beta;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.TaskStackBuilder;


/**
 * Opens up in the starting of the app
 * Checks if the user is logged in or not and
 * opens the activity according to it
 */

public class SplashScreen extends Activity {


//    ImageView imageView;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_splash_screen);
//
//
//        imageView = (ImageView) findViewById(R.id.splash_image);
//
//        Animation animationUtils = AnimationUtils.loadAnimation(getBaseContext(), R.anim.anim_splash_screen);
//        imageView.startAnimation(animationUtils);
//        animationUtils.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                finish();
//
//                Intent intent = new Intent(SplashScreen.this, MainActivity.class);
//                startActivity(intent);
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//        });
//    }

    SharedPreferences shared;

    @Override
    protected void onStart() {
        super.onStart();
    }

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
    protected void onPause() {
        super.onPause();
        finish();

        shared = getSharedPreferences("ShaPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        boolean firstTime = shared.getBoolean("first", true);

        if (firstTime) {
            editor.putBoolean("first", false);
            editor.apply();
            Intent intent = new Intent(SplashScreen.this, IntroActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(SplashScreen.this, MainActivity.class);
            startActivity(intent);
        }

    }

}

