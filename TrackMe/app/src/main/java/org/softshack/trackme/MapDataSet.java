package org.softshack.trackme;

import com.google.maps.android.heatmaps.WeightedLatLng;

import java.util.ArrayList;

/**
 * Helper class - stores data sets and sources.
 */
public class MapDataSet {
    private ArrayList<WeightedLatLng> mDataset;
    private String mUrl;

    public MapDataSet(ArrayList<WeightedLatLng> dataSet, String url) {
        this.mDataset = dataSet;
        this.mUrl = url;
    }

    public ArrayList<WeightedLatLng> getData() {
        return mDataset;
    }

    public String getUrl() {
        return mUrl;
    }
}
