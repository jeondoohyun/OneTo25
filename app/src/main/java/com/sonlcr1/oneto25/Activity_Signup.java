package com.sonlcr1.oneto25;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import static com.sonlcr1.oneto25.Activity_Main.mAuth;

public class Activity_Signup extends AppCompatActivity {
    EditText EditText_email;
    EditText EditText_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__signup);

        EditText_email = findViewById(R.id.EditText_email);
        EditText_password = findViewById(R.id.EditText_password);
        EditText_password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                switch (i) {
                    case EditorInfo.IME_ACTION_DONE:
                        emailSignup();
                        break;
                }
                return true;
            }
        });
    }

    public void emailSignup() {
        if (!TextUtils.isEmpty(EditText_email.getText().toString()) && !TextUtils.isEmpty(EditText_password.getText().toString())) {
            mAuth.createUserWithEmailAndPassword(EditText_email.getText().toString(), EditText_password.getText().toString()).addOnCompleteListener((task) -> {
                if (task.isSuccessful()) {
                    emailLogin();
                    // 아이디가 생성이 완료 되었을때
                } else if (!task.getException().getMessage().isEmpty()) {
                    // 아이디가 생성되지 않았을때
                    String exception = task.getException().toString();
//                    String aa = "com.google.firebase.auth.FirebaseAuthWeakPasswordException: The given password is invalid. [ Password should be at least 6 characters ]";
                    String aa = "FirebaseAuthWeakPasswordException";
                    Log.e("exception", exception.equals(aa)+"");
                    if (exception.contains("FirebaseAuthWeakPasswordException")) {
                        Log.e("예외","진입");
                        Toast.makeText(this, "비밀번호를 6자리 이상으로 설정하세요", Toast.LENGTH_SHORT).show();
                    } else if (exception.contains("FirebaseAuthUserCollisionException")) {
                        Toast.makeText(this, "이메일이 이미 존재합니다.", Toast.LENGTH_SHORT).show();
                    }
                    Log.e("Singup",task.getException().toString());
                }
            });
        } else {

        }
    }

    public void emailLogin() {
        if ( !TextUtils.isEmpty(EditText_email.getText().toString()) && !TextUtils.isEmpty(EditText_password.getText().toString())) {
            mAuth.signInWithEmailAndPassword(EditText_email.getText().toString(), EditText_password.getText().toString())
                    .addOnCompleteListener((task) -> {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(Activity_Signup.this, Activity_Main.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        } else {
                            Toast.makeText(this, "이메일 또는 비밀번호를 확인하세요.", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(this, "이메일 또는 비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show();
        }
    }

    public void clickBtn(View view) {
        switch (view.getId()) {
            case R.id.Button_Create:
                emailSignup();
                break;
        }
    }
}