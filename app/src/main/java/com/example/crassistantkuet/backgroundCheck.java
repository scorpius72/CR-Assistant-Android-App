package com.example.crassistantkuet;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class backgroundCheck extends Service {

    @Override
    public void onCreate() {
        super.onCreate();


        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        DatabaseReference homeRoot = FirebaseDatabase.getInstance().getReference().child("Home");
        homeRoot.addChildEventListener(new ChildEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

                String test = "new";
                DatabaseReference ans = snapshot.getRef().child("checkUserHashmap");
                HashMap<String,String> m = new HashMap<>();

                String userID = mAuth.getCurrentUser().getUid();

                boolean i =snapshot.child("checkUserHashmap").hasChild(userID);
                //Toast.makeText(backgroundCheck.this, ""+i, Toast.LENGTH_SHORT).show();

                if (i){

                }
                else {
                    String name = snapshot.child("HomeSenderName").getValue(String.class);
                    String Message = snapshot.child("MessageText").getValue(String.class);
                    ans.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                            for (DataSnapshot childSnapshot : snapshot.getChildren()){
                                m.put(childSnapshot.getKey(),"1");
                                Log.d("new test",childSnapshot.getKey());
                            }
                            m.put(userID,"1");
                            ans.setValue(m);
                            notificationDialog(name , Message);
                        }

                        @Override
                        public void onCancelled(@NonNull @NotNull DatabaseError error) {

                        }
                    });
                    //Toast.makeText(backgroundCheck.this, "", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onChildChanged(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull @NotNull DataSnapshot snapshot) {
                Toast.makeText(backgroundCheck.this, "New child remove", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onChildMoved(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }

    public boolean internetIsConnected() {
        try {
            String command = "ping -c 1 google.com";
            return (Runtime.getRuntime().exec(command).waitFor() == 0);
        } catch (Exception e) {
            return false;
        }
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    // notification

    HashMap<String , String> main = new HashMap<>();

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void notificationDialog(String name , String Message) {

        main.put(Message,name);
        int k =0;

        for (Map.Entry entry:main.entrySet()) {

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            String NOTIFICATION_CHANNEL_ID = "tutorialspoint_01";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                @SuppressLint("WrongConstant") NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "My Notifications", NotificationManager.IMPORTANCE_MAX);
                // Configure the notification channel.
                notificationChannel.setDescription("Sample Channel description");
                notificationChannel.enableLights(true);
                notificationChannel.setLightColor(Color.RED);
                notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
                notificationChannel.enableVibration(true);
                notificationManager.createNotificationChannel(notificationChannel);
            }

            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
            notificationBuilder.setAutoCancel(true)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setTicker("Tutorialspoint")
                    //.setPriority(Notification.PRIORITY_MAX)
                    .setContentTitle(entry.getKey() + " Posted in Group")
                    .setContentText(entry.getValue().toString())
                    .setContentInfo("Information");
            notificationManager.notify(k, notificationBuilder.build());
            k++;
        }
    }
}

