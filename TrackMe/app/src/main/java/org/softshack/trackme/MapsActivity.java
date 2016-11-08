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

public class MapsActivity extends BaseDemoActivity implements GoogleMap.OnCameraIdleListener {

        /**
     * Alternative heatmap gradient (blue -> red)
     * Copied from Javascript version
     */
    private static final int[] ALT_HEATMAP_GRADIENT_COLORS = {
            Color.argb(0, 0, 255, 255),// transparent
            Color.argb(255 / 3 * 2, 0, 255, 255),
            Color.rgb(0, 191, 255),
            Color.rgb(0, 0, 127),
            Color.rgb(255, 0, 0)
    };

    public static final float[] ALT_HEATMAP_GRADIENT_START_POINTS = {
            0.0f, 0.10f, 0.20f, 0.60f, 1.0f
    };

    private HeatmapTileProvider mProvider;
    private TileOverlay mOverlay;

    public static final Gradient ALT_HEATMAP_GRADIENT = new Gradient(ALT_HEATMAP_GRADIENT_COLORS,
            ALT_HEATMAP_GRADIENT_START_POINTS);

    /**
     * Maps name of data set to data (list of LatLngs)
     * Also activity_maps to the URL of the data set for attribution
     */
    private HashMap<String, MapDataSet> mLists = new HashMap<String, MapDataSet>();

    private String tokenizedUrl = "http://138.68.151.94/clean/%s&%s/%s&2&100";

    private LocationManager locationManager;

    private AsyncTask downloadMapDataTask;

    private String newYear="2015";

