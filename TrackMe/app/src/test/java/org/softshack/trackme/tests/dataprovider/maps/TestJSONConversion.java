package org.softshack.trackme.tests.dataprovider.maps;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.softshack.trackme.MapDataProvider;
import org.softshack.trackme.DataSetMapMapper;
import org.softshack.trackme.DataSetMapperFactory;
import org.softshack.trackme.JSONFactory;
import org.softshack.trackme.fakes.FakeContext;
import org.softshack.trackme.fakes.FakeTaskFactory;

import static org.junit.Assert.assertNull;

@RunWith(MockitoJUnitRunner.class)
public class TestJSONConversion {
    MapDataProvider mapDataProvider;

    @Before
    public void setup(){
        this.mapDataProvider = new MapDataProvider(
                new FakeTaskFactory(),
                new FakeContext(),
                new DataSetMapperFactory(),
                new JSONFactory());
    }

    @Test
    public void no_error_when_data_is_null() throws Exception {
        // Arrange
        this.mapDataProvider.setData(null);

        // Act
        DataSetMapMapper result = this.mapDataProvider.convertData();

        // Assert
        assertNull(result);
    }
}
