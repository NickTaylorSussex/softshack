package org.softshack.trackme.tests.dataprovider;

import android.os.AsyncTask;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.softshack.trackme.DataProvider;
import org.softshack.trackme.MapDataSet;
import org.softshack.trackme.fakes.FakeContext;
import org.softshack.trackme.interfaces.ITaskFactory;

import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;

public class TestMethodCalls {
    DataProvider dataProvider;

    @Mock
    ITaskFactory mockTaskFactory;
    AsyncTask<String,Integer,String> mockTask;

    @Before
    public void setup(){
//        when(mockTaskFactory.createMapDataTask())
//                .thenReturn(mockTask);

        this.dataProvider = new DataProvider(mockTaskFactory, new FakeContext());
    }

//    @Test
//    public void no_error_when_data_is_null() throws Exception {
//        // Arrange
//        this.dataProvider.setData(null);
//
//        // Act
//        MapDataSet result = this.dataProvider.convertData();
//
//        // Assert
//        assertNull(result);
//    }

}
