package org.softshack.trackme.tests.dataprovider;

import org.json.JSONArray;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import org.mockito.Mock;
import org.softshack.trackme.DataProvider;
import org.softshack.trackme.MapDataSet;
import org.softshack.trackme.fakes.FakeContext;
import org.softshack.trackme.fakes.FakeTaskFactory;

public class TestJSONConversion {
    DataProvider dataProvider;

    @Before
    public void setup(){
        this.dataProvider = new DataProvider(new FakeTaskFactory(), new FakeContext());
    }

    @Test
    public void no_error_when_data_is_null() throws Exception {
        // Arrange
        this.dataProvider.setData(null);

        // Act
        MapDataSet result = this.dataProvider.convertData();

        // Assert
        assertNull(result);
    }


}
