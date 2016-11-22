package org.softshack.trackme;

import com.google.maps.android.heatmaps.WeightedLatLng;

import org.json.JSONException;
import org.softshack.trackme.interfaces.IContext;
import org.softshack.trackme.interfaces.IDataProvider;
import org.softshack.trackme.interfaces.IDataTask;
import org.softshack.trackme.interfaces.ITaskFactory;
import org.softshack.utils.obs.DefaultEvent;
import org.softshack.utils.obs.EventArgs;
import org.softshack.utils.obs.EventHandler;

import java.util.ArrayList;

/**
 * This class handles task management for data requests.
 */
public class DataProvider implements IDataProvider {

    private final DefaultEvent<EventArgs> onDataChanged = new DefaultEvent<EventArgs>();

    private IDataTask task;
    private ITaskFactory taskFactory;
    private IContext context;
    private DataSetMapperFactory dataSetMapperFactory;
    private JSONFactory jsonFactory;
    private String data;

    /**
     * Constructor.
     * @param taskFactory A factory for creating async data tasks.
     * @param context Application context
     */
    public DataProvider(
            ITaskFactory taskFactory,
            IContext context,
            DataSetMapperFactory dataSetMapperFactory,
            JSONFactory jsonFactory){
        this.taskFactory = taskFactory;
        this.context = context;
        this.dataSetMapperFactory = dataSetMapperFactory;
        this.jsonFactory = jsonFactory;
    }

    /**
     * Cancels the last data request if one is pending.
     */
    @Override
    public void cancelLastRequest(){
        if(this.task != null && !this.task.isAlreadyCancelled()){
            this.task.cancel(true);
        }
    }

    @Override
    public DefaultEvent<EventArgs> getOnDataChanged() {
        return onDataChanged;
    }

    /**
     * Makes an async data request and notifies of completion.
     * @param lookupUrl A remote address to use as the basis of the request.
     */
    @Override
    public void requestData(String lookupUrl){
        task = this.taskFactory.createMapDataTask();

        task.getOnTaskFinished().addHandler(new EventHandler<EventArgs>() {
                                                @Override
                                                public void handle(Object sender, EventArgs args) {
                                                    setData(task.getResult());
                                                    handleTaskFinished();
                                                }
                                            });
        task.execute(lookupUrl);
    }

    private void handleTaskFinished() {
        this.getOnDataChanged().fire(this, EventArgs.Empty);
    }

    @Override
    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    /**
     * Converts a JSON object to an ArrayList.
     * @return ArrayList of WeightedLatLong
     * @throws JSONException
     */
    @Override
    public DataSetMapper convertData() throws JSONException{
        if(this.data != null && !this.data.isEmpty()) {
            ArrayList<WeightedLatLng> array = jsonFactory.readItems(this.data);

            return this.dataSetMapperFactory.createDataSetMapper(
                    array, this.getMapDataSetName());
        }

        return null;
    }

    @Override
    public String getMapDataSetName(){
        return this.context.getString(R.string.title_activity_maps);
    }
}
