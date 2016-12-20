package com.example.guest.trimetalert;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Guest on 12/20/16.
 */
public class TriMetActivity extends AppCompatActivity {
    public static final String TAG = TriMetActivity.class.getSimpleName();
    public ArrayList<TriMetStop> mTriMetStops = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);


        getTrimets();

    }

    private void getTrimets(){
        final TriMetService triMetService = new TriMetService();
        triMetService.getTriMetStop(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String jsonData = response.body().string();
                    Log.v(TAG, jsonData);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

//                mTriMetStops = TriMetService.processResults(response);
//
//                TriMetActivity.this.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//
//                    }
//                });
//            }
        });
    }
}
