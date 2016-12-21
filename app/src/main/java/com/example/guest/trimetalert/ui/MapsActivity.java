package com.example.guest.trimetalert.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class MapsActivity extends AppCompatActivity implements GoogleMap.OnMarkerClickListener, OnMapReadyCallback {

    private static final String TAG = MapsActivity.class.getSimpleName();
    private GoogleMap mMap;
    private Circle mCircle;

    public ArrayList<TriMet> mTriMets = new ArrayList<>();
    public ArrayList<TriMetLocation> mTriMetLocations = new ArrayList<>();
    public ArrayList<LatLng> mlocations = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        findTransitStop();
        findTrimetLocation();
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
        // Add a marker in Sydney and move the camera
        LatLng latLng = new LatLng(45.5206011, -122.677683621);
        mMap.addMarker(new MarkerOptions().position(latLng).title("Epicodus"));

        for(TriMet trimet : mTriMets){
            LatLng place = new LatLng(trimet.getmLatitude(), trimet.getmLongitude());
            String description = new String(trimet.getmDescription());
            mlocations.add(new LatLng(trimet.getmLatitude(), trimet.getmLongitude()));
            mMap.addMarker(new MarkerOptions()
                    .position(place)
                    .title(description)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.bus)));
        }

        Polyline northBoundLine = mMap.addPolyline(new PolylineOptions()
                .add(new LatLng( 45.5096158600378, -122.683593047781)
                        ,new LatLng(45.5115968952508, -122.682550493399)
                        ,new LatLng(45.5156328809934,  -122.680380635272)
                        ,new LatLng(45.5189638279112,  -122.67855822485)
                        ,new LatLng(45.5223232870211,  -122.676755576899)
                        ,new LatLng(45.5244053705922, -122.676429156497)
                        ,new LatLng(45.527221999998,  -122.67651699998)
                        ,new LatLng(45.5302795802814,  -122.667879156583)
                        ,new LatLng(45.5396263320439,  -122.675617462578)
                        ,new LatLng(45.5483082816098,  -122.68092454074)
                        ,new LatLng(45.5552784153517,  -122.682282063481)
                        ,new LatLng(45.563117436338,  -122.682149563751)
                        ,new LatLng(45.5704161547004,  -122.682061649868)
                        ,new LatLng(45.5776419218896,  -122.682004116327)
                        ,new LatLng(45.5839271310172,  -122.685943926137)
                        ,new LatLng(45.5961469262441,  -122.685562346844)
                        ,new LatLng(45.6053543094761,  -122.685706075831)
                )
                .width(15)
                .color(Color.YELLOW)
                .geodesic(true));


        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        CameraUpdate zoom=CameraUpdateFactory.zoomTo(15);
        mMap.animateCamera(zoom);

        mMap.setOnCircleClickListener(new GoogleMap.OnCircleClickListener() {
            @Override
            public void onCircleClick(Circle circle) {
                int strokeColor = circle.getStrokeColor() ^ 0x00ffffff;
                circle.setStrokeColor(strokeColor);
            }
        });

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

}

//      for(TriMetLocation trimetLocation : mTriMetLocations){
//            LatLng place = new LatLng(trimetLocation.getmLatitude(), trimetLocation.getmLongitude());
//            mMap.addMarker(new MarkerOptions()
//                    .position(place)
//                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.train)));
//        }


//            PolylineOptions polylineOptions = new PolylineOptions();
//            polylineOptions.addAll(locations);
//            polylineOptions
//                    .width(15)
//                    .color(Color.YELLOW)
//                    .geodesic(true);


//        PolylineOptions polylineOptions = new PolylineOptions();
//        for(TriMet trimet : mTriMets){
//            LatLng place = new LatLng(trimet.getmLatitude(), trimet.getmLongitude());
//            polylineOptions.add(place);
//        }
//        polylineOptions
//                .width(15)
//                .color(Color.YELLOW)
//                .geodesic(true);


//        PolylineOptions polylineOptions = new PolylineOptions();
//        polylineOptions.addAll(mlocations);
//        polylineOptions
//                .width(15)
//                .color(Color.YELLOW)
//                .geodesic(true);


