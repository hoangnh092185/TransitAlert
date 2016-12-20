package com.example.guest.trimetalert;

import android.util.Log;

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

    public void getTriMetStop(Callback callback) {
        OkHttpClient client = new OkHttpClient.Builder()
                .build();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.TRANSIT_RAILS_STOP_URL).newBuilder();

        String url = urlBuilder.build().toString();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public void getTrimetCurrentLocation(String location, Callback callback){
        OkHttpClient client = new OkHttpClient.Builder()
                .build();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.TRANSIT_VEHICLES_URL).newBuilder();

        String url = urlBuilder.build().toString();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public ArrayList<TriMetStop> processResults(Response response){
        ArrayList<TriMetStop> stopArrays = new ArrayList<>();

        try {
            String jsonData = response.body().string();
            Log.d("jspn", jsonData);
            if(response.isSuccessful()){
                Log.d("this methods call: ", "call is successful");
            }
        }  catch (IOException e) {
            e.printStackTrace();
        }
        return stopArrays;

    }

}
