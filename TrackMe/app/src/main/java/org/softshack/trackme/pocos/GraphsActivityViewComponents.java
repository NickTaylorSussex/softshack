package org.softshack.trackme.pocos;

import org.softshack.trackme.GraphsActivityModel;
import org.softshack.trackme.interfaces.ITrackGraph;
import org.softshack.utils.log.ILogger;

/**
 * POCO for transporting objects to the view.
 */

public class GraphsActivityViewComponents {
    ILogger logger;
    ITrackGraph trackGraph;
    GraphsActivityModel activityModel;

    public GraphsActivityViewComponents(
            ILogger logger,
            ITrackGraph trackGraph,
            GraphsActivityModel activityModel){
        this.logger = logger;
        this.trackGraph = trackGraph;
        this.activityModel = activityModel;
    }

    public ITrackGraph getTrackGraph() {
        return this.trackGraph;
    }

    public GraphsActivityModel getActivityModel() {
        return this.activityModel;
    }

    public ILogger getLogger() { return this.logger; }
}
