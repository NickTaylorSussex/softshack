package org.softshack.trackme;

import org.softshack.trackme.interfaces.IButton;
import org.softshack.trackme.interfaces.IDialog;
import org.softshack.trackme.interfaces.ITrackMap;

public class ViewComponents {

    private MapsActivityModel mapsActivityModel;
    private ITrackMap trackMap;
    private IButton yearButton;
    private IDialog yearPicker;

    public ViewComponents(
        MapsActivityModel mapsActivityModel,
        ITrackMap trackMap,
        IButton yearButton,
        IDialog yearPicker) {

        this.mapsActivityModel = mapsActivityModel;
        this.trackMap = trackMap;
        this.yearButton = yearButton;
        this.yearPicker = yearPicker;
    }

    public MapsActivityModel getMapsActivityModel() {
        return mapsActivityModel;
    }

    public ITrackMap getTrackMap() {
        return trackMap;
    }

    public IButton getYearButton() {
        return yearButton;
    }

    public IDialog getYearPicker() {
        return yearPicker;
    }
}
