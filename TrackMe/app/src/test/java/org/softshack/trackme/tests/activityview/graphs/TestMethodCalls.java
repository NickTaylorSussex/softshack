package org.softshack.trackme.tests.activityview.graphs;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.softshack.trackme.DataSetGraphMapper;
import org.softshack.trackme.GraphsActivityModel;
import org.softshack.trackme.GraphsActivityView;
import org.softshack.trackme.pocos.GraphsActivityViewComponents;
import org.softshack.trackme.interfaces.ITrackGraph;
import org.softshack.utils.log.ILogger;
import org.softshack.utils.obs.DefaultEvent;
import org.softshack.utils.obs.EventArgs;

import java.util.HashMap;
import java.util.UUID;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TestMethodCalls {

    GraphsActivityView graphsActivityView;

    @Mock
    GraphsActivityViewComponents mockGraphsActivityViewComponents;

    @Mock
    GraphsActivityModel mockActivityModel;

    @Mock
    ITrackGraph mockTrackGraph;

    @Mock
    DefaultEvent<EventArgs> mockEvent;

    @Mock
    HashMap<String, DataSetGraphMapper> mockGraphs;

    @Mock
    ILogger mockLogger;

    @Before
    public void setup() throws Exception {
        when(mockGraphsActivityViewComponents.getActivityModel()).thenReturn(mockActivityModel);
        when(mockGraphsActivityViewComponents.getTrackGraph()).thenReturn(mockTrackGraph);
        when(mockGraphsActivityViewComponents.getLogger()).thenReturn(mockLogger);
    }

    @Test
    public void testClearGraph() throws Exception{
        // Arrange
        this.graphsActivityView = new GraphsActivityView(mockGraphsActivityViewComponents);

        // Act
        this.graphsActivityView.clearGraph();

        // Assert
        verify(this.mockTrackGraph, times(1)).clearGraph();
    }

    @Test
    public void testBuildGraph() throws Exception{
        // Arrange
        String fakeKey = UUID.randomUUID().toString();

        this.graphsActivityView = new GraphsActivityView(mockGraphsActivityViewComponents);

        when(this.mockActivityModel.getGraphs()).thenReturn(mockGraphs);
        when(this.mockActivityModel.getGraphsKey()).thenReturn(fakeKey);

        // Act
        this.graphsActivityView.buildGraph();

        // Assert
        verify(this.mockTrackGraph, times(1)).buildGraph(this.mockGraphs, fakeKey);
    }
}
