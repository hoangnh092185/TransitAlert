package com.example.guest.trimetalert.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.guest.trimetalert.R;
import com.example.guest.trimetalert.models.TriMet;
import com.example.guest.trimetalert.models.TriMetLocation;
import com.example.guest.trimetalert.services.TriMetService;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MapsActivity extends AppCompatActivity implements GoogleMap.OnMarkerClickListener, OnMapReadyCallback{
    private static final String TAG = MapsActivity.class.getSimpleName();

    private GoogleMap mMap;


    public ArrayList<TriMet> mTriMets = new ArrayList<>();
    public ArrayList<TriMetLocation> mTriMetLocations = new ArrayList<>();
    private final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

        findTransitStop();
        findTrimetLocation();
        doTheAutoRefresh();
    }

    private void findTransitStop(){
        final TriMetService triMetService = new TriMetService();
        triMetService.findStopLocation(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String jsonData = response.body().string();
                    if (response.isSuccessful()){
                        mTriMets =  triMetService.processResults(jsonData);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private void findTrimetLocation(){
        final TriMetService triMetLocation = new TriMetService();
        triMetLocation.findTriMetLocation(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String jsonData = response.body().string();
                    if (response.isSuccessful()){
                        mTriMetLocations =  triMetLocation.processLocations(jsonData);

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng latLng = new LatLng(45.5206011, -122.677683621);
        mMap.addMarker(new MarkerOptions().position(latLng).title("Epicodus"));

//        for(TriMet trimet : mTriMets){
//            LatLng stoplocations = new LatLng(trimet.getmLatitude(), trimet.getmLongitude());
//            String description = new String(trimet.getmDescription());
//            mMap.addMarker(new MarkerOptions()
//                    .position(stoplocations)
//                    .title(description)
//                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.bus)));
//        }
//
//        PolylineOptions rectOptions = new PolylineOptions().width(15).color(Color.YELLOW).geodesic(true);
//        for (int i = 0; i < mTriMets.size() -1; i++){
//            rectOptions.add(new LatLng(mTriMets.get(i).getmLatitude(), mTriMets.get(i).getmLongitude()));
//            Log.d("hehhehe", rectOptions.toString());
//        }
//        Polyline polyline = mMap.addPolyline(rectOptions);
//
//
//        for(TriMetLocation trimetLocation : mTriMetLocations){
//            LatLng trimetcurrentlocation = new LatLng(trimetLocation.getmLatitude(), trimetLocation.getmLongitude());
//            mMap.addMarker(new MarkerOptions()
//                    .position(trimetcurrentlocation)
//                    .title("Yellow Max Line")
//                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.train)));
//        }

//        if(mTriMets.size() > 5){
//            LatLng latLng = new LatLng(45.5206011, -122.677683621);
//            mMap.addMarker(new MarkerOptions().position(latLng).title("Epicodus"));
//
//            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//            CameraUpdate zoom=CameraUpdateFactory.zoomTo(14);
//            mMap.animateCamera(zoom);
//        }

        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        CameraUpdate zoom=CameraUpdateFactory.zoomTo(14);
        mMap.animateCamera(zoom);

        mMap.setOnCircleClickListener(new GoogleMap.OnCircleClickListener() {
            @Override
            public void onCircleClick(Circle circle) {
                int strokeColor = circle.getStrokeColor() ^ 0x00ffffff;
                circle.setStrokeColor(strokeColor);
            }
        });

    }

    private void doTheAutoRefresh() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                findTrimetLocation();
                doTheAutoRefresh();
                addMarker();
                Log.d("kejwklj ", "hehey it has been 10 seconds");
            }
        }, 30000);
    }


    @Override
    public boolean onMarkerClick(final Marker marker) {
        // Retrieve the data from the marker.
        Integer clickCount = (Integer) marker.getTag();
        // Check if a click count was set, then display the click count.
        if (clickCount != null) {
            clickCount = clickCount + 1;
            marker.setTag(clickCount);
            Toast.makeText(this,
                    marker.getTitle() +
                            " has been clicked " + clickCount + " times.",
                    Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    private void addMarker() {
        for(TriMet trimet : mTriMets){
            LatLng stoplocations = new LatLng(trimet.getmLatitude(), trimet.getmLongitude());
            String description = new String(trimet.getmDescription());
            mMap.addMarker(new MarkerOptions()
                    .position(stoplocations)
                    .title(description)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.bus)));
        }

        PolylineOptions rectOptions = new PolylineOptions().width(15).color(Color.YELLOW).geodesic(true);
        for (int i = 0; i < mTriMets.size() -1; i++){
            rectOptions.add(new LatLng(mTriMets.get(i).getmLatitude(), mTriMets.get(i).getmLongitude()));
            Log.d("hehhehe", rectOptions.toString());
        }
        Polyline polyline = mMap.addPolyline(rectOptions);


        for(TriMetLocation trimetLocation : mTriMetLocations){
            LatLng trimetcurrentlocation = new LatLng(trimetLocation.getmLatitude(), trimetLocation.getmLongitude());
            mMap.addMarker(new MarkerOptions()
                    .position(trimetcurrentlocation)
                    .title("Yellow Max Line")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.train)));
        }

    }
}


