package org.softshack.trackme.pocos;

import org.softshack.trackme.MapsActivityModel;
import org.softshack.trackme.MapsActivityView;
import org.softshack.trackme.interfaces.IDataProvider;
import org.softshack.trackme.interfaces.ILocationProvider;
import org.softshack.trackme.interfaces.IMapsActivityView;
import org.softshack.utils.log.ILogger;

public class MapsActivityControllerComponents {
    private ILogger logger;
    private IMapsActivityView mapsActivityView;
    private MapsActivityModel mapsActivityModel;
    private ILocationProvider locationProvider;
    private IDataProvider dataProvider;

    /**
     * Constructor.
     * @param logger
     * @param mapsActivityView
     * @param mapsActivityModel
     * @param locationProvider
     * @param dataProvider
     */
    public MapsActivityControllerComponents(
            ILogger logger,
            IMapsActivityView mapsActivityView,
            MapsActivityModel mapsActivityModel,
            ILocationProvider locationProvider,
            IDataProvider dataProvider) {
        this.logger = logger;
        this.mapsActivityView = mapsActivityView;
        this.mapsActivityModel = mapsActivityModel;
        this.locationProvider = locationProvider;
        this.dataProvider = dataProvider;

    }

    public ILogger getLogger() {
        return this.logger;
    }

    public IMapsActivityView getMapsActivityView() {
        return this.mapsActivityView;
    }

    public MapsActivityModel getMapsActivityModel() {

        return this.mapsActivityModel;
    }

    public ILocationProvider getLocationProvider() {
        return this.locationProvider;
    }

    public IDataProvider getDataProvider() {
        return this.dataProvider;
    }
}
