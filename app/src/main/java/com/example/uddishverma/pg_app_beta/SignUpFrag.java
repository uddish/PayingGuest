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
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.concurrent.Executor;

/**
 * Created by UddishVerma on 03/09/16.
 */
public class SignUpFrag extends Fragment {

    EditText fName, lName, signupEmail, signupPass, signupRepass;
    Button btnSignup, btnGoogle, btnFacebook;
    ImageView eye;

    ProgressDialog progressDialog;

    FirebaseAuth firebaseAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_signup, container, false);

        progressDialog = new ProgressDialog(getContext());

//      Firebase.getDefaultConfig().setPersistenceEnabled(true);
        Firebase.goOnline();
        Firebase.setAndroidContext(getContext());

        firebaseAuth = FirebaseAuth.getInstance();

        fName = (EditText) view.findViewById(R.id.et_fname);
        lName = (EditText) view.findViewById(R.id.et_lname);
        signupEmail = (EditText) view.findViewById(R.id.et_email);
        signupPass = (EditText) view.findViewById(R.id.et_password);
        signupRepass = (EditText) view.findViewById(R.id.et_repassword);
        btnSignup = (Button) view.findViewById(R.id.btn_signup);
        eye = (ImageView) view.findViewById(R.id.eye);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUserFunction();
            }
        });

        eye.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        signupPass.setInputType(InputType.TYPE_CLASS_TEXT);
                        break;
                    case MotionEvent.ACTION_UP:
                        signupPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        break;
                }
                return true;
            }
        });

        return view;
    }

    private void registerUserFunction() {

        String firstName = fName.getText().toString().trim();
        String lastName = lName.getText().toString().trim();
        String email = signupEmail.getText().toString().trim();
        String password = signupPass.getText().toString().trim();
        String repeatPassword = signupRepass.getText().toString().trim();


        //Checking for the null fields
        if (TextUtils.isEmpty(firstName)) {
            Toast.makeText(getContext(), "Please Enter the First Name!", Toast.LENGTH_SHORT).show();
            //Stopping further execution
            return;
        }
        if (TextUtils.isEmpty(lastName)) {
            Toast.makeText(getContext(), "Please Enter the Last Name!", Toast.LENGTH_SHORT).show();
            //Stopping further execution
            return;
        }
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getContext(), "Please Enter the Email Id!", Toast.LENGTH_SHORT).show();
            //Stopping further execution
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getContext(), "Please Enter the Password!", Toast.LENGTH_SHORT).show();
            //Stopping further execution
            return;
        }
        if (TextUtils.isEmpty(repeatPassword)) {
            Toast.makeText(getContext(), "Please Enter the Password!", Toast.LENGTH_SHORT).show();
            //Stopping further execution
            return;
        }
        if (!password.equals(repeatPassword)) {
            Toast.makeText(getContext(), "Passwords do not Match!", Toast.LENGTH_SHORT).show();
            return;
        }


        progressDialog.setMessage("Registering User...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(fName.getText().toString() + " " + lName.getText().toString()).build();
                            user.updateProfile(profileUpdate)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            //start the activity which you want to open after the login in successful
                                            progressDialog.dismiss();
                                            Toast.makeText(getContext(), "Welcome!", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(getContext(), MainActivity.class));
                                        }
                                    });
                        } else
                            Toast.makeText(getContext(), "Could Not Register... Please Try Again!", Toast.LENGTH_SHORT).show();

                    }

                });
    }
}
