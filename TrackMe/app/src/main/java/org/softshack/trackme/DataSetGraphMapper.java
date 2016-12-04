package org.softshack.trackme;

import com.github.mikephil.charting.data.BarEntry;

import org.softshack.trackme.interfaces.IDataSetMapper;

import java.util.ArrayList;


/**
 * Helper class - stores data sets and sources.
 */
public class DataSetGraphMapper implements IDataSetMapper {
    private ArrayList<BarEntry> dataSet;
    private String url;

    /**
     * Constructor.
     * @param dataSet
     * @param url
     */
    public DataSetGraphMapper(ArrayList<BarEntry> dataSet, String url) {
        this.dataSet = dataSet;
        this.url = url;
    }

    /**
     * @return dataSet
     */
    public ArrayList<BarEntry> getData() {
        return this.dataSet;
    }

    /**
     * @return url
     */
    public String getUrl() {
        return url;
    }
}