package org.softshack.trackme;

import org.softshack.utils.obs.DefaultEvent;
import org.softshack.utils.obs.EventArgs;
import org.softshack.utils.obs.EventHandler;

public class MapsActivityView implements IMapsActivityView {

    MapsActivityModel mapsActivityModel;
    ITrackMap trackMap;

    private final DefaultEvent<EventArgs> onMapIdle = new DefaultEvent<EventArgs>();

    public MapsActivityView(MapsActivityModel mapsActivityModel, ITrackMap trackMap ){
        this.mapsActivityModel = mapsActivityModel;
        this.trackMap = trackMap;

        this.trackMap.getOnMapIdle().addHandler(new EventHandler<EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs args) {
                onMapIdle.fire(this, EventArgs.Empty);
            }
        });
    }

    @Override
    public void initialize(){
        this.trackMap.allowUserToCentreMap(this.mapsActivityModel.getAllowUserToCentreMap());
    }

    @Override
    public DefaultEvent<EventArgs> getOnMapIdle() {
        return onMapIdle;
    }

    @Override
    public void setMapPositionCurrent() {
        trackMap.setMapPosition(
                this.mapsActivityModel.getCurrentLatitude(),
                this.mapsActivityModel.getCurrentLongitude());
    }

    @Override
    public void getMapCentre() {
        TrackLocation mapCentre = trackMap.getMapCentre();
        this.mapsActivityModel.setCurrentLatitude(mapCentre.getLatitude());
        this.mapsActivityModel.setCurrentLongitude(mapCentre.getLongitude());
    }

    @Override
    public void clearMap(){
        trackMap.clearMap();
    }

    @Override
    public void buildHeatMap(){
        this.trackMap.buildHeatMap(
                this.mapsActivityModel.getPositions(), this.mapsActivityModel.getPositionsKey());
    }
}
