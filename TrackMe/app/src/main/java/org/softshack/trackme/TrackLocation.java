package org.softshack.trackme;

public class TrackLocation {
    private double latitude;
    private double longitude;

    public TrackLocation(double latitude, double longitude){
        this.setLatitude(latitude);
        this.setLongitude(longitude);
    }


    public double getLongitude() {
        return longitude;
    }

    private void setLongitude(double longitude) {
        this.longitude = longitude;
    }


    public double getLatitude() {
        return latitude;
    }

    private void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
