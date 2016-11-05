package org.softshack.trackme;

import org.softshack.utils.obs.*;

import android.Manifest;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;

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

    private boolean mDefaultGradient = true;
    private boolean mDefaultRadius = true;
    private boolean mDefaultOpacity = true;

    /**
     * Alternative radius for convolution
     */
    private static final int ALT_HEATMAP_RADIUS = 10;

    /**
     * Alternative opacity of heatmap overlay
     */
    private static final double ALT_HEATMAP_OPACITY = 0.4;

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
     * Also maps to the URL of the data set for attribution
     */
    private HashMap<String, MapDataSet> mLists = new HashMap<String, MapDataSet>();

    private String tokenizedUrl = "http://138.68.151.94/clean/%s&%s/2010&25&10000";

    private LocationManager locationManager;

    private AsyncTask downloadMapDataTask;

    @Override
    protected void start() {
        try {

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
//            mMap.setOnCameraMoveStartedListener(this);
//            mMap.setOnCameraMoveListener(this);
//            mMap.setOnCameraMoveCanceledListener(this);


            if(checkPermission(Manifest.permission.ACCESS_FINE_LOCATION, android.os.Process.myPid(), android.os.Process.myUid()) == getPackageManager().PERMISSION_GRANTED) {
                GoogleMap themap = getMap();

                getMap().setMyLocationEnabled(true);

                // Getting LocationManager object from System Service LOCATION_SERVICE
                this.locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

                // Creating a criteria object to retrieve provider
                Criteria criteria = new Criteria();

                // Getting the name of the best provider
                String provider = locationManager.getBestProvider(criteria, true);

                // Getting Current Location
                Location location = locationManager.getLastKnownLocation(provider);

                getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 10));


            }





        } finally {
           //####
        }
    }

    public void changeRadius(View view) {
        if (mDefaultRadius) {
            mProvider.setRadius(ALT_HEATMAP_RADIUS);
        } else {
            mProvider.setRadius(HeatmapTileProvider.DEFAULT_RADIUS);
        }
        mOverlay.clearTileCache();
        mDefaultRadius = !mDefaultRadius;
    }

    public void changeGradient(View view) {
        if (mDefaultGradient) {
            mProvider.setGradient(ALT_HEATMAP_GRADIENT);
        } else {
            mProvider.setGradient(HeatmapTileProvider.DEFAULT_GRADIENT);
        }
        mOverlay.clearTileCache();
        mDefaultGradient = !mDefaultGradient;
    }

    public void changeOpacity(View view) {
        if (mDefaultOpacity) {
            mProvider.setOpacity(ALT_HEATMAP_OPACITY);
        } else {
            mProvider.setOpacity(HeatmapTileProvider.DEFAULT_OPACITY);
        }
        mOverlay.clearTileCache();
        mDefaultOpacity = !mDefaultOpacity;
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

    @Override
    public void onCameraIdle() {
        if(checkPermission(Manifest.permission.ACCESS_FINE_LOCATION, android.os.Process.myPid(), android.os.Process.myUid()) == getPackageManager().PERMISSION_GRANTED) {
            //####Toast.makeText(this, String.format("position changed: %s",changed++), Toast.LENGTH_LONG).show();
            LatLng currentPosition = getMap().getProjection().getVisibleRegion().latLngBounds.getCenter();
            String lookupUrl = String.format(tokenizedUrl,currentPosition.latitude,currentPosition.longitude);
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
            //####mLists.put(getString(R.string.title_activity_maps), new MapDataSet(readItems(R.raw.brighton),getString(R.string.title_activity_maps)));
            mLists.put(getString(R.string.title_activity_maps), new MapDataSet(readItems(result),getString(R.string.title_activity_maps)));

        } catch (JSONException e) {
            //####Toast.makeText(this, "Problem reading list of markers.", Toast.LENGTH_LONG).show();
        }

            mProvider = new HeatmapTileProvider.Builder().weightedData(
                    mLists.get(getString(R.string.title_activity_maps)).getData()).build();

            mProvider.setRadius(HeatmapTileProvider.DEFAULT_RADIUS);


            mOverlay = getMap().addTileOverlay(new TileOverlayOptions().tileProvider(mProvider));

            mOverlay.clearTileCache();
        }


    }
}

