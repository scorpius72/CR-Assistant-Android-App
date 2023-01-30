package com.example.crassistantkuet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private TextView needNewAccountTextView;
    private EditText loginEmailAddress , loginPassword;
    private Button loginButton;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initialization();

        needNewAccountTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyEmailActivityIntent();
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginProcessFirebase();
            }
        });
    }

    private void loginProcessFirebase() {
        mAuth = FirebaseAuth.getInstance();
        String emailAddress = loginEmailAddress.getText().toString().trim();
        String password = loginPassword.getText().toString().trim();
        if (emailAddress.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please Give your Email Address and Password Correctly", Toast.LENGTH_SHORT).show();
        }
        else {

            mAuth.signInWithEmailAndPassword(emailAddress, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                StartActivityIntent();
                            }
                            else {
                                Toast.makeText(LoginActivity.this, "Please Try Again", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    private void StartActivityIntent() {
        Intent startActivityIntent = new Intent(this,StartActivity.class);
        startActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(startActivityIntent);
    }

    private void initialization() {
        needNewAccountTextView = (TextView) findViewById(R.id.need_new_account);
        loginEmailAddress = (EditText) findViewById(R.id.login_email_text);
        loginPassword = (EditText) findViewById(R.id.login_password_text);
        loginButton = (Button) findViewById(R.id.login_button);
    }
    private void RegisterActivityIntent() {
        Intent registerIntent = new Intent(this, RegisterActivity.class);
        registerIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(registerIntent);
    }

    private void verifyEmailActivityIntent(){
        Intent verifyEmailIntent = new Intent(this,VerifyEmail.class);
        startActivity(verifyEmailIntent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        stopService(new Intent(this,backgroundCheck.class));
    }

    @Override
    protected void onStop() {
        super.onStop();
       // startService(new Intent(this,backgroundCheck.class));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //startService(new Intent(this,backgroundCheck.class));

    }
}