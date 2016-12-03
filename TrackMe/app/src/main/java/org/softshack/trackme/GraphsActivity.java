package org.softshack.trackme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.EntryXComparator;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;

public class GraphsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        BarChart lineChart = (BarChart) findViewById(R.id.chart);

        ArrayList<BarEntry> entries = new ArrayList<>();

        entries.add(new BarEntry(1995, 400000f));
        entries.add(new BarEntry(1996, 800000f));
        entries.add(new BarEntry(1997, 600000f));
        entries.add(new BarEntry(1998, 1200000f));
        entries.add(new BarEntry(1999, 1800000f));
        entries.add(new BarEntry(2000, 900000f));
        entries.add(new BarEntry(2001, 400000f));
        entries.add(new BarEntry(2002, 800000f));
        entries.add(new BarEntry(2003, 600000f));
        entries.add(new BarEntry(2004, 1200000f));
        entries.add(new BarEntry(2005,1800000f));
        entries.add(new BarEntry(2006,900000f));
        entries.add(new BarEntry(2007,400000f));
        entries.add(new BarEntry(2008,800000f));
        entries.add(new BarEntry(2009,600000f));
        entries.add(new BarEntry(2010,1200000f));
        entries.add(new BarEntry(2011,1800000f));
        entries.add(new BarEntry(2012,900000f));
       // Collections.sort(entries, new EntryXComparator());

        BarDataSet dataSet = new BarDataSet(entries, "Years");



        BarData data = new BarData(dataSet);
        data.setBarWidth(0.9f);
        lineChart.setData(data); // set the data and list of lables into chart<br />


        lineChart.setFitBars(true);
        lineChart.animateY(5000);
        lineChart.setDrawGridBackground(false);
        Description description = new Description();
        description.setText("Average price of viewable area by year");
        lineChart.setDescription(description);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
        xAxis.setLabelRotationAngle(45f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new MyAxisValueFormatter());

        lineChart.invalidate();
    }
}

