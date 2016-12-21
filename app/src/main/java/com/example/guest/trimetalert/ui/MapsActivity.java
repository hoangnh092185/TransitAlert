package com.example.guest.trimetalert.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.guest.trimetalert.R;
import com.example.guest.trimetalert.models.TriMet;
import com.example.guest.trimetalert.services.TriMetService;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MapsActivity extends AppCompatActivity implements
        GoogleMap.OnMarkerClickListener,
        OnMapReadyCallback {

    public double pointLat[] = {45.5096158600378, 45.5115968952508, 45.5156328809934, 45.5189638279112, 45.5223232870211, 45.5244053705922, 45.527221999998, 45.5302795802814, 45.5396263320439, 45.5483082816098, 45.5552784153517, 45.563117436338, 45.5704161547004, 45.5776419218896, 45.5839271310172, 45.5961469262441, 45.6053543094761};
    public double pointLng[] = {-122.682550493399, -122.680380635272, -122.67855822485, -122.676755576899, -122.676429156497, -122.67651699998, -122.667879156583, -122.675617462578, -122.68092454074, -122.682282063481, -122.682149563751, -122.682061649868, -122.682004116327, -122.685943926137, -122.685562346844, -122.685706075831};



    private static final LatLng pSU01 = new LatLng( 45.5096158600378, -122.683593047781);
    private static final LatLng pSU02 = new LatLng(45.5115968952508, -122.682550493399);
    private static final LatLng pSU03= new LatLng(45.5156328809934,  -122.680380635272);
    private static final LatLng pSU04 = new LatLng(45.5189638279112,  -122.67855822485);
    private static final LatLng pSU05 = new LatLng(45.5223232870211,  -122.676755576899);
    private static final LatLng pSU06 = new LatLng(45.5244053705922, -122.676429156497);
    private static final LatLng pSU07 = new LatLng(45.527221999998,  -122.67651699998);
    private static final LatLng pSU08 = new LatLng(45.5302795802814,  -122.667879156583);
    private static final LatLng pSU09= new LatLng(45.5396263320439,  -122.675617462578);
    private static final LatLng pSU10 = new LatLng(45.5483082816098,  -122.68092454074);
    private static final LatLng pSU11 = new LatLng(45.5552784153517,  -122.682282063481);
    private static final LatLng pSU12 = new LatLng(45.563117436338,  -122.682149563751);
    private static final LatLng pSU13 = new LatLng(45.5704161547004,  -122.682061649868);
    private static final LatLng pSU14 = new LatLng(45.5776419218896,  -122.682004116327);
    private static final LatLng pSU15 = new LatLng(45.5839271310172,  -122.685943926137);
    private static final LatLng pSU16 = new LatLng(45.5961469262441,  -122.685562346844);
    private static final LatLng pSU17 = new LatLng(45.6053543094761,  -122.685706075831);

    private static final String TAG = MapsActivity.class.getSimpleName();
    private GoogleMap mMap;
    private Circle mCircle;


    public ArrayList<TriMet> mTriMets = new ArrayList<>();

    private Marker mPerth;
    private Marker mSydney;
    private Marker mBrisbane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        findTransitStop();
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
                        Log.v(TAG, jsonData);
                        mTriMets =  triMetService.processResults(response);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

      ArrayList<LatLng> locations = new ArrayList<>();
        locations.add(new LatLng( 45.5096158600378, -122.683593047781));
        locations.add(new LatLng(45.5115968952508, -122.682550493399));
        locations.add(new LatLng(45.5156328809934,  -122.680380635272));
        locations.add(new LatLng(45.5189638279112,  -122.67855822485));
        locations.add(new LatLng(45.5223232870211,  -122.676755576899));
        locations.add(new LatLng(45.5244053705922, -122.676429156497));
        locations.add(new LatLng(45.527221999998,  -122.67651699998));
        locations.add(new LatLng(45.5302795802814,  -122.667879156583));
        locations.add(new LatLng(45.5396263320439,  -122.675617462578));
        locations.add(new LatLng(45.5483082816098,  -122.68092454074));
        locations.add(new LatLng(45.5552784153517,  -122.682282063481));
        locations.add(new LatLng(45.563117436338,  -122.682149563751));
        locations.add(new LatLng(45.5704161547004,  -122.682061649868));
        locations.add(new LatLng(45.5776419218896,  -122.682004116327));
        locations.add(new LatLng(45.5839271310172,  -122.685943926137));
        locations.add(new LatLng(45.5961469262441,  -122.685562346844));
        locations.add(new LatLng(45.6053543094761,  -122.685706075831));

        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        LatLng latLng = new LatLng(45.5206011, -122.677683621);
        mMap.addMarker(new MarkerOptions().position(latLng).title("Epicodus"));

        for(LatLng location : locations){
            mMap.addMarker(new MarkerOptions()
                    .position(location)
                    .title("stop")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.train)));
        }

        PolylineOptions polylineOptions = new PolylineOptions();
        polylineOptions.addAll(locations);
            polylineOptions
                .width(15)
                .color(Color.YELLOW)
                .geodesic(true);

