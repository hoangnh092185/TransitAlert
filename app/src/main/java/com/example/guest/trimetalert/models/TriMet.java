package com.example.guest.trimetalert.models;

import org.parceler.Parcel;

/**
 * Created by Guest on 12/20/16.
 */
@Parcel
public class TriMet {
     String mDescription;
     double mLongitude;
     double mLatitude;

    public TriMet(){}

    public TriMet(String mDescription, double mLongitude, double mLatitude) {
        this.mDescription = mDescription;
        this.mLongitude = mLongitude;
        this.mLatitude = mLatitude;
    }

    public String getmDescription() {
        return mDescription;
    }

    public double getmLongitude() {
        return mLongitude;
    }

    public double getmLatitude() {
        return mLatitude;
    }
}
