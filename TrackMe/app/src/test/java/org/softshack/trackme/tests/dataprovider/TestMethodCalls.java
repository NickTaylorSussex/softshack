package org.softshack.trackme.tests.dataprovider;

import com.google.maps.android.heatmaps.WeightedLatLng;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.softshack.trackme.DataProvider;
import org.softshack.trackme.DataSetMapper;
import org.softshack.trackme.DataSetMapperFactory;
import org.softshack.trackme.DataTask;
import org.softshack.trackme.JSONFactory;
import org.softshack.trackme.TaskFactory;
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
    DataProvider dataProvider;

    @Mock
    ITaskFactory mockTaskFactory;

    @Mock
    IDataTask mockTask;

    @Mock
    DefaultEvent<EventArgs> mockEvent;

    @Mock
    DataSetMapper mockDataSetMapper;

    @Mock
    DataSetMapperFactory mockDataSetMapperFactory;

    @Mock
    JSONFactory mockJSONFactory;

    String fakeRawData = "[{\"latitude\":50.841,\"longitude\":-0.12068468468468,\"avgYearPostcodeNorm\":20}]";
    ArrayList<WeightedLatLng> weightedArray = new ArrayList<WeightedLatLng>();

    @Before
    public void setup(){
        this.dataProvider = new DataProvider(
                mockTaskFactory,
                new FakeContext(),
                mockDataSetMapperFactory,
                mockJSONFactory);
    }

    @Test
    public void testConvertDataNullWhenNoData() throws Exception {
        // Arrange
        this.dataProvider.setData(null);

        // Act
        DataSetMapper dataSetMapper = this.dataProvider.convertData();

        // Assert
        verify(this.mockJSONFactory, times(0)).readItems(anyString());
        verify(this.mockDataSetMapperFactory, times(0)).createDataSetMapper(Mockito.<ArrayList<WeightedLatLng>>any(), anyString());
        assertNull(dataSetMapper);
    }

    @Test
    public void testRequestData() throws Exception {
        // Arrange
        when(mockTask.getOnTaskFinished()).thenReturn(mockEvent);

        when(mockTaskFactory.createMapDataTask()).thenReturn(mockTask);

        this.dataProvider = new DataProvider(
                mockTaskFactory,
                new FakeContext(),
                mockDataSetMapperFactory,
                mockJSONFactory);

        // Act
        this.dataProvider.requestData("Some request");

        // Assert
        verify(this.mockTaskFactory, times(1)).createMapDataTask();

        verify(this.mockTask, times(1)).getOnTaskFinished();

        verify(this.mockTask, times(1)).execute("Some request");
    }
}
