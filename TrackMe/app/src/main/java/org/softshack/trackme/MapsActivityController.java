package org.softshack.trackme;

import org.json.JSONException;
import org.softshack.trackme.pocos.MapsActivityControllerComponents;
import org.softshack.utils.obs.EventArgs;
import org.softshack.utils.obs.EventHandler;

/**
 * This class manages the data requests and commands the user interface.
 */
public class MapsActivityController {
    MapsActivityControllerComponents mapsActivityControllerComponents;

    public MapsActivityController(
            MapsActivityControllerComponents mapsActivityControllerComponents) {

        this.mapsActivityControllerComponents = mapsActivityControllerComponents;

        // Store the default map values.
        this.mapsActivityControllerComponents.getActivityModel().setAllowUserToCentreMap(true);
        this.mapsActivityControllerComponents.getActivityModel().setYear("2015");

        // Create a listener for stale data.
        this.mapsActivityControllerComponents.getMapsActivityView().getOnDataStale().addHandler(new EventHandler<EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs args) {
                handleStaleData();
            }
        });

        // Create a listener for when the year is changed.
        this.mapsActivityControllerComponents.getMapsActivityView().getOnChangeYearRequested().addHandler(new EventHandler<EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs args) {
                handleChangeYearRequested();
            }
        });

        // Create a listener for when the device's current location is found.
        this.mapsActivityControllerComponents.getLocationProvider().getOnLocationFound().addHandler(new EventHandler<EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs args) {
                handleLocationFound();
            }
        });

        // Create a listener for when the map data has changed.
        this.mapsActivityControllerComponents.getDataProvider().getOnDataChanged().addHandler(new EventHandler<EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs args) {
                handleDataChanged();
            }
        });

        // Create a listener for when the history is requested.
        this.mapsActivityControllerComponents.getMapsActivityView().getOnHistoryRequested().addHandler(new EventHandler<EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs args) {
                handleHistoryRequested();
            }
        });
    }

    /**
     * Handle stale data. This normally happens when the map display is moved by the user.
     */
    public void handleStaleData(){

        // Request map centre.
        this.mapsActivityControllerComponents.getMapsActivityView().getMapCentre();

        double latitude = this.mapsActivityControllerComponents.getActivityModel().getCurrentLatitude();
        double longitude = this.mapsActivityControllerComponents.getActivityModel().getCurrentLongitude();

        // Apparently java assertions are not recommended in Android. Therefore using conditional compilation.
        if (BuildConfig.DEBUG) {
            if (!(latitude >= -90 && latitude <= 90)) throw new AssertionError();
            if (!(longitude >= -180 && longitude <= 180)) throw new AssertionError();
        }

        // Build URL
        String lookupUrl = String.format(
                this.mapsActivityControllerComponents.getActivityModel().getTokenizedMapUrl(),
                latitude,
                longitude,
                this.mapsActivityControllerComponents.getActivityModel().getYear());

        // Cancel existing async data request
        this.mapsActivityControllerComponents.getDataProvider().cancelLastRequest();

        // Clear last overlay from map.
        this.mapsActivityControllerComponents.getMapsActivityView().clearMap();

        // Launch async data request.
        this.mapsActivityControllerComponents.getDataProvider().requestData(lookupUrl);
    }

    /**
     * Handle the device's location being found.
     */
    public void handleLocationFound(){
        // Get the location information from the provider that supplies location.
        TrackLocation currentLocation = this.mapsActivityControllerComponents.getLocationProvider().getTrackLocation();

        double latitude = currentLocation.getLatitude();
        double longitude = currentLocation.getLongitude();;

        // Apparently java assertions are not recommended in Android. Therefore using conditional compilation.
        if (BuildConfig.DEBUG) {
            if (!(latitude >= -90 && latitude <= 90)) throw new AssertionError();
            if (!(longitude >= -180 && longitude <= 180)) throw new AssertionError();
        }

        // Store the new values.
        this.mapsActivityControllerComponents.getActivityModel().setCurrentLatitude(latitude);
        this.mapsActivityControllerComponents.getActivityModel().setCurrentLongitude(longitude);

        // Tell the map to move to the new location.
        this.mapsActivityControllerComponents.getMapsActivityView().setMapPositionCurrent();

    }

    /**
     * start control of the data and command of the user interface.
     * @throws SecurityException
     */
    public void start() throws SecurityException {
        // Command the map to initialise and go to default settings.
        this.mapsActivityControllerComponents.getMapsActivityView().initialize();

        // Ask the provider for current location to refresh.
        this.mapsActivityControllerComponents.getLocationProvider().requestCurrentLocation();

    }

    /**
     * Handle the result of a new data set of prices/locations.
     */
    public void handleDataChanged() {
        try {
            this.mapsActivityControllerComponents.getLogger().LogDebug("handleDataChanged", "Attempting data change.");

            // Convert the raw data to the form expected by the map.
            DataSetMapMapper dataSetMapMapper = this.mapsActivityControllerComponents.getDataProvider().convertData();

            // Check if the raw data could be converted.
            if (dataSetMapMapper != null) {
                // The raw data could be converted, so store the converted data.
                this.mapsActivityControllerComponents.getActivityModel().setPositionsKey(this.mapsActivityControllerComponents.getDataProvider().getMapDataSetName());
                this.mapsActivityControllerComponents.getActivityModel().getPositions().put(this.mapsActivityControllerComponents.getDataProvider().getMapDataSetName(), dataSetMapMapper);

                // Command the map to show a heatmap based on the new data.
                this.mapsActivityControllerComponents.getMapsActivityView().buildHeatMap();

            } else {
                // The raw data could not be converted, so clear the map of existing data.
                this.mapsActivityControllerComponents.getMapsActivityView().clearMap();
            }
        } catch (JSONException e) {
            this.mapsActivityControllerComponents.getLogger().LogError("handleDataChanged", e);
            this.mapsActivityControllerComponents.getMapsActivityView().clearMap();
        }
    }

    /**
     * Handle the year being changed.
     */
    public void handleChangeYearRequested() {
        // Command the user interface to update the year display.
        this.mapsActivityControllerComponents.getMapsActivityView().updateYear();
    }

    /**
     * Handle the history being requested
     */
    public void handleHistoryRequested() {
        // Command the user interface to shpw the history;
        this.mapsActivityControllerComponents.getMapsActivityView().ShowHistory();
    }
}
