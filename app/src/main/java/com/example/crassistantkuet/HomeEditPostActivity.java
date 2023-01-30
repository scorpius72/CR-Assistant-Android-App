package com.example.crassistantkuet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.crassistantkuet.R.color.colorPrimary;

public class HomeEditPostActivity extends AppCompatActivity {

    Button homeEditPostImageButton , homeEditPostBackgroundButton , homeEditPostPostButton;
    View homeEditPostImageLayout , homeEditPostBakgroundLayout;
    CircleImageView homeEditPostUserProfileImage;
    TextView homeEditPostUserName;
    EditText homeEditPostMainMessageImageLayout , homeEditPostMainMessageBackgroundLayout;
    ImageView homeEditPostMainImageImageLayout;
    String userName , userProfileImage;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    DatabaseReference homeRoot = FirebaseDatabase.getInstance().getReference().child("Home");
    DatabaseReference userRoot = FirebaseDatabase.getInstance().getReference().child("Users");
    int i = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_edit_post);

        initialization();

        //userName = intent.getStringExtra("userName");
        //userProfileImage = intent.getStringExtra("userProfileImage");

        homeEditPostImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = 0;
                homeEditPostImageButton.setBackgroundColor(Color.BLUE);
                homeEditPostBackgroundButton.setBackgroundColor(Color.BLACK);

                homeEditPostBakgroundLayout.setVisibility(View.GONE);
                homeEditPostImageLayout.setVisibility(View.VISIBLE);
            }
        });
        homeEditPostBackgroundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = 1;
                homeEditPostImageButton.setBackgroundColor(Color.BLACK);
                homeEditPostBackgroundButton.setBackgroundColor(Color.BLUE);

                homeEditPostBakgroundLayout.setVisibility(View.VISIBLE);
                homeEditPostImageLayout.setVisibility(View.GONE);
            }
        });

        homeEditPostPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postThisPost();
            }
        });
    }

    private void initialization() {
        homeEditPostImageButton = findViewById(R.id.home_edit_post_image_button);
        homeEditPostBackgroundButton = findViewById(R.id.home_edit_post_backgroud_button);
        homeEditPostImageLayout = findViewById(R.id.home_edit_post_image_layout);
        homeEditPostBakgroundLayout = findViewById(R.id.home_edit_post_background_layout);
        homeEditPostPostButton = findViewById(R.id.home_edit_post_post_button);
        homeEditPostUserProfileImage = findViewById(R.id.home_edit_post_user_profile_image);
        homeEditPostUserName = findViewById(R.id.home_edit_post_user_name);
        homeEditPostMainMessageImageLayout = findViewById(R.id.home_edit_post_main_message_image_layout);
        homeEditPostMainMessageBackgroundLayout = findViewById(R.id.home_edit_post_main_message_background_layout);
        homeEditPostMainImageImageLayout = findViewById(R.id.home_edit_post_main_image_image_layout);

    }


    private void postThisPost() {
        if (i==0){
            String message = homeEditPostMainMessageImageLayout.getText().toString().trim();
            String messageImage = "https://firebasestorage.googleapis.com/v0/b/cr-assistant.appspot.com/o/image2021_05_09_20_32_09.tar.gz?alt=media&token=62b531f4-8843-4b24-a9cb-2840d9531c62";

            HashMap<String ,String> main = new HashMap<>();
            main.put("HomeSenderName",userName);
            main.put("HomeSenderImage",userProfileImage);
            main.put("HomeTime","653.8763.753");
            main.put("MessageText",message);
            main.put("MessageImage",messageImage);
            main.put("Like","1");
            main.put("Comment","1");
            main.put("Dislike","3");
            main.put("LayoutType","WithImage");

            homeRoot.push().setValue(main).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<Void> task) {
                    Toast.makeText(HomeEditPostActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else {
            String message = homeEditPostMainMessageBackgroundLayout.getText().toString().trim();
            HashMap<String ,String> main = new HashMap<>();
            main.put("HomeSenderName",userName);
            main.put("HomeSenderImage",userProfileImage);
            main.put("HomeTime","653.8763.753");
            main.put("MessageText",message);
            main.put("MessageImage","abc");
            main.put("Like","1");
            main.put("Comment","1");
            main.put("Dislike","3");
            main.put("LayoutType","WithOutImage");

            homeRoot.push().setValue(main).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<Void> task) {
                    Toast.makeText(HomeEditPostActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        userRoot.child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                userName = snapshot.child("Name").getValue().toString();
                userProfileImage = snapshot.child("Image").getValue().toString();

                if (userProfileImage!=null)
                    Picasso.get().load(userProfileImage).into(homeEditPostUserProfileImage);
                homeEditPostUserName.setText(userName);
                // Toast.makeText(getActivity(),"Hello "+ snapshot.child("Name").getValue().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }
}