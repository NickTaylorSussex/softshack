package org.softshack.trackme.interfaces;


import org.json.JSONException;
import org.softshack.trackme.DataSetMapMapper;
import org.softshack.utils.obs.DefaultEvent;
import org.softshack.utils.obs.EventArgs;

public interface IMapDataProvider {
    void cancelLastRequest();

    DefaultEvent<EventArgs> getOnDataChanged();

    void requestData(String lookupUrl);

    String getData();

    DataSetMapMapper convertData() throws JSONException;

    String getMapDataSetName();
}
