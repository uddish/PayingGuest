package app.paying.guest.release;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uddishverma.pg_app_beta.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.firebase.client.Firebase;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by UddishVerma on 03/09/16.
 */

//TODO Open register activity from FB if register is clicked
public class LoginFrag extends Fragment {

    public static final String TAG = "LoginFrag";

    EditText loginEmail, loginPassword;
    TextView linkSignUp;
    Button btnLogin, btnGoogle;
    TextView forgotPass;
    // ImageView eye;
    FirebaseAuth firebaseAuth;

    SweetAlertDialog pDialog;

    //For facebook login
    CallbackManager callbackManager;
    LoginButton facebookBtn;
    static int t = 0;

    //Google Sign In
    GoogleApiClient mGoogleApiClient;
    public static final int RC_SIGN_IN = 9001;
    FirebaseAuth.AuthStateListener mAuthListener;

    ViewPager viewPager;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        FacebookSdk.sdkInitialize(getContext());
        callbackManager = CallbackManager.Factory.create();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        final View view = inflater.inflate(R.layout.fragment_login, container, false);

        viewPager = (ViewPager) getActivity().findViewById(R.id.container);

        Firebase.goOnline();
        Firebase.setAndroidContext(getContext());

        firebaseAuth = FirebaseAuth.getInstance();


        //Google Sign In Integration in the app
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(getContext()).enableAutoManage(LoginFrag.this.getActivity(), 0, new GoogleApiClient.OnConnectionFailedListener() {
            @Override
            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                Toast.makeText(getContext(), "Google Play Services Error", Toast.LENGTH_SHORT).show();
            }
        }).addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();


        //eye = (ImageView) view.findViewById(R.id.eye);
        loginEmail = (EditText) view.findViewById(R.id.et_email);
        loginPassword = (EditText) view.findViewById(R.id.et_password);
        btnLogin = (Button) view.findViewById(R.id.btn_login);
        btnGoogle = (Button) view.findViewById(R.id.btn_google);
        facebookBtn = (LoginButton) view.findViewById(R.id.btn_fb);
        forgotPass = (TextView) view.findViewById(R.id.forgot_pass);
        linkSignUp = (TextView) view.findViewById(R.id.link_signup);


        linkSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(1);
            }
        });

        //Adding click events on the login button
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
                userLogin();
            }
        });


        //Adding click events to the Google SignIn Button
        btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
                googleSignIn();
            }
        });


        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth auth = FirebaseAuth.getInstance();

                if (!TextUtils.isEmpty(loginEmail.getText())) {
                    String emailAddress = loginEmail.getText().toString();

                    auth.sendPasswordResetEmail(emailAddress).
                            addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getContext(), "Check your email for the password reset link", Toast.LENGTH_SHORT).show();
                                    } else
                                        Toast.makeText(getContext(), "Incorrect Email ID", Toast.LENGTH_SHORT).show();
                                }
                            });
                } else {
                    Toast.makeText(getContext(), "Enter Email Id First", Toast.LENGTH_SHORT).show();
                }
            }
        });


        facebookBtn.setFragment(this);

        facebookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
                facebookBtn.setReadPermissions("email", "public_profile");
                LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                        pDialog.setTitleText("Please Wait");
                        pDialog.setCancelable(false);
                        pDialog.show();
                        handleFacebookAccessToken(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {

                    }

                    @Override
                    public void onError(FacebookException error) {

                    }
                });
            }
        });

        return view;
    }

    //Custom Login function with EmailId and Password
    private void userLogin() {

        String email = loginEmail.getText().toString().trim();
        String password = loginPassword.getText().toString().trim();

        if (email.isEmpty()) {
            loginEmail.setError("Please Enter the Email Id!");
            return;
        }
        if (password.isEmpty()) {
            loginPassword.setError("Please Enter the Password!");
            return;
        }

        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Please Wait");
        pDialog.setCancelable(false);
        pDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password).
                addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        pDialog.dismiss();

                        if (task.isSuccessful()) {

                            if (MainActivity.REGISTERED_CALLED == 7990) {
                                startActivity(new Intent(getContext(), MyAccountPage.class));
                                Toast.makeText(getContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                            } else {
                                startActivity(new Intent(getContext(), MainActivity.class));
                                Toast.makeText(getContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                            }

                            getActivity().finish();
                        } else {
                            Toast.makeText(getContext(), "Login Unsuccessful", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    //Google SignIn Function
    private void googleSignIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    //Callback for Google SignIn
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                GoogleSignInAccount account = result.getSignInAccount();
                fireBaseAuthWithGoogle(account);
            }
        }
    }

    private void fireBaseAuthWithGoogle(GoogleSignInAccount acct) {

        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Please Wait");
        pDialog.setCancelable(false);
        pDialog.show();

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);

        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        pDialog.dismiss();
                        if (!task.isSuccessful()) {
                            Toast.makeText(getContext(), "Authentication Failed !", Toast.LENGTH_SHORT).show();
                        } else {
                            if (MainActivity.REGISTERED_CALLED == 7990) {
                                startActivity(new Intent(getContext(), MyAccountPage.class));
                                Toast.makeText(getContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                            } else {
                                startActivity(new Intent(getContext(), MainActivity.class));
                                Toast.makeText(getContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    public void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());

        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());
                        pDialog.dismiss();
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(getContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            t = 1;
//                            startActivity(new Intent(getContext(), MainActivity.class));
                            if (MainActivity.REGISTERED_CALLED == 7990) {
                                startActivity(new Intent(getContext(), MyAccountPage.class));
                                Toast.makeText(getContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                            } else {
                                startActivity(new Intent(getContext(), MainActivity.class));
                                Toast.makeText(getContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mGoogleApiClient.stopAutoManage(getActivity());
        mGoogleApiClient.disconnect();
        FacebookSdk.clearLoggingBehaviors();
    }

    @Override
    public void onStop() {

        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
        FacebookSdk.clearLoggingBehaviors();
        super.onStop();
    }
}
