package org.softshack.trackme;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.heatmaps.WeightedLatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.softshack.trackme.interfaces.IContext;
import org.softshack.trackme.interfaces.IDataProvider;
import org.softshack.trackme.interfaces.ITaskFactory;
import org.softshack.utils.obs.DefaultEvent;
import org.softshack.utils.obs.EventArgs;
import org.softshack.utils.obs.EventHandler;

import java.util.ArrayList;
import java.util.Scanner;

public class DataProvider implements IDataProvider {

    private final DefaultEvent<EventArgs> onDataChanged = new DefaultEvent<EventArgs>();

    private DataTask task;
    private ITaskFactory taskFactory;
    private IContext context;
    private String data;

    public DataProvider(ITaskFactory taskFactory, IContext context){
        this.taskFactory = taskFactory;
        this.context = context;
    }

    @Override
    public void cancelLastRequest(){
        if(this.task != null && !this.task.isCancelled()){
            this.task.cancel(true);
        }
    }

    @Override
    public DefaultEvent<EventArgs> getOnDataChanged() {
        return onDataChanged;
    }

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
    public MapDataSet convertData() throws JSONException{
        if(this.data != null) {
            return new MapDataSet(this.readItems(this.data), this.getMapDataSetName());
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
