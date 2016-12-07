package org.softshack.trackme.interfaces;

import org.softshack.utils.obs.DefaultEvent;
import org.softshack.utils.obs.EventArgs;

public interface IDataTask {
    DefaultEvent<EventArgs> getOnTaskFinished();

    DefaultEvent<EventArgs> getOnTaskStarted();

    String getResult();

    void cancel(Boolean mayInterruptIfRunning);
    Boolean isAlreadyCancelled();
    void execute(String param);
}
