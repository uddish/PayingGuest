package com.example.uddishverma.pg_app_beta;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.concurrent.Executor;

/**
 * Created by UddishVerma on 03/09/16.
 */
public class LoginFrag extends Fragment {

    EditText loginEmail, loginPassword;
    Button btnLogin, btnGoogle, btnFb;
    TextView forgotPass;
    ProgressDialog progressDialog;
    ImageView eye;
    FirebaseAuth firebaseAuth;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
//        Firebase.getDefaultConfig().setPersistenceEnabled(true);
        Firebase.goOnline();
        Firebase.setAndroidContext(getContext());

        firebaseAuth = FirebaseAuth.getInstance();

        eye = (ImageView) view.findViewById(R.id.eye);
        loginEmail = (EditText) view.findViewById(R.id.et_email);
        loginPassword = (EditText) view.findViewById(R.id.et_password);
        btnLogin = (Button) view.findViewById(R.id.btn_login);
        btnGoogle = (Button) view.findViewById(R.id.btn_google);
        btnFb = (Button) view.findViewById(R.id.btn_fb);
        forgotPass = (TextView) view.findViewById(R.id.forgot_pass);


       //Adding click events on the login button
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
            }
        });

        //Adding click events on the eye button
        eye.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction())  {
                    case MotionEvent.ACTION_DOWN:
                        loginPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                        break;
                    case MotionEvent.ACTION_UP:
                        loginPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        break;
                }
                return true;
            }
        });

        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth auth = FirebaseAuth.getInstance();

                if(!TextUtils.isEmpty(loginEmail.getText()))    {
                    String emailAddress = loginEmail.getText().toString();

                    auth.sendPasswordResetEmail(emailAddress).
                            addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()) {
                                        Toast.makeText(getContext(), "Check your email for the password reset link", Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                        Toast.makeText(getContext(), "Incorrect Email ID", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
                else    {
                    Toast.makeText(getContext(), "Enter Email Id First", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }


    private void userLogin() {

        String email = loginEmail.getText().toString().trim();
        String password = loginPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getContext(), "Please Enter the Email Id!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getContext(), "Please Enter the Password!", Toast.LENGTH_SHORT).show();
            return;
        }

        firebaseAuth.signInWithEmailAndPassword(email, password).
                addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()) {
                            Toast.makeText(getContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getContext(), MainActivity.class));
                        }
                        else    {
                            Toast.makeText(getContext(), "Login Unsuccessful", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
