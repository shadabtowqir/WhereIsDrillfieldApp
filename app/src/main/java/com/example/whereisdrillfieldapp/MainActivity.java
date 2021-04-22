package com.example.whereisdrillfieldapp;

import android.Manifest;
import android.app.Notification;
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
    }

    @Override
    protected void onStart() {
        super.onStart();
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, requestCode);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    public void updateGPSLocation(Location location) {

    }

    public void updateSensor(double angle) {

    }

    public void updateUI(float degree, int dist) {

    }

    public void createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

        }
    }

    public void buildNotifications(){

    }


    public void networkNotification(){

    }

    @SuppressWarnings("deprecation")
    public boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

}
