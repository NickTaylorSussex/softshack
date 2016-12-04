package org.softshack.trackme.tests.activitycontroller.graphs;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.softshack.trackme.DataSetGraphMapper;
import org.softshack.trackme.GraphsActivityController;
import org.softshack.trackme.GraphsActivityModel;
import org.softshack.trackme.interfaces.IGraphDataProvider;
import org.softshack.trackme.interfaces.IGraphsActivityView;
import org.softshack.trackme.pocos.GraphsActivityControllerComponents;
import org.softshack.utils.log.ILogger;
import org.softshack.utils.obs.DefaultEvent;
import org.softshack.utils.obs.EventArgs;

import java.util.HashMap;
import java.util.Random;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TestMethodCalls {
    GraphsActivityController graphsActivityController;

    @Mock
    IGraphsActivityView mockGraphsActivityView;

    @Mock
    GraphsActivityModel mockActivityModel;

    @Mock
    IGraphDataProvider mockDataProvider;

    @Mock
    DataSetGraphMapper mockDataSetGraphMapper;

    @Mock
    DefaultEvent<EventArgs> mockOnDataStaleEvent;

    @Mock
    DefaultEvent<EventArgs> mockOnChangeYearRequestedEvent;

    @Mock
    DefaultEvent<EventArgs> mockOnLocationFoundEvent;

    @Mock
    DefaultEvent<EventArgs> mockOnDataChangedEvent;

    @Mock
    HashMap<String, DataSetGraphMapper> mockHashMap;

    @Mock
    GraphsActivityControllerComponents mockGraphsActivityControllerComponents;

    @Mock
    ILogger mockLogger;

    @Mock
    DefaultEvent<EventArgs> mockOnHistoryRequestedEvent;

    @Before
    public void setup() throws Exception {
        when(mockGraphsActivityControllerComponents.getDataProvider()).thenReturn(mockDataProvider);
        when(mockGraphsActivityControllerComponents.getGraphsActivityView()).thenReturn(mockGraphsActivityView);
        when(mockGraphsActivityControllerComponents.getActivityModel()).thenReturn(mockActivityModel);
        when(mockGraphsActivityControllerComponents.getLogger()).thenReturn(mockLogger);

        when(mockDataProvider.getOnDataChanged()).thenReturn(mockOnDataChangedEvent);
        when(mockDataProvider.getOnTaskStarted()).thenReturn(mockOnDataChangedEvent);

        this.graphsActivityController = new GraphsActivityController(
                mockGraphsActivityControllerComponents);
    }

    @Test
    public void testConstructor() throws Exception {
        // Arrange

        // Act

        // Assert
        verify(this.mockDataProvider, times(1)).getOnTaskStarted();
        verify(this.mockDataProvider, times(1)).getOnDataChanged();
    }

    @Test
    public void testStart() throws Exception {
        // Arrange
        when(mockActivityModel.getCurrentLatitude()).thenReturn(0d);
        when(mockActivityModel.getCurrentLongitude()).thenReturn(0d);
        when(mockActivityModel.getTokenizedGraphUrl()).thenReturn("");

        // Act
        this.graphsActivityController.start();

        // Assert
        verify(this.mockActivityModel, times(1)).getCurrentLatitude();
        verify(this.mockActivityModel, times(1)).getCurrentLongitude();
        verify(this.mockActivityModel, times(1)).getTokenizedGraphUrl();
        verify(this.mockDataProvider, times(1)).cancelLastRequest();
        verify(this.mockDataProvider, times(1)).requestData(anyString());
    }

    @Test
    public void testHandleDataChange() throws Exception {
        // Arrange
        when(mockDataProvider.convertData()).thenReturn(mockDataSetGraphMapper);
        when(mockDataProvider.getGraphDataSetName()).thenReturn("name");
        when(mockActivityModel.getGraphs()).thenReturn(mockHashMap);

        // Act
        this.graphsActivityController.handleDataChanged();

        // Assert
        verify(this.mockDataProvider, times(1)).convertData();

        verify(this.mockActivityModel, times(1)).setGraphsKey("name");
        verify(this.mockActivityModel, times(1)).getGraphs();
        verify(this.mockHashMap, times(1)).put("name", mockDataSetGraphMapper);
        verify(this.mockGraphsActivityView, times(1)).buildGraph();
    }

    @Test
    public void testHandleDataChangeNullMapper() throws Exception {
        // Arrange
        when(mockDataProvider.convertData()).thenReturn(null);

        // Act
        this.graphsActivityController.handleDataChanged();

        // Assert
        verify(this.mockGraphsActivityView, times(1)).clearGraph();
    }
}