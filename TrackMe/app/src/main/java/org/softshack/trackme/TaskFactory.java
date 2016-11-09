package org.softshack.trackme;

public class TaskFactory implements ITaskFactory {
    @Override
    public DataTask createMapDataTask(){
        return new DataTask();
    }
}
