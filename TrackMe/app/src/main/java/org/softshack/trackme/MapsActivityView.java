package org.softshack.trackme;

import org.softshack.utils.obs.DefaultEvent;
import org.softshack.utils.obs.EventArgs;
import org.softshack.utils.obs.EventHandler;

public class MapsActivityView implements IMapsActivityView {

    private MapsActivityModel mapsActivityModel;
    private ITrackMap trackMap;
    private IButton yearButton;
    private IDialog yearPicker;

    private final DefaultEvent<EventArgs> onDataStale = new DefaultEvent<EventArgs>();
    private final DefaultEvent<EventArgs> onChangeYearRequested = new DefaultEvent<EventArgs>();

    public MapsActivityView(
            MapsActivityModel mapsActivityModel,
            ITrackMap trackMap,
            IButton yearButton,
            final IDialog yearPicker){
        this.setMapsActivityModel(mapsActivityModel);
        this.trackMap = trackMap;
        this.setYearButton(yearButton);
        this.setYearPicker(yearPicker);

        this.trackMap.getOnMapIdle().addHandler(new EventHandler<EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs args) {
                getOnDataStale().fire(this, EventArgs.Empty);
            }
        });

        this.getYearButton().getOnClicked().addHandler(new EventHandler<EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs args) {
                getOnChangeYearRequested().fire(this, EventArgs.Empty);
            }
        });

        this.getYearPicker().getOnYearChanged().addHandler(new EventHandler<EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs args) {
                getMapsActivityModel().setYear(yearPicker.getYear());
                getYearButton().setText(getMapsActivityModel().getYear());
                getOnDataStale().fire(this, EventArgs.Empty);
            }
        });
    }

    @Override
    public void initialize(){
        this.trackMap.allowUserToCentreMap(this.getMapsActivityModel().getAllowUserToCentreMap());
    }

    @Override
    public DefaultEvent<EventArgs> getOnDataStale() {
        return onDataStale;
    }


    @Override
    public DefaultEvent<EventArgs> getOnChangeYearRequested() {
        return onChangeYearRequested;
    }

    @Override
    public void setMapPositionCurrent() {
        trackMap.setMapPosition(
                this.getMapsActivityModel().getCurrentLatitude(),
                this.getMapsActivityModel().getCurrentLongitude());
    }

    @Override
    public void getMapCentre() {
        TrackLocation mapCentre = trackMap.getMapCentre();
        this.getMapsActivityModel().setCurrentLatitude(mapCentre.getLatitude());
        this.getMapsActivityModel().setCurrentLongitude(mapCentre.getLongitude());
    }

    @Override
    public void clearMap(){
        trackMap.clearMap();
    }

    @Override
    public void buildHeatMap() {
        this.trackMap.buildHeatMap(
                this.getMapsActivityModel().getPositions(), this.getMapsActivityModel().getPositionsKey());
    }

    @Override
    public void updateYear() {
        this.getYearPicker().show();
    }

    private MapsActivityModel getMapsActivityModel() {
        return mapsActivityModel;
    }

    private void setMapsActivityModel(MapsActivityModel mapsActivityModel) {
        this.mapsActivityModel = mapsActivityModel;
    }

    private IDialog getYearPicker() {
        return yearPicker;
    }

    private void setYearPicker(IDialog yearPicker) {
        this.yearPicker = yearPicker;
    }

    private IButton getYearButton() {
        return yearButton;
    }

    private void setYearButton(IButton yearButton) {
        this.yearButton = yearButton;
    }
}
