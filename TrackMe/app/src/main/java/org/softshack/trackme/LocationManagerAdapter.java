package org.softshack.trackme;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;

public class LocationManagerAdapter implements ILocationManager{

    LocationManager locationManager;
    IPermissionManager permissionManager;

    public LocationManagerAdapter(
            LocationManager locationManager,
            IPermissionManager permissionManager) {
        this.locationManager = locationManager;
        this.permissionManager = permissionManager;
    }

    @Override
    public TrackLocation requestCurrentLocation() throws SecurityException{
        if (this.permissionManager.PermissionFineLocationAllowed()) {

            // Creating a criteria object to retrieve provider
            Criteria criteria = new Criteria();

            // Getting the name of the best provider
            String provider = locationManager.getBestProvider(criteria, true);

            // Getting Current TrackLocation
            Location location = null;
            location = locationManager.getLastKnownLocation(provider);


            if (location != null) {
                return new TrackLocation(location.getLatitude(), location.getLongitude());
            }
        }

        return new TrackLocation(0,0);
    }
}