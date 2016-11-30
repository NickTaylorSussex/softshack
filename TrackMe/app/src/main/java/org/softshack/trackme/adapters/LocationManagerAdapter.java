package org.softshack.trackme.adapters;

import android.location.Criteria;
import android.location.Location;

import org.softshack.trackme.pocos.LocationManagerComponents;
import org.softshack.trackme.TrackLocation;
import org.softshack.trackme.interfaces.ILocationManager;

public class LocationManagerAdapter implements ILocationManager {

    LocationManagerComponents locationManagerComponents;

    public LocationManagerAdapter(
            LocationManagerComponents locationManagerComponents) {

        this.locationManagerComponents = locationManagerComponents;

    }

    @Override
    public TrackLocation requestCurrentLocation() throws SecurityException{
        if (this.locationManagerComponents.getPermissionManager().PermissionFineLocationAllowed()) {

            // Creating a criteria object to retrieve provider
            Criteria criteria = new Criteria();

            // Getting the name of the best provider
            String provider =
                    this.locationManagerComponents.getLocationManager().getBestProvider(criteria, true);

            // Getting Current TrackLocation
            Location location = null;
            location = this.locationManagerComponents.getLocationManager().getLastKnownLocation(provider);


            if (location != null) {
                return new TrackLocation(location.getLatitude(), location.getLongitude());
            }
        }

        return new TrackLocation(0,0);
    }
}