package org.softshack.trackme;

import android.app.Dialog;
import android.location.LocationManager;
import android.widget.Button;

import org.softshack.trackme.adapters.ButtonAdapter;
import org.softshack.trackme.adapters.ContextAdapter;
import org.softshack.trackme.adapters.GoogleMapAdapter;
import org.softshack.trackme.adapters.LocationManagerAdapter;
import org.softshack.trackme.adapters.PermissionAdapter;
import org.softshack.trackme.adapters.YearPickerDialogAdapter;
import org.softshack.trackme.interfaces.IButton;
import org.softshack.trackme.interfaces.IMapsActivityView;
import org.softshack.trackme.interfaces.ITrackMap;

public class MapsActivity extends BaseDemoActivity {

    /**
     * Starts the main app.
     */
    @Override
    public void start() {
        // Adapt a google map to an abstraction to be used in the application.
        ITrackMap trackMap = new GoogleMapAdapter(getMap());

        // Create a data model.
        MapsActivityModel mapsActivityModel = new MapsActivityModel();

        // Adapt a button to an abstraction to be used in the application.
        IButton yearButton = new ButtonAdapter((Button)this.findViewById(R.id.yearButton));

        // Create a view which is an abstraction of the user interface.
        ViewComponents viewComponents = new ViewComponents(
                mapsActivityModel,
                trackMap,
                yearButton,
                new YearPickerDialogAdapter(new Dialog(MapsActivity.this)));

        IMapsActivityView mapsActivityView = new MapsActivityView(viewComponents);

        // Create a controller to manage the data requests and command the user interface.
        MapsActivityController mapsActivityController =
                new MapsActivityController(
                        mapsActivityView,
                        mapsActivityModel,
                        new LocationProvider(new LocationManagerAdapter(
                                (LocationManager) getSystemService(LOCATION_SERVICE),
                                new PermissionAdapter(
                                        getPackageManager(),
                                        getApplicationContext()))),
                        new DataProvider(
                                new TaskFactory(),
                                new ContextAdapter(getApplicationContext()),
                                new DataSetMapperFactory()));

        // Initiate the controller.
        mapsActivityController.start();
    }
}

