package org.softshack.trackme;


import org.json.JSONException;
import org.softshack.utils.obs.DefaultEvent;
import org.softshack.utils.obs.EventArgs;

public interface IDataProvider {
    void cancelLastRequest();

    DefaultEvent<EventArgs> getOnDataChanged();

    void requestData(String lookupUrl);

    String getData();

    MapDataSet convertData() throws JSONException;

    String getMapDataSetName();
}
