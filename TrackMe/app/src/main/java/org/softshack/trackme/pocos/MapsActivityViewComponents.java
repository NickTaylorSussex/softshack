package org.softshack.trackme.pocos;

import org.softshack.trackme.ActivityModel;
import org.softshack.trackme.interfaces.IButton;
import org.softshack.trackme.interfaces.IDialog;
import org.softshack.trackme.interfaces.ITrackMap;
import org.softshack.utils.log.ILogger;

public class MapsActivityViewComponents {

    private ILogger logger;
    private ActivityModel activityModel;
    private ITrackMap trackMap;
    private IButton yearButton;
    private IDialog yearPicker;

    public MapsActivityViewComponents(
            ILogger logger,
            ActivityModel activityModel,
            ITrackMap trackMap,
            IButton yearButton,
            IDialog yearPicker) {

        this.logger = logger;
        this.activityModel = activityModel;
        this.trackMap = trackMap;
        this.yearButton = yearButton;
        this.yearPicker = yearPicker;
    }

    public ActivityModel getActivityModel() {
        return activityModel;
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
