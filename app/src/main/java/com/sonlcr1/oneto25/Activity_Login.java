package com.sonlcr1.oneto25;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import static com.sonlcr1.oneto25.Activity_Main.mAuth;

public class Activity_Login extends AppCompatActivity {
    ImageButton ImageButton_Google;
    ImageButton ImageButton_Kakao;
    ImageButton ImageButton_Naver;
    EditText EditText_password;
    EditText EditText_email;

    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        getSupportActionBar().hide();

        ImageButton_Google = findViewById(R.id.ImageButton_Google);
        ImageButton_Kakao = findViewById(R.id.ImageButton_Kakao);
        ImageButton_Naver = findViewById(R.id.ImageButton_Naver);
        EditText_email = findViewById(R.id.EditText_email);
        EditText_password = findViewById(R.id.EditText_password);


        EditText_password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                switch (i) {
                    case EditorInfo.IME_ACTION_DONE:
                        emailLogin();
                        break;
                }
                return true;
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
            }
        }
    }

    public void clickLogin(View view) {
        switch (view.getId()) {
            case R.id.ImageButton_Google:
//                Toast.makeText(this, "구글로그인", Toast.LENGTH_SHORT).show();
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
                break;
            case R.id.ImageButton_Kakao:
                Toast.makeText(this, "카카오로그인", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ImageButton_Naver:
                Toast.makeText(this, "네이버로그인", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void emailLogin() {
        if (!TextUtils.isEmpty(EditText_email.getText().toString()) && !TextUtils.isEmpty(EditText_password.getText().toString())) {
            mAuth.signInWithEmailAndPassword(EditText_email.getText().toString(), EditText_password.getText().toString())
                    .addOnCompleteListener((task) -> {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(Activity_Login.this, Activity_Main.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        } else {
                            Toast.makeText(this, "이메일 또는 비밀번호를 확인하세요.", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(this, "이메일 및 비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show();
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(Activity_Login.this, "로그인성공", Toast.LENGTH_SHORT).show();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(Activity_Login.this, "로그인실패", Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    private void updateUI(FirebaseUser user) { //update ui code here
        if (user != null) {
            Intent intent = new Intent(this, Activity_Main.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
    }

    public void clickBtn(View view) {
        switch (view.getId()) {
            case R.id.Button_Login:
                emailLogin();
                break;

            case R.id.Button_Join:
                startActivity(new Intent(Activity_Login.this, Activity_Signup.class));
                break;
        }
    }
}