package org.softshack.trackme.interfaces;

import org.softshack.utils.obs.DefaultEvent;
import org.softshack.utils.obs.EventArgs;

public interface IButton {
    DefaultEvent<EventArgs> getOnClicked();

    void setText(String text);
}
