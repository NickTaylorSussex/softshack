package org.softshack.trackme;

import org.json.JSONException;
import org.softshack.trackme.interfaces.IDataProvider;
import org.softshack.trackme.interfaces.ILocationProvider;
import org.softshack.trackme.interfaces.IMapsActivityView;
import org.softshack.utils.obs.EventArgs;
import org.softshack.utils.obs.EventHandler;

/**
 * This class manages the data requests and commands the user interface.
 */
public class MapsActivityController {
    private IMapsActivityView mapsActivityView;
    private MapsActivityModel mapsActivityModel;
    private ILocationProvider locationProvider;
    private IDataProvider dataProvider;

    /**
     * Constructor.
     * @param mapsActivityView
     * @param mapsActivityModel
     * @param locationProvider
     * @param dataProvider
     */
    public MapsActivityController(
            IMapsActivityView mapsActivityView,
            MapsActivityModel mapsActivityModel,
            ILocationProvider locationProvider,
            IDataProvider dataProvider) {

        this.mapsActivityView = mapsActivityView;
        this.mapsActivityModel = mapsActivityModel;
        this.locationProvider = locationProvider;
        this.dataProvider = dataProvider;

        // Store the default map values.
        this.mapsActivityModel.setAllowUserToCentreMap(true);
        this.mapsActivityModel.setYear("2015");

        // Create a listener for stale data.
        this.mapsActivityView.getOnDataStale().addHandler(new EventHandler<EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs args) {
                handleStaleData();
            }
        });

        // Create a listener for when the year is changed.
        this.mapsActivityView.getOnChangeYearRequested().addHandler(new EventHandler<EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs args) {
                handleChangeYearRequested();
            }
        });

        // Create a listener for when the device's current location is found.
        this.locationProvider.getOnLocationFound().addHandler(new EventHandler<EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs args) {
                handleLocationFound();
            }
        });

        // Create a listener for when the map data has changed.
        this.dataProvider.getOnDataChanged().addHandler(new EventHandler<EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs args) {
                handleDataChanged();
            }
        });
    }

    /**
     * Handle stale data. This normally happens when the map display is moved by the user.
     */
    public void handleStaleData(){

        // Request map centre.
        this.mapsActivityView.getMapCentre();

        double latitude = this.mapsActivityModel.getCurrentLatitude();
        double longitude = this.mapsActivityModel.getCurrentLongitude();

        // Apparently java assertions are not recommended in Android. Therefore using conditional compilation.
        if (BuildConfig.DEBUG) {
            if (!(latitude >= -90 && latitude <= 90)) throw new AssertionError();
            if (!(longitude >= -180 && longitude <= 180)) throw new AssertionError();
        }

        // Build URL
        String lookupUrl = String.format(
                this.mapsActivityModel.getTokenizedUrl(),
                latitude,
                longitude,
                this.mapsActivityModel.getYear());

        // Cancel existing async data request
        this.dataProvider.cancelLastRequest();

        // Clear last overlay from map.
        this.mapsActivityView.clearMap();

        // Launch async data request.
        this.dataProvider.requestData(lookupUrl);
    }

    /**
     * Handle the device's location being found.
     */
    public void handleLocationFound(){
        // Get the location information from the provider that supplies location.
        TrackLocation currentLocation = this.locationProvider.getTrackLocation();

        double latitude = currentLocation.getLatitude();
        double longitude = currentLocation.getLongitude();;

        // Apparently java assertions are not recommended in Android. Therefore using conditional compilation.
        if (BuildConfig.DEBUG) {
            if (!(latitude >= -90 && latitude <= 90)) throw new AssertionError();
            if (!(longitude >= -180 && longitude <= 180)) throw new AssertionError();
        }

        // Store the new values.
        this.mapsActivityModel.setCurrentLatitude(latitude);
        this.mapsActivityModel.setCurrentLongitude(longitude);

        // Tell the map to move to the new location.
        this.mapsActivityView.setMapPositionCurrent();

    }

    /**
     * start control of the data and command of the user interface.
     * @throws SecurityException
     */
    public void start() throws SecurityException {
        // Command the map to initialise and go to default settings.
        this.mapsActivityView.initialize();

        // Ask the provider for current location to refresh.
        this.locationProvider.requestCurrentLocation();

    }

    /**
     * Handle the result of a new data set of prices/locations.
     */
    public void handleDataChanged() {
        try {
            // Convert the raw data to the form expected by the map.
            DataSetMapper dataSetMapper = this.dataProvider.convertData();

            // Check if the raw data could be converted.
            if (dataSetMapper != null) {
                // The raw data could be converted, so store the converted data.
                this.mapsActivityModel.setPositionsKey(this.dataProvider.getMapDataSetName());
                this.mapsActivityModel.getPositions().put(this.dataProvider.getMapDataSetName(), dataSetMapper);

                // Command the map to show a heatmap based on the new data.
                this.mapsActivityView.buildHeatMap();

            } else {
                // The raw data could not be converted, so clear the map of existing data.
                this.mapsActivityView.clearMap();
            }
        } catch (JSONException e) {
            this.mapsActivityView.clearMap();
        }
    }

    /**
     * Handle the year being changed.
     */
    public void handleChangeYearRequested() {
        // Command the user interface to update the year display.
        this.mapsActivityView.updateYear();
    }
}
