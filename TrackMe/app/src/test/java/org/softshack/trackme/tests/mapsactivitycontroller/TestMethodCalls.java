package org.softshack.trackme.tests.mapsactivitycontroller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.softshack.trackme.DataSetMapMapper;
import org.softshack.trackme.MapsActivityController;
import org.softshack.trackme.MapsActivityModel;
import org.softshack.trackme.TrackLocation;
import org.softshack.trackme.interfaces.IMapDataProvider;
import org.softshack.trackme.interfaces.ILocationProvider;
import org.softshack.trackme.interfaces.IMapsActivityView;
import org.softshack.trackme.pocos.MapsActivityControllerComponents;
import org.softshack.utils.log.ILogger;
import org.softshack.utils.obs.DefaultEvent;
import org.softshack.utils.obs.EventArgs;
import org.softshack.utils.obs.EventHandler;

import java.util.HashMap;
import java.util.Random;

import static org.mockito.Matchers.anyDouble;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TestMethodCalls {
    MapsActivityController mapsActivityController;

    @Mock
    IMapsActivityView mockMapsActivityView;

    @Mock
    MapsActivityModel mockActivityModel;

    @Mock
    ILocationProvider mockLocationProvider;

    @Mock
    IMapDataProvider mockDataProvider;

    @Mock
    DataSetMapMapper mockDataSetMapMapper;

    @Mock
    DefaultEvent<EventArgs> mockOnDataStaleEvent;

    @Mock
    DefaultEvent<EventArgs> mockOnChangeYearRequestedEvent;

    @Mock
    DefaultEvent<EventArgs> mockOnLocationFoundEvent;

    @Mock
    DefaultEvent<EventArgs> mockOnDataChangedEvent;

    @Mock
    HashMap<String, DataSetMapMapper> mockHashMap;

    @Mock
    MapsActivityControllerComponents mockMapsActivityControllerComponents;

    @Mock
    ILogger mockLogger;

    private double randomLatitude(){
        Random r = new Random();
        return (r.nextDouble()-0.5d) * 90;
    }

    private double randomLongitude(){
        Random r = new Random();
        return (r.nextDouble()-0.5d) * 180;
    }

    @Before
    public void setup() throws Exception {
        when(mockMapsActivityControllerComponents.getDataProvider()).thenReturn(mockDataProvider);
        when(mockMapsActivityControllerComponents.getLocationProvider()).thenReturn(mockLocationProvider);
        when(mockMapsActivityControllerComponents.getMapsActivityView()).thenReturn(mockMapsActivityView);
        when(mockMapsActivityControllerComponents.getActivityModel()).thenReturn(mockActivityModel);
        when(mockMapsActivityControllerComponents.getLogger()).thenReturn(mockLogger);

        when(mockMapsActivityView.getOnDataStale()).thenReturn(mockOnDataStaleEvent);
        when(mockMapsActivityView.getOnChangeYearRequested()).thenReturn(mockOnChangeYearRequestedEvent);
        when(mockLocationProvider.getOnLocationFound()).thenReturn(mockOnLocationFoundEvent);
        when(mockDataProvider.getOnDataChanged()).thenReturn(mockOnDataChangedEvent);

        this.mapsActivityController = new MapsActivityController(
                mockMapsActivityControllerComponents);
    }

    @Test
    public void testConstructor() throws Exception {
        // Arrange

        // Act

        // Assert
        verify(this.mockActivityModel, times(1)).setAllowUserToCentreMap(true);
        verify(this.mockActivityModel, times(1)).setYear("2015");

        verify(this.mockMapsActivityView, times(1)).getOnDataStale();
        verify(this.mockOnDataStaleEvent, times(1)).addHandler(Mockito.<EventHandler<EventArgs>>any());

        verify(this.mockMapsActivityView, times(1)).getOnChangeYearRequested();
        verify(this.mockOnChangeYearRequestedEvent, times(1)).addHandler(Mockito.<EventHandler<EventArgs>>any());

        verify(this.mockLocationProvider, times(1)).getOnLocationFound();
        verify(this.mockOnLocationFoundEvent, times(1)).addHandler(Mockito.<EventHandler<EventArgs>>any());

        verify(this.mockDataProvider, times(1)).getOnDataChanged();
        verify(this.mockOnDataChangedEvent, times(1)).addHandler(Mockito.<EventHandler<EventArgs>>any());

    }

    @Test
    public void testHandleStaleData() throws Exception {
        // Arrange
        double randomLatitude = this.randomLatitude();
        double randomLongitude = this.randomLongitude();

        when(mockActivityModel.getTokenizedMapUrl()).thenReturn("%s|%s|%s");
        when(mockActivityModel.getCurrentLatitude()).thenReturn(randomLatitude);
        when(mockActivityModel.getCurrentLongitude()).thenReturn(randomLongitude );
        when(mockActivityModel.getYear()).thenReturn("2016");
        // Act
        this.mapsActivityController.handleStaleData();

        // Assert
        verify(this.mockMapsActivityView, times(1)).getMapCentre();

        verify(this.mockActivityModel, times(1)).getTokenizedMapUrl();
        verify(this.mockActivityModel, times(1)).getCurrentLatitude();
        verify(this.mockActivityModel, times(1)).getCurrentLongitude();
        verify(this.mockActivityModel, times(1)).getYear();

        verify(this.mockDataProvider, times(1)).cancelLastRequest();

        verify(this.mockMapsActivityView, times(1)).clearMap();

        verify(this.mockDataProvider, times(1)).requestData(randomLatitude + "|" + randomLongitude + "|" + "2016");
    }

    @Test(expected=AssertionError.class)
    public void testHandleStaleDataInvalidLatitude() throws Exception {
        // Arrange
        double invalidLatitude = 1111111111;
        double randomLongitude = this.randomLongitude();

        when(mockActivityModel.getCurrentLatitude()).thenReturn(invalidLatitude);
        when(mockActivityModel.getCurrentLongitude()).thenReturn(randomLongitude );

        // Act
        this.mapsActivityController.handleStaleData();

        // Assert
        verify(this.mockMapsActivityView, times(0)).getMapCentre();

        verify(this.mockActivityModel, times(0)).getTokenizedMapUrl();
        verify(this.mockActivityModel, times(0)).getCurrentLatitude();
        verify(this.mockActivityModel, times(0)).getCurrentLongitude();
        verify(this.mockActivityModel, times(0)).getYear();

        verify(this.mockDataProvider, times(0)).cancelLastRequest();

        verify(this.mockMapsActivityView, times(0)).clearMap();

        verify(this.mockDataProvider, times(0)).requestData(anyString());
    }

    @Test(expected=AssertionError.class)
    public void testHandleStaleDataInvalidLongitude() throws Exception {
        // Arrange
        double randomLatitude = this.randomLatitude();
        double invalidLongitude = 999999999;

        when(mockActivityModel.getCurrentLatitude()).thenReturn(randomLatitude);
        when(mockActivityModel.getCurrentLongitude()).thenReturn(invalidLongitude );

        // Act
        this.mapsActivityController.handleStaleData();

        // Assert
        verify(this.mockMapsActivityView, times(0)).getMapCentre();

        verify(this.mockActivityModel, times(0)).getTokenizedMapUrl();
        verify(this.mockActivityModel, times(0)).getCurrentLatitude();
        verify(this.mockActivityModel, times(0)).getCurrentLongitude();
        verify(this.mockActivityModel, times(0)).getYear();

        verify(this.mockDataProvider, times(0)).cancelLastRequest();

        verify(this.mockMapsActivityView, times(0)).clearMap();

        verify(this.mockDataProvider, times(0)).requestData(anyString());
    }

    @Test
    public void testHandleLocationFound() throws Exception {
        // Arrange
        double randomLatitude = this.randomLatitude();
        double randomLongitude = this.randomLongitude();

        when(mockLocationProvider.getTrackLocation()).thenReturn(new TrackLocation(randomLatitude,randomLongitude));

        // Act
        this.mapsActivityController.handleLocationFound();

        // Assert

        verify(this.mockActivityModel, times(1)).setCurrentLatitude(randomLatitude);
        verify(this.mockActivityModel, times(1)).setCurrentLongitude(randomLongitude);

        verify(this.mockMapsActivityView, times(1)).setMapPositionCurrent();
    }

    @Test(expected = AssertionError.class)
    public void testHandleLocationFoundInvalidLatitude() throws Exception {
        // Arrange
        double invalidLatitude = 222222222;
        double randomLongitude = this.randomLongitude();

        when(mockLocationProvider.getTrackLocation()).thenReturn(new TrackLocation(invalidLatitude,randomLongitude));

        // Act
        this.mapsActivityController.handleLocationFound();

        // Assert

        verify(this.mockActivityModel, times(0)).setCurrentLatitude(anyDouble());
        verify(this.mockActivityModel, times(0)).setCurrentLongitude(anyDouble());

        verify(this.mockMapsActivityView, times(0)).setMapPositionCurrent();
    }

    @Test(expected = AssertionError.class)
    public void testHandleLocationFoundInvalidLongitude() throws Exception {
        // Arrange
        double randomLatitude = this.randomLatitude();
        double invalidLongitude = 222222222;

        when(mockLocationProvider.getTrackLocation()).thenReturn(new TrackLocation(randomLatitude,invalidLongitude));

        // Act
        this.mapsActivityController.handleLocationFound();

        // Assert

        verify(this.mockActivityModel, times(0)).setCurrentLatitude(anyDouble());
        verify(this.mockActivityModel, times(0)).setCurrentLongitude(anyDouble());

        verify(this.mockMapsActivityView, times(0)).setMapPositionCurrent();
    }

    @Test
    public void testStart() throws Exception {
        // Arrange

        // Act
        this.mapsActivityController.start();

        // Assert
        verify(this.mockMapsActivityView, times(1)).initialize();

        verify(this.mockLocationProvider, times(1)).requestCurrentLocation();
    }



    @Test
    public void testHandleDataChange() throws Exception {
        // Arrange
        when(mockDataProvider.convertData()).thenReturn(mockDataSetMapMapper);
        when(mockDataProvider.getMapDataSetName()).thenReturn("name");
        when(mockActivityModel.getPositions()).thenReturn(mockHashMap);

        // Act
        this.mapsActivityController.handleDataChanged();

        // Assert
        verify(this.mockDataProvider, times(1)).convertData();

        verify(this.mockActivityModel, times(1)).setPositionsKey("name");
        verify(this.mockActivityModel, times(1)).getPositions();
        verify(this.mockHashMap, times(1)).put("name", mockDataSetMapMapper);
        verify(this.mockMapsActivityView, times(1)).buildHeatMap();

    }

    @Test
    public void testHandleDataChangeNullMapper() throws Exception {
        // Arrange
        when(mockDataProvider.convertData()).thenReturn(null);

        // Act
        this.mapsActivityController.handleDataChanged();

        // Assert
        verify(this.mockMapsActivityView, times(1)).clearMap();
    }

    @Test
    public void testHandleChangeYearRequested() throws Exception {
        // Arrange

        // Act
        this.mapsActivityController.handleChangeYearRequested();

        // Assert
        verify(this.mockMapsActivityView, times(1)).updateYear();
    }
}