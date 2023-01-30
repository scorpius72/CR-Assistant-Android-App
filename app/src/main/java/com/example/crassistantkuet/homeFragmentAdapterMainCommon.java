package com.example.crassistantkuet;

import android.app.Activity;
import android.content.Intent;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.security.AccessController;

import de.hdodenhof.circleimageview.CircleImageView;

public class homeFragmentAdapterMainCommon extends FirebaseRecyclerAdapter<homeModel,homeFragmentAdapterMainCommon.myViewHolderHomeFragmentCommon> {


    public homeFragmentAdapterMainCommon(@NonNull @NotNull FirebaseRecyclerOptions<homeModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull @NotNull myViewHolderHomeFragmentCommon holder, int position, @NonNull @NotNull homeModel model) {

        //check the layout

        String main = model.getLayoutType();

        holder.homeFragmentCommonSenderName.setText(model.getHomeSenderName());
        holder.homeFragmentCommonSenderTime.setText(model.getHomeTime());
        holder.homeFragmentCommonMainText.setText(model.getMessageText());
        holder.homeFragmentCommonNumberOfLike.setText(model.getLike()+"  Like");
        holder.homeFragmentCommonNumberOfDislike.setText(model.getDislike()+"  Dislike");
        holder.homeFragmentCommonMainTextLayout1.setText(model.getMessageText());

        holder.homeFragmentCommonSenderName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), main, Toast.LENGTH_SHORT).show();
            }
        });


            Picasso.get().load(model.getMessageImage()).networkPolicy(NetworkPolicy.OFFLINE).into(holder.homeFragmentCommonMainImage, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError(Exception e) {
                    Picasso.get().load(model.getMessageImage()).into(holder.homeFragmentCommonMainImage);
                }
            });

        Picasso.get().load(model.getHomeSenderImage()).networkPolicy(NetworkPolicy.OFFLINE).into(holder.homeFragmentCommonSenderImage, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError(Exception e) {
                Picasso.get().load(model.getHomeSenderImage()).into(holder.homeFragmentCommonSenderImage);
            }
        });

        // for initializations
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        DatabaseReference rootUser = FirebaseDatabase.getInstance().getReference().child("Users");

        if (position== getSnapshots().size()-1 || getSnapshots().size()==0){
            holder.homeFragmentCommonFirstRow.setVisibility(View.VISIBLE);

            String name , profileImageOfUser;

            rootUser.child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    // Toast.makeText(getActivity(),"Hello "+ snapshot.child("Name").getValue().toString(), Toast.LENGTH_SHORT).show();
                     Log.d("user Name",snapshot.child("Name").getValue().toString());
                     Log.d("User image",snapshot.child("Image").getValue().toString());

                     Picasso.get().load(snapshot.child("Image").getValue().toString()).networkPolicy(NetworkPolicy.OFFLINE).into(holder.homeUserProfileImageFirstRow, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError(Exception e) {
                            Picasso.get().load(snapshot.child("Image").getValue().toString()).into(holder.homeUserProfileImageFirstRow);
                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });

        }
        if (main.compareTo("WithImage")==0){
            //holder.homePostCommonLayout1.setVisibility(View.VISIBLE);
            holder.homePostCommonLayoutWithImage.setVisibility(View.VISIBLE);
            
        }
        else {
            //holder.homePostCommonLayout0.setVisibility(View.VISIBLE);
            //holder.homePostCommonLayout0.setVisibility(View.VISIBLE);

            holder.homePostCommonLayoutWithOutImage.setVisibility(View.VISIBLE);
            //holder.homePostCommonLayoutWithImage.setVisibility(View.VISIBLE);

        }

        // on click on the whats on your mind

        holder.homeFragmentCommonFirstRowWhatsOnMind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity)v.getContext();
                Intent homeEditPostIntent = new Intent(activity,HomeEditPostActivity.class);
                activity.startActivity(homeEditPostIntent);
            }
        });

    }


    @NonNull
    @NotNull
    @Override
    public myViewHolderHomeFragmentCommon onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_fragment_common_row_layout,parent,false);
        return new myViewHolderHomeFragmentCommon(view);
    }

    public class myViewHolderHomeFragmentCommon extends RecyclerView.ViewHolder {
        CircleImageView homeFragmentCommonSenderImage , homeUserProfileImageFirstRow ;
        TextView homeFragmentCommonSenderName , homeFragmentCommonSenderTime , homeFragmentCommonMainText, homeFragmentCommonNumberOfLike, homeFragmentCommonNumberOfDislike;
        ImageView homeFragmentCommonMainImage;
        View homeFragmentCommonFirstRow;

        // Extra section

        View homePostCommonLayoutWithImage , homePostCommonLayoutWithOutImage;
        TextView homeFragmentCommonMainTextLayout1 , homeFragmentCommonFirstRowWhatsOnMind;

        public myViewHolderHomeFragmentCommon(@NonNull @NotNull View itemView) {
            super(itemView);

            homeFragmentCommonSenderImage = itemView.findViewById(R.id.home_fragment_common_sender_image);
            homeFragmentCommonSenderName = itemView.findViewById(R.id.home_fragment_common_sender_name);
            homeFragmentCommonSenderTime = itemView.findViewById(R.id.home_fragment_common_send_time);
            homeFragmentCommonMainText = itemView.findViewById(R.id.home_fragment_common_main_text);
            homeFragmentCommonMainImage = itemView.findViewById(R.id.home_fragment_common_main_image);
            homeFragmentCommonNumberOfLike = itemView.findViewById(R.id.home_fragment_common_number_of_like);
            homeFragmentCommonNumberOfDislike = itemView.findViewById(R.id.home_fragment_common_number_of_dislike);
            homeFragmentCommonFirstRow = itemView.findViewById(R.id.home_fragment_common_first_row);
            homeUserProfileImageFirstRow = itemView.findViewById(R.id.home_user_profile_image_1st_row);

            homePostCommonLayoutWithImage = itemView.findViewById(R.id.home_post_common_layout_with_image);
            homePostCommonLayoutWithOutImage = itemView.findViewById(R.id.home_post_common_layout_with_out_image);
            homeFragmentCommonMainTextLayout1 = itemView.findViewById(R.id.home_fragment_common_main_text_layout_1);
            homeFragmentCommonFirstRowWhatsOnMind = itemView.findViewById(R.id.home_fragment_common_first_row_whats_on_mind);
        }
    }
}
