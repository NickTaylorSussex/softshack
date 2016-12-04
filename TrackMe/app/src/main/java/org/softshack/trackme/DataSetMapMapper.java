package org.softshack.trackme;

import com.google.maps.android.heatmaps.WeightedLatLng;

import org.softshack.trackme.interfaces.IDataSetMapper;

import java.util.ArrayList;

/**
 * Helper class - stores data sets and sources.
 */
public class DataSetMapMapper implements IDataSetMapper {
    private ArrayList<WeightedLatLng> dataSet;
    private String url;

    /**
     * Constructor.
     * @param dataSet
     * @param url
     */
    public DataSetMapMapper(ArrayList<WeightedLatLng> dataSet, String url) {
        this.dataSet = dataSet;
        this.url = url;
    }

    /**
     * @return dataSet
     */
    public ArrayList<WeightedLatLng> getData() {
        return this.dataSet;
    }

    /**
     * @return url
     */
    public String getUrl() {
        return url;
    }
}
