package org.softshack.trackme;

import org.softshack.trackme.interfaces.IDataTask;
import org.softshack.trackme.interfaces.ITaskFactory;

public class TaskFactory implements ITaskFactory {
    @Override
    public IDataTask createMapDataTask(){
        return new DataTask();
    }
}
