package com.example.whereisdrillfieldapp;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;


public class DirectionManager implements SensorEventListener {
    MainActivity mainActivity;

    SensorManager sensorManager;
    Sensor accSensor;
    Sensor magneticSensor;

    float[] accData;
    float[] magData;

    public DirectionManager(MainActivity mainActivity) {

    }

    public void register() {

    }

    public void unregister() {

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {


        if (accData != null && magData != null) {
            float R[] = new float[9];
            float I[] = new float[9];

            if (SensorManager.getRotationMatrix(R, I, accData, magData)) {
                float orientation[] = new float[3];
                SensorManager.getOrientation(R, orientation);
                double angle = orientation[0] * 360 / (2 * Math.PI);
                if (angle < 0) {
                    angle += 360;
                }

            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

}
