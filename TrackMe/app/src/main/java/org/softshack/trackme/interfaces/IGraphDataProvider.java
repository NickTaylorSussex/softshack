package org.softshack.trackme.interfaces;

import org.json.JSONException;
import org.softshack.trackme.DataSetGraphMapper;
import org.softshack.utils.obs.DefaultEvent;
import org.softshack.utils.obs.EventArgs;

public interface IGraphDataProvider {
    void cancelLastRequest();

    DefaultEvent<EventArgs> getOnDataChanged();

    DefaultEvent<EventArgs> getOnTaskStarted();

    void requestData(String lookupUrl);

    String getData();

    DataSetGraphMapper convertData() throws JSONException;

    String getGraphDataSetName();
}
