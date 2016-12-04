package org.softshack.trackme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import org.softshack.trackme.adapters.AndroidLogAdapter;
import org.softshack.trackme.adapters.ContextAdapter;
import org.softshack.trackme.adapters.GraphsAdapter;
import org.softshack.trackme.pocos.GraphsActivityControllerComponents;
import org.softshack.trackme.pocos.GraphsActivityViewComponents;
import org.softshack.utils.log.ILogger;

import java.util.ArrayList;


public class GraphsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        // Set up the logging.
        ILogger logger = new AndroidLogAdapter();

        // Create a data model.
        GraphsActivityModel activityModel = new GraphsActivityModel();

        Intent intent = getIntent();

        activityModel.setCurrentLatitude(intent.getDoubleExtra("latitude", 0));
        activityModel.setCurrentLongitude(intent.getDoubleExtra("longitude", 0));

        BarChart chart = (BarChart) findViewById(R.id.chart);
        chart.setFitBars(true);
        chart.animateY(5000);
        chart.setDrawGridBackground(false);
        Description description = new Description();
        description.setText("Average price of viewable area by year");
        chart.setDescription(description);

        XAxis xAxis = chart.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
        xAxis.setLabelRotationAngle(45f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new MyAxisValueFormatter());

        GraphsAdapter graphsAdapter = new GraphsAdapter(chart);

        GraphsActivityViewComponents graphsActivityViewComponents =
                new GraphsActivityViewComponents(
                        graphsAdapter,
                        activityModel);


        GraphsActivityView graphsActivityView = new GraphsActivityView(graphsActivityViewComponents);


        GraphsActivityControllerComponents graphsActivityControllerComponents =
                new GraphsActivityControllerComponents(
                        logger,
                        graphsActivityView,
                        activityModel,
                        new GraphDataProvider(
                                new TaskFactory(),
                                new ContextAdapter(getApplicationContext()),
                                new DataSetMapperFactory(),
                                new JSONFactory())
                );

        GraphsActivityController graphsActivityController =
                new GraphsActivityController(
                        graphsActivityControllerComponents);

        graphsActivityController.start();
    }
}

