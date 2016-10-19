package com.example.uddishverma.pg_app_beta;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
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
        setSkipButtonVisible();


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
                        .title("welcome to The New City")
                        .description("We Will Help You Find Your Home")
                        .build(),

                new MessageButtonBehaviour(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(IntroActivity.this,"We Provide Solutions to Make You Love Your Work"
                                ,Toast.LENGTH_SHORT).show();
                    }
                },"Sit back and Search"));



        addSlide(new SlideFragmentBuilder()
                        .backgroundColor(R.color.second_slide_background)
                        .buttonsColor(R.color.second_slide_buttons)
                        .image(R.drawable.register)
                        .title("Give Someone A Home")
                        .description("Register Your PG(s)")
                        .build(),
                new MessageButtonBehaviour(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(IntroActivity.this,"Try Us",Toast.LENGTH_SHORT).show();

                    }

                },"Register Now"));


//        addSlide(new SlideFragmentBuilder()
//                .backgroundColor(R.color.third_slide_background)
//                .buttonsColor(R.color.third_slide_buttons)
//                .possiblePermissions(new String[]{Manifest.permission.CALL_PHONE})
//                .neededPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
//                .image(R.drawable.call)
//                .title("Contact Directly")
//                .description("Connect to your favourite PG directly")
//                .build());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            addSlide(new SlideFragmentBuilder()
                    .backgroundColor(R.color.third_slide_background)
                    .buttonsColor(R.color.third_slide_buttons)
                    .possiblePermissions(new String[]{Manifest.permission.CALL_PHONE,Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
    //                .neededPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
                    .image(R.drawable.call)
                    .title("Contact Directly")
                    .description("Connect to your favourite PG directly")
                    .build());
        }
    }

    @Override
    public void onFinish() {
        super.onFinish();
        Intent intent = new Intent(IntroActivity.this,SplashScreen.class);
    }
}
