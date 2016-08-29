package com.example.uddishverma.pg_app_beta;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterUser extends AppCompatActivity implements View.OnClickListener {

    Button btnRegisterUser;
    EditText registerEmail, registerPassword;
    TextView tvSignIn, tvSkip;
    private ProgressDialog dialog;

    public static final String TAG = "RegisterUser";

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        btnRegisterUser = (Button) findViewById(R.id.btn_register);
        registerEmail = (EditText) findViewById(R.id.et_email);
        registerPassword = (EditText) findViewById(R.id.et_password);
        tvSignIn = (TextView) findViewById(R.id.tv_signin);
        tvSkip = (TextView) findViewById(R.id.tv_skip);

        btnRegisterUser.setOnClickListener(this);
        tvSignIn.setOnClickListener(this);

        firebaseAuth = FirebaseAuth.getInstance();

        tvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }

    @Override
    public void onClick(View v) {

        if (v == btnRegisterUser) {
            registerUserFunction();
        }
        if (v == tvSignIn) {
            Toast.makeText(RegisterUser.this, "Please Wait!", Toast.LENGTH_SHORT).show();
            //do login work here
        }
//        if(v == tvSkip) {
//            Intent i = new Intent(this, FindPGActivity.class);
//            startActivity(i);
//            Log.d(TAG, "onClick: Starting Main Activity");
//        }
    }

    //This function registers the user to firebase.

    private void registerUserFunction() {

        String email = registerEmail.getText().toString().trim();
        String password = registerPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(RegisterUser.this, "Please Enter the Email Id!", Toast.LENGTH_SHORT).show();
            //Stopping further execution
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(RegisterUser.this, "Please Enter the Password!", Toast.LENGTH_SHORT).show();
            //Stopping further execution
            return;
        }

        dialog.setMessage("Registering User...");
        dialog.show();
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            //start the activity which you want to open after the login in successful
                            dialog.dismiss();
                            Toast.makeText(RegisterUser.this, "Welcome!", Toast.LENGTH_SHORT).show();
                        }
                        else
                            Toast.makeText(RegisterUser.this, "Could Nor Register... Please Try Again!", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
