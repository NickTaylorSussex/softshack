package org.softshack.trackme.pocos;

import org.softshack.trackme.MapsActivityModel;
import org.softshack.trackme.interfaces.IMapDataProvider;
import org.softshack.trackme.interfaces.ILocationProvider;
import org.softshack.trackme.interfaces.IMapsActivityView;
import org.softshack.utils.log.ILogger;

public class MapsActivityControllerComponents {
    private ILogger logger;
    private IMapsActivityView mapsActivityView;
    private MapsActivityModel activityModel;
    private ILocationProvider locationProvider;
    private IMapDataProvider dataProvider;

    /**
     * Constructor.
     * @param logger
     * @param mapsActivityView
     * @param activityModel
     * @param locationProvider
     * @param dataProvider
     */
    public MapsActivityControllerComponents(
            ILogger logger,
            IMapsActivityView mapsActivityView,
            MapsActivityModel activityModel,
            ILocationProvider locationProvider,
            IMapDataProvider dataProvider) {
        this.logger = logger;
        this.mapsActivityView = mapsActivityView;
        this.activityModel = activityModel;
        this.locationProvider = locationProvider;
        this.dataProvider = dataProvider;
    }

    public ILogger getLogger() {
        return this.logger;
    }

    public IMapsActivityView getMapsActivityView() {
        return this.mapsActivityView;
    }

    public MapsActivityModel getActivityModel() {

        return this.activityModel;
    }

    public ILocationProvider getLocationProvider() {
        return this.locationProvider;
    }

    public IMapDataProvider getDataProvider() { return this.dataProvider; }
}
