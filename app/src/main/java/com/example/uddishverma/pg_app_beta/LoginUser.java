package com.example.uddishverma.pg_app_beta;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginUser extends AppCompatActivity implements View.OnClickListener{

    EditText etEmail, etPassword;
    Button btnSignIn;
    TextView tvRegister, tvSkip;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);

        etEmail = (EditText) findViewById(R.id.et_login_email);
        etPassword = (EditText) findViewById(R.id.et_login_password);
        btnSignIn = (Button) findViewById(R.id.btn_login_register);
        tvRegister = (TextView) findViewById(R.id.tv_login_signin);
        tvSkip = (TextView) findViewById(R.id.tv_login_skip);

        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        if(v == btnSignIn)
            loginUser();
        if(v == tvRegister)
            startActivity(new Intent(getApplicationContext(), RegisterUser.class));
    }

    private void loginUser() {

        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email))    {
            Toast.makeText(LoginUser.this, "Please Enter the Email Id!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)) {
            Toast.makeText(LoginUser.this, "Please Enter the Password!", Toast.LENGTH_SHORT).show();
            return;
        }

        firebaseAuth.signInWithEmailAndPassword(email, password).
                addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()) {
                            finish();
                            Toast.makeText(LoginUser.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            //set the credentials in navigation drawer and start the next activity
                        }
                        else
                            Toast.makeText(LoginUser.this, "Login Unsuccessful", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
