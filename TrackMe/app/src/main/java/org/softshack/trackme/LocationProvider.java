package org.softshack.trackme;

import org.softshack.trackme.interfaces.ILocationManager;
import org.softshack.trackme.interfaces.ILocationProvider;
import org.softshack.utils.obs.DefaultEvent;
import org.softshack.utils.obs.EventArgs;

/**
 * This class abstracts a location request from a specific location manager.
 */
public class LocationProvider implements ILocationProvider {
    private final DefaultEvent<EventArgs> onLocationFound = new DefaultEvent<EventArgs>();
    private ILocationManager locationManager;
    private TrackLocation trackLocation;

    /**
     * Constructor.
     * @param locationManager to use for a location request.
     */
    public LocationProvider(ILocationManager locationManager){
        this.locationManager = locationManager;
        this.setTrackLocation(new TrackLocation(0,0));
    }

    /**
     * @return the broadcaster object to listeners can subscribe.
     */
    @Override
    public DefaultEvent<EventArgs> getOnLocationFound() {
        return onLocationFound;
    }

    /**
     * Requests the current device location by accessing the given location manager.
     * Notifies if a location is found.
     */
    @Override
    public void requestCurrentLocation(){
        this.setTrackLocation(locationManager.requestCurrentLocation());
        this.onLocationFound.fire(this, new EventArgs());
    }

    /**
     * @return the current location value.
     */
    public TrackLocation getTrackLocation() {
        return trackLocation;
    }

    private void setTrackLocation(TrackLocation trackLocation) {
        this.trackLocation = trackLocation;
    }
}
