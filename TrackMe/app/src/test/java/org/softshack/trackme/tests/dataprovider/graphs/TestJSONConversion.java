package org.softshack.trackme.tests.dataprovider.graphs;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.softshack.trackme.GraphDataProvider;
import org.softshack.trackme.DataSetGraphMapper;
import org.softshack.trackme.DataSetMapperFactory;
import org.softshack.trackme.JSONFactory;
import org.softshack.trackme.fakes.FakeContext;
import org.softshack.trackme.fakes.FakeTaskFactory;

import static org.junit.Assert.assertNull;

@RunWith(MockitoJUnitRunner.class)
public class TestJSONConversion {
    GraphDataProvider graphDataProvider;

    @Before
    public void setup(){
        this.graphDataProvider = new GraphDataProvider(
                new FakeTaskFactory(),
                new FakeContext(),
                new DataSetMapperFactory(),
                new JSONFactory());
    }

    @Test
    public void no_error_when_data_is_null() throws Exception {
        // Arrange
        this.graphDataProvider.setData(null);

        // Act
        DataSetGraphMapper result = this.graphDataProvider.convertData();

        // Assert
        assertNull(result);
    }
}