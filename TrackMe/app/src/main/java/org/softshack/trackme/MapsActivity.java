package org.softshack.trackme;

import org.softshack.utils.obs.*;

import android.Manifest;
import android.app.Dialog;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.maps.android.heatmaps.Gradient;
import com.google.maps.android.heatmaps.HeatmapTileProvider;
import com.google.maps.android.heatmaps.WeightedLatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class MapsActivity extends BaseDemoActivity {







//    public void changeYear(View view) {
//
//        final Dialog d = new Dialog(MapsActivity.this);
//        d.setTitle("NumberPicker");
//        d.setContentView(R.layout.dialog_yearpicker);
//        Button b1 = (Button) d.findViewById(R.id.button1);
//        Button b2 = (Button) d.findViewById(R.id.button2);
//        final NumberPicker np = (NumberPicker) d.findViewById(R.id.numberPicker1);
//        np.setMaxValue(2015);
//        np.setMinValue(1995);
//        np.setWrapSelectorWheel(true);
//        b1.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v) {
//                newYear = String.valueOf(np.getValue());
//                d.dismiss();
//                initiateDataFetch();
//            }
//        });
//        b2.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v) {
//                d.dismiss();
//            }
//        });
//        d.show();
//    }







}

