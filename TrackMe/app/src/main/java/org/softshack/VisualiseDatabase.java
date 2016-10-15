package org.softshack;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import static org.softshack.Constants.FIRST_COLUMN;
import static org.softshack.Constants.FOURTH_COLUMN;
import static org.softshack.Constants.SECOND_COLUMN;
import static org.softshack.Constants.THIRD_COLUMN;

public class VisualiseDatabase extends Activity {

    private ArrayList<HashMap<String, String>> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualise_database);

        ListView listView = (ListView)findViewById(R.id.listView);
        list = new ArrayList<HashMap<String,String>>();

        HashMap<String,String> temp=new HashMap<String, String>();
            temp.put(FIRST_COLUMN, "Latitude");
            temp.put(SECOND_COLUMN, "Longitude");
            temp.put(THIRD_COLUMN, "Time");
            temp.put(FOURTH_COLUMN, "Sync?");
        list.add(temp);

        HashMap<String,String> temp2=new HashMap<String, String>();
            temp2.put(FIRST_COLUMN, "1.0000");
            temp2.put(SECOND_COLUMN, "1.000");
            temp2.put(THIRD_COLUMN, "12:00");
            temp2.put(FOURTH_COLUMN, "False");
        list.add(temp2);

        ListViewAdapter adapter = new ListViewAdapter(this,list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id)
            {
                int pos=position+1;
                Toast.makeText(VisualiseDatabase.this, Integer.toString(pos)+" Clicked", Toast.LENGTH_SHORT).show();
            }

        });

    }
}
