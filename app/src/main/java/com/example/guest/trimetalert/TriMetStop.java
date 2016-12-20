package com.example.guest.trimetalert;

/**
 * Created by Guest on 12/20/16.
 */
public class TriMetStop {
    private String mDesc;
    private float mLng;
    private float mLat;

    public TriMetStop(String mDesc, float mLng, float mLat) {
        this.mDesc = mDesc;
        this.mLng = mLng;
        this.mLat = mLat;
    }

    public String getmDesc() {
        return mDesc;
    }

    public void setmDesc(String mDesc) {
        this.mDesc = mDesc;
    }

    public float getmLng() {
        return mLng;
    }

    public void setmLng(float mLng) {
        this.mLng = mLng;
    }

    public float getmLat() {
        return mLat;
    }

    public void setmLat(float mLat) {
        this.mLat = mLat;
    }
}
