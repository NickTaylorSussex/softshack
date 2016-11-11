package org.softshack.trackme.tests.locationprovider;

import org.junit.Before;
import org.junit.Test;
import org.softshack.trackme.LocationProvider;
import org.softshack.trackme.TrackLocation;
import org.softshack.trackme.fakes.FakeLocationManager;
import org.softshack.trackme.interfaces.ILocationProvider;

import static org.junit.Assert.*;

public class TestDefaultData {
    ILocationProvider locationProvider;

    @Before
    public void setup(){
        locationProvider = new LocationProvider(new FakeLocationManager());
    }

    @Test
    public void default_data_is_not_null() throws Exception {
        // Arrange

        // Act
        TrackLocation result = this.locationProvider.getTrackLocation();

        // Assert
        assertNotNull(result);
    }

    @Test
    public void default_location() throws Exception {
        // Arrange

        // Act
        TrackLocation result = this.locationProvider.getTrackLocation();

        // Assert
        assertTrue("Expected Latitude to be zero", result.getLatitude() == 0);
        assertTrue("Expected Longitude to be zero", result.getLongitude() == 0);

    }
}
