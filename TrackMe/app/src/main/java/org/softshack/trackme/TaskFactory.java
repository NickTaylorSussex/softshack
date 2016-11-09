package org.softshack.trackme;

import org.softshack.trackme.interfaces.ITaskFactory;

public class TaskFactory implements ITaskFactory {
    @Override
    public DataTask createMapDataTask(){
        return new DataTask();
    }
}
