package com.example.guest.trimetalert.services;

import android.util.Log;

import com.example.guest.trimetalert.Constants;
import com.example.guest.trimetalert.models.TriMet;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Guest on 12/20/16.
 */
public class TriMetService {
    public static final String TAG = TriMetService.class.getSimpleName();

    public void findStopLocation(Callback callback) {
        OkHttpClient client = new OkHttpClient.Builder().build();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.TRANSIT_RAILS_STOP_URL).newBuilder();
        String url = urlBuilder.build().toString();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
    }

//    public void getTrimetCurrentLocation(String location, Callback callback){
//        OkHttpClient client = new OkHttpClient.Builder()
//                .build();
//
//        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.TRANSIT_VEHICLES_URL).newBuilder();
//
//        String url = urlBuilder.build().toString();
//        Request request = new Request.Builder()
//                .url(url)
//                .build();
//        Call call = client.newCall(request);
//        call.enqueue(callback);
//    }
//
    public ArrayList<TriMet> processResults(Response response) {
        ArrayList<TriMet> trimets = new ArrayList<>();
        try {
            String jsonData = response.body().string();
            Log.d(TAG, jsonData);
            if(response.isSuccessful()){
                JSONObject trimetJSON = new JSONObject(jsonData);

            }else {
                Log.d(TAG, "call was unsuccessful");
            }
        }  catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e){
            e.printStackTrace();
        }
        return trimets;

    }

}
