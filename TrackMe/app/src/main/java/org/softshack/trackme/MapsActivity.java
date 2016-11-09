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

