package org.softshack.trackme;

import java.util.HashMap;

/**
 * This is the data used by the application.
 */

public class GraphsActivityModel {

    private String tokenizedGraphUrl = "http://138.68.151.94/1234/graph/%s&%s/2&1000";
    private double currentLatitude;
    private double currentLongitude;
    private HashMap<String, DataSetGraphMapper> graphs = new HashMap<>();
    private String graphsKey;


    public String getGraphsKey() {
        return graphsKey;
    }

    public void setGraphsKey(String graphsKey) {
        this.graphsKey = graphsKey;
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

    /**
     * Maps name of data set to data (list of Bar)
     * Also activity_graphs to the URL of the data set for attribution
     */
    public HashMap<String, DataSetGraphMapper> getGraphs() {
        return graphs;
    }

    public String getTokenizedGraphUrl() {
        return tokenizedGraphUrl;
    }
}
