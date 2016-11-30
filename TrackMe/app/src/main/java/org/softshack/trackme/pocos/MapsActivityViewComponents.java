package org.softshack.trackme.pocos;

import org.softshack.trackme.MapsActivityModel;
import org.softshack.trackme.interfaces.IButton;
import org.softshack.trackme.interfaces.IDialog;
import org.softshack.trackme.interfaces.ITrackMap;
import org.softshack.utils.log.ILogger;

public class MapsActivityViewComponents {

    private ILogger logger;
    private MapsActivityModel mapsActivityModel;
    private ITrackMap trackMap;
    private IButton yearButton;
    private IDialog yearPicker;

    public MapsActivityViewComponents(
            ILogger logger,
            MapsActivityModel mapsActivityModel,
            ITrackMap trackMap,
            IButton yearButton,
            IDialog yearPicker) {

        this.logger = logger;
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

    public ILogger getLogger() { return logger; }
}
