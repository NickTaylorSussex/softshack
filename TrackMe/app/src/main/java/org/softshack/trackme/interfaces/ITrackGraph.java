package org.softshack.trackme.interfaces;


import org.softshack.trackme.DataSetGraphMapper;

import java.util.HashMap;

public interface ITrackGraph {
    void clearGraph();

    void refresh();

    void buildGraph(HashMap<String, DataSetGraphMapper> positions, String key);
}
