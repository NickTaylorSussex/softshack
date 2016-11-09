package org.softshack.trackme;

import android.os.AsyncTask;

import org.softshack.utils.obs.DefaultEvent;
import org.softshack.utils.obs.EventArgs;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DataTask extends AsyncTask<String,Integer,String> {
    private final DefaultEvent<EventArgs> onTaskFinished = new DefaultEvent<EventArgs>();
    private String result;

    @Override
    protected String doInBackground(String... urls) {
        String data = null;

        try {
            URL url = new URL(urls[0]);

            try {
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestProperty("Accept-Encoding", "identity");

                try {
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    ByteArrayOutputStream result = new ByteArrayOutputStream();
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = in.read(buffer)) != -1) {
                        result.write(buffer, 0, length);
                    }

                    data = result.toString("UTF-8");

                } catch (Exception e) {
                     //####
                } finally {
                    urlConnection.disconnect();
                }
            } catch (IOException e){
                //####
            }
        }
        catch(MalformedURLException e){
            //####
        }

        return data;
    }

    @Override
    protected void onCancelled(){
        // Do nothing.
    }

    @Override
    protected void onPostExecute(String result) {
        this.setResult(result);
        this.getOnTaskFinished().fire(this, EventArgs.Empty); //#### remember to replace all new event args with eventargs.empty.
    }

    public DefaultEvent<EventArgs> getOnTaskFinished() {
        return onTaskFinished;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
