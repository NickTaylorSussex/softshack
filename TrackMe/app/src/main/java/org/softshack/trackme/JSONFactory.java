package org.softshack.trackme;

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

    public ArrayList<WeightedLatLng> readItems(String data) throws JSONException {
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
}
