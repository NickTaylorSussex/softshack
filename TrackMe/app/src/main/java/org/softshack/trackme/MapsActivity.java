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
import org.softshack.trackme.adapters.LocationManagerAdapter;
import org.softshack.trackme.adapters.PermissionAdapter;
import org.softshack.trackme.adapters.YearPickerDialogAdapter;
import org.softshack.trackme.interfaces.IButton;
import org.softshack.trackme.interfaces.IMapsActivityView;
import org.softshack.trackme.interfaces.ITrackMap;
import org.softshack.trackme.pocos.GraphsActivityControllerComponents;
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
        ActivityModel activityModel = new ActivityModel();

        // Adapt a button to an abstraction to be used in the application.
        IButton yearButton = new ButtonAdapter((Button)this.findViewById(R.id.yearButton));

        // Create a view which is an abstraction of the user interface.
        MapsActivityViewComponents mapsActivityViewComponents = new MapsActivityViewComponents(
                logger,
                activityModel,
                trackMap,
                yearButton,
                new YearPickerDialogAdapter(new Dialog(MapsActivity.this)));

        IMapsActivityView mapsActivityView = new MapsActivityView(mapsActivityViewComponents);

        LocationManagerComponents locationManagerComponents = new LocationManagerComponents(
                (LocationManager) getSystemService(LOCATION_SERVICE),
                new PermissionAdapter(
                        getPackageManager(),
                        getApplicationContext()));


        // Create a controller to manage the data requests and command the user interface.
        MapsActivityControllerComponents mapsActivityControllerComponents =
                new MapsActivityControllerComponents(
                        logger,
                        mapsActivityView,
                        activityModel,
                        new LocationProvider(new LocationManagerAdapter(locationManagerComponents)),
                        new DataProvider(
                                new TaskFactory(),
                                new ContextAdapter(getApplicationContext()),
                                new DataSetMapperFactory(),
                                new JSONFactory())
                );

        MapsActivityController mapsActivityController =
                new MapsActivityController(mapsActivityControllerComponents);

        GraphsActivityView graphsActivityView = new GraphsActivityView();

        GraphsActivityControllerComponents graphsActivityControllerComponents =
                new GraphsActivityControllerComponents(
                        graphsActivityView,
                        activityModel);

        GraphsActivityController graphsActivityController =
                new GraphsActivityController(
                        graphsActivityControllerComponents );


        // Initiate the controller.
        try {
            mapsActivityController.start();
        } catch (SecurityException e) {

            Toast.makeText(this, "Security error. Please allow the fine location permissions.", Toast.LENGTH_SHORT).show();
        }
    }

    public void onHistoryClicked(android.view.View view) {
        Intent intent = new Intent(this, GraphsActivity.class);
        startActivity(intent);
    }
}

