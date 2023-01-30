package com.example.crassistantkuet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Random;
import java.text.SimpleDateFormat;

public class UserProfileSetting extends AppCompatActivity {

    private CircleImageView imageView;
    EditText profileName , profileDepartment , profileVarsity;
    Button uploadButton , submitButton ;
    final private int PICK_IMAGE = 1;
    Uri filePath;
    Bitmap bitmap;
    FirebaseAuth mAuth;
    ProgressDialog uploadSettingProgressDialogue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE); // omit the app bar
        setContentView(R.layout.activity_user_profile_setting);


        initialization();
        mAuth= FirebaseAuth.getInstance();

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageSelect();
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadToFirebase();
            }
        });
    }

    private void uploadToFirebase() {
        uploadSettingProgressDialogue = new ProgressDialog(UserProfileSetting.this);
        uploadSettingProgressDialogue.show();
        uploadSettingProgressDialogue.setContentView(R.layout.upload_progress_dialogue_design);
        uploadSettingProgressDialogue.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.UK);
        Date now = new Date();
        String fileName = formatter.format(now) + ".tar.gz";
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference uploader = storage.getReference("image"+fileName);

        if (filePath!=null) {
            uploader.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    uploader.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            FirebaseDatabase db = FirebaseDatabase.getInstance();
                            DatabaseReference root = db.getReference().child("Users");
                            HashMap<String, String> data = new HashMap<>();
                            data.put("Name", profileName.getText().toString().trim());
                            data.put("Department", profileDepartment.getText().toString().toUpperCase().trim());
                            data.put("Varsity", profileVarsity.getText().toString().toUpperCase().trim());
                            data.put("Image", uri.toString());

                            root.child(mAuth.getCurrentUser().getUid()).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    uploadSettingProgressDialogue.dismiss();
                                    callIntentStart();
                                    Toast.makeText(UserProfileSetting.this, "Profile Update successful", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                }
            });
        }
        else {
            FirebaseDatabase db = FirebaseDatabase.getInstance();
            DatabaseReference root = db.getReference().child("Users");
            HashMap<String, String> data = new HashMap<>();
            data.put("Name", profileName.getText().toString().trim());
            data.put("Department", profileDepartment.getText().toString().toUpperCase().trim());
            data.put("Varsity", profileVarsity.getText().toString().toUpperCase().trim());


            root.child(mAuth.getCurrentUser().getUid()).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    uploadSettingProgressDialogue.dismiss();
                    callIntentStart();
                    Toast.makeText(UserProfileSetting.this, "Profile Update successful", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void imageSelect() {
        Dexter.withActivity(UserProfileSetting.this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setType("image/*");
                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }



    private void initialization() {
        imageView = (CircleImageView) findViewById(R.id.profile_image_view);
        profileName = (EditText) findViewById(R.id.profile_name);
        profileDepartment = (EditText) findViewById(R.id.profile_department);
        profileVarsity = (EditText) findViewById(R.id.profile_varsity);
        uploadButton = (Button) findViewById(R.id.image_browse_button);
        submitButton = (Button) findViewById(R.id.submit_button);
    }
    private void callIntentStart() {
        Intent startintent = new Intent(UserProfileSetting.this,StartActivity.class);
        startintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(startintent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
            if (data == null) {
                Toast.makeText(this, "Please Try Again", Toast.LENGTH_SHORT).show();
                return;
            }
            filePath = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(filePath);
                bitmap = BitmapFactory.decodeStream(inputStream);
                imageView.setImageBitmap(bitmap);

            }catch (Exception e){
                Toast.makeText(this, "Please try again", Toast.LENGTH_SHORT).show();
            }

            super.onActivityResult(requestCode, resultCode, data);
        }
        else {
            filePath = null;
        }
    }
}