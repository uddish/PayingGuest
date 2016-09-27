package com.example.uddishverma.pg_app_beta;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Opens up in the starting of the app
 * Checks if the user is logged in or not and
 * opens the activity according to it
 */

public class SplashScreen extends Activity {

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        imageView = (ImageView) findViewById(R.id.splash_image);

        Animation animationUtils= AnimationUtils.loadAnimation(getBaseContext(),R.anim.anim_splash_screen);
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
    }
}
