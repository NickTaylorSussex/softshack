package org.softshack.trackme.adapters;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.maps.android.heatmaps.HeatmapTileProvider;

import org.softshack.trackme.DataSetMapMapper;
import org.softshack.trackme.TrackLocation;
import org.softshack.trackme.interfaces.ITrackMap;
import org.softshack.utils.obs.DefaultEvent;
import org.softshack.utils.obs.EventArgs;

import java.util.HashMap;

public class GoogleMapAdapter implements ITrackMap, GoogleMap.OnCameraIdleListener {

    GoogleMap googleMap;
    private TileOverlay mapOverlay;

    private final DefaultEvent<EventArgs> onMapIdle = new DefaultEvent<EventArgs>();

    public GoogleMapAdapter(GoogleMap googleMap){
        this.googleMap = googleMap;

        this.googleMap.setOnCameraIdleListener(this);
    }

    @Override
    public void allowUserToCentreMap(Boolean allow) throws SecurityException{
        this.googleMap.setMyLocationEnabled(allow);
    }

    @Override
    public void onCameraIdle() {
        this.onMapIdle.fire(this, EventArgs.Empty);
    }

    public DefaultEvent<EventArgs> getOnMapIdle() {
        return onMapIdle;
    }

    @Override
    public void setMapPosition(double latitude, double longitude){
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 15));
    }

    @Override
    public TrackLocation getMapCentre(){
        LatLng mapCentre = this.googleMap.getProjection().getVisibleRegion().latLngBounds.getCenter();
        return new TrackLocation(mapCentre.latitude, mapCentre.longitude);
    }

    @Override
    public void clearMap(){
        if(mapOverlay != null) {
            mapOverlay.remove();
        }
    }

    @Override
    public void buildHeatMap(HashMap<String, DataSetMapMapper> positions, String key){
        HeatmapTileProvider heatmap = new HeatmapTileProvider.Builder().weightedData(
                positions.get(key).getData()).build();

        heatmap.setRadius(HeatmapTileProvider.DEFAULT_RADIUS);

        this.mapOverlay = this.googleMap.addTileOverlay(new TileOverlayOptions().tileProvider(heatmap));

        this.mapOverlay.clearTileCache();
    }

    @Override
    public float getCurrentZoom() {
        return googleMap.getCameraPosition().zoom;
    }
}
