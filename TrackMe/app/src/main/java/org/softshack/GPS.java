package org.softshack;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;

/**
 * Holds details of the GPS and related methods.
 * */
public class GPS extends Service implements LocationListener {
	 
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters - The minimum distance to change Updates in meters
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1;  // 1 Minute - Minimum time between updates in milliseconds
	
    private final Context context; //context of the current activity
    private Location location; // location
    private double latitude; // latitude value
    private double longitude; // longitude value
    private float accuracy;//accuracy value
    private boolean isGPSEnabled = false;
    private boolean isNetworkEnabled = false;
    private LocationManager locationManager;
 
    /**
     * Construct an instance of GPS
     * */
    public GPS(Context context) {
        this.context = context;
        getLocation();
    }
    
    /**
     * @return Location instance
     * */
    public Location getLocation() {
        try {
            locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
            // getting GPS status
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            // getting network status
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            
            if (isGPSEnabled && isNetworkEnabled) {
            	getGPSLocation();
            	getNetworkLocation();
            }
 
        } catch (Exception e) {
            e.printStackTrace();
        }
        return location;
    }
   
   /**
    * Receives location updates from GPS provider.
    * */
   private void getGPSLocation() {
       // if GPS is Enabled retrieve coords
       if (isGPSEnabled) {
           if (location == null) {
               locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,MIN_TIME_BW_UPDATES,MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
               if (locationManager != null) {
                   location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                   if (location != null) {
                       latitude = location.getLatitude();
                       longitude = location.getLongitude();
                   }
               }
           }
       }
	}
   
   /**
    * Receives location updates from network provider.
    * */
   private void getNetworkLocation() {
       if (isNetworkEnabled) {
           locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,MIN_TIME_BW_UPDATES,MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
           if (locationManager != null) {
               location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
               if (location != null) {
                   latitude = location.getLatitude();
                   longitude = location.getLongitude();
               }
           }
       }
	}

   /**
    * Checks if location exists and returns latitude.
    * @return double latitude coords
    * */
   public double getLatitude(){
       if(location != null){
           latitude = location.getLatitude();
       }
       return latitude;
   }
    
   /**
    * Checks if location exists and returns longitude.
    * @return double longitude coords
    * */
   public double getLongitude(){
       if(location != null){
           longitude = location.getLongitude();
       }
       return longitude;
   }
   
   /**
    * @return float accuracy of the location
    * */
   public float getAccuracy(){
	   if(location!=null){
		   accuracy = location.getAccuracy();
	   }
	return accuracy;
   }

@Override
public void onLocationChanged(Location location) {
	// TODO Auto-generated method stub
}

@Override
public void onStatusChanged(String provider, int status, Bundle extras) {
	// TODO Auto-generated method stub
}

@Override
public void onProviderEnabled(String provider) {
	// TODO Auto-generated method stub
}

@Override
public void onProviderDisabled(String provider) {
	// TODO Auto-generated method stub
}

@Override
public IBinder onBind(Intent intent) {
	// TODO Auto-generated method stub
	return null;
}

public boolean isGPSEnabled() {
	return isGPSEnabled;
}

public void setGPSEnabled(boolean isGPSEnabled) {
	this.isGPSEnabled = isGPSEnabled;
}

public boolean isNetworkEnabled() {
	return isNetworkEnabled;
}

public void setNetworkEnabled(boolean isNetworkEnabled) {
	this.isNetworkEnabled = isNetworkEnabled;
}
   
}
