package org.softshack.trackme.pocos;

import org.softshack.trackme.MapsActivityModel;
import org.softshack.trackme.interfaces.IButton;
import org.softshack.trackme.interfaces.IDialog;
import org.softshack.trackme.interfaces.IScreen;
import org.softshack.trackme.interfaces.ITrackMap;
import org.softshack.utils.log.ILogger;

/**
 * POCO for transporting objects to the view.
 */
public class MapsActivityViewComponents {

    private ILogger logger;
    private MapsActivityModel activityModel;
    private ITrackMap trackMap;
    private IButton yearButton;
    private IDialog yearPicker;
    private IButton historyButton;
    private IScreen graphScreen;

    public MapsActivityViewComponents(
            ILogger logger,
            MapsActivityModel activityModel,
            ITrackMap trackMap,
            IButton yearButton,
            IDialog yearPicker,
            IButton historyButton,
            IScreen graphScreen) {

        this.logger = logger;
        this.activityModel = activityModel;
        this.trackMap = trackMap;
        this.yearButton = yearButton;
        this.yearPicker = yearPicker;
        this.historyButton = historyButton;
        this.graphScreen = graphScreen;
    }

    public MapsActivityModel getActivityModel() {
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

    public IButton getHistoryButton() { return historyButton; }

    public IScreen getGraphScreen() { return graphScreen; }
}
