package com.example.guest.trimetalert.services;

import com.example.guest.trimetalert.Constants;
import com.example.guest.trimetalert.models.TriMet;
import com.example.guest.trimetalert.models.TriMetLocation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by Guest on 12/21/16.
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
    public void findTriMetLocation(Callback callback) {
        OkHttpClient client = new OkHttpClient.Builder().build();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.TRANSIT_VEHICLES_URL).newBuilder();
        String url = urlBuilder.build().toString();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public ArrayList<TriMet> processResults(String response) {
        ArrayList<TriMet> trimets = new ArrayList<>();

        try {
            JSONObject trimetJSON = new JSONObject(response);
            JSONObject resultsJSON = trimetJSON.getJSONObject("resultSet");
            JSONObject routeJSON = resultsJSON.getJSONArray("route").getJSONObject(0);
            JSONArray dirArray = routeJSON.getJSONArray("dir");
            for(int i=0; i<dirArray.length(); i++){
                JSONObject dirJSON = dirArray.getJSONObject(i);
                JSONArray stopArray = dirJSON.getJSONArray("stop");
                for(int j=0; j< stopArray.length(); j++){
                    JSONObject stopJSON = stopArray.getJSONObject(j);
                    String direction = stopJSON.getString("dir");
                    String description = stopJSON.getString("desc");
                    double longitude = stopJSON.getDouble("lng");
                    double latitude = stopJSON.getDouble("lat");
                    TriMet trimet = new TriMet(direction, description,latitude, longitude);
                    trimets.add(trimet);
                }
            }

        } catch (JSONException e){
            e.printStackTrace();
        }
        return trimets;

    }

    public ArrayList<TriMetLocation> processLocations(String response) {
        ArrayList<TriMetLocation> trimetLocations = new ArrayList<>();
        try {
            JSONObject trimetJSON = new JSONObject(response);
            JSONObject resultsJSON = trimetJSON.getJSONObject("resultSet");
            JSONArray vehicleArray = resultsJSON.getJSONArray("vehicle");
            for(int i=0; i<vehicleArray.length(); i++){
                JSONObject vehicleJSON = vehicleArray.getJSONObject(i);
                double latitude = vehicleJSON.getDouble("latitude");
                double longitude = vehicleJSON.getDouble("longitude");
                String signMessage = vehicleJSON.getString("signMessage");
                TriMetLocation trimetLocation = new TriMetLocation(latitude, longitude, signMessage);
                trimetLocations.add(trimetLocation);
            }
        } catch (JSONException e){
            e.printStackTrace();
        }
        return trimetLocations;
    }
}


