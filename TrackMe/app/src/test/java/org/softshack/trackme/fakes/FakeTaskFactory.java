package org.softshack.trackme.fakes;

import org.softshack.trackme.DataTask;
import org.softshack.trackme.interfaces.ITaskFactory;


public class FakeTaskFactory implements ITaskFactory {
    @Override
    public DataTask createMapDataTask() {
        return null;
    }
}
