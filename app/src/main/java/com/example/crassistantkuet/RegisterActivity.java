package com.example.crassistantkuet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private TextView alreadyHaveAnAccountTextView;
    private EditText registerEmailText , registerPasswordText ;
    private Button registerButton;
    private ProgressBar registerProgressBar;
    public FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initialization();

        alreadyHaveAnAccountTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivityIntent();
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerNewAccountNow();
            }
        });

    }


    private void initialization() {
        alreadyHaveAnAccountTextView = (TextView) findViewById(R.id.already_have_an_account);
        registerEmailText = (EditText) findViewById(R.id.register_email_text);
        registerPasswordText = (EditText) findViewById(R.id.register_password_text);
        registerButton = (Button) findViewById(R.id.register_button);
        registerProgressBar = (ProgressBar) findViewById(R.id.register_progressBar);
    }

    private void registerNewAccountNow() {
        registerProgressBar.setVisibility(View.VISIBLE);
        String emailAddress = registerEmailText.getText().toString().trim();
        String password = registerPasswordText.getText().toString().trim();
        if (emailAddress.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please Give your Email Address and Password Correctly", Toast.LENGTH_SHORT).show();
        }
        else {
            mAuth = FirebaseAuth.getInstance();

            mAuth.createUserWithEmailAndPassword(emailAddress,password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(RegisterActivity.this, "Register Successfully", Toast.LENGTH_SHORT).show();
                                userProfileActivityIntent();
                            }
                            else{
                                Toast.makeText(RegisterActivity.this, "Please Try Again", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

        registerProgressBar.setVisibility(View.GONE);
    }

    private void userProfileActivityIntent() {
        Intent userProfile = new Intent(this,UserProfileSetting.class);
        userProfile.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(userProfile);
    }

    private void LoginActivityIntent() {
        Intent loginIntent = new Intent(this,LoginActivity.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);
    }
}