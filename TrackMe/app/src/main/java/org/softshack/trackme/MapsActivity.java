package org.softshack.trackme;

import android.app.Dialog;
import android.location.LocationManager;
import android.widget.Button;

public class MapsActivity extends BaseDemoActivity {

    @Override
    public void start() {
        ITrackMap trackMap = new GoogleMapAdapter(getMap());

        MapsActivityModel mapsActivityModel = new MapsActivityModel();

        IButton yearButton = new ButtonAdapter((Button)this.findViewById(R.id.yearButton));

        IMapsActivityView mapsActivityView = new MapsActivityView(
                mapsActivityModel,
                trackMap,
                yearButton,
                new YearPickerDialogAdapter(new Dialog(MapsActivity.this)));

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
                                new ContextAdapter(getApplicationContext())));

        mapsActivityController.start();
    }
}

