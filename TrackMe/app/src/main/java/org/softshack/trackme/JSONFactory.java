package org.softshack.trackme;

import com.github.mikephil.charting.data.BarEntry;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.heatmaps.WeightedLatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * creates a new array from JSON.
 */

public class JSONFactory {

    public ArrayList<WeightedLatLng> readMapItems(String data) throws JSONException {
        // Apparently java assertions are not recommended in Android. Therefore using conditional compilation.
        if (BuildConfig.DEBUG) {
            if (data == null) throw new AssertionError();
            if (data.isEmpty()) throw new AssertionError();
        }

        ArrayList<WeightedLatLng> list = new ArrayList<WeightedLatLng>();
        String json = new Scanner(data).useDelimiter("\\A").next();

        JSONArray array = new JSONArray(json);

        // Apparently java assertions are not recommended in Android. Therefore using conditional compilation.
        if (BuildConfig.DEBUG) {
            if (array == null) throw new AssertionError();
        }

        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);
            double lat = object.getDouble("latitude");
            double lng = object.getDouble("longitude");
            int weight = object.getInt("avgYearPostcodeNorm");
            list.add(new WeightedLatLng(new LatLng(lat, lng), weight));
        }

        return list;
    }

    public ArrayList<BarEntry> readGraphItems(String data) throws JSONException {
        // Apparently java assertions are not recommended in Android. Therefore using conditional compilation.
        if (BuildConfig.DEBUG) {
            if (data == null) throw new AssertionError();
            if (data.isEmpty()) throw new AssertionError();
        }

        ArrayList<BarEntry> list = new ArrayList<BarEntry>();
        String json = new Scanner(data).useDelimiter("\\A").next();

        JSONArray array = new JSONArray(json);

        // Apparently java assertions are not recommended in Android. Therefore using conditional compilation.
        if (BuildConfig.DEBUG) {
            if (array == null) throw new AssertionError();
        }

        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);
            int yearSold = object.getInt("yearSold");
            int averagePrice = object.getInt("averagePrice");
            list.add(new BarEntry(yearSold, averagePrice));
        }

        return list;
    }
}
