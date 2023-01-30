package com.example.crassistantkuet;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class VerifyEmail extends AppCompatActivity {

    TextInputEditText verifyEmailEditText;
    EditText verifyEmailCode;
    Button verifyEmailAddressButton , verifyCodeButton;
    View verifyEmailLayout , verifyCodeLayout;

    String user= "mh.emon243530@gmail.com";
    String password = "1312756401";
    String sb, bd, rp;

    GMailSender sender;
    int code;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_email);

        Initialization();

        sender = new GMailSender(user, password);

        verifyEmailAddressButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                String main = verifyEmailEditText.getText().toString();
                if (main.contains("@stud.kuet.ac.bd")){

                    code = (int) Math.floor(Math.random() * 2000);
                    code = 123;

                    sb = "Welcome to CR Assistant";
                    bd = "Congratulation! This is your code: "+code+"Please Enter this code in the verify code section and Spacial greetings from MD.Mehedi Hasan Emon";
                    rp = main.trim();

                    new MyAsyncClass().execute();

                }
                else {
                    Toast.makeText(VerifyEmail.this, "Please Enter the Kuet Email Please", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void Initialization() {
        verifyEmailEditText = findViewById(R.id.verify_Email_address_edit_text);
        verifyEmailAddressButton = findViewById(R.id.verify_email_address_button);

        verifyEmailCode = findViewById(R.id.verify_code);
        verifyCodeButton = findViewById(R.id.verify_code_button);

        verifyEmailLayout = findViewById(R.id.send_email_layout);
        verifyCodeLayout = findViewById(R.id.verify_code_layout);
    }

    class MyAsyncClass extends AsyncTask<Void, Void, Void> {

        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            pDialog = new ProgressDialog(VerifyEmail.this);
            pDialog.setMessage("Please wait...");
            pDialog.show();

        }

        @Override

        protected Void doInBackground(Void... mApi) {
            try {

                // Add subject, Body, your mail Id, and receiver mail Id.
                sender.sendMail(sb, bd, user, rp);
                Log.d("send", "done");
            }
            catch (Exception ex) {
                Log.d("exceptionsending", ex.toString());
            }
            return null;
        }

        @Override

        protected void onPostExecute(Void result) {

            super.onPostExecute(result);
            pDialog.cancel();

            Toast.makeText(VerifyEmail.this, "mail send", Toast.LENGTH_SHORT).show();

            verifyEmailLayout.setVisibility(View.GONE);
            verifyCodeLayout.setVisibility(View.VISIBLE);

            verifyCodeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String codeText = verifyEmailCode.getText().toString().trim();
                    int codeInt = Integer.parseInt(codeText);

                    if (codeInt==code){
                        Toast.makeText(VerifyEmail.this, "Welcome", Toast.LENGTH_SHORT).show();
                        RegisterActivityIntent();
                    }
                    else {
                        Toast.makeText(VerifyEmail.this, "Please try again..", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }

    private void RegisterActivityIntent() {
        Intent registerIntent = new Intent(this, RegisterActivity.class);
        registerIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(registerIntent);
    }

}