package com.poly.moneylover.ui.user;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RawRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.credentials.Credential;
import androidx.credentials.CredentialManager;
import androidx.credentials.CustomCredential;
import androidx.credentials.GetCredentialRequest;
import androidx.credentials.GetCredentialResponse;
import androidx.credentials.PasswordCredential;
import androidx.credentials.PublicKeyCredential;

import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.BeginSignInResult;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.identity.SignInCredential;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.android.material.textfield.TextInputLayout;
import com.poly.moneylover.MainActivity;
import com.poly.moneylover.R;
import com.poly.moneylover.models.Request.ServerReqLogin;
import com.poly.moneylover.models.Request.ServerReqLoginGoogle;
import com.poly.moneylover.models.Response.ServerResToken;
import com.poly.moneylover.network.ApiService;
import com.poly.moneylover.network.RetrofitClient;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    private ImageView imgBack;

    private TextInputLayout txtEmail, txtPass;

    private Button btnGoogle, btnSignUp;

    private TextView tvSignIn;

    private SignInClient oneTapClient;

    private BeginSignInRequest signInRequest;

    private static final int REQ_ONE_TAP = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();

        oneTapClient = Identity.getSignInClient(this);
        signInRequest = BeginSignInRequest.builder()

                .setPasswordRequestOptions(BeginSignInRequest.PasswordRequestOptions.builder()
                        .setSupported(true)
                        .build())

                .setGoogleIdTokenRequestOptions(BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                        .setSupported(true)
                        .setServerClientId("423787012578-c9u88i965g9ebgidspqiph1vgpcmr7v5.apps.googleusercontent.com") // TODO
                        .setFilterByAuthorizedAccounts(false)
                        .build())
                .setAutoSelectEnabled(true)
                .build();
        onclick();
    }


    private void onclick() {
        imgBack.setOnClickListener(v -> {
            onBackPressed();
        });

        btnGoogle.setOnClickListener(v -> loginByGoogle());

    }


    private void loginByGoogle() {

        oneTapClient.beginSignIn(signInRequest)
                .addOnSuccessListener(this, new OnSuccessListener<BeginSignInResult>() {
                    @Override

                    public void onSuccess(BeginSignInResult result) {

                        try {
                            startIntentSenderForResult(
                                    result.getPendingIntent().getIntentSender(), REQ_ONE_TAP,
                                    null, 0, 0, 0);
                        } catch (IntentSender.SendIntentException e) {
                            throw new RuntimeException(e);
                        }

                    }

                })

                .addOnFailureListener(this, new OnFailureListener() {

                    @Override

                    public void onFailure(@NonNull Exception e) {

                        // No saved credentials found. Launch the One Tap sign-up flow, or

                        // do nothing and continue presenting the signed-out UI.

                        Log.d(TAG, e.getLocalizedMessage());

                    }

                });

    }

    @Override

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case REQ_ONE_TAP:

                try {

                    SignInCredential credential = oneTapClient.getSignInCredentialFromIntent(data);

                    String idToken = credential.getGoogleIdToken();

                    String username = credential.getId();

                    String password = credential.getPassword();

                    tvSignIn.setText("Authentication done.\nUsername is " + username);

                    loginByEmail(username);


                    if (idToken != null) {

                        // Got an ID token from Google. Use it to authenticate

                        // with your backend.

                        Log.d(TAG, "Got ID token.");

                    } else if (password != null) {

                        // Got a saved username and password. Use them to authenticate

                        // with your backend.

                        Log.d(TAG, "Got password.");

                    }
                    oneTapClient.signOut();

                } catch (ApiException e) {
                    tvSignIn.setText(e.toString());
                }
                break;

        }
    }



    private void loginByEmail(String email) {

        ServerReqLoginGoogle serverReqLoginGoogle = new ServerReqLoginGoogle();
        serverReqLoginGoogle.setEmail(email);

        ApiService.api.loginByEmail(serverReqLoginGoogle).enqueue(new Callback<ServerResToken>() {
            @Override
            public void onResponse(Call<ServerResToken> call, Response<ServerResToken> response) {
                if (response.isSuccessful()) {
                    RetrofitClient.setAuthToken(response.body().getToken());
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                } else {

                }
            }

            @Override
            public void onFailure(Call<ServerResToken> call, Throwable t) {
                Log.d("TAG", "onFailure: " + t);
            }
        });
    }

    private void initView() {
        imgBack = findViewById(R.id.imgBackRegister);
        txtEmail = findViewById(R.id.edt_Email_Layout_Register);
        txtPass = findViewById(R.id.edt_Pass_Layout_Register);
        btnGoogle = findViewById(R.id.btn_GoogleRegister);
        btnSignUp = findViewById(R.id.btn_RegisterActivity);
        tvSignIn = findViewById(R.id.tvLogin);
    }
}