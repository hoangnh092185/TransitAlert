package com.example.guest.trimetalert.models;

import org.parceler.Parcel;

/**
 * Created by Guest on 12/21/16.
 */
@Parcel
public class TriMetLocation {
    double mLatitude;
    double mLongitude;

    public TriMetLocation(){}

    public TriMetLocation(double mLatitude, double mLongitude) {
        this.mLatitude = mLatitude;
        this.mLongitude = mLongitude;
    }

    public double getmLatitude() {
        return mLatitude;
    }

    public double getmLongitude() {
        return mLongitude;
    }
}
