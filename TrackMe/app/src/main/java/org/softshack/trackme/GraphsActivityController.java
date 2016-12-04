package org.softshack.trackme;

import com.github.mikephil.charting.BuildConfig;

import org.json.JSONException;
import org.softshack.trackme.pocos.GraphsActivityControllerComponents;
import org.softshack.utils.obs.EventArgs;
import org.softshack.utils.obs.EventHandler;

/**
 * This class manages the data requests and commands the user interface.
 */

public class GraphsActivityController {
    GraphsActivityControllerComponents graphsActivityControllerComponents;

    public GraphsActivityController(
            GraphsActivityControllerComponents graphsActivityControllerComponents ) {

        this.graphsActivityControllerComponents = graphsActivityControllerComponents;

        // Create a listener for when the map data has changed.
        this.graphsActivityControllerComponents.getDataProvider().getOnDataChanged().addHandler(new EventHandler<EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs args) {
                handleDataChanged();
            }
        });

    }

    /**
     * Handle stale data. This normally happens when the history button is pressed.
     */
    public void start(){
        double latitude = this.graphsActivityControllerComponents.getActivityModel().getCurrentLatitude();
        double longitude = this.graphsActivityControllerComponents.getActivityModel().getCurrentLongitude();

        // Apparently java assertions are not recommended in Android. Therefore using conditional compilation.
        if (BuildConfig.DEBUG) {
            if (!(latitude >= -90 && latitude <= 90)) throw new AssertionError();
            if (!(longitude >= -180 && longitude <= 180)) throw new AssertionError();
        }

        // Build URL
        String lookupUrl = String.format(
                this.graphsActivityControllerComponents.getActivityModel().getTokenizedGraphUrl(),
                latitude,
                longitude);

        // Cancel existing async data request
        this.graphsActivityControllerComponents.getDataProvider().cancelLastRequest();

        // Launch async data request.
        this.graphsActivityControllerComponents.getDataProvider().requestData(lookupUrl);
    }

    /**
     * Handle the result of a new data set of prices/locations.
     */
    public void handleDataChanged() {
        try {
            // Convert the raw data to the form expected by the map.
            DataSetGraphMapper dataSetGraphMapper = this.graphsActivityControllerComponents.getDataProvider().convertData();

            // Check if the raw data could be converted.
            if (dataSetGraphMapper != null) {
                // The raw data could be converted, so store the converted data.
                this.graphsActivityControllerComponents.getActivityModel().setGraphsKey(this.graphsActivityControllerComponents.getDataProvider().getGraphDataSetName());
                this.graphsActivityControllerComponents.getActivityModel().getGraphs().put(this.graphsActivityControllerComponents.getDataProvider().getGraphDataSetName(), dataSetGraphMapper);

                // Command the graph to show the new data.
                this.graphsActivityControllerComponents.getGraphsActivityView().buildGraph();

            } else {
                // The raw data could not be converted, so clear the map of existing data.
                this.graphsActivityControllerComponents.getGraphsActivityView().clearGraph();
            }
        } catch (JSONException e) {
            this.graphsActivityControllerComponents.getGraphsActivityView().clearGraph();
        }
    }
}
