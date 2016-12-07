package org.softshack.trackme.tests.dataprovider.graphs;

import com.github.mikephil.charting.data.BarEntry;
import com.google.maps.android.heatmaps.WeightedLatLng;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.softshack.trackme.GraphDataProvider;
import org.softshack.trackme.DataSetGraphMapper;
import org.softshack.trackme.DataSetMapperFactory;
import org.softshack.trackme.JSONFactory;
import org.softshack.trackme.fakes.FakeContext;
import org.softshack.trackme.interfaces.IDataTask;
import org.softshack.trackme.interfaces.ITaskFactory;
import org.softshack.utils.obs.DefaultEvent;
import org.softshack.utils.obs.EventArgs;

import java.util.ArrayList;

import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TestMethodCalls {
    GraphDataProvider graphDataProvider;

    @Mock
    ITaskFactory mockTaskFactory;

    @Mock
    IDataTask mockTask;

    @Mock
    DefaultEvent<EventArgs> mockEvent;

    @Mock
    DataSetGraphMapper mockDataSetGraphMapper;

    @Mock
    DataSetMapperFactory mockDataSetMapperFactory;

    @Mock
    JSONFactory mockJSONFactory;

    String fakeRawData = "[{\"latitude\":50.841,\"longitude\":-0.12068468468468,\"avgYearPostcodeNorm\":20}]";
    ArrayList<BarEntry> weightedArray = new ArrayList<BarEntry>();

    @Before
    public void setup(){
        this.graphDataProvider = new GraphDataProvider(
                mockTaskFactory,
                new FakeContext(),
                mockDataSetMapperFactory,
                mockJSONFactory);
    }

    @Test
    public void testConvertDataNullWhenNoData() throws Exception {
        // Arrange
        this.graphDataProvider.setData(null);

        // Act
        DataSetGraphMapper dataSetGraphMapper = this.graphDataProvider.convertData();

        // Assert
        verify(this.mockJSONFactory, times(0)).readGraphItems(anyString());
        verify(this.mockDataSetMapperFactory, times(0)).createDataSetGraphMapper(Mockito.<ArrayList<BarEntry>>any(), anyString());
        assertNull(dataSetGraphMapper);
    }

    @Test
    public void testRequestData() throws Exception {
        // Arrange
        when(mockTask.getOnTaskFinished()).thenReturn(mockEvent);
        when(mockTask.getOnTaskStarted()).thenReturn(mockEvent);

        when(mockTaskFactory.createGraphDataTask()).thenReturn(mockTask);

        this.graphDataProvider = new GraphDataProvider(
                mockTaskFactory,
                new FakeContext(),
                mockDataSetMapperFactory,
                mockJSONFactory);

        // Act
        this.graphDataProvider.requestData("Some request");

        // Assert
        verify(this.mockTaskFactory, times(1)).createGraphDataTask();

        verify(this.mockTask, times(1)).getOnTaskFinished();

        verify(this.mockTask, times(1)).getOnTaskStarted();

        verify(this.mockTask, times(1)).execute("Some request");
    }
}
