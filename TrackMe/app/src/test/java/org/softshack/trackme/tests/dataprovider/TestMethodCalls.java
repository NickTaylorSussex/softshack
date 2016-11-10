package org.softshack.trackme.tests.dataprovider;

import com.google.maps.android.heatmaps.WeightedLatLng;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.softshack.trackme.DataProvider;
import org.softshack.trackme.DataSetMapper;
import org.softshack.trackme.DataSetMapperFactory;
import org.softshack.trackme.fakes.FakeContext;
import org.softshack.trackme.interfaces.IDataTask;
import org.softshack.trackme.interfaces.ITaskFactory;
import org.softshack.utils.obs.DefaultEvent;
import org.softshack.utils.obs.EventArgs;

import java.util.ArrayList;

import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyCollectionOf;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.anySetOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
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
    ArrayList<WeightedLatLng> mockData;

    String fakeRawData = "rawData";

    @Before
    public void setup(){
        this.dataProvider = new DataProvider(mockTaskFactory, new FakeContext(), mockDataSetMapperFactory);
    }

    @Test
    public void testConvertData() throws Exception {
        // Arrange
        when(mockDataSetMapperFactory.createDataSetMapper(mockData, fakeRawData)).thenReturn(mockDataSetMapper);

        this.dataProvider.setData(fakeRawData);

        // Act
        DataSetMapper dataSetMapper = this.dataProvider.convertData();

        // Assert
        verify(this.mockDataSetMapperFactory, times(1)).createDataSetMapper(mockData, anyString());
    }

    @Test
    public void testRequestData() throws Exception {
        // Arrange
        when(mockTask.getOnTaskFinished()).thenReturn(mockEvent);

        when(mockTaskFactory.createMapDataTask()).thenReturn(mockTask);

        this.dataProvider = new DataProvider(mockTaskFactory, new FakeContext(), mockDataSetMapperFactory);

        // Act
        this.dataProvider.requestData("Some request");

        // Assert
        verify(this.mockTaskFactory, times(1)).createMapDataTask();

        verify(this.mockTask, times(1)).getOnTaskFinished();

        verify(this.mockTask, times(1)).execute("Some request");
    }
}
