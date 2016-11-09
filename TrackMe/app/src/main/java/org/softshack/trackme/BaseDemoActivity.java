/*
 * Copyright 2013 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.softshack.trackme;

import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

public abstract class BaseDemoActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;

    protected int getLayoutId() {
        return R.layout.activity_maps;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        setUpMap();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMap();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        if (mMap != null) {
            return;
        }
        mMap = map;

        ITrackMap trackMap = new GoogleMapAdapter(map);

        MapsActivityModel mapsActivityModel = new MapsActivityModel();

        IMapsActivityView mapsActivityView = new MapsActivityView(mapsActivityModel,trackMap);

        MapsActivityController mapsActivityController  =
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

    private void setUpMap() {
        ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMapAsync(this);
    }

    protected GoogleMap getMap() {
        return mMap;
    }
}
