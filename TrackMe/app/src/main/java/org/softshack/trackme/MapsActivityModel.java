package org.softshack.trackme;

import java.util.HashMap;

/**
 * This is the data used by the application.
 */
public class MapsActivityModel {
    private String year;
    private Boolean allowUserToCentreMap;
    private double currentLatitude;
    private double currentLongitude;
    private String tokenizedMapUrl = "http://138.68.151.94/1234/clean/%s&%s/%s&2&1000&15";

    private HashMap<String, DataSetMapMapper> positions = new HashMap<>();
    private String positionsKey;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Boolean getAllowUserToCentreMap() {
        return allowUserToCentreMap;
    }

    public void setAllowUserToCentreMap(Boolean allowUserToCentreMap) {
        this.allowUserToCentreMap = allowUserToCentreMap;
    }

    public double getCurrentLatitude() {
        return currentLatitude;
    }

    public void setCurrentLatitude(double currentLatitude) {
        this.currentLatitude = currentLatitude;
    }

    public double getCurrentLongitude() {
        return currentLongitude;
    }

    public void setCurrentLongitude(double currentLongitude) {
        this.currentLongitude = currentLongitude;
    }

    public String getTokenizedMapUrl() {
        return tokenizedMapUrl;
    }

    /**
     * Maps name of data set to data (list of LatLngs)
     * Also activity_maps to the URL of the data set for attribution
     */
    public HashMap<String, DataSetMapMapper> getPositions() {
        return positions;
    }

    public String getPositionsKey() {
        return positionsKey;
    }

    public void setPositionsKey(String positionsKey) {
        this.positionsKey = positionsKey;
    }

}
