package org.softshack;

import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

/**
 * Base class for all {@link Robolectric} test suites.
 */
@RunWith(RobolectricTestRunner.class)
@Config(
        constants = BuildConfig.class,
        packageName = BuildConfig.CANONICAL_APPLICATION_ID,
        sdk = BuildConfig.TEST_TARGET_SDK_VERSION
)
public abstract class BaseRobolectricTest {}
