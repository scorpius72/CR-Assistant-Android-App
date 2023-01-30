package com.example.crassistantkuet;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GroupFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GroupFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public GroupFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GroupFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GroupFragment newInstance(String param1, String param2) {
        GroupFragment fragment = new GroupFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    EditText editText ;
    Button sendButton;
    RecyclerView recyclerViewInGroup;
    View view;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    String senderUid= mAuth.getCurrentUser().getUid();
    DatabaseReference usersRoot = FirebaseDatabase.getInstance().getReference().child("Users");
    DatabaseReference groupRoot = FirebaseDatabase.getInstance().getReference().child("Group");
    String UserName ;
    String UserImage;
    groupFragmentAdapter groupAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =inflater.inflate(R.layout.fragment_group, container, false);

        Initialization();

        groupRoot.keepSynced(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerViewInGroup.setLayoutManager(linearLayoutManager);

        FirebaseRecyclerOptions<groupModel> optionsGroup =
                new FirebaseRecyclerOptions.Builder<groupModel>()
                        .setQuery(groupRoot, groupModel.class)
                        .build();


        groupAdapter = new groupFragmentAdapter(optionsGroup);
        recyclerViewInGroup.setAdapter(groupAdapter);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = editText.getText().toString().trim();
                if (s.length()==0){
                    Toast.makeText(getActivity(), "Please Enter something", Toast.LENGTH_SHORT).show();
                }
                else {
                    HashMap<String,String> map = new HashMap<>();

                    map.put("Name",UserName);
                    map.put("Image",UserImage);
                    map.put("UID",senderUid);
                    map.put("Message",s);
                    map.put("Time","22.33.32");

                    groupRoot.push().setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<Void> task) {
                            //Toast.makeText(getContext(), "send", Toast.LENGTH_SHORT).show();
                            editText.setText("");
                            groupAdapter.stopListening();
                            groupAdapter.startListening();
                        }
                    });
                }
            }
        });

        return view;
    }

    private void Initialization() {
        editText = view.findViewById(R.id.edit_text_in_group_fragment);
        sendButton = view.findViewById(R.id.send_button_in_grorp_fragment);
        recyclerViewInGroup = view.findViewById(R.id.recyclerView_in_group_fragment);

        usersRoot.child(senderUid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                UserName = snapshot.child("Name").getValue().toString();
                UserImage = snapshot.child("Image").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        groupAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        groupAdapter.stopListening();
    }
}