    @Override
    protected void start() {
        try {

            //####
//            Dialog dialog = new Dialog(MapsActivity.this,
//                    android.R.style.Theme_Translucent_NoTitleBar);
//            dialog.setContentView(R.layout.progress);
//            dialog.show();

            //####
            final DefaultEvent<EventArgs> myEvent = new DefaultEvent<EventArgs>();

            myEvent.addHandler(new EventHandler<EventArgs>() {
                @Override
                public void handle(Object sender, EventArgs args) {
                    System.out.println("The connection is lost");
                }
            });

            myEvent.fire(this, EventArgs.Empty);

            //####
            getMap().setOnCameraIdleListener(this);

            if(checkPermission(Manifest.permission.ACCESS_FINE_LOCATION, android.os.Process.myPid(), android.os.Process.myUid()) == getPackageManager().PERMISSION_GRANTED) {
                // Getting LocationManager object from System Service LOCATION_SERVICE
                this.locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

                getMap().setMyLocationEnabled(true);


                // Creating a criteria object to retrieve provider
                Criteria criteria = new Criteria();

                // Getting the name of the best provider
                String provider = locationManager.getBestProvider(criteria, true);

                // Getting Current Location
                Location location = locationManager.getLastKnownLocation(provider);

                if(location != null) {
                    getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 15));
                }


            }





        } finally {
           //####
        }
    }


    public void changeYear(View view) {

        final Dialog d = new Dialog(MapsActivity.this);
        d.setTitle("NumberPicker");
        d.setContentView(R.layout.dialog_yearpicker);
        Button b1 = (Button) d.findViewById(R.id.button1);
        Button b2 = (Button) d.findViewById(R.id.button2);
        final NumberPicker np = (NumberPicker) d.findViewById(R.id.numberPicker1);
        np.setMaxValue(2015);
        np.setMinValue(1995);
        np.setWrapSelectorWheel(true);
        b1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                newYear = String.valueOf(np.getValue());
                d.dismiss();
                initiateDataFetch();
            }
        });
        b2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });
        d.show();
    }


    private ArrayList<WeightedLatLng> readItems(int resource) throws JSONException {
        ArrayList<WeightedLatLng> list = new ArrayList<WeightedLatLng>();
        InputStream inputStream = getResources().openRawResource(resource);
        String json = new Scanner(inputStream).useDelimiter("\\A").next();
        JSONArray array = new JSONArray(json);
        double weight = 10;
        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);
            double lat = object.getDouble("latitude");
            double lng = object.getDouble("longitude");

            list.add(new WeightedLatLng(new LatLng(lat, lng), i==0 ? 10 : 1));
        }

        return list;
    }

    private ArrayList<WeightedLatLng> readItems(String data) throws JSONException {
        ArrayList<WeightedLatLng> list = new ArrayList<WeightedLatLng>();
        String json = new Scanner(data).useDelimiter("\\A").next();
        JSONArray array = new JSONArray(json);

        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);
            double lat = object.getDouble("latitude");
            double lng = object.getDouble("longitude");
            int weight = object.getInt("avgYearPostcodeNorm");
            list.add(new WeightedLatLng(new LatLng(lat, lng), weight));
        }

        return list;
    }

    private int changed = 0;

    public void initiateDataFetch(){
        Button b1 = (Button) this.findViewById(R.id.yearButton);
        newYear = String.valueOf(newYear);
        b1.setText(newYear);

        if(checkPermission(Manifest.permission.ACCESS_FINE_LOCATION, android.os.Process.myPid(), android.os.Process.myUid()) == getPackageManager().PERMISSION_GRANTED) {
            //####Toast.makeText(this, String.format("position changed: %s",changed++), Toast.LENGTH_LONG).show();
            LatLng currentPosition = getMap().getProjection().getVisibleRegion().latLngBounds.getCenter();
            String lookupUrl = String.format(tokenizedUrl,currentPosition.latitude,currentPosition.longitude,newYear);
            //####new DownloadMapDataTask().execute("https://api.github.com/users/nicktaylorsussex/repos");

            if(this.downloadMapDataTask != null && !this.downloadMapDataTask.isCancelled()){
                this.downloadMapDataTask.cancel(true);
            }

            if(mOverlay != null) {
                mOverlay.remove();
            }

            this.downloadMapDataTask = new DownloadMapDataTask().execute(lookupUrl);

        }
    }

    @Override
    public void onCameraIdle() {
        initiateDataFetch();
    }

    private class DownloadMapDataTask extends AsyncTask<String,Integer,String> {


        protected String doInBackground(String... urls) {
            String data = null;
            try {
                //####URL url = new URL("http://138.68.151.94/clean/50.830106&-0.129456");
                URL url = new URL(urls[0]); //####

                try {
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestProperty("Accept-Encoding", "identity");

                    try {
                        InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                        ByteArrayOutputStream result = new ByteArrayOutputStream();
                        byte[] buffer = new byte[1024];
                        int length;
                        while ((length = in.read(buffer)) != -1) {
                            result.write(buffer, 0, length);
                        }

                        data = result.toString("UTF-8");

                    } catch (Exception e) {
                        Exception a = e;
                    } finally {
                        urlConnection.disconnect();
                    }
                } catch (IOException e){
                    //####
                }


            }
            catch(MalformedURLException e){
                //####
            }

            return data;
        }

        @Override
        protected void onCancelled(){
            // Do nothing.
        }

        protected void onPostExecute(String result) {

            try{
                if(result != null) {
                    //####mLists.put(getString(R.string.title_activity_maps), new MapDataSet(readItems(R.raw.brighton),getString(R.string.title_activity_maps)));
                    mLists.put(getString(R.string.title_activity_maps), new MapDataSet(readItems(result), getString(R.string.title_activity_maps)));

                    mProvider = new HeatmapTileProvider.Builder().weightedData(
                            mLists.get(getString(R.string.title_activity_maps)).getData()).build();

                    mProvider.setRadius(HeatmapTileProvider.DEFAULT_RADIUS);


                    mOverlay = getMap().addTileOverlay(new TileOverlayOptions().tileProvider(mProvider));

                } else {
                    mLists.clear();
                }
            } catch (JSONException e) {
                //####Toast.makeText(this, "Problem reading list of markers.", Toast.LENGTH_LONG).show();

            }

            mOverlay.clearTileCache();
        }
    }
}

