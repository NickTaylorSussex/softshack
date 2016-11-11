package org.softshack.trackme.interfaces;


import org.json.JSONException;
import org.softshack.trackme.DataSetMapper;
import org.softshack.utils.obs.DefaultEvent;
import org.softshack.utils.obs.EventArgs;

public interface IDataProvider {
    void cancelLastRequest();

    DefaultEvent<EventArgs> getOnDataChanged();

    void requestData(String lookupUrl);

    String getData();

    DataSetMapper convertData() throws JSONException;

    String getMapDataSetName();
}
