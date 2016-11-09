package org.softshack.trackme;

import org.softshack.utils.obs.DefaultEvent;
import org.softshack.utils.obs.EventArgs;


public class LocationProvider implements ILocationProvider{
    private final DefaultEvent<EventArgs> onLocationFound = new DefaultEvent<EventArgs>();
    private ILocationManager locationManager;
    private TrackLocation trackLocation;

    public LocationProvider(ILocationManager locationManager){
        this.locationManager = locationManager;
        this.setTrackLocation(new TrackLocation(0,0));
    }

    @Override
    public DefaultEvent<EventArgs> getOnLocationFound() {
        return onLocationFound;
    }

    @Override
    public void requestCurrentLocation(){
        this.setTrackLocation(locationManager.requestCurrentLocation());
        this.onLocationFound.fire(this, new EventArgs());
    }

    public TrackLocation getTrackLocation() {
        return trackLocation;
    }

    public void setTrackLocation(TrackLocation trackLocation) {
        this.trackLocation = trackLocation;
    }
}
