package org.softshack.trackme.tests.dataprovider;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.softshack.trackme.DataProvider;
import org.softshack.trackme.DataSetMapper;
import org.softshack.trackme.DataSetMapperFactory;
import org.softshack.trackme.JSONFactory;
import org.softshack.trackme.fakes.FakeContext;
import org.softshack.trackme.fakes.FakeTaskFactory;

@RunWith(MockitoJUnitRunner.class)
public class TestJSONConversion {
    DataProvider dataProvider;

    @Before
    public void setup(){
        this.dataProvider = new DataProvider(
                new FakeTaskFactory(),
                new FakeContext(),
                new DataSetMapperFactory(),
                new JSONFactory());
    }

    @Test
    public void no_error_when_data_is_null() throws Exception {
        // Arrange
        this.dataProvider.setData(null);

        // Act
        DataSetMapper result = this.dataProvider.convertData();

        // Assert
        assertNull(result);
    }


}
