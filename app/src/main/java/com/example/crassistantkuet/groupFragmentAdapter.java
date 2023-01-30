package com.example.crassistantkuet;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import de.hdodenhof.circleimageview.CircleImageView;

public class groupFragmentAdapter extends FirebaseRecyclerAdapter<groupModel,groupFragmentAdapter.myRecyclerViewHolderCommon> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public groupFragmentAdapter(@NonNull @NotNull FirebaseRecyclerOptions<groupModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull @NotNull myRecyclerViewHolderCommon holder, int position, @NonNull @NotNull groupModel model) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String uid = mAuth.getCurrentUser().getUid();

        if (model.getUID().compareTo(uid)==0){
            holder.ReceiverGroupLayout.setVisibility(View.VISIBLE);

            if (model.getMessage().length() >=35){
                holder.ReceiverMessageMax.setVisibility(View.VISIBLE);
                holder.ReceiverMessageMax.setText(model.getMessage());
            }
            else {
                holder.ReceiverMessage.setVisibility(View.VISIBLE);
                holder.ReceiverMessage.setText(model.getMessage());
            }
        }
        else {
            holder.SenderGroupLayout.setVisibility(View.VISIBLE);
            holder.SenderName.setText(model.getName());

            if (model.getMessage().length() >=35){
                holder.SenderMessageMax.setVisibility(View.VISIBLE);
                holder.SenderMessageMax.setText(model.getMessage());
            }
            else {
                holder.SenderMessage.setVisibility(View.VISIBLE);
                holder.SenderMessage.setText(model.getMessage());
            }

            Picasso.get().load(model.getImage()).networkPolicy(NetworkPolicy.OFFLINE).into(holder.senderImage, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError(Exception e) {
                    Picasso.get().load(model.getImage()).into(holder.senderImage);
                }
            });
        }
    }

    @NonNull
    @NotNull
    @Override
    public myRecyclerViewHolderCommon onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_sender_receiver_message_layout,parent,false);
        return new myRecyclerViewHolderCommon(view);
    }

    public class myRecyclerViewHolderCommon extends RecyclerView.ViewHolder {

        CircleImageView senderImage ;
        TextView SenderName , ReceiverMessage , SenderMessage , ReceiverMessageMax , SenderMessageMax;
        View SenderGroupLayout , ReceiverGroupLayout;
        public myRecyclerViewHolderCommon(@NonNull @NotNull View itemView) {
            super(itemView);

            senderImage = itemView.findViewById(R.id.sender_profile_picture_in_group);
            SenderName = itemView.findViewById(R.id.sender_name_in_group);
            ReceiverMessage = itemView.findViewById(R.id.receiver_message_in_group);
            SenderMessage = itemView.findViewById(R.id.sender_message_in_group);

            ReceiverMessageMax = itemView.findViewById(R.id.receiver_message_in_group_max);
            SenderMessageMax = itemView.findViewById(R.id.sender_message_in_group_max);

            SenderGroupLayout = itemView.findViewById(R.id.sender_group_relative_layout);
            ReceiverGroupLayout = itemView.findViewById(R.id.receiver_group_relative_layout);
        }
    }
}
