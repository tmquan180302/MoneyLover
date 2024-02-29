package com.poly.moneylover.ui.user;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.poly.moneylover.MainActivity;
import com.poly.moneylover.R;
import com.poly.moneylover.models.Request.ServerReqLogin;
import com.poly.moneylover.models.Request.ServerReqLoginGoogle;
import com.poly.moneylover.models.Response.ServerResToken;
import com.poly.moneylover.models.User;
import com.poly.moneylover.network.ApiService;
import com.poly.moneylover.utils.StringUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private ImageView imgBack;
    private TextInputLayout txtEmail, txtPass;
    private CheckBox cbRemember;
    private Button btnGoogle, btnLogin;

    private TextView tvSignUp, tvForgotPass;

    private GoogleSignInOptions sgo;

    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        setUpFunction();
    }

    private void setUpFunction() {
        sgo = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, sgo);

        onclick();
    }

    private void onclick() {
        imgBack.setOnClickListener(v -> {
            onBackPressed();
        });

        btnGoogle.setOnClickListener(v -> loginByGoogle());

        btnLogin.setOnClickListener(v -> login());

    }

    private void login() {
        String email = txtEmail.getEditText().getText().toString().trim();
        String passWord = txtPass.getEditText().getText().toString().trim();

        ServerReqLogin serverReqLogin = new ServerReqLogin();
        serverReqLogin.setEmail(email);
        serverReqLogin.setPassWord(passWord);

        ApiService.apiService.login(serverReqLogin).enqueue(new Callback<ServerResToken>() {
            @Override
            public void onResponse(Call<ServerResToken> call, Response<ServerResToken> response) {
                if (response.isSuccessful()) {

                    SharedPreferences sharedPreferences = getSharedPreferences("myPreferences", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    editor.remove("token");
                    editor.putString("token", response.body().getToken());
                    editor.apply();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    txtEmail.setError("Tên đăng nhập hoặc mật khẩu không chính xác");
                    txtPass.getEditText().setText("");
                }
            }

            @Override
            public void onFailure(Call<ServerResToken> call, Throwable t) {
                Log.d("TAG", "onFailure: " + t);
            }
        });
    }

    private void loginByGoogle() {
        try {
            Intent intent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(intent, 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                String email = account.getEmail();
                String passWord = StringUtils.generateRandomPassword();

                register(email, passWord);

                mGoogleSignInClient.signOut();

            } catch (ApiException e) {
                throw new RuntimeException(e);
            }
        }


    }

    private void register(String email, String passWord) {

        ServerReqLogin serverReqSignup = new ServerReqLogin();
        serverReqSignup.setEmail(email);
        serverReqSignup.setPassWord(passWord);


        ApiService.apiService.register(serverReqSignup).enqueue(new Callback<ServerResToken>() {
            @Override
            public void onResponse(Call<ServerResToken> call, Response<ServerResToken> response) {
                loginByEmail(email);
            }

            @Override
            public void onFailure(Call<ServerResToken> call, Throwable t) {

            }
        });
    }

    private void loginByEmail(String email) {

        ServerReqLoginGoogle serverReqLoginGoogle = new ServerReqLoginGoogle();
        serverReqLoginGoogle.setEmail(email);

        ApiService.apiService.loginByEmail(serverReqLoginGoogle).enqueue(new Callback<ServerResToken>() {
            @Override
            public void onResponse(Call<ServerResToken> call, Response<ServerResToken> response) {
                if (response.isSuccessful()) {
                    SharedPreferences sharedPreferences = getSharedPreferences("myPreferences", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear();
                    editor.putString("token", response.body().getToken());
                    editor.apply();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
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
        imgBack = findViewById(R.id.imgBack);
        txtEmail = findViewById(R.id.edt_Email_Layout);
        txtPass = findViewById(R.id.edt_Pass_Layout);
        cbRemember = findViewById(R.id.cbRemember);
        btnGoogle = findViewById(R.id.btn_Google);
        btnLogin = findViewById(R.id.btn_login);
        tvForgotPass = findViewById(R.id.tvForgotPass);
    }
}