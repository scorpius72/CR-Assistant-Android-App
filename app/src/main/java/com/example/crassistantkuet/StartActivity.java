package com.example.crassistantkuet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class StartActivity extends AppCompatActivity {
    Toolbar appMainToolBar ;
    MeowBottomNavigation bottomNavigation;
    FirebaseAuth mAuth;
    DatabaseReference root = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        // this will start the background event
        startService(new Intent(this,backgroundCheck.class));


        initialization();
        appMainToolBar.setTitle("");
        setSupportActionBar(appMainToolBar);
        loadFragment(new HomeFragment());
        bottomNavigation.show(1,false);

        bottomNavigationController();

    }


    private void initialization() {
        appMainToolBar = (Toolbar) findViewById(R.id.app_main_toolbar);
        bottomNavigation = findViewById(R.id.bottom_navigation_view);

        bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.ic_home));
        bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.ic_group));
        bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.ic_calender));

        // for new count if anyone give new data on the firebase
        // add number into the group view navigation icon
        bottomNavigation.setCount(2,"15");

    }

    @Override
    protected void onStart() {
        super.onStart();
        verifyExistence();

    }

    private void verifyExistence() {
        mAuth = FirebaseAuth.getInstance();
        String currentUserID = mAuth.getCurrentUser().getUid();

        root.child("Users").child(currentUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if ((dataSnapshot.child("Name").exists()))
                {
                    //Toast.makeText(StartActivity.this, "Welcome", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    SendUserToUserProfileActivity();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void SendUserToUserProfileActivity() {
        Intent userIntent = new Intent(StartActivity.this, UserProfileSetting.class);
        startActivity(userIntent);
    }

    private void bottomNavigationController() {

        bottomNavigation.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                Fragment fragment = null;

                switch (model.getId()){
                    case 1 :
                        fragment = new HomeFragment();
                        break;
                    case 2 :
                        fragment = new GroupFragment();
                        break;
                    case 3:
                        fragment = new CalenderFragment();
                        break;

                }
                loadFragment(fragment);
                return null;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tool_bar_menu,menu);
        super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId()==R.id.menu_toolbar){
            loadFragment(new MenuFragment());
            bottomNavigation.show(0,false);

        }

        return true;
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_container,fragment)
                .commit();
    }
}