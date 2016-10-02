package com.example.uddishverma.pg_app_beta;

import android.Manifest;
import android.content.Intent;
import android.support.annotation.FloatRange;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import agency.tango.materialintroscreen.MaterialIntroActivity;
import agency.tango.materialintroscreen.MessageButtonBehaviour;
import agency.tango.materialintroscreen.SlideFragmentBuilder;
import agency.tango.materialintroscreen.animations.IViewTranslation;


public class IntroActivity extends MaterialIntroActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        enableLastSlideAlphaExitTransition(true);

        getNextButtonTranslationWrapper()
                .setEnterTranslation(new IViewTranslation() {
                    @Override
                    public void translate(View view, @FloatRange(from = 0.0, to = 1.0) float percentage) {
                        view.setAlpha(percentage);
                    }
                }) ;

        addSlide(new SlideFragmentBuilder()
                        .backgroundColor(R.color.first_slide_background)
                        .buttonsColor(R.color.first_slide_buttons)
                        .image(R.drawable.travel)
                        .title("welcome to the new city")
                        .description("We will help you to find your home")
                        .build(),

                new MessageButtonBehaviour(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(IntroActivity.this,"We provide solutions to make you love your work"
                                ,Toast.LENGTH_SHORT).show();
                    }
                },"Sit back and Search"));



        addSlide(new SlideFragmentBuilder()
                        .backgroundColor(R.color.second_slide_background)
                        .buttonsColor(R.color.second_slide_buttons)
                        .image(R.drawable.key)
                        .title("Here is the key of your new house")
                        .description("Enjoy")
                        .build(),
                new MessageButtonBehaviour(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(IntroActivity.this,"Try us",Toast.LENGTH_SHORT).show();

                    }

                },"Grab Your key"));

        addSlide(new SlideFragmentBuilder()
                .backgroundColor(R.color.third_slide_background)
                .buttonsColor(R.color.third_slide_buttons)
                .possiblePermissions(new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.READ_SMS})
                .neededPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
                .image(R.drawable.interiorhome)
                .title("Start Your new Life")
                .description("With us?")
                .build());
    }

    @Override
    public void onFinish() {
        super.onFinish();
        Intent intent = new Intent(IntroActivity.this,SplashScreen.class);
    }
}
