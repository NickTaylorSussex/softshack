package org.softshack.trackme;

import org.softshack.trackme.interfaces.IGraphsActivityView;
import org.softshack.trackme.interfaces.ITrackGraph;
import org.softshack.trackme.pocos.GraphsActivityViewComponents;
import org.softshack.utils.log.ILogger;
import org.softshack.utils.obs.DefaultEvent;
import org.softshack.utils.obs.EventArgs;

public class GraphsActivityView implements IGraphsActivityView {
    private ILogger logger;
    private GraphsActivityModel activityModel;
    private ITrackGraph trackGraph;
    private final DefaultEvent<EventArgs> onActivityWaitShow = new DefaultEvent<EventArgs>();
    private final DefaultEvent<EventArgs> onActivityWaitDismiss = new DefaultEvent<EventArgs>();

    public GraphsActivityView(GraphsActivityViewComponents graphsActivityViewComponents) {
        this.logger = graphsActivityViewComponents.getLogger();
        this.setActivityModel(graphsActivityViewComponents.getActivityModel());
        this.trackGraph = graphsActivityViewComponents.getTrackGraph();

    }
     /**
     * Clears the graph of any data.
     */
    @Override
    public void clearGraph(){
        trackGraph.clearGraph();
    }

    /**
     * Builds the graph based on the stored location.
     */
    @Override
    public void buildGraph() {
        logger.LogDebug("buildGraph", "Being asked to build graph.");
        this.trackGraph.buildGraph(
                this.getActivityModel().getGraphs(), this.getActivityModel().getGraphsKey());

        this.trackGraph.refresh();
    }

    /**
     * @return model data.
     */
    private GraphsActivityModel getActivityModel() {
        return activityModel;
    }

    private void setActivityModel(GraphsActivityModel activityModel) {
        this.activityModel = activityModel;
    }

    @Override
    public void showWait() {
        logger.LogDebug("showWait", "Being asked to show wait.");
        getOnActivityWaitShow().fire(this, EventArgs.Empty);
    }

    @Override
    public void dismissWait() {
        logger.LogDebug("dismissWait", "Being asked to dismiss wait.");
        getOnActivityWaitDismiss().fire(this, EventArgs.Empty);
    }

    public DefaultEvent<EventArgs> getOnActivityWaitShow() { return onActivityWaitShow; }
    public DefaultEvent<EventArgs> getOnActivityWaitDismiss() { return onActivityWaitDismiss; }
}
