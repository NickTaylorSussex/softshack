package org.softshack.trackme.interfaces;

import org.softshack.trackme.TrackLocation;
import org.softshack.utils.obs.DefaultEvent;
import org.softshack.utils.obs.EventArgs;

public interface ILocationProvider {
    DefaultEvent<EventArgs> getOnLocationFound();

    void requestCurrentLocation();

    TrackLocation getTrackLocation();
}
