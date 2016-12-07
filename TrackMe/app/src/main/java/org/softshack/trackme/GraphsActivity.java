package org.softshack.trackme;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;

import org.softshack.trackme.adapters.AndroidLogAdapter;
import org.softshack.trackme.adapters.ContextAdapter;
import org.softshack.trackme.adapters.GraphsAdapter;
import org.softshack.trackme.pocos.GraphsActivityControllerComponents;
import org.softshack.trackme.pocos.GraphsActivityViewComponents;
import org.softshack.utils.log.ILogger;
import org.softshack.utils.obs.EventArgs;
import org.softshack.utils.obs.EventHandler;

public class GraphsActivity extends AppCompatActivity {

    ProgressDialog progrssDialog;

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

        logger.LogDebug("onCreate", "Attempting create of BarChart");

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

        logger.LogDebug("onCreate", "Attempting create of GraphsAdapter");

        GraphsAdapter graphsAdapter = new GraphsAdapter(chart);

        logger.LogDebug("onCreate", "Attempting create of GraphsActivityViewComponents");

        GraphsActivityViewComponents graphsActivityViewComponents =
                new GraphsActivityViewComponents(
                        logger,
                        graphsAdapter,
                        activityModel);

        logger.LogDebug("onCreate", "Attempting create of GraphsActivityView");

        GraphsActivityView graphsActivityView = new GraphsActivityView(graphsActivityViewComponents);

        logger.LogDebug("onCreate", "Attempting handler creation of getOnActivityWaitShow");

        graphsActivityView.getOnActivityWaitShow().addHandler(new EventHandler<EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs args) {
                handleWaitShow();
            }
        });

        logger.LogDebug("onCreate", "Attempting handler creation of getOnActivityWaitDismiss");

        graphsActivityView.getOnActivityWaitDismiss().addHandler(new EventHandler<EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs args) {
                handleWaitDismiss();
            }
        });

        logger.LogDebug("onCreate", "Attempting creation of GraphsActivityControllerComponents");

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

        logger.LogDebug("onCreate", "Attempting creation of GraphsActivityController");

        GraphsActivityController graphsActivityController =
                new GraphsActivityController(
                        graphsActivityControllerComponents);

        logger.LogDebug("onCreate", "Attempting start of GraphsActivityController");
        graphsActivityController.start();
    }

    private void handleWaitShow() {
        progrssDialog = ProgressDialog.show(this, "Loading", "Please wait...", true);
    }

    private void handleWaitDismiss() {
        progrssDialog.dismiss();
    }
}

