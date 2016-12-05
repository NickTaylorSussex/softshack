package org.softshack.trackme;

import org.softshack.trackme.interfaces.IButton;
import org.softshack.trackme.interfaces.IDialog;
import org.softshack.trackme.interfaces.IMapsActivityView;
import org.softshack.trackme.interfaces.IScreen;
import org.softshack.trackme.interfaces.ITrackMap;
import org.softshack.trackme.pocos.MapsActivityViewComponents;
import org.softshack.utils.obs.DefaultEvent;
import org.softshack.utils.obs.EventArgs;
import org.softshack.utils.obs.EventHandler;

public class MapsActivityView implements IMapsActivityView {

    private MapsActivityModel activityModel;
    private ITrackMap trackMap;
    private IButton yearButton;
    private IDialog yearPicker;
    private IButton historyButton;
    private IScreen historyScreen;

    private final DefaultEvent<EventArgs> onDataStale = new DefaultEvent<EventArgs>();
    private final DefaultEvent<EventArgs> onChangeYearRequested = new DefaultEvent<EventArgs>();
    private final DefaultEvent<EventArgs> onHistoryRequested = new DefaultEvent<EventArgs>();

    /**
     * Constructor
     * @param mapsActivityViewComponents Abstracted view components.
     */
    public MapsActivityView(MapsActivityViewComponents mapsActivityViewComponents){
        this.setActivityModel(mapsActivityViewComponents.getActivityModel());
        this.trackMap = mapsActivityViewComponents.getTrackMap();
        this.setYearButton(mapsActivityViewComponents.getYearButton());
        this.setYearPicker(mapsActivityViewComponents.getYearPicker());
        this.setHistoryButton(mapsActivityViewComponents.getHistoryButton());
        this.setHistoryScreen(mapsActivityViewComponents.getGraphScreen());

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
                getActivityModel().setYear(yearPicker.getYear());
                getYearButton().setText(getActivityModel().getYear());
                getOnDataStale().fire(this, EventArgs.Empty);
            }
        });

        // Set the handler for history button clicked and notify listeners.
        this.getHistoryButton().getOnClicked().addHandler(new EventHandler<EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs args) {
                getOnHistoryRequested().fire(this, EventArgs.Empty);
            }
        });
    }

    /**
     * Initial set up for first time map is displayed.
     */
    @Override
    public void initialize(){
        // Enable of disable the centre-map controls.
        this.trackMap.allowUserToCentreMap(this.getActivityModel().getAllowUserToCentreMap());
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

    @Override
    public DefaultEvent<EventArgs> getOnHistoryRequested() { return onHistoryRequested; }

    /**
     * Moves the map to the current coordinates.
     */
    @Override
    public void setMapPositionCurrent() {
        trackMap.setMapPosition(
                this.getActivityModel().getCurrentLatitude(),
                this.getActivityModel().getCurrentLongitude());
    }

    /**
     * Stores the location as at the centre of the map.
     */
    @Override
    public void getMapCentre() {
        TrackLocation mapCentre = trackMap.getMapCentre();
        this.getActivityModel().setCurrentLatitude(mapCentre.getLatitude());
        this.getActivityModel().setCurrentLongitude(mapCentre.getLongitude());
        this.getActivityModel().setCurrentZoom(trackMap.getCurrentZoom());
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
                this.getActivityModel().getPositions(), this.getActivityModel().getPositionsKey());
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
    private MapsActivityModel getActivityModel() {
        return activityModel;
    }

    private void setActivityModel(MapsActivityModel activityModel) {
        this.activityModel = activityModel;
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

    private IButton getHistoryButton() { return historyButton; }

    private void setHistoryButton(IButton historyButton) { this.historyButton = historyButton; }

    @Override
    public void ShowHistory(){
        this.historyScreen.setLocation(
                this.getActivityModel().getCurrentLatitude(),
                this.getActivityModel().getCurrentLongitude());

        this.historyScreen.show();
    }

    private IScreen getHistoryScreen() {
        return historyScreen;
    }

    private void setHistoryScreen(IScreen historyScreen) {
        this.historyScreen = historyScreen;
    }
}
