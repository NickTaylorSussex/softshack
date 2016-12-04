package org.softshack.trackme.pocos;

import org.softshack.trackme.GraphsActivityModel;
import org.softshack.trackme.interfaces.ITrackGraph;

/**
 * POCO for transporting objects to the view.
 */

public class GraphsActivityViewComponents {
    ITrackGraph trackGraph;
    GraphsActivityModel activityModel;

    public GraphsActivityViewComponents(
            ITrackGraph trackGraph,
            GraphsActivityModel activityModel){
        this.trackGraph = trackGraph;
        this.activityModel = activityModel;
    }

    public ITrackGraph getTrackGraph() {
        return this.trackGraph;
    }

    public GraphsActivityModel getActivityModel() {
        return this.activityModel;
    }
}
