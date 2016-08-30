package com.example.uddishverma.pg_app_beta;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterUserActivity extends AppCompatActivity {

    EditText etEmail, etPassword, etRepeatpassword;
    Button btnNext;
    FloatingActionButton fabRegister;
    CardView cvAdd;

    FirebaseAuth firebaseAuth;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        etEmail = (EditText) findViewById(R.id.et_email);
        etPassword = (EditText) findViewById(R.id.et_password);
        etRepeatpassword = (EditText) findViewById(R.id.et_repeatpassword);
        btnNext = (Button) findViewById(R.id.bt_next);
        fabRegister = (FloatingActionButton) findViewById(R.id.register_fab);
        cvAdd = (CardView) findViewById(R.id.cv_add);

        progressDialog = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();

        //Starting the animation
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            showEnterAnimation();
        }

        //Handling the click events of the Next Button which uploads the credentials to the Firebase
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUserFunction();
            }
        });

        //setting the click event of the floating action button
        fabRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateRevealClose();
            }
        });


    }

    //This function registers the user's credentials and save them to firebase
    private void registerUserFunction() {

        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String repeatPassword = etRepeatpassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(RegisterUserActivity.this, "Please Enter the Email Id!", Toast.LENGTH_SHORT).show();
            //Stopping further execution
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(RegisterUserActivity.this, "Please Enter the Password!", Toast.LENGTH_SHORT).show();
            //Stopping further execution
            return;
        }

        if (TextUtils.isEmpty(repeatPassword)) {
            Toast.makeText(RegisterUserActivity.this, "Please Enter the Password!", Toast.LENGTH_SHORT).show();
            //Stopping further execution
            return;
        }

        if(!password.equals(repeatPassword))    {
            Toast.makeText(RegisterUserActivity.this, "Passwords do not Match!", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Registering User...");
        progressDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            //start the activity which you want to open after the login in successful
                            progressDialog.dismiss();
                            Toast.makeText(RegisterUserActivity.this, "Welcome!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }
                        else
                            Toast.makeText(RegisterUserActivity.this, "Could Not Register... Please Try Again!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void showEnterAnimation()   {
            Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.fabtransition);
        getWindow().setSharedElementEnterTransition(transition);

        transition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
                cvAdd.setVisibility(View.GONE);
            }

            @Override
            public void onTransitionEnd(Transition transition) {
                transition.removeListener(this);
                animateRevealShow();
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }
        });
    }

    public void animateRevealShow() {
        Animator mAnimator = ViewAnimationUtils.createCircularReveal(cvAdd, cvAdd.getWidth()/2, 0, fabRegister.getWidth()/2, cvAdd.getHeight());
        mAnimator.setDuration(500);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                cvAdd.setVisibility(View.VISIBLE);
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();
    }

    public void animateRevealClose()    {
        Animator mAnimator = ViewAnimationUtils.createCircularReveal(cvAdd,cvAdd.getWidth()/2,0, cvAdd.getHeight(), fabRegister.getWidth() / 2);
        mAnimator.setDuration(500);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                cvAdd.setVisibility(View.INVISIBLE);
                super.onAnimationEnd(animation);
                fabRegister.setImageResource(R.drawable.plus);
                RegisterUserActivity.super.onBackPressed();
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();
    }

    @Override
    public void onBackPressed() {
        animateRevealClose();
    }
}