//        mPerth = mMap.addMarker(new MarkerOptions()
//                .position(pSU02)
//                .title("Stop1")
//                .icon(BitmapDescriptorFactory.fromResource(R.drawable.train)));
//        mPerth.setTag(0);
//
//        mSydney = mMap.addMarker(new MarkerOptions()
//                .position(pSU03)
//                .title("Stop2")
//                .icon(BitmapDescriptorFactory.fromResource(R.drawable.train)));
//        mSydney.setTag(0);
//
//        mBrisbane = mMap.addMarker(new MarkerOptions()
//                .position(pSU04)
//                .title("Stop3")
//                .icon(BitmapDescriptorFactory.fromResource(R.drawable.train)));
//        mBrisbane.setTag(0);


        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        CameraUpdate zoom=CameraUpdateFactory.zoomTo(15);
        mMap.animateCamera(zoom);
        // ... get a map.
        // Add a thin red line from London to New York.
//        Polyline northBoundLine = mMap.addPolyline(new PolylineOptions()
//                .add(new LatLng( 45.5096158600378, -122.683593047781)
//                        ,new LatLng(45.5115968952508, -122.682550493399)
//                        ,new LatLng(45.5156328809934,  -122.680380635272)
//                        ,new LatLng(45.5189638279112,  -122.67855822485)
//                        ,new LatLng(45.5223232870211,  -122.676755576899)
//                        ,new LatLng(45.5244053705922, -122.676429156497)
//                        ,new LatLng(45.527221999998,  -122.67651699998)
//                        ,new LatLng(45.5302795802814,  -122.667879156583)
//                        ,new LatLng(45.5396263320439,  -122.675617462578)
//                        ,new LatLng(45.5483082816098,  -122.68092454074)
//                        ,new LatLng(45.5552784153517,  -122.682282063481)
//                        ,new LatLng(45.563117436338,  -122.682149563751)
//                        ,new LatLng(45.5704161547004,  -122.682061649868)
//                        ,new LatLng(45.5776419218896,  -122.682004116327)
//                        ,new LatLng(45.5839271310172,  -122.685943926137)
//                        ,new LatLng(45.5961469262441,  -122.685562346844)
//                        ,new LatLng(45.6053543094761,  -122.685706075831)
//                )
//                .width(15)
//                .color(Color.YELLOW)
//                .geodesic(true));

        mCircle = mMap.addCircle(new CircleOptions()
                .center(new LatLng(45.5096158600378, -122.683593047781))
                .radius(15)
                .strokeWidth(10)
                .strokeColor(Color.GREEN)
                .fillColor(Color.argb(128, 255, 0, 0))
                .clickable(true));

        mMap.setOnCircleClickListener(new GoogleMap.OnCircleClickListener() {
            @Override
            public void onCircleClick(Circle circle) {
                // Flip the r, g and b components of the circle's
                // stroke color.
                int strokeColor = circle.getStrokeColor() ^ 0x00ffffff;
                circle.setStrokeColor(strokeColor);
            }
        });

    }

//    @Override
//    public void onMapPolyline (){
//        // ... get a map.
//        // Add a thin red line from London to New York.
//        Polyline northBoundLine = mMap.addPolyline(new PolylineOptions()
//                .add(new LatLng( 45.5096158600378, -122.683593047781)
//                        ,new LatLng(45.5115968952508, -122.682550493399)
//                        ,new LatLng(45.5156328809934,  -122.680380635272)
//                        ,new LatLng(45.5189638279112,  -122.67855822485)
//                        ,new LatLng(45.5223232870211,  -122.676755576899)
//                        ,new LatLng(45.5244053705922, -122.676429156497)
//                        ,new LatLng(45.527221999998,  -122.67651699998)
//                        ,new LatLng(45.5302795802814,  -122.667879156583)
//                        ,new LatLng(45.5396263320439,  -122.675617462578)
//                        ,new LatLng(45.5483082816098,  -122.68092454074)
//                        ,new LatLng(45.5552784153517,  -122.682282063481)
//                        ,new LatLng(45.563117436338,  -122.682149563751)
//                        ,new LatLng(45.5704161547004,  -122.682061649868)
//                        ,new LatLng(45.5776419218896,  -122.682004116327)
//                        ,new LatLng(45.5839271310172,  -122.685943926137)
//                        ,new LatLng(45.5961469262441,  -122.685562346844)
//                        ,new LatLng(45.6053543094761,  -122.685706075831)
//
//                )
//                .width(10)
//                .color(Color.YELLOW)
//                .geodesic(true));
//    }
//    @Override
//    public void onMapCircle(){
//        mCircle = mMap.addCircle(new CircleOptions()
//                .center(new LatLng(45.5096158600378, -122.683593047781))
//                .radius(10)
//                .strokeWidth(10)
//                .strokeColor(Color.GREEN)
//                .fillColor(Color.argb(128, 255, 0, 0))
//                .clickable(true));
//
//        mMap.setOnCircleClickListener(new GoogleMap.OnCircleClickListener() {
//            @Override
//            public void onCircleClick(Circle circle) {
//                // Flip the r, g and b components of the circle's
//                // stroke color.
//                int strokeColor = circle.getStrokeColor() ^ 0x00ffffff;
//                circle.setStrokeColor(strokeColor);
//            }
//        });
//    }

    /** Called when the user clicks a marker. */
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

        // Return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
        return false;
    }
}
