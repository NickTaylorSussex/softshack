package org.softshack.trackme.pocos;

import org.softshack.trackme.ActivityModel;
import org.softshack.trackme.interfaces.IGraphsActivityView;

/**
 * Created by Nick on 03/12/2016.
 */

public class GraphsActivityControllerComponents {
    private IGraphsActivityView graphsActivityView;
    private ActivityModel activityModel;

    public GraphsActivityControllerComponents(
            IGraphsActivityView graphsActivityView,
            ActivityModel activityModel) {

        this.graphsActivityView = graphsActivityView;
        this.activityModel = activityModel;
    }

    public IGraphsActivityView getGraphsActivityView() {
        return graphsActivityView;
    }

    public ActivityModel getActivityModel() {
        return activityModel;
    }
}
