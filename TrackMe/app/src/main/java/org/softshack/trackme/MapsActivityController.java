package org.softshack.trackme;

import org.json.JSONException;
import org.softshack.trackme.interfaces.IDataProvider;
import org.softshack.trackme.interfaces.ILocationProvider;
import org.softshack.trackme.interfaces.IMapsActivityView;
import org.softshack.utils.obs.EventArgs;
import org.softshack.utils.obs.EventHandler;

public class MapsActivityController {
    private IMapsActivityView mapsActivityView;
    private MapsActivityModel mapsActivityModel;
    private ILocationProvider locationProvider;
    private IDataProvider dataProvider;

    public MapsActivityController(
            IMapsActivityView mapsActivityView,
            MapsActivityModel mapsActivityModel,
            ILocationProvider locationProvider,
            IDataProvider dataProvider) {

        this.mapsActivityView = mapsActivityView;
        this.mapsActivityModel = mapsActivityModel;
        this.locationProvider = locationProvider;
        this.dataProvider = dataProvider;

        this.mapsActivityModel.setAllowUserToCentreMap(true);
        this.mapsActivityModel.setYear("2015");

        this.mapsActivityView.getOnDataStale().addHandler(new EventHandler<EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs args) {
                handleStaleData();
            }
        });

        this.mapsActivityView.getOnChangeYearRequested().addHandler(new EventHandler<EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs args) {
                handleChangeYearRequested();
            }
        });

        this.locationProvider.getOnLocationFound().addHandler(new EventHandler<EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs args) {
                handleLocationFound();
            }
        });

        this.dataProvider.getOnDataChanged().addHandler(new EventHandler<EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs args) {
                handleDataChanged();
            }
        });
    }

    public void handleStaleData(){

        // Request map centre.
        this.mapsActivityView.getMapCentre();

        // Build URL
        String lookupUrl = String.format(
                this.mapsActivityModel.getTokenizedUrl(),
                this.mapsActivityModel.getCurrentLatitude(),
                this.mapsActivityModel.getCurrentLongitude(),
                this.mapsActivityModel.getYear());

        // Cancel existing async data request
        this.dataProvider.cancelLastRequest();

        // Clear last overlay from map.
        this.mapsActivityView.clearMap();

        // Launch async data request.
        this.dataProvider.requestData(lookupUrl);
    }

    public void handleLocationFound(){
        TrackLocation currentLocation = this.locationProvider.getTrackLocation();
        if(currentLocation.getLatitude() != 0 && currentLocation.getLongitude() != 0) {
            this.mapsActivityModel.setCurrentLatitude(currentLocation.getLatitude());

            this.mapsActivityModel.setCurrentLongitude(currentLocation.getLongitude());

            this.mapsActivityView.setMapPositionCurrent();
        }
    }

    public void start() throws SecurityException {

        this.mapsActivityView.initialize();

        this.locationProvider.requestCurrentLocation();

    }

    public void handleDataChanged() {
        try {
            MapDataSet mapDataSet = this.dataProvider.convertData();
            if (mapDataSet != null) {
                this.mapsActivityModel.setPositionsKey(this.dataProvider.getMapDataSetName());
                this.mapsActivityModel.getPositions().put(this.dataProvider.getMapDataSetName(), mapDataSet);
                this.mapsActivityView.buildHeatMap();
            } else {
                this.mapsActivityView.clearMap();
            }
        } catch (JSONException e) {
            this.mapsActivityView.clearMap();
        }
    }

    public void handleChangeYearRequested() {
        this.mapsActivityView.updateYear();
    }
}
