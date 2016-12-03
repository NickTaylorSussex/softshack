package org.softshack.trackme.pocos;

import org.softshack.trackme.ActivityModel;
import org.softshack.trackme.interfaces.IDataProvider;
import org.softshack.trackme.interfaces.ILocationProvider;
import org.softshack.trackme.interfaces.IMapsActivityView;
import org.softshack.utils.log.ILogger;

public class MapsActivityControllerComponents {
    private ILogger logger;
    private IMapsActivityView mapsActivityView;
    private ActivityModel activityModel;
    private ILocationProvider locationProvider;
    private IDataProvider dataProvider;

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
            ActivityModel activityModel,
            ILocationProvider locationProvider,
            IDataProvider dataProvider) {
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

    public ActivityModel getActivityModel() {

        return this.activityModel;
    }

    public ILocationProvider getLocationProvider() {
        return this.locationProvider;
    }

    public IDataProvider getDataProvider() {
        return this.dataProvider;
    }
}
