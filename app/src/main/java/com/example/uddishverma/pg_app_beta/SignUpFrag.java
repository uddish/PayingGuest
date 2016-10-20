package com.example.uddishverma.pg_app_beta;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import android.view.WindowManager;


import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by UddishVerma on 03/09/16.
 */
public class SignUpFrag extends Fragment {

    EditText userFirstName,userLastName, signupEmail, signupPassword, signupRepassword;
    Button btnSignup, btnGoogle, btnFacebook;
    TextView loginLink;

    SweetAlertDialog pDialog;

    FirebaseAuth firebaseAuth;

    ViewPager viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_signup, container, false);

        viewPager = (ViewPager) getActivity().findViewById(R.id.container);

        Firebase.goOnline();
        Firebase.setAndroidContext(getContext());

        firebaseAuth = FirebaseAuth.getInstance();

        userFirstName = (EditText) view.findViewById(R.id.edittext_firstusername);
        userLastName = (EditText) view.findViewById(R.id.edittext_lastusername);
        signupEmail = (EditText) view.findViewById(R.id.edittext_email);
        signupPassword = (EditText) view.findViewById(R.id.edittext_password);
        signupRepassword = (EditText) view.findViewById(R.id.edittext_repassword);
        btnSignup = (Button) view.findViewById(R.id.btn_signup);
        btnGoogle = (Button) view.findViewById(R.id.btn_google);
        btnFacebook = (Button) view.findViewById(R.id.btn_facebook);
        loginLink = (TextView) view.findViewById(R.id.link_login);


        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(0);
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUserFunction();
            }
        });


        btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("Please Go To The Login Page!")
                        .show();
            }
        });

        btnFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("Please Go To The Login Page!")
                        .show();
            }
        });

        return view;
    }

    private void registerUserFunction() {

        String userFirstNameCheck = userFirstName.getText().toString().trim();
        String userLastNameCheck = userLastName.getText().toString().trim();
        String emailCheck = signupEmail.getText().toString().trim();
        String passwordCheck = signupPassword.getText().toString().trim();
        String repeatPasswordCheck = signupRepassword.getText().toString().trim();


        /**
         * Check the condition on username ,email ,password and re entered password.
         */

        //Checking for the null fields
        if (TextUtils.isEmpty(userFirstNameCheck)) {
            userFirstName.setError("Enter Atleast 3 Characters");
            Toast.makeText(getActivity(), "Enter your First Name Correctly!", Toast.LENGTH_SHORT).show();
            //Stopping further execution
            return;
        }

        if (TextUtils.isEmpty(userLastNameCheck)){
            userLastName.setError("Enter Atleast 3 Characters");
            Toast.makeText(getActivity(), "Enter your Last Name Correctly!", Toast.LENGTH_SHORT).show();
            //Stopping further execution
            return;
        }

        if (emailCheck.isEmpty()
                || !Patterns.EMAIL_ADDRESS.matcher(emailCheck).matches()){
            signupEmail.setError("Enter a Valid Email Address");
            Toast.makeText(getActivity(), "Enter a Valid Email Address!", Toast.LENGTH_SHORT).show();
            return;
        }


        if (passwordCheck.isEmpty() || passwordCheck.length() < 6) {
            signupPassword.setError("It Should Be Between 6 and 15 Characters");
            Toast.makeText(getActivity(), "Enter a Valid Password!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (repeatPasswordCheck.isEmpty() || (!passwordCheck.equals(repeatPasswordCheck))) {
            signupRepassword.setError("Both passwords should be same");
            Toast.makeText(getActivity(), "Enter a Valid Password!", Toast.LENGTH_SHORT).show();
            return;
        }

        pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Registering You");
        pDialog.setCancelable(false);
        pDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(emailCheck, passwordCheck)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                            UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()

                                    .setDisplayName(userFirstName.getText().toString() + " " + userLastName.getText().toString()).build();
                            user.updateProfile(profileUpdate)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            //start the activity which you want to open after the login in successful
                                            pDialog.dismiss();
                                            Toast.makeText(getContext(), "Welcome!", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(getContext(), MainActivity.class));
                                            getActivity().finish();
                                        }
                                    });
                        } else
                            Toast.makeText(getContext(), "Could Not Register... Please Try Again!", Toast.LENGTH_SHORT).show();

                    }

                });
    }


}
