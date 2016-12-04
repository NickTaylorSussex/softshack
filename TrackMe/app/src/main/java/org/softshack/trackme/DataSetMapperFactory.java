package org.softshack.trackme;

import com.github.mikephil.charting.data.BarEntry;
import com.google.maps.android.heatmaps.WeightedLatLng;

import java.util.ArrayList;

public class DataSetMapperFactory {

    public DataSetMapMapper createDataSetMapMapper(ArrayList<WeightedLatLng> dataSet, String dataSetName) {
        return new DataSetMapMapper(dataSet, dataSetName);
    }

    public DataSetGraphMapper createDataSetGraphMapper(ArrayList<BarEntry> dataSet, String dataSetName) {
        return new DataSetGraphMapper(dataSet, dataSetName);
    }
}
