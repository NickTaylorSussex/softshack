package org.softshack.trackme.interfaces;

import org.softshack.utils.obs.DefaultEvent;
import org.softshack.utils.obs.EventArgs;


public interface IMapsActivityView  {
    void initialize();

    DefaultEvent<EventArgs> getOnDataStale();

    DefaultEvent<EventArgs> getOnChangeYearRequested();

    DefaultEvent<EventArgs> getOnHistoryRequested();

    void setMapPositionCurrent();

    void getMapCentre();

    void clearMap();

    void buildHeatMap();

    void updateYear();

    void ShowHistory();
}
