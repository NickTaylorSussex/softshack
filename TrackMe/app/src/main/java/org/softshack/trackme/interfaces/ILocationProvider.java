package org.softshack.trackme.interfaces;

import org.softshack.trackme.TrackLocation;
import org.softshack.utils.obs.DefaultEvent;
import org.softshack.utils.obs.EventArgs;

/**
 * An interface for abstracting a location request from a specific location manager.
 */
public interface ILocationProvider {
    /**
     * @return the broadcaster object to listeners can subscribe.
     */
    DefaultEvent<EventArgs> getOnLocationFound();

    /**
     * Requests the current device location by accessing the given location manager.
     * Notifies if a location is found.
     */
    void requestCurrentLocation();

    /**
     * @return the current location value.
     */
    TrackLocation getTrackLocation();
}
