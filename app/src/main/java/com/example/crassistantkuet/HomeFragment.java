package com.example.crassistantkuet;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.common.graph.ImmutableNetwork;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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

    // The main code start from here ........

    FirebaseAuth mAuth ;
    DatabaseReference homeRoot;
    DatabaseReference userRoot;
    DatabaseReference priorityRoot;
    RecyclerView homeRecyclerView;
    View view;
    homeFragmentAdapterMainCommon homeMyAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);


        initialization();

        //saving data offline
        homeRoot.keepSynced(true);

        // add data on the firebase for home section
        //inPutData();

        // assign the recyclerView
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        linearLayoutManager.setStackFromEnd(true);
        homeRecyclerView.setLayoutManager(linearLayoutManager);
        FirebaseRecyclerOptions<homeModel> options =
                new FirebaseRecyclerOptions.Builder<homeModel>()
                        .setQuery(homeRoot, homeModel.class)
                        .build();

        homeMyAdapter= new homeFragmentAdapterMainCommon(options);
        homeRecyclerView.setAdapter(homeMyAdapter);


        return view;
    }

    private void initialization() {
        mAuth = FirebaseAuth.getInstance();
        homeRoot = FirebaseDatabase.getInstance().getReference().child("Home");
        userRoot = FirebaseDatabase.getInstance().getReference().child("Users");
        homeRecyclerView = view.findViewById(R.id.home_fragment_recycler_view);
    }

    private void inPutData() {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.UK);
        Date now = new Date();
        String time = formatter.format(now) ;

        //homeModel inputFile = new homeModel();
        //inputFile.homeSenderName = "Mehedi Hasan Emon ";
        //inputFile.homeSenderImage = "https://firebasestorage.googleapis.com/v0/b/cr-assistant.appspot.com/o/image2021_05_09_20_32_09.tar.gz?alt=media&token=62b531f4-8843-4b24-a9cb-2840d9531c62";
        //inputFile.homeTime = "12.12.12232";
        //inputFile.homeMessageText= "I am just for the test nothing else";
        //inputFile.homeMessageImage = "https://firebasestorage.googleapis.com/v0/b/cr-assistant.appspot.com/o/image2021_05_09_20_32_09.tar.gz?alt=media&token=62b531f4-8843-4b24-a9cb-2840d9531c62";
        //inputFile.homeLike = "2";
        //inputFile.homeDislike = "3";
        //HashMap<String,String> newOne = new HashMap<>();
        //newOne.put("emon","2");
        //newOne.put("Tripty","3");
        //inputFile.checkUserHashmap= newOne;


         //homeRoot.push().setValue(inputFile, new DatabaseReference.CompletionListener() {
         // @Override
         // public void onComplete(@Nullable @org.jetbrains.annotations.Nullable DatabaseError error, @NonNull @NotNull DatabaseReference ref) {
        //       //Toast.makeText(getContext(), "Successful", Toast.LENGTH_SHORT).show();
        //       }
        //  });

    }


    @Override
    public void onStart() {
        super.onStart();
        homeMyAdapter.startListening();
        
       // RetriveUserInfo();
    }

    private void RetriveUserInfo() {
        userRoot.child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
               // Toast.makeText(getActivity(),"Hello "+ snapshot.child("Name").getValue().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        homeMyAdapter.stopListening();
    }
}