package com.sonlcr1.oneto25;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.firebase.auth.FirebaseAuth;

public class Activity_Login extends AppCompatActivity {
    ImageButton ImageButton_Google;
    ImageButton ImageButton_Kakao;
    ImageButton ImageButton_Naver;

    private FirebaseAuth mAuth = null;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ImageButton_Google = findViewById(R.id.ImageButton_Google);
        ImageButton_Kakao = findViewById(R.id.ImageButton_Kakao);
        ImageButton_Naver = findViewById(R.id.ImageButton_Naver);

        mAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    public void clickLogin(View view) {
        switch (view.getId()) {
            case R.id.ImageButton_Google:
                Toast.makeText(this, "구글로그인", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ImageButton_Kakao:
                Toast.makeText(this, "카카오로그인", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ImageButton_Naver:
                Toast.makeText(this, "네이버로그인", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}