package org.softshack.trackme;

import org.softshack.trackme.interfaces.IButton;
import org.softshack.trackme.interfaces.IDialog;
import org.softshack.trackme.interfaces.IMapsActivityView;
import org.softshack.trackme.interfaces.ITrackMap;
import org.softshack.trackme.pocos.MapsActivityViewComponents;
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

    /**
     * Constructor
     * @param mapsActivityViewComponents Abstracted view components.
     */
    public MapsActivityView(MapsActivityViewComponents mapsActivityViewComponents){
        this.setMapsActivityModel(mapsActivityViewComponents.getMapsActivityModel());
        this.trackMap = mapsActivityViewComponents.getTrackMap();
        this.setYearButton(mapsActivityViewComponents.getYearButton());
        this.setYearPicker(mapsActivityViewComponents.getYearPicker());

        // Set handler for stale data notification and notify listeners.
        this.trackMap.getOnMapIdle().addHandler(new EventHandler<EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs args) {
                getOnDataStale().fire(this, EventArgs.Empty);
            }
        });

        // Set handler for year button clicked and notify listeners of a change in year request.
        this.getYearButton().getOnClicked().addHandler(new EventHandler<EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs args) {
                getOnChangeYearRequested().fire(this, EventArgs.Empty);
            }
        });

        // Set the handler for when the year has changed and notify listeners.
        this.getYearPicker().getOnYearChanged().addHandler(new EventHandler<EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs args) {
                getMapsActivityModel().setYear(yearPicker.getYear());
                getYearButton().setText(getMapsActivityModel().getYear());
                getOnDataStale().fire(this, EventArgs.Empty);
            }
        });
    }

    /**
     * Initial set up for first time map is displayed.
     */
    @Override
    public void initialize(){
        // Enable of disable the centre-map controls.
        this.trackMap.allowUserToCentreMap(this.getMapsActivityModel().getAllowUserToCentreMap());
    }

    /**
     * @return Stale data event.
     */
    @Override
    public DefaultEvent<EventArgs> getOnDataStale() {
        return onDataStale;
    }

    /**
     * @return Change year event.
     */
    @Override
    public DefaultEvent<EventArgs> getOnChangeYearRequested() {
        return onChangeYearRequested;
    }

    /**
     * Moves the map to the current coordinates.
     */
    @Override
    public void setMapPositionCurrent() {
        trackMap.setMapPosition(
                this.getMapsActivityModel().getCurrentLatitude(),
                this.getMapsActivityModel().getCurrentLongitude());
    }

    /**
     * Stores the location as at the centre of the map.
     */
    @Override
    public void getMapCentre() {
        TrackLocation mapCentre = trackMap.getMapCentre();
        this.getMapsActivityModel().setCurrentLatitude(mapCentre.getLatitude());
        this.getMapsActivityModel().setCurrentLongitude(mapCentre.getLongitude());
    }

    /**
     * Clears the map of any overlay data.
     */
    @Override
    public void clearMap(){
        trackMap.clearMap();
    }

    /**
     * Builds the heatmap based on the stored location.
     */
    @Override
    public void buildHeatMap() {
        this.trackMap.buildHeatMap(
                this.getMapsActivityModel().getPositions(), this.getMapsActivityModel().getPositionsKey());
    }

    /**
     * Displays the year picker.
     */
    @Override
    public void updateYear() {
        this.getYearPicker().show();
    }

    /**
     * @return model data.
     */
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
