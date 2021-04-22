package com.example.whereisdrillfieldapp;

import android.location.Location;

public class DrillfieldCompass {
    double angle;
    Location currentLocation;
    Location drillfieldLocation;

    public DrillfieldCompass() {
        drillfieldLocation= new Location("");
        drillfieldLocation.setLatitude(37.227429);
        drillfieldLocation.setLongitude(-80.422230);
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public void setCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
    }

    public double getDrillfieldAngle() {
        if(currentLocation != null){
            double mapAngle = currentLocation.bearingTo(drillfieldLocation);
            if(mapAngle < 0){
                mapAngle += 360;
            }
            return mapAngle - angle;
        }
        return 0;
    }

    public double getDrillfieldDistance() {
        if(currentLocation != null){
            return currentLocation.distanceTo(drillfieldLocation);
        }
        return 0;
    }
}
