package org.softshack.trackme;

import com.google.maps.android.heatmaps.WeightedLatLng;

import java.util.ArrayList;

public class DataSetMapperFactory {

    public DataSetMapper createDataSetMapper(ArrayList<WeightedLatLng> dataSet, String dataSetName) {
        return new DataSetMapper(dataSet, dataSetName);
    }
}
