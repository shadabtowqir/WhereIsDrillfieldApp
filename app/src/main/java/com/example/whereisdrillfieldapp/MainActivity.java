package com.example.whereisdrillfieldapp;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;


public class MainActivity extends AppCompatActivity {

    ImageView arrow;
    TextView distance;

    GPSManager gpsManager;
    DirectionManager directionManager;
    DrillfieldCompass drillfieldCompass;

    Notification notification1;
    Notification notification2;
    boolean isConnected;

    int requestCode = 0;
    public static final String CHANNEL_ID= "Channel1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        arrow = (ImageView) findViewById(R.id.arrow);
        distance = (TextView) findViewById(R.id.distance);

        gpsManager = new GPSManager(this);
        directionManager = new DirectionManager(this);
        drillfieldCompass = new DrillfieldCompass();

        isConnected= true;

        createNotificationChannel();
        buildNotifications();
    }

    @Override
    protected void onStart() {
        super.onStart();
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, requestCode);
        directionManager.register();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        gpsManager.register();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        directionManager.unregister();
        gpsManager.unregister();
    }

    public void updateGPSLocation(Location location) {
        networkNotification();
        drillfieldCompass.setCurrentLocation(location);
    }

    public void updateSensor(double angle) {
        drillfieldCompass.setAngle(angle);
        updateUI((float) drillfieldCompass.getDrillfieldAngle(), (int) drillfieldCompass.getDrillfieldDistance());
    }

    public void updateUI(float degree, int dist) {
        arrow.setRotation(degree);
        distance.setText("Distance: "+dist+" meters");
    }

    public void createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Network Notification", NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("Notifications regarding network issues");

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void buildNotifications(){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this, CHANNEL_ID)
                .setSmallIcon(R.drawable.arrow)
                .setContentTitle("WhereIsDrillfield")
                .setContentText("Network Connection Not Available")
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        notification1 = builder.build();

        NotificationCompat.Builder builder2 = new NotificationCompat.Builder(MainActivity.this, CHANNEL_ID)
                .setSmallIcon(R.drawable.arrow)
                .setContentTitle("WhereIsDrillfield")
                .setContentText("Network Connection Is Back Online")
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        notification2 = builder2.build();

    }


    public void networkNotification(){

        if(!isOnline() && isConnected){
            NotificationManagerCompat managerCompat= NotificationManagerCompat.from(MainActivity.this);
            managerCompat.notify(1, notification1);
            isConnected = false;
        }

        if(isOnline() && !isConnected){
            NotificationManagerCompat managerCompat= NotificationManagerCompat.from(MainActivity.this);
            managerCompat.notify(2, notification1);
        }
    }


    @SuppressWarnings("deprecation")
    public boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

}
