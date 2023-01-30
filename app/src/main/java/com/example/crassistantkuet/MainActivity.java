package com.example.crassistantkuet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser Uid = mAuth.getCurrentUser();

       // startService(new Intent(this,backgroundCheck.class));


        //just for the test
        //callVerifyEmailActivity();
        //callHomeEditAcitivity();

        if (Uid == null){
           callIntentlogin();
        }
        else{
            startService(new Intent(this,backgroundCheck.class));
            callIntentStart();
        }
    }

    private void callHomeEditAcitivity() {
        Intent homeEditActivity = new Intent(this, HomeEditPostActivity.class);
        startActivity(homeEditActivity);
    }

    private void callVerifyEmailActivity() {
        Intent verifyEmailActivityIntent = new Intent(this,VerifyEmail.class);
        verifyEmailActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(verifyEmailActivityIntent);
    }

    private void callIntentProfile() {
        Intent profileIntent = new Intent(this,UserProfileSetting.class);
        startActivity(profileIntent);
    }

    private void callIntentStart() {
        Intent startintent = new Intent(MainActivity.this,StartActivity.class);
        startintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(startintent);
    }

    private void callIntentlogin() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        },3000);
    }
}