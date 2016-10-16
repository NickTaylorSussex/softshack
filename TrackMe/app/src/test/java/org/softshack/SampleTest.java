package org.softshack;

// Static imports for assertion methods
import org.junit.Before;
import org.junit.Test;
import org.robolectric.Robolectric;


import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

public class SampleTest extends BaseRobolectricTest {

    // @Before => JUnit 4 annotation that specifies this method should run before each test is run
    // Useful to do setup for objects that are needed in the test
    @Before
    public void setup() {
        // Convenience method to run MainActivity through the Activity Lifecycle methods:
        // onCreate(...) => onStart() => onPostCreate(...) => onResume()

    }

    // @Test => JUnit 4 annotation specifying this is a test to be run
    // The test simply checks that our TextView exists and has the text "Hello world!"
    @Test
    public void sampleTest() {
        assertTrue(true);
    }
}