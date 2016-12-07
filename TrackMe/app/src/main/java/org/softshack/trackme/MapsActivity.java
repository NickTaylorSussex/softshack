package org.softshack.trackme;

import android.app.Dialog;
import android.content.Intent;
import android.location.LocationManager;
import android.widget.Button;
import android.widget.Toast;

import org.softshack.trackme.adapters.AndroidLogAdapter;
import org.softshack.trackme.adapters.ButtonAdapter;
import org.softshack.trackme.adapters.ContextAdapter;
import org.softshack.trackme.adapters.GoogleMapAdapter;
import org.softshack.trackme.adapters.IntentAdapter;
import org.softshack.trackme.adapters.LocationManagerAdapter;
import org.softshack.trackme.adapters.PermissionAdapter;
import org.softshack.trackme.adapters.YearPickerDialogAdapter;
import org.softshack.trackme.interfaces.IButton;
import org.softshack.trackme.interfaces.IMapsActivityView;
import org.softshack.trackme.interfaces.ITrackMap;
import org.softshack.trackme.pocos.LocationManagerComponents;
import org.softshack.trackme.pocos.MapsActivityControllerComponents;
import org.softshack.trackme.pocos.MapsActivityViewComponents;
import org.softshack.utils.log.ILogger;

public class MapsActivity extends BaseActivity {

    /**
     * Starts the main app.
     */
    @Override
    public void start() {
        // Set up the logging.
        ILogger logger = new AndroidLogAdapter();

        // Adapt a google map to an abstraction to be used in the application.
        ITrackMap trackMap = new GoogleMapAdapter(getMap());

        // Create a data model.
        MapsActivityModel activityModel = new MapsActivityModel();

        // Adapt a button to an abstraction to be used in the application.
        IButton yearButton = new ButtonAdapter((Button)this.findViewById(R.id.yearButton));

        IButton historyButton = new ButtonAdapter((Button)this.findViewById(R.id.historyButton));

        Intent graphsIntent = new Intent(this, GraphsActivity.class);

        logger.LogDebug("start", "Attempting create of MapsActivityViewComponents");

        // Create a view which is an abstraction of the user interface.
        MapsActivityViewComponents mapsActivityViewComponents = new MapsActivityViewComponents(
                logger,
                activityModel,
                trackMap,
                yearButton,
                new YearPickerDialogAdapter(new Dialog(MapsActivity.this)),
                historyButton,
                new IntentAdapter(this, graphsIntent));


        logger.LogDebug("start", "Attempting create of IMapsActivityView");

        IMapsActivityView mapsActivityView = new MapsActivityView(mapsActivityViewComponents);

        logger.LogDebug("start", "Attempting create of LocationManagerComponents");

        LocationManagerComponents locationManagerComponents = new LocationManagerComponents(
                (LocationManager) getSystemService(LOCATION_SERVICE),
                new PermissionAdapter(
                        getPackageManager(),
                        getApplicationContext()));

        logger.LogDebug("start", "Attempting create of MapsActivityControllerComponents");

        // Create a controller to manage the data requests and command the user interface.
        MapsActivityControllerComponents mapsActivityControllerComponents =
                new MapsActivityControllerComponents(
                        logger,
                        mapsActivityView,
                        activityModel,
                        new LocationProvider(new LocationManagerAdapter(locationManagerComponents)),
                        new MapDataProvider(
                                new TaskFactory(),
                                new ContextAdapter(getApplicationContext()),
                                new DataSetMapperFactory(),
                                new JSONFactory())
                );

        logger.LogDebug("start", "Attempting create of MapsActivityController");
        MapsActivityController mapsActivityController =
                new MapsActivityController(mapsActivityControllerComponents);


        // Initiate the main controller.
        try {
            logger.LogDebug("start", "Attempting start of MapsActivityController");
            mapsActivityController.start();
        } catch (SecurityException e) {
            logger.LogError("start", e);
            Toast.makeText(this, "Security error. Please allow the fine location permissions.", Toast.LENGTH_SHORT).show();
        }
    }
}

