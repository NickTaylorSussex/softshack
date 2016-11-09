package org.softshack.trackme;

import org.softshack.utils.obs.DefaultEvent;
import org.softshack.utils.obs.EventArgs;

public interface ILocationProvider {
    DefaultEvent<EventArgs> getOnLocationFound();

    void requestCurrentLocation();

    TrackLocation getTrackLocation();
}
