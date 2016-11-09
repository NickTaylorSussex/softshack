package org.softshack.trackme;

import org.softshack.utils.obs.DefaultEvent;
import org.softshack.utils.obs.EventArgs;

import java.util.HashMap;


public interface ITrackMap {
    void allowUserToCentreMap(Boolean allow) throws SecurityException;

    DefaultEvent<EventArgs> getOnMapIdle();

    void setMapPosition(double latitude, double longitude);

    TrackLocation getMapCentre();

    void clearMap();

    void buildHeatMap(HashMap<String, MapDataSet> positions, String key);
}
