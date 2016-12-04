package org.softshack.trackme.adapters;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;

import org.softshack.trackme.DataSetGraphMapper;
import org.softshack.trackme.interfaces.ITrackGraph;

import java.util.HashMap;


public class GraphsAdapter implements ITrackGraph {

    private BarChart chart;

    public GraphsAdapter(BarChart chart) {
        this.chart = chart;
    }

    @Override
    public void clearGraph() {
        this.chart.clear();
    }

    @Override
    public void refresh() {
        this.chart.invalidate();
    }

    @Override
    public void buildGraph(HashMap<String, DataSetGraphMapper> graphs, String key) {

        BarDataSet dataSet = new BarDataSet(graphs.get(key).getData(), "Years");
        BarData data = new BarData(dataSet);
        data.setBarWidth(0.9f);
        chart.setData(data); // set the data and list of lables into chart<br />
    }
}
