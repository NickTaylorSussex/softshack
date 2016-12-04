package org.softshack.trackme.pocos;

import android.location.LocationManager;

import org.softshack.trackme.interfaces.IPermissionManager;

public class LocationManagerComponents {
    LocationManager locationManager;
    IPermissionManager permissionManager;

    public LocationManagerComponents(
            LocationManager locationManager,
            IPermissionManager permissionManager) {
        this.locationManager = locationManager;
        this.permissionManager = permissionManager;
    }

    public LocationManager getLocationManager() { return this.locationManager; }

    public IPermissionManager getPermissionManager() { return this.permissionManager; }
}