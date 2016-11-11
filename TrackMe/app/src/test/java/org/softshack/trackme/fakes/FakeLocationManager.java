package org.softshack.trackme.fakes;

import org.softshack.trackme.TrackLocation;
import org.softshack.trackme.interfaces.ILocationManager;

public class FakeLocationManager implements ILocationManager{
    @Override
    public TrackLocation requestCurrentLocation() {
        return null;
    }
}
