package org.softshack.trackme.tests.locationprovider.maps;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.softshack.trackme.LocationProvider;
import org.softshack.trackme.TrackLocation;
import org.softshack.trackme.interfaces.ILocationManager;
import org.softshack.utils.obs.EventArgs;
import org.softshack.utils.obs.EventHandler;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TestMethodCalls {
    @Mock
    ILocationManager mockLocationManager;

    LocationProvider locationProvider;

    Boolean calledOnLocationFound = false;

    @Before
    public void setup() throws Exception {
        this.locationProvider = new LocationProvider(this.mockLocationManager);
    }

    @Test
    public void testRequestCurrentLocation() throws Exception {

        // Arrange
        when(mockLocationManager.requestCurrentLocation())
                .thenReturn(new TrackLocation(0,0));

        this.locationProvider.getOnLocationFound().addHandler(new EventHandler<EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs args) {
                calledOnLocationFound = true;
            }
        });

        // Act
        this.locationProvider.requestCurrentLocation();

        // Assert
        verify(this.mockLocationManager, times(1)).requestCurrentLocation();

        assertTrue("Expected OnLocationFound to be called.", this.calledOnLocationFound == true);
    }
}
