package org.softshack.trackme.pocos;

import org.softshack.trackme.GraphsActivityModel;
import org.softshack.trackme.interfaces.IGraphDataProvider;
import org.softshack.trackme.interfaces.IGraphsActivityView;
import org.softshack.utils.log.ILogger;

public class GraphsActivityControllerComponents {
    private ILogger logger;
    private IGraphsActivityView graphsActivityView;
    private GraphsActivityModel activityModel;
    private IGraphDataProvider dataProvider;

    /**
     * Constructor.
     * @param logger
     * @param graphsActivityView
     * @param activityModel
     * @param dataProvider
     */
    public GraphsActivityControllerComponents(
            ILogger logger,
            IGraphsActivityView graphsActivityView,
            GraphsActivityModel activityModel,
            IGraphDataProvider dataProvider) {

        this.logger = logger;
        this.graphsActivityView = graphsActivityView;
        this.activityModel = activityModel;
        this.dataProvider = dataProvider;
    }

    public ILogger getLogger() {
        return this.logger;
    }

    public IGraphsActivityView getGraphsActivityView() {
        return graphsActivityView;
    }

    public GraphsActivityModel getActivityModel() {
        return activityModel;
    }

    public IGraphDataProvider getDataProvider() { return this.dataProvider; }
}
