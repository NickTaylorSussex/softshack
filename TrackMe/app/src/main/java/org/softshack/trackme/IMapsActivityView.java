package org.softshack.trackme;

import org.softshack.utils.obs.DefaultEvent;
import org.softshack.utils.obs.EventArgs;


public interface IMapsActivityView  {
    void initialize();

    DefaultEvent<EventArgs> getOnMapIdle();

    void setMapPositionCurrent();

    void getMapCentre();

    void clearMap();

    void buildHeatMap();
}
