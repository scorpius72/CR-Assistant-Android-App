package com.example.crassistantkuet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class allEventView extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference calenderRoot;
    calenderViewEventAdapterView eventAdapter;
    ArrayList<calenderEventAllDataModelClass> events;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_event_view);
        
        initialization();

        //recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        events = new ArrayList<>();
        eventAdapter = new calenderViewEventAdapterView(this,events);
        recyclerView.setAdapter(eventAdapter);

        calenderRoot.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot main: snapshot.getChildren()){

                    calenderEventAllDataModelClass eventClass = main.getValue(calenderEventAllDataModelClass.class);
                    events.add(eventClass);
                }

                eventAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    private void initialization() {
        recyclerView = findViewById(R.id.all_event_recycler_view);
        calenderRoot = FirebaseDatabase.getInstance().getReference().child("Calender");
    }
}