package org.softshack.trackme;

import android.os.AsyncTask;

import org.softshack.trackme.interfaces.IDataTask;
import org.softshack.utils.obs.DefaultEvent;
import org.softshack.utils.obs.EventArgs;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * A class performs a threaded task to get the remote data.
 */
public class DataTask extends AsyncTask<String,Integer,String> implements IDataTask {
    private final DefaultEvent<EventArgs> onTaskFinished = new DefaultEvent<EventArgs>();
    private String result;

    /**
     * Gets the remote data
     * @param urls
     * @return JSON data
     */
    @Override
    protected String doInBackground(String... urls) {
        assert (urls != null);
        assert (urls.length > 0);

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
                     data = "";
                } finally {
                    urlConnection.disconnect();
                }
            } catch (IOException e){
                data = "";
            }
        }
        catch(MalformedURLException e){
            data = "";
        }

        return data;
    }


    @Override
    protected void onPostExecute(String result) {
        this.setResult(result);
        this.getOnTaskFinished().fire(this, EventArgs.Empty);
    }

    @Override
    public DefaultEvent<EventArgs> getOnTaskFinished() {
        return onTaskFinished;
    }

    @Override
    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public void cancel(Boolean mayInterruptIfRunning){
        super.cancel(mayInterruptIfRunning);
    }

    public Boolean isAlreadyCancelled() {
        return super.isCancelled();
    }

    public void execute(String param) {
        super.execute(param);
    }
}
