package org.softshack.trackme;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.heatmaps.WeightedLatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.softshack.trackme.interfaces.IContext;
import org.softshack.trackme.interfaces.IDataProvider;
import org.softshack.trackme.interfaces.IDataTask;
import org.softshack.trackme.interfaces.ITaskFactory;
import org.softshack.utils.obs.DefaultEvent;
import org.softshack.utils.obs.EventArgs;
import org.softshack.utils.obs.EventHandler;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class handles task management for data requests.
 */
public class DataProvider implements IDataProvider {

    private final DefaultEvent<EventArgs> onDataChanged = new DefaultEvent<EventArgs>();

    private IDataTask task;
    private ITaskFactory taskFactory;
    private IContext context;
    private DataSetMapperFactory dataSetMapperFactory;
    private String data;

    /**
     * Constructor.
     * @param taskFactory A factory for creating async data tasks.
     * @param context Application context
     */
    public DataProvider(
            ITaskFactory taskFactory,
            IContext context,
            DataSetMapperFactory dataSetMapperFactory){
        this.taskFactory = taskFactory;
        this.context = context;
        this.dataSetMapperFactory = dataSetMapperFactory;
    }

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

    @Override
    public DataSetMapper convertData() throws JSONException{
        if(this.data != null) {
            return this.dataSetMapperFactory.createDataSetMapper(
                    this.readItems(this.data), this.getMapDataSetName());
        }

        return null;
    }

    @Override
    public String getMapDataSetName(){
        return this.context.getString(R.string.title_activity_maps);
    }

    private ArrayList<WeightedLatLng> readItems(String data) throws JSONException {
        ArrayList<WeightedLatLng> list = new ArrayList<WeightedLatLng>();
        String json = new Scanner(data).useDelimiter("\\A").next();
        JSONArray array = new JSONArray(json);

        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);
            double lat = object.getDouble("latitude");
            double lng = object.getDouble("longitude");
            int weight = object.getInt("avgYearPostcodeNorm");
            list.add(new WeightedLatLng(new LatLng(lat, lng), weight));
        }

        return list;
    }
}